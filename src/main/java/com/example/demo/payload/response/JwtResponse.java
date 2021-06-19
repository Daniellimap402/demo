package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String documento;
    private List<String> roles;

    public JwtResponse(String accessToken, String username, String email, String documento, List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.email = email;
        this.documento = documento;
        this.roles = roles;
    }
}
