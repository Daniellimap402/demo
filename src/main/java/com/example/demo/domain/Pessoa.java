package com.example.demo.domain;

import com.example.demo.service.enumerations.RegistroAtivoEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "TB_PESSOA")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "CARTAO")
    private String cartao;

    @Column(name = "REGISTRO_ATIVO")
    private RegistroAtivoEnum registroAtivo;

}
