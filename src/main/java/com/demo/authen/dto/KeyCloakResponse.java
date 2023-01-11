package com.demo.authen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyCloakResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("grand_type")
    private String grandType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
