package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadEnderecoResponseDTO {

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private ReadCidadeResponseDTO cidade;
    private ReadEstadoResponseDTO estado;

    public ReadEnderecoResponseDTO(Endereco endereco) {
        id = endereco.getId();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidade = new ReadCidadeResponseDTO(endereco.getCidade());
        estado = new ReadEstadoResponseDTO(endereco.getCidade().getEstado());
    }

}
