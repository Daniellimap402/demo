package com.example.demo.service.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegistroAtivoEnum {
    A("ATIVO"),
    I("INATIVO");

    private String value;
}
