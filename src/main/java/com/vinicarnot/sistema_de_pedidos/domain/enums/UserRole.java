package com.vinicarnot.sistema_de_pedidos.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("Admin"),
    NORMAL("Normal");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

}
