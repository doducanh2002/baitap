package org.aibles.authentication.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.authentication.repository.UserRepository;
import org.aibles.authentication.utils.Base64Encoding;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {

  private static final String URI_API_LOGIN = "/api/v1/users/login";
  private static final String URI_API_REGISTRY = "/api/v1/users";
  private static final String HTTP_METHOD_POST = "POST";
  private static final String STRING_SEPARATOR = " ";
  private static final String KEY_REQUEST_JWT_FROM_HEADER = "Bearer";
  private static final int EMAIL_INDEX = 0;
  private final UserRepository repository;

  /**
   * preHandle sẽ thực hiện việc kiểm tra mã thông báo đầu vào bằng cách giải mã jwt sau đó lấy
   * email . từ email ta lấy user by email sau đó check xem mã thông báo trong db có giống với mã thông báo hiện tại không
   * nếu bằng thì request đến controller
   * nếu không bằng thì trả về 401
   *
   * preHandle sẽ mở các request login và create user
   *
   * @param request  current HTTP request
   * @param response current HTTP response
   * @param handler  chosen handler to execute, for type and/or instance evaluation
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (HTTP_METHOD_POST.equalsIgnoreCase(request.getMethod()) && request.getRequestURI()
        .equals(URI_API_LOGIN)) {
      return true;
    }

    if (HTTP_METHOD_POST.equalsIgnoreCase(request.getMethod()) && request.getRequestURI()
        .equals(URI_API_REGISTRY)) {
      return true;
    }

    var jwt = request.getHeader(KEY_REQUEST_JWT_FROM_HEADER);
    var username = Base64Encoding.decrypt(jwt).split(STRING_SEPARATOR)[EMAIL_INDEX];
    var user = repository.findByUsername(username)
        .orElse(null);

    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception exception) throws Exception {
  }

}
