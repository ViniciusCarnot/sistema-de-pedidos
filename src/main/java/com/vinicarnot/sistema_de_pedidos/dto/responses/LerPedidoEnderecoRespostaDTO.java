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
public class LerPedidoEnderecoRespostaDTO {

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private LerPedidoEnderecoCidadeRespostaDTO cidade;
    private LerPedidoEnderecoEstadoRespostaDTO estado;

    public LerPedidoEnderecoRespostaDTO(Endereco endereco) {
        id = endereco.getId();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidade = new LerPedidoEnderecoCidadeRespostaDTO(endereco.getCidade());
        estado = new LerPedidoEnderecoEstadoRespostaDTO(endereco.getCidade().getEstado());
    }

}
