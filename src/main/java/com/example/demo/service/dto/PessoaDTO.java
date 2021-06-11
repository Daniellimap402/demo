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

    @Email
    private String email;

    @NotBlank
    private String cpf;

    @NotBlank
    private String cartao;
}
