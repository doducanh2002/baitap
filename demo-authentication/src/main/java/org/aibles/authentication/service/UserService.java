package org.aibles.authentication.service;

import org.aibles.authentication.dto.reponse.UserResponseDTO;
import org.aibles.authentication.dto.request.LoginRequest;
import org.aibles.authentication.dto.request.SignUpRequest;

public interface UserService {

//  UserResponseDTO create(SignUpRequest signUpRequest);
  UserResponseDTO login(LoginRequest loginRequest);

  void register(SignUpRequest request);

  void logout(String authorizationHeader);

}
