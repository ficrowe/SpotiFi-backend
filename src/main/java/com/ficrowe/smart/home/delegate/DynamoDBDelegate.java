package com.ficrowe.smart.home.delegate;

import com.ficrowe.smart.home.model.SmartLightSettings;
import com.ficrowe.smart.home.model.SmartLightStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DynamoDBDelegate {

    private final String TABLE_NAME = "smart-home";

    @Autowired
    private DynamoDbClient dynamoDB;

    /** LIST LIGHTS **/
    public List<SmartLightSettings> getAllLights() {
        HashMap<String,AttributeValue> keyToGet = new HashMap<String,AttributeValue>();

        keyToGet.put(key, AttributeValue.builder()
                .s(keyVal).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(TABLE_NAME)
                .build();

        try {
            Map<String,AttributeValue> returnedItem = dynamoDB.getItem(request).item();

            if (returnedItem != null) {
                Set<String> keys = returnedItem.keySet();
                System.out.println("Amazon DynamoDB table attributes: \n");

                for (String key1 : keys) {
                    System.out.format("%s: %s\n", key1, returnedItem.get(key1).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", key);
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /** STATUS **/
    public SmartLightStatus getLightStatus(Long deviceId) {
        return SmartLightStatus.ON;
    }

    public void setLightStatus(Long deviceId, SmartLightStatus status) {

    }

    /** TIME ON **/
    public LocalDateTime getTimeOn(Long deviceId) {
        return LocalDateTime.now();
    }

    public void setTimeOn(Long deviceId, LocalDateTime timeOn) {

    }


    /** TIME OFF **/
    public LocalDateTime getTimeOff(Long deviceId) {
        return LocalDateTime.now();
    }

    public void setTimeOff(Long deviceId, LocalDateTime timeOff) {

    }
}
