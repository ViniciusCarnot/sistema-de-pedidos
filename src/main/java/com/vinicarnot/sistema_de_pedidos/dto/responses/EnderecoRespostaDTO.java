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
public class EnderecoRespostaDTO {

    private Long id;
    private String logradouro;
    private String numero;
    private String bairro;
    private CidadeRespostaDTO cidade;
    private EstadoRespostaDTO estado;

    public EnderecoRespostaDTO(Endereco endereco) {
        id = endereco.getId();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        cidade = new CidadeRespostaDTO(endereco.getCidade());
        estado = new EstadoRespostaDTO(endereco.getCidade().getEstado());
    }

}
