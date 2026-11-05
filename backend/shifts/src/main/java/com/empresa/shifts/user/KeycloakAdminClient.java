package com.empresa.shifts.user;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.attribute.standard.Media;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.empresa.shifts.config.KeycloakProperties;
import com.empresa.shifts.shared.exception.BusinessRuleException;

@Component 
public class KeycloakAdminClient {
    
    private static final int ACTIVATION_LINK_SECONDS = 3 * 24 * 3600; // link válido por 3 dias

    private final KeycloakProperties props;
    private final RestClient http;

    private String token;
    private Instant tokenExpiresAt = Instant.EPOCH;

    public KeycloakAdminClient(KeycloakProperties props) {
        this.props = props;
        this.http = RestClient.builder().baseUrl(props.baseUrl()).build();
    }

    /* Cira usuário e devolve o UUID ( o "sub" dos tokens dele). */
    public UUID createUser(String email, String firstName, String lastName ) {
        try {
            ResponseEntity<Void> response = http.post()
                .uri("/admin/realms/{realm}/users", props.realm())
                .headers(h -> h.setBearerAuth(accessToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                    "username", email,
                    "email", email,
                    "fistName", firstName,
                    "lastName", lastName,
                    "enable", true,
                    "emailVerified",false))
                .retrieve()
                .toBodilessEntity();

            // O Keycloak devolve o id no cabeçaho Location: ../users/<uuid>
            String location = response.getHeaders().getLocation().toString();
            return UUID.fromString(location.substring(location.lastIndexOf('/' )+1));
        } catch (HttpClientErrorException.Conflict e) {
            throw new BusinessRuleException("Já existe uma conta com este e-mail no provedor de identidade");
        }
    }

    public void assignRealmRole(UUID userId, String roleName ) {
        Map<String, Object> role = http.get()
            .uri("/admin/realms/{realm}/roles/{name}", props.realm(), roleName)
            .headers(h -> h.setBearerAuth(accessToken()))
            .retrieve()
            .body(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    /* Dispara o e-mail "degina sua senha e confirme seu e-mail" */
    public void sendActivationEmail(UUID userId) {
        http.put()
            .uri("/admin/realms/{realm}/users/{id}/execute-actions-email?lifespan={s}",
                props.realm(), userId, ACTIVATION_LINK_SECONDS)
            .headers(h -> h.setBearerAuth(accessToken()))
            .contentType(MediaType.APPLICATION_JSON)
            .body(List.of("UPDATE_PASSWORD","VERIFY_EMAIL"))
            .retrieve()
            .toBodilessEntity();
    }

    public void deleteUser(UUID userId) {
        http.delete()
            .uri("/admin/realms/{realm}/users/{id}",props.realm(), userId)
            .headers(h -> h.setBearerAuth(accessToken()))
            .retrieve()
            .toBodilessEntity();
    }
 
    /* Token do próprio backend (client, creadentials), reaproveitado até perto de expirar */
    private synchronized String accessToken() {
        if (Instant.now().isBefore(tokenExpiresAt)) {
            return token;
        }

        MultiValueMap<String, String> from = new LinkedMultiValueMap<>();
        from.add("grant_type", "client_credentials");
        from.add("client_id", props.adminClientId());
        from.add("client_sercret", props.adminClientSecrect());

        Map<String, Object> response = http.post()
            .uri("/realms/{realm}/protocol/openid-connect/token", props.realm())
            .contentType(MediaType.APPLICATION_JSON)
            .body(from)
            .retrieve()
            .body(new ParameterizedTypeReference<Map<String,Object>>() {});

        token = (String) response.get("access_token");
        int expiresIn = ((Number) response.get("expires_ind")).intValue();
        tokenExpiresAt = Instant.now().plusSeconds(expiresIn -30); // margem de 30segundos
        return token;
    }

}
