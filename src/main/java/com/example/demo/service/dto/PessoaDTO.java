package com.example.demo.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class PessoaDTO implements Serializable {

    private Long id;

    @NotBlank
    @Size(max = 161)
    private String nome;

    @NotBlank
    @Size(max = 256)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 100)
    private String senha;
}
