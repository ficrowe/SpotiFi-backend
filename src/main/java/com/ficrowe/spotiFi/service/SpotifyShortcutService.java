package com.ficrowe.spotiFi.service;

import com.ficrowe.spotiFi.constants.SpotifyShortcut;
import com.ficrowe.spotiFi.constants.SpotifyShortcutArgument;
import com.ficrowe.spotiFi.delegate.FirestoreDelegate;
import com.ficrowe.spotiFi.delegate.SpotifyActionsDelegate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotifyShortcutService {

  @Autowired private SpotifyActionsDelegate spotifyActionsDelegate;

  @Autowired private FirestoreDelegate firestoreDelegate;

  public List<SpotifyShortcut> getShortcutsForUser(String userId) {
    // Get shortcut & arguments from firestore
    List<SpotifyShortcut> spotifyShortcuts = firestoreDelegate.getShortcutsForUser(userId);

    return spotifyShortcuts;
  }

  //    public List<SpotifyShortcut> getShortcuts() {
  //        // Get shortcut & arguments from firestore
  //        List<SpotifyShortcut> spotifyShortcuts =
  // firestoreDelegate.getShortcutsForUser(authenticationService.getUserId());
  //
  //        return spotifyShortcuts;
  //    }

  public void setShortcutForUser(String userId, SpotifyShortcut spotifyShortcut) {
    firestoreDelegate.setShortcutForUser(userId, spotifyShortcut);
  }

  public void executeShortcutForUser(String userId, String shortcutId)
      throws ExecutionException, InterruptedException {
    // Get shortcut & arguments from firestore
    SpotifyShortcut spotifyShortcut = firestoreDelegate.getShortcutForUser(userId, shortcutId);

    // Execute shortcut using spotifyApi
    executeShortcutType(spotifyShortcut);
  }

  public void triggerShortcutForDeviceKey(String deviceId, String deviceKeyId)
      throws ExecutionException, InterruptedException {
    // get shortcut for device key
    SpotifyShortcut spotifyShortcut =
        firestoreDelegate.getShortcutForDeviceKey(deviceId, deviceKeyId);

    // Execute shortcut
    executeShortcutType(spotifyShortcut);
  }

  //    public void executeShortcut(String shortcutId) throws ExecutionException,
  // InterruptedException {
  //        // Get shortcut & arguments from firestore
  //        SpotifyShortcut spotifyShortcut =
  // firestoreDelegate.getShortcutForUser(authenticationService.getUserId(), shortcutId);
  //
  //        // Execute shortcut using spotifyApi
  //        executeShortcutType(spotifyShortcut);
  //    }

  private void executeShortcutType(SpotifyShortcut spotifyShortcut) {
    switch (spotifyShortcut.getType()) {
      case ADD_CURRENT_TRACK_TO_PLAYLIST -> {
        spotifyActionsDelegate.addCurrentTrackToPlaylist(
            spotifyShortcut.getInputs().get(SpotifyShortcutArgument.PLAYLIST));
      }
      case REMOVE_CURRENT_TRACK_FROM_PLAYLIST -> {
        spotifyActionsDelegate.removeCurrentTrackFromPlaylist(
            spotifyShortcut.getInputs().get(SpotifyShortcutArgument.PLAYLIST),
            spotifyShortcut.getInputs().get(SpotifyShortcutArgument.SNAPSHOT_ID));
      }
      case PLAY -> {
        spotifyActionsDelegate.play();
      }
      case PAUSE -> {
        spotifyActionsDelegate.pause();
      }
      case NEXT -> {
        spotifyActionsDelegate.nextTrack();
      }
      case PREVIOUS -> {
        spotifyActionsDelegate.previousTrack();
      }
    }
  }
}
