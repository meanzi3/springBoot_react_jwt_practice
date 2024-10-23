package com.example.jwt.controller;

import com.example.jwt.constants.SecurityConstants;
import com.example.jwt.domain.AuthenticationRequest;
import com.example.jwt.prop.JwtProp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

  private final JwtProp jwtProp;

  // 로그인, 토큰 생성
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
    String username = request.getUsername();
    String password = request.getPassword();

    log.info("username : " + username);
    log.info("password : " + password);

    // 임의 사용자 권한
    List<String> roles = new ArrayList<>();
    roles.add("ROLE_USER");
    roles.add("ROLE_ADMIN");

    byte[] signingKey = jwtProp.getSecret().getBytes();

    // 토큰 생성
    String jwt = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512 )  // 시그니처 사용할 비밀키, 알고리즘 설정
            .header()                                                   // 헤더 설정
              .add("typ", SecurityConstants.TOKEN_TYPE)             // typ : JWT
            .and()
            .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) // 토큰 만료 시간 설정 
            .claim("uid", username)                                // PAYLOAD - uid : user
            .claim("rol", roles)                                   // PAYLOAD - rol : [ROLE_USER, ROLE_ADMIN]
            .compact();

    log.info("jwt" + jwt);

    return new ResponseEntity<String>(jwt, HttpStatus.OK);
  }

  // 토큰 해석
  @GetMapping("/user/info")
  public ResponseEntity<?> getUserInfo(@RequestHeader(name="Authorization") String header) {
    log.info("==== header ====");
    log.info("Authorization : " + header);

    String jwt = header.replace(SecurityConstants.TOKEN_PREFIX, "");

    byte[] signingKey = jwtProp.getSecret().getBytes();

    // 토큰 해석
    Jws<Claims> parsedToken = Jwts.parser()
                                .verifyWith(Keys.hmacShaKeyFor(signingKey))
                                .build()
                                .parseSignedClaims(jwt);

    String username = parsedToken.getPayload().get("uid").toString();
    log.info("username : " + username);

    Claims claims = parsedToken.getPayload();
    Object roles = claims.get("rol");
    log.info("roles : " + roles);

    return new ResponseEntity<String>(parsedToken.toString(), HttpStatus.OK);
  }
}
