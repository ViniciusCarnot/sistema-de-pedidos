package com.vinicarnot.sistema_de_pedidos.projections;

public interface LerEnderecoRespostaProjecao {

    Long getEnderecoId();
    String getLogradouro();
    String getNumero();
    String getBairro();
    String getCidadeNome();
    String getEstadoNome();

}
