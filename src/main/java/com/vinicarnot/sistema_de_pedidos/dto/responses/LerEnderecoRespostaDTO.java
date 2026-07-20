package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LerEnderecoRespostaDTO {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cidadeNome;
    private String estadoNome;

    public LerEnderecoRespostaDTO(Endereco endereco) {
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidadeNome = endereco.getCidade().getNome();
        estadoNome = endereco.getCidade().getEstado().getNome();
    }

}
