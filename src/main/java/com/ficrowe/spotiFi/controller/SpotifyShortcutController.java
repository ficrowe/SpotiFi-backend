package com.ficrowe.spotiFi.controller;

import com.ficrowe.spotiFi.constants.SpotifyShortcut;
import com.ficrowe.spotiFi.delegate.SpotifyActionsDelegate;
import com.ficrowe.spotiFi.model.shortcut.DeviceKeyPressRequest;
import com.ficrowe.spotiFi.service.SpotifyShortcutService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortcuts")
public class SpotifyShortcutController {

  @Autowired private SpotifyActionsDelegate spotifyActionsDelegate;

  @Autowired private SpotifyShortcutService spotifyShortcutService;

  @GetMapping("/user/{userId}")
  public List<SpotifyShortcut> getUsersShortcuts(@PathVariable String userId) {
    return spotifyShortcutService.getShortcutsForUser(userId);
  }

  @PostMapping("/user/{userId}")
  public void setUserShortcut(
      @PathVariable String userId, @RequestBody SpotifyShortcut spotifyShortcut) {
    spotifyShortcutService.setShortcutForUser(userId, spotifyShortcut);
  }

  @GetMapping("/execute/{shortcutId}")
  public void executeShortcut(@RequestHeader String userId, @PathVariable String shortcutId)
      throws ExecutionException, InterruptedException {
    spotifyShortcutService.executeShortcutForUser(userId, shortcutId);
  }

  @PostMapping("/execute")
  public void deviceKeyPressed(@RequestBody DeviceKeyPressRequest request)
      throws ExecutionException, InterruptedException {
    spotifyShortcutService.triggerShortcutForDeviceKey(
        request.getDeviceId(), request.getDeviceKeyId());
  }
}
