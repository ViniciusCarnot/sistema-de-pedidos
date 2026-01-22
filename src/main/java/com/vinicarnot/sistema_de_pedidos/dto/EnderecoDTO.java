package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "O campo 'Logradouro' é obrigatório.")
    private String logradouro;

    @NotBlank(message = "O campo 'Número' é obrigatório.")
    private String numero;

    private String bairro;

    private CidadeDTO cidade;

    public EnderecoDTO(Endereco entity) {
        id = entity.getId();
        logradouro = entity.getLogradouro();
        numero = entity.getNumero();
        bairro = entity.getBairro();
        cidade = new CidadeDTO(entity.getCidade());
    }

}
