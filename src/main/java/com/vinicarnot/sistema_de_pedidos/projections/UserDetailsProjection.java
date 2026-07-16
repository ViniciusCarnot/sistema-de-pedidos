package com.vinicarnot.sistema_de_pedidos.projections;

public interface UserDetailsProjection {

    String getUsername();
    String getSenha();
    Long getRoleId();
    String getRoleNome();
    boolean getAtivo();

}
