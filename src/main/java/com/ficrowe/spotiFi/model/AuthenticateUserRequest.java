package com.ficrowe.spotiFi.model;

import lombok.Data;

@Data
public class AuthenticateUserRequest {

  private String email;
  private String password;
}
