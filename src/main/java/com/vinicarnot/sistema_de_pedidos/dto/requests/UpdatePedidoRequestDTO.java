package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePedidoRequestDTO {

    @NotNull(message = "O campo 'enderecoDeEntrega' é obrigatório.")
    private UpdateEnderecoRequestDTO enderecoDeEntrega;

    @NotNull(message = "O campo 'pagamento' é obrigatório.")
    private UpdatePagamentoRequestDTO pagamento;

}
