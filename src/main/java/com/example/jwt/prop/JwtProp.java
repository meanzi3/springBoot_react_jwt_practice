package com.example.jwt.prop;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
// properties 파일로부터 properties 바인딩
@ConfigurationProperties( prefix = "com.example.jwt")
public class JwtProp {

  private String secret;
}
