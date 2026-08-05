package com.vinicarnot.sistema_de_pedidos.dto.requests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoPagamento", // Campo discriminador do JSON
        visible = true // Mantém a propriedade visível para popular o atributo Java, e assim realizar a validação do campo
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CriarBoletoRequisicaoDTO.class, name = "BOLETO"),
        @JsonSubTypes.Type(value = CriarCartaoDeCreditoRequisicaoDTO.class, name = "CARTAO_DE_CREDITO")
})
public abstract class CriarPagamentoRequisicaoDTO {

    @NotNull(message = "O campo 'tipoPagamento' é obrigatório.")
    private TipoPagamento tipoPagamento;

}
