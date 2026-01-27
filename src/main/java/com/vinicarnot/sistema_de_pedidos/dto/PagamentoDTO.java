package com.vinicarnot.sistema_de_pedidos.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vinicarnot.sistema_de_pedidos.entities.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.entities.TipoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "tipoPagamento",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PagamentoBoletoDTO.class, name = "BOLETO"),
        @JsonSubTypes.Type(value = PagamentoCartaoDeCreditoDTO.class, name = "CARTAO_DE_CREDITO")
})
public abstract class PagamentoDTO {

    private Long id;

    private EstadoPagamento estadoPagamento;

    @NotNull(message = "O campo 'Tipo de Pagamento' é obrigatório.")
    private TipoPagamento tipoPagamento;

}
