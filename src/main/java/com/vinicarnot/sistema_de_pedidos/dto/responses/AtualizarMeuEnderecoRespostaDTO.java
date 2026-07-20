package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtualizarMeuEnderecoRespostaDTO {

    private String logradouro;

    private String numero;

    private String bairro;

    private LerCidadeRespostaDTO cidade;

    private LerEstadoRespostaDTO estado;

    public AtualizarMeuEnderecoRespostaDTO(Endereco endereco) {
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidade = new LerCidadeRespostaDTO(endereco.getCidade());
        estado = new LerEstadoRespostaDTO(endereco.getCidade().getEstado());
    }

}
