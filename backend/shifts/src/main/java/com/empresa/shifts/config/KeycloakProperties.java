package com.empresa.shifts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "keycloak")
public record KeycloakProperties(
    String baseUrl,
    String realm,
    String adminClientId,
    String adminClientSecrect
) {
    
}
