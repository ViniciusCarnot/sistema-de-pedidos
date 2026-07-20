package com.vinicarnot.sistema_de_pedidos.projections;

public interface UserDetailsProjecao {

    String getUsername();
    String getSenha();
    Long getRoleId();
    String getRoleNome();
    boolean getAtivo();

}
