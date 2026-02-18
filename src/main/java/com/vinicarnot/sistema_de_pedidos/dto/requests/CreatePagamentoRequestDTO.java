package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
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
        @JsonSubTypes.Type(value = CreateBoletoRequestDTO.class, name = "BOLETO"),
        @JsonSubTypes.Type(value = CreateCartaoDeCreditoRequestDTO.class, name = "CARTAO_DE_CREDITO")
})
public abstract class CreatePagamentoRequestDTO {

    @NotNull(message = "O campo 'tipoPagamento' do Pagamento é obrigatório.")
    private TipoPagamento tipoPagamento;

}
