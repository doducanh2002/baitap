package org.up.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.up.finance.configuration.FinanceServiceTestConfiguration;
import org.up.finance.configuration.RedisConfiguration;
import org.up.finance.dto.request.ActiveUserRequest;
import org.up.finance.dto.request.LoginRequest;
import org.up.finance.dto.request.UserRegisterRequest;
import org.up.finance.dto.response.LoginResponse;
import org.up.finance.dto.response.UserRegisterResponse;
import org.up.finance.facade.AuthenticationFacadeService;
import org.up.finance.security.JwtAuthenticationFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AuthenticationController authenticationController;

  @MockBean
  private AuthenticationFacadeService authenticationFacadeService;

  @MockBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  private UserRegisterRequest mockUserRegisterRequestValid() {
    return UserRegisterRequest.from(
          "dajktatj123@gmail.com",
          "luatnq",
          "Nguyen Quoc Luat",
          "Luatnq@123",
          "Luatnq@123"
    );
  }

  private ActiveUserRequest mockActiveUserRequestValid() {
    return ActiveUserRequest.of(
          "dajktatj123@gmail.com",
          "1234"
    );
  }

  private UserRegisterResponse mockUserRegisterResponse(UserRegisterRequest request) {
    return UserRegisterResponse.of(
          "id",
          request.getEmail(),
          request.getUsername(),
          request.getFullName(),
          false
    );
  }

  private LoginRequest mockLoginRequest() {
    return LoginRequest.of(
          "luatnq@gmail.com",
          "Luat@123"
    );
  }

  private LoginResponse mockLoginResponse() {
    return LoginResponse.builder()
          .accessToken("access-token")
          .refreshToken("refresh-token")
          .accessTokenLifeTime(60000L)
          .refreshTokenLifeTime(600000L)
          .build();
  }

  @Test
  public void testUserRegisterApi_WhenInputValid_Return201AndResponseBody() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();

    Mockito.when(authenticationFacadeService.register(request)).
          thenReturn(mockUserRegisterResponse(request));
    MvcResult mvcResult = mockMvc
          .perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andReturn();
    int status = mvcResult.getResponse().getStatus();
    String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(201, status);
    Assertions.assertEquals(responseBody, objectMapper.writeValueAsString(authenticationController.register(request)));
  }

  @Test
  public void testUserRegisterApi_WhenUsernameMalformed_ReturnBadRequestException() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();
    request.setUsername("!@");

    mockMvc.perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testUserRegisterApi_WhenUsernameBlank_ReturnBadRequestException() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();
    request.setUsername("");

    mockMvc.perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testUserRegisterApi_WhenEmailMalformed_ReturnBadRequestException() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();
    request.setEmail("finance");

    mockMvc.perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testUserRegisterApi_WhenEmailBlank_ReturnBadRequestException() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();
    request.setEmail("");

    mockMvc.perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testUserRegisterApi_WhenPasswordMalformed_ReturnBadRequestException() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();
    request.setPassword("finance");

    mockMvc.perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testUserRegisterApi_WhenPasswordBlank_ReturnBadRequestException() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();
    request.setPassword("");

    mockMvc.perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testUserRegisterApi_WhenPasswordConfirmNotMatch_ReturnBadRequestException() throws Exception {
    UserRegisterRequest request = mockUserRegisterRequestValid();
    request.setPassword("Luatnq@1234");

    mockMvc.perform(post("/api/v1/auth/users/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testActiveUserApi_WhenInputValid_Return200() throws Exception {
    ActiveUserRequest request = mockActiveUserRequestValid();

    Mockito.doNothing().when(authenticationFacadeService).activeUser(request);

    mockMvc.perform(post("/api/v1/auth/users/active")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk());
  }

  @Test
  public void testActiveUserApi_OTPInValid_ReturnBadRequestException() throws Exception {
    ActiveUserRequest request = mockActiveUserRequestValid();
    request.setOtp("#213");

    Mockito.doNothing().when(authenticationFacadeService).activeUser(request);

    mockMvc.perform(post("/api/v1/auth/users/active")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testActiveUserApi_EmailInValid_ReturnBadRequestException() throws Exception {
    ActiveUserRequest request = mockActiveUserRequestValid();
    request.setEmail("#213");

    Mockito.doNothing().when(authenticationFacadeService).activeUser(request);

    mockMvc.perform(post("/api/v1/auth/users/active")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testLoginApi_WhenInputValid_Return200AndResponseBody() throws Exception {
    LoginRequest request = mockLoginRequest();

    Mockito.when(authenticationFacadeService.login(request)).
          thenReturn(mockLoginResponse());

    MvcResult mvcResult = mockMvc
          .perform(post("/api/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andReturn();
    int status = mvcResult.getResponse().getStatus();

    Assertions.assertEquals(200, status);
    Assertions.assertNotNull(objectMapper.writeValueAsString(authenticationController.login(request)));
  }

  @Test
  public void testLoginApi_WhenEmailMalFormat_ThrowBadRequest() throws Exception {
    LoginRequest request = mockLoginRequest();
    request.setEmail("email.test");

    Mockito.when(authenticationFacadeService.login(request)).
          thenReturn(mockLoginResponse());


    mockMvc.perform(post("/api/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

  @Test
  public void testLoginApi_WhenPasswordMalFormat_ThrowBadRequest() throws Exception {
    LoginRequest request = mockLoginRequest();
    request.setPassword("");

    Mockito.when(authenticationFacadeService.login(request)).
          thenReturn(mockLoginResponse());

    mockMvc.perform(post("/api/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
  }

}
