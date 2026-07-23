package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class CriarPedidoRequisicaoDTO {

    @NotEmpty(message = "O pedido deve ter pelo menos um item.")
    private List<CriarPedidoItemPedidoRequisicaoDTO> items = new ArrayList<>();

    @NotNull(message = "O campo 'enderecoDeEntregaId' é obrigatório.")
    private Long enderecoDeEntregaId;

}
