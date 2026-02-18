package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadClienteResponseDTO {

    private Long id;
    private String email;
    private String nome;
    private String tipoPessoa;

    public ReadClienteResponseDTO(Cliente cliente) {
        id = cliente.getId();
        email = cliente.getEmail();
        nome = cliente.getNome();
        tipoPessoa = cliente.getTipo().getTipo();
    }

}
