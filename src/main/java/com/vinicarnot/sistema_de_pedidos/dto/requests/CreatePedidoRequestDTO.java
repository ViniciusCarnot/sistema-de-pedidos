package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class CreatePedidoRequestDTO {

    @NotEmpty(message = "O pedido deve ter pelo menos um item.")
    private Set<CreateItemPedidoRequestDTO> items = new HashSet<>();

    @NotNull(message = "O campo 'Endereço de Entrega' é obrigatório.")
    private CreateEnderecoRequestDTO enderecoDeEntrega;

    @NotNull(message = "O campo 'Pagamento' é obrigatório.")
    private CreatePagamentoRequestDTO pagamento;

}
