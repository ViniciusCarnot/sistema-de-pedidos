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
public class CreateEnderecoResponseDTO {

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private CreateCidadeResponseDTO cidade;
    private CreateEstadoResponseDTO estado;

    public CreateEnderecoResponseDTO(Endereco endereco) {
        id = endereco.getId();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidade = new CreateCidadeResponseDTO(endereco.getCidade());
        estado = new CreateEstadoResponseDTO(endereco.getCidade().getEstado());
    }

}
