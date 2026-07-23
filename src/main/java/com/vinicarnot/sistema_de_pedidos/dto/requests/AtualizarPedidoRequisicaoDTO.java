package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtualizarPedidoRequisicaoDTO {

    @NotNull(message = "O campo 'enderecoDeEntregaId' é obrigatório.")
    private Long enderecoDeEntregaId;

}
