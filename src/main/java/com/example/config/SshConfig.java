package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "ssh")
public record SshConfig(
        String host,
        int port,
        String username,
        String password,
        int connectTimeout
) {}