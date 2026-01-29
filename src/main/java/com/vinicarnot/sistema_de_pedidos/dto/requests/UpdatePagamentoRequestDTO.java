package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vinicarnot.sistema_de_pedidos.entities.TipoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "tipoPagamento",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateBoletoRequestDTO.class, name = "BOLETO"),
        @JsonSubTypes.Type(value = UpdateCartaoDeCreditoRequestDTO.class, name = "CARTAO_DE_CREDITO")
})
public abstract class UpdatePagamentoRequestDTO {

    @NotNull(message = "O campo 'Tipo de Pagamento' do Pagamento é obrigatório.")
    private TipoPagamento tipoPagamento;

}
