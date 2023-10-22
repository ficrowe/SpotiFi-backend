package com.ficrowe.spotiFi.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ficrowe.spotiFi.constants.SpotifyShortcut;
import com.ficrowe.spotiFi.constants.SpotifyShortcutType;
import com.ficrowe.spotiFi.model.device.Device;
import com.ficrowe.spotiFi.model.device.DeviceKey;
import com.ficrowe.spotiFi.model.firestore.FirestoreUser;
import com.google.cloud.firestore.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestoreDelegate {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired private Firestore firestore;

  private String USERS_PATH = "users";

  private String DEVICES_PATH = "devices";
  private String KEYS_PATH = "keys";

  private String SHORTCUTS_PATH = "shortcuts";

  private ObjectMapper objectMapper = new ObjectMapper();

  public void addUser(String userId) {
    CollectionReference userCollection = firestore.collection(USERS_PATH);
    DocumentReference userReference = userCollection.document(userId);

    FirestoreUser firestoreUser = FirestoreUser.builder().id(userId).build();

    userReference.set(firestoreUser);
  }

  public List<SpotifyShortcut> getShortcutsForUser(String userId) {
    CollectionReference userShortcutsCollection =
        firestore.collection(USERS_PATH + userId + SHORTCUTS_PATH);

    List<SpotifyShortcut> spotifyShortcuts = new ArrayList<SpotifyShortcut>();

    userShortcutsCollection
        .listDocuments()
        .forEach(
            (DocumentReference shortcutDocument) -> {
              try {
                DocumentSnapshot shortcutSnapshot = shortcutDocument.get().get();
                SpotifyShortcut spotifyShortcut =
                    spotifyShortcutFromDocumentSnapshot(shortcutSnapshot);
                spotifyShortcuts.add(spotifyShortcut);
              } catch (Exception e) {
                LOGGER.error(e.toString());
              }
            });

    return spotifyShortcuts;
  }

  public SpotifyShortcut getShortcutForDeviceKey(String deviceId, String deviceKeyId)
      throws ExecutionException, InterruptedException {
    CollectionReference deviceKeysCollection =
        firestore.collection(DEVICES_PATH + "/" + deviceId + "/" + KEYS_PATH);
    DocumentReference deviceKeyReference = deviceKeysCollection.document(deviceKeyId);
    LOGGER.info(deviceKeysCollection.listDocuments().toString());
    LOGGER.info(deviceKeyReference.toString());

    try {
      DocumentSnapshot deviceKeySnapshot = deviceKeyReference.get().get();
      LOGGER.info(deviceKeySnapshot.toString());
      DeviceKey deviceKey = objectMapper.convertValue(deviceKeySnapshot.getData(), DeviceKey.class);
      return deviceKey.getShortcut();
    } catch (Exception exception) {
      LOGGER.error(
          "Unable to get shortcut for device {} and key {}", deviceId, deviceKeyId, exception);
      throw exception;
    }
  }

  public SpotifyShortcut getShortcutForUser(String userId, String shortcutId)
      throws ExecutionException, InterruptedException {
    CollectionReference userShortcutsCollection =
        firestore.collection(USERS_PATH + userId + SHORTCUTS_PATH);
    DocumentReference shortcutReference = userShortcutsCollection.document(shortcutId);

    DocumentSnapshot shortcutSnapshot = shortcutReference.get().get();

    return spotifyShortcutFromDocumentSnapshot(shortcutSnapshot);
  }

  public void setShortcutForUser(String userId, SpotifyShortcut shortcut) {
    CollectionReference userShortcutsCollection =
        firestore.collection(USERS_PATH + userId + SHORTCUTS_PATH);
    DocumentReference shortcutReference = userShortcutsCollection.document(shortcut.getId());

    shortcutReference.set(shortcut);
  }

  //    public void linkDeviceToUser(String userId, String deviceId) {
  //        LOGGER.info("linkDeviceToUser");
  //        CollectionReference usersCollection = firestore.collection(USERS_PATH);
  //        DocumentReference userReference = usersCollection.document(userId);
  //        userReference.update(DEVICES_PATH, FieldValue.arrayUnion(deviceId));
  //    }

  public void linkUserToDevice(String userId, String deviceId, String deviceName) {
    LOGGER.info("linkUserToDevice");
    CollectionReference devicesCollection = firestore.collection(DEVICES_PATH);
    DocumentReference deviceReference = devicesCollection.document(deviceId);
    deviceReference.update("userId", userId);
    deviceReference.update("name", deviceName);
  }

  public String addDevice(Device device) throws ExecutionException, InterruptedException {
    LOGGER.info("addDevice");
    device.setId(null);
    CollectionReference devicesCollection = firestore.collection(DEVICES_PATH);
    DocumentReference deviceReference = devicesCollection.document();
    CollectionReference deviceKeysCollection = deviceReference.collection(KEYS_PATH);
    List<DeviceKey> deviceKeys = device.getKeys();
    deviceKeys.forEach(
        (deviceKey) -> {
          DocumentReference deviceKeyReference = deviceKeysCollection.document(deviceKey.getId());
          deviceKeyReference.set(deviceKey);
        });
    Map<String, Object> deviceMap = new HashMap<>();
    deviceMap.put("type", device.getType());
    deviceMap.put("configuration", device.getConfiguration());
    deviceReference.set(deviceMap);
    return deviceReference.getId();
  }

  public void addKeyToDevice(String deviceId, DeviceKey deviceKey) {
    LOGGER.info("addDeviceKey");
    CollectionReference devicesCollection = firestore.collection(DEVICES_PATH);
    DocumentReference deviceReference = devicesCollection.document(deviceId);
    CollectionReference deviceKeysCollection = deviceReference.collection(KEYS_PATH);
    deviceKeysCollection.document(deviceKey.getId()).set(deviceKey.toMap());
  }

  public List<Device> getUserDevices(String userId)
      throws ExecutionException, InterruptedException {
    LOGGER.info("getUserDevices");

    List<Device> userDevices = new ArrayList<>();

    CollectionReference devicesCollection = firestore.collection(DEVICES_PATH);
    Query userDevicesQuery = devicesCollection.whereEqualTo("userId", userId);
    QuerySnapshot userDevicesSnapshot = userDevicesQuery.get().get();

    userDevicesSnapshot
        .getDocuments()
        .forEach(
            (userDeviceDocument) -> {
              try {
                Device device = getDevice(userDeviceDocument.getId());
                userDevices.add(device);
              } catch (Exception exception) {
                LOGGER.error(
                    "Unable to get device with id: {}", userDeviceDocument.getId(), exception);
              }
            });

    return userDevices;
  }

  private SpotifyShortcut spotifyShortcutFromDocumentSnapshot(DocumentSnapshot shortcutSnapshot) {
    return SpotifyShortcut.builder()
        .id(shortcutSnapshot.getId())
        .type(shortcutSnapshot.get("type", SpotifyShortcutType.class))
        .inputs(shortcutSnapshot.get("inputs", Map.class))
        .build();
  }

  private List<DeviceKey> getDeviceKeys(String deviceId) {
    LOGGER.info("getDeviceKeys {}", deviceId);
    CollectionReference devicesCollection = firestore.collection(DEVICES_PATH);
    DocumentReference deviceReference = devicesCollection.document(deviceId);
    CollectionReference deviceKeysCollection = deviceReference.collection(KEYS_PATH);

    List<DeviceKey> deviceKeys = new ArrayList<>();

    deviceKeysCollection
        .listDocuments()
        .forEach(
            (deviceKeyReference) -> {
              try {
                DocumentSnapshot deviceKeySnapshot = deviceKeyReference.get().get();
                DeviceKey deviceKey =
                    objectMapper.convertValue(deviceKeySnapshot.getData(), DeviceKey.class);
                deviceKey.setId(deviceKeyReference.getId());
                deviceKeys.add(deviceKey);
              } catch (Exception exception) {
                LOGGER.error("Unable to get shortcut", exception);
              }
            });
    return deviceKeys;
  }

  public Device getDevice(String deviceId) throws ExecutionException, InterruptedException {
    LOGGER.info("getDevice {}", deviceId);
    CollectionReference devicesCollection = firestore.collection(DEVICES_PATH);
    DocumentReference deviceReference = devicesCollection.document(deviceId);
    DocumentSnapshot deviceSnapshot = deviceReference.get().get();

    Device device = deviceSnapshot.toObject(Device.class);
    if (device == null) {
      device = Device.builder().id(deviceId).keys(getDeviceKeys(deviceId)).build();
    } else {
      device.setId(deviceId);
      device.setKeys(getDeviceKeys(deviceId));
    }
    return device;
  }
}
