package com.example.demo.service.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegistroAtivoEnum {
    S("Sim"),
    N("Nao");

    private String value;
}
