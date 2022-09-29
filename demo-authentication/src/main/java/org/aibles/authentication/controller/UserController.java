package org.aibles.authentication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.authentication.dto.reponse.UserResponseDTO;
import org.aibles.authentication.dto.request.LoginRequest;
import org.aibles.authentication.dto.request.SignUpRequest;
import org.aibles.authentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService service;

//  @PostMapping
//  @ResponseStatus(HttpStatus.CREATED)
//  public void create(@RequestBody @Validated LoginRequest loginRequest) {
//    service.create(loginRequest);
//  }

//  @PostMapping
//  @ResponseStatus(HttpStatus.CREATED)
//  public ResponseEntity<UserResponseDTO> create(@RequestBody @Validated SignUpRequest signUpRequest){
//    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(signUpRequest));
//  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<UserResponseDTO> login(@RequestBody @Validated LoginRequest loginRequest) {
    return ResponseEntity.status(HttpStatus.OK).body(service.login(loginRequest));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String register(@RequestBody @Valid SignUpRequest request) {
    log.info("(register)username : {}", request.getUsername());
    service.register(request);
    return "Register successfully!!!";
  }


  @GetMapping("/hello")
  @ResponseStatus(HttpStatus.OK)
  public String greeting() {
    return"Hello";
  }


  @GetMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public String logout(@RequestHeader(name = "Authorization") String authorizationHeader) {
    log.info("(logout)header : {}", authorizationHeader);
    service.logout(authorizationHeader);
    return "Logout successfully!!!";
  }
}
