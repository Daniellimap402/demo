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
    private Long id;
    private String username;
    private String email;
    private String documento;
    private String numTelefone;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, String documento, String numTelefone, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.documento = documento;
        this.numTelefone = numTelefone;
        this.roles = roles;
    }
}
