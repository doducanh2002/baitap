package org.up.finance.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.up.finance.service.AuthenticationTokenService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {
  private final Long accessTokenLifeTime;
  private final String accessTokenJwtSecret;
  private final Long refreshTokenLifeTime;
  private final String refreshTokenJwtSecret;

  public AuthenticationTokenServiceImpl(
        Long accessTokenLifeTime,
        String accessTokenJwtSecret,
        Long refreshTokenLifeTime,
        String refreshTokenJwtSecret
  ) {
    this.accessTokenLifeTime = accessTokenLifeTime;
    this.accessTokenJwtSecret = accessTokenJwtSecret;
    this.refreshTokenLifeTime = refreshTokenLifeTime;
    this.refreshTokenJwtSecret = refreshTokenJwtSecret;
  }

  @Override
  public String generateAccessToken(
        String userId,
        String email,
        String username,
        String fullName
  ) {
    log.info(
          "(generateAccessToken)userId: {}, email: {}, username: {}, fullName: {}",
          userId,
          email,
          username,
          fullName
    );

    var claims = buildClaims(username, email, fullName);
    return generateToken(userId, claims, accessTokenLifeTime, accessTokenJwtSecret);
  }

  @Override
  public String generateRefreshToken(
        String userId,
        String email,
        String username,
        String fullName
  ) {
    log.info(
          "(generateRefreshToken)userId: {}, email: {}, username: {}, fullName: {}",
          userId,
          email,
          username,
          fullName
    );
    var claims = buildClaims(username, email, fullName);
    return generateToken(userId, claims, refreshTokenLifeTime, refreshTokenJwtSecret);
  }

  @Override
  public String getSubjectFromAccessToken(String accessToken) {
    log.debug("(getSubjectFromAccessToken)accessToken: {}", accessToken);
    return getClaim(accessToken, Claims::getSubject, accessTokenJwtSecret);
  }

  @Override
  public boolean validateAccessToken(String accessToken, String userId) {
    log.debug("(validateAccessToken)accessToken: {}, userId: {}", accessToken, userId);
    return getSubjectFromAccessToken(accessToken).equals(userId)
          && !isExpiredToken(accessToken, accessTokenJwtSecret);
  }

  private HashMap<String, Object> buildClaims(
        String username,
        String email,
        String fullName
  ) {
    log.info(
          "(buildClaims) username: {}, email: {}, fullName: {}",
          username,
          email,
          fullName
    );
    var claims = new HashMap<String, Object>();
    claims.put("username", username);
    claims.put("email", email);
    claims.put("fullName", fullName);
    return claims;
  }

  private String generateToken(
        String subject,
        Map<String, Object> claims,
        long tokenLifeTime,
        String jwtSecret
  ) {
    log.info(
          "(generateToken)subject : {}, claims : {}, tokenLifeTime : {}, jwtSecret : {}",
          subject,
          claims,
          tokenLifeTime,
          jwtSecret);

    return Jwts.builder()
          .setHeader(header())
          .setClaims(claims)
          .setSubject(subject)
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + tokenLifeTime))
          .signWith(SignatureAlgorithm.HS256, jwtSecret)
          .compact();
  }

  private Map<String, Object> header() {
    Map<String, Object> map = new HashMap<>();
    map.put("typ", "JWT");
    return map;
  }

  private boolean isExpiredToken(String token, String secretKey) {
    return getClaim(token, Claims::getExpiration, secretKey).before(new Date());
  }

  private <T> T getClaim(String token, Function<Claims, T> claimsResolve, String secretKey) {
    log.info("(getClaim)token : {}, claimsResolve : {}, secretKey : {}", token, claimsResolve, secretKey);
    return claimsResolve.apply(getClaims(token, secretKey));
  }

  private Claims getClaims(String token, String secretKey) {
    log.info("(getClaims)token : {}, secretKey : {}", token, secretKey);
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

}
