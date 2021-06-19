package com.example.demo.domain;

import com.example.demo.service.enumerations.PermissaoEnum;
import com.example.demo.service.enumerations.RegistroAtivoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "TB_PESSOA")
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PESSOA")
    @SequenceGenerator(name = "SEQ_PESSOA", sequenceName = "SEQ_PESSOA", allocationSize = 1)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 161)
    private String nome;

    @Column(name = "SENHA", nullable = false, length = 100)
    private String senha;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 256)
    private String email;

    @Column(name = "DOCUMENTO", nullable = false, unique = true, length = 14)
    private String documento;

    @Column(name = "NUM_TELEFONE", unique = true, length = 15)
    private String numTelefone;

    @Column(name = "REGISTRO_ATIVO", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private RegistroAtivoEnum registroAtivo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TB_PERMISSAO_USUARIO")
    @Enumerated(EnumType.STRING)
    private List<PermissaoEnum> roles;

}
