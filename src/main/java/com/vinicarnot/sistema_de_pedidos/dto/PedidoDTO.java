package com.vinicarnot.sistema_de_pedidos.dto;

import com.vinicarnot.sistema_de_pedidos.entities.ItemPedido;
import com.vinicarnot.sistema_de_pedidos.entities.Pagamento;
import com.vinicarnot.sistema_de_pedidos.entities.Pedido;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PedidoDTO {

    private Long id;

    private Instant instanteDaCompra;

    private ClienteDTO cliente;

    private PagamentoDTO pagamento;

    private EnderecoDTO enderecoDeEntrega;

    @NotEmpty(message = "O pedido deve ter pelo menos um item.")
    private List<ItemPedidoDTO> items = new ArrayList<>();

    public PedidoDTO(Long id, Instant instanteDaCompra, ClienteDTO cliente, PagamentoDTO pagamento,
    EnderecoDTO enderecoDeEntrega) {
        this.id = id;
        this.instanteDaCompra = instanteDaCompra;
        this.cliente = cliente;
        this.pagamento = pagamento;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public PedidoDTO(Pedido entity) {
        id = entity.getId();
        instanteDaCompra = entity.getInstanteDaCompra();
        cliente = new ClienteDTO(entity.getCliente());
        enderecoDeEntrega = new EnderecoDTO(entity.getEnderecoDeEntrega());
        for(ItemPedido itemPedido : entity.getItemsPedidos()) {
            items.add(new ItemPedidoDTO(itemPedido));
        }
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.valueOf(0);
        for(ItemPedidoDTO dto : items) {
            total = total.add(dto.getSubTotal());
        }
        return total;
    }
}
