package com.example.jwt.constants;

// Security 및 JWT 관련 상수를 관리
public class SecurityConstants {

  // JWT 토큰을 담을 HTTP 요청 헤더 이름
  public static final String TOKEN_HEADER = "Authorization";

  // 헤더의 접두사
  public static final String TOKEN_PREFIX = "Bearer ";

  // 토큰 타입
  public static final String TOKEN_TYPE = "JWT";
}
