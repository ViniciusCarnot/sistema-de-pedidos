package com.vinicarnot.sistema_de_pedidos.dto.responses;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Boleto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CriarBoletoRespostaDTO extends CriarPagamentoRespostaDTO {

    private String pagadorCpfOuCnpj;

    private String pagadorNome;

    private String pagadorEmail;

    private String codigoDeBarras;

    private LocalDate dataVencimento;

    private Instant dataPagamento;

    public CriarBoletoRespostaDTO(Boleto boleto) {
        super(boleto.getId(), boleto.getTipoPagamento(), boleto.getEstadoPagamento());
        pagadorCpfOuCnpj = boleto.getPagadorCpfOuCnpj();
        pagadorNome = boleto.getPagadorNome();
        pagadorEmail = boleto.getPagadorEmail();
        codigoDeBarras = boleto.getCodigoDeBarras();
        dataVencimento = boleto.getDataVencimento();
    }

}
