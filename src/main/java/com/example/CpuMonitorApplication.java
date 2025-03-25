package com.example;


import com.example.config.SshConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SshConfig.class)
public class CpuMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CpuMonitorApplication.class, args);
    }
}