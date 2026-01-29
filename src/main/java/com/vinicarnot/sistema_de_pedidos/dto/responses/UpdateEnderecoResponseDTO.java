package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.entities.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEnderecoResponseDTO {

    private String logradouro;
    private String numero;
    private String bairro;
    private UpdateCidadeResponseDTO cidade;
    private UpdateEstadoResponseDTO estado;

    public UpdateEnderecoResponseDTO(Endereco endereco) {
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidade = new UpdateCidadeResponseDTO(endereco.getCidade());
        estado = new UpdateEstadoResponseDTO(endereco.getCidade().getEstado());
    }

}
