package com.example.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(max = 161)
    private String nome;

    @NotBlank
    @Size(max = 256)
    @Email
    private String email;

    @NotBlank
    @Size(max = 14)
    @Email
    private String documento;

    @NotBlank
    @Size(max = 15)
    private String numTelefone;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 100)
    private String senha;
}
