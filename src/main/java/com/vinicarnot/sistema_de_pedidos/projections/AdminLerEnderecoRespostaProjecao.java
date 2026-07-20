package com.vinicarnot.sistema_de_pedidos.projections;

public interface AdminLerEnderecoRespostaProjecao {

    Long getEnderecoId();
    String getLogradouro();
    String getNumero();
    String getBairro();
    Long getCidadeId();
    String getCidadeNome();
    Long getEstadoId();
    String getEstadoNome();

}
