package com.vinicarnot.sistema_de_pedidos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vinicarnot.sistema_de_pedidos.entities.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O campo 'Pagamento' é obrigatório.")
    private PagamentoDTO pagamento;

    @NotNull(message = "O campo 'Endereço de Entrega' é obrigatório.")
    private EnderecoDTO enderecoDeEntrega;

    @NotEmpty(message = "O pedido deve ter pelo menos um item.")
    private List<ItemPedidoDTO> items = new ArrayList<>();

    public PedidoDTO(Pedido entity) {
        id = entity.getId();
        instanteDaCompra = entity.getInstanteDaCompra();
        cliente = new ClienteDTO(entity.getCliente());
        if(entity.getPagamento() instanceof Boleto boleto) {
            PagamentoBoletoDTO boletoDTO = new PagamentoBoletoDTO();
            boletoDTO.setId(boleto.getId());
            boletoDTO.setTipoPagamento(boleto.getTipoPagamento());
            boletoDTO.setEstadoPagamento(boleto.getEstadoPagamento());
            boletoDTO.setDataVencimento(boleto.getDataVencimento());
            boletoDTO.setDataPagamento(boleto.getDataPagamento());
            pagamento = boletoDTO;
        } else if (entity.getPagamento() instanceof CartaoDeCredito cartaoDeCredito) {
            PagamentoCartaoDeCreditoDTO cartaoDeCreditoDTO = new PagamentoCartaoDeCreditoDTO();
            cartaoDeCreditoDTO.setId(cartaoDeCredito.getId());
            cartaoDeCreditoDTO.setTipoPagamento(cartaoDeCredito.getTipoPagamento());
            cartaoDeCreditoDTO.setEstadoPagamento(cartaoDeCredito.getEstadoPagamento());
            cartaoDeCreditoDTO.setQuantidadeDeParcelas(cartaoDeCredito.getQuantidadeParcelas());
            pagamento = cartaoDeCreditoDTO;
        }
        enderecoDeEntrega = new EnderecoDTO(entity.getEnderecoDeEntrega());
        for(ItemPedido itemPedido : entity.getItemsPedidos()) {
            items.add(new ItemPedidoDTO(itemPedido));
        }
    }

    @JsonProperty("valorTotal")
    public BigDecimal getValorTotal() {
        BigDecimal total = BigDecimal.valueOf(0);
        for(ItemPedidoDTO dto : items) {
            total = total.add(dto.getSubTotal());
        }
        return total;
    }
}
