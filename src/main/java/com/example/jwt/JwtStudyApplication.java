package com.example.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.jwt.prop.JwtProp;

@SpringBootApplication
@EnableConfigurationProperties(JwtProp.class)
public class JwtStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtStudyApplication.class, args);
	}

}
