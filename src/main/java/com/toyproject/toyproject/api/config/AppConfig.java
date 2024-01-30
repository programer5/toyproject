package com.toyproject.toyproject.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "hodolman")
public class AppConfig {

    public String hello;

}
