package com.ficrowe.spotiFi.util;

import com.ficrowe.spotiFi.model.SpotifyTokens;
import java.io.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TokenFileUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenFileUtil.class);

  private static final String ACCESS_TOKEN = "accessToken";
  private static final String REFRESH_TOKEN = "refreshToken";
  private static final String COLON = ": ";
  private static final String tokenFileName = "src/main/resources/tokenFile.txt";

  public static SpotifyTokens readTokenFile() throws IOException {
    LOGGER.info("Reading token file...");

    BufferedReader reader = new BufferedReader(new FileReader(tokenFileName));

    List<String> lines = reader.lines().toList();

    reader.close();

    if (!lines.isEmpty()) {
      String accessToken = lines.get(0).split(ACCESS_TOKEN + COLON)[1];
      String refreshToken = lines.get(1).split(REFRESH_TOKEN + COLON)[1];
      return SpotifyTokens.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
    throw new IOException("File " + tokenFileName + " is empty", new Throwable());
  }

  public static void writeTokenFile(String accessToken, String refreshToken) throws IOException {
    LOGGER.info("Writing to token file...");

    BufferedWriter writer = new BufferedWriter(new FileWriter(tokenFileName));
    writer.write(ACCESS_TOKEN + COLON + accessToken);
    writer.newLine();
    writer.write(REFRESH_TOKEN + COLON + refreshToken);
    writer.close();
  }
}
