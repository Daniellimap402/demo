package com.example.demo.service.enumerations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum PermissaoEnum {
    USUARIO("USUARIO"),
    ADMIN("ADMIN");

    private String value;
}
