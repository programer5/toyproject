package com.toyproject.toyproject.api.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.util.Base64;


@Getter
@ConfigurationProperties(prefix = "hodolman")
public class AppConfig {

    private SecretKey jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKey));
    }

}
