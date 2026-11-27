package com.backend.hotelbooking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.StandardApiVersionDeprecationHandler;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.time.ZonedDateTime;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        StandardApiVersionDeprecationHandler deprecation = new StandardApiVersionDeprecationHandler();
        deprecation.configureVersion("1")
                .setDeprecationDate(ZonedDateTime.parse("2026-12-01T00:00:00+05:30"))
                .setSunsetDate(ZonedDateTime.parse("2027-06-30T00:00:00+05:30"))
                .setDeprecationLink(URI.create("https://hotelbooking.example.com/docs/migrate-to-v2"));

        configurer
                .useRequestHeader("X-API-Version")
                .addSupportedVersions("1", "2")
                .setDefaultVersion("1")
                .setDeprecationHandler(deprecation);
    }
}