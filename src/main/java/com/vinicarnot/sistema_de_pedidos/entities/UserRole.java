package com.vinicarnot.sistema_de_pedidos.entities;

public enum UserRole {
    ADMIN("ADMIN"),
    NORMAL("NORMAL");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
