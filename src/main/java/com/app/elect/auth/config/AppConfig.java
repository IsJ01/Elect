package com.app.elect.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AuthConfiguration.class)
public class AppConfig {

}

