package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePedidoRequestDTO {

    @NotNull(message = "O campo 'Endereço de Entrega' é obrigatório.")
    private UpdateEnderecoRequestDTO enderecoDeEntrega;

    @NotNull(message = "O campo 'Pagamento' é obrigatório.")
    private UpdatePagamentoRequestDTO pagamento;

}
