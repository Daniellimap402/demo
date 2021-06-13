package com.example.demo.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class PessoaDTO implements Serializable {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String senha;

    @Email
    private String email;

    @NotBlank
    private String cpfCnpj;

    @NotBlank
    private String numTelefone;

}
