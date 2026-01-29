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
public class CreateEnderecoResponseDTO {

    private String logradouro;
    private String numero;
    private String bairro;
    private CreateCidadeResponseDTO cidade;

    public CreateEnderecoResponseDTO(Endereco endereco) {
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidade = new CreateCidadeResponseDTO(endereco.getCidade());
    }

}
