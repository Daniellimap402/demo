package com.example.demo.domain;

import com.example.demo.service.enumerations.PermissoesEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PERMISSAO")
@Getter
@Setter
public class Permissao {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERMISSAO")
    @SequenceGenerator(name = "SEQ_PERMISSAO", sequenceName = "SEQ_PERMISSAO", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PermissoesEnum nome;
}