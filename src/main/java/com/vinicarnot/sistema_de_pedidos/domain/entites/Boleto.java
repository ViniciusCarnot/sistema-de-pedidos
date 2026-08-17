package com.vinicarnot.sistema_de_pedidos.domain.entites;

import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "tb_boleto")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("BOLETO")
public class Boleto extends Pagamento {

    public Boleto(Long id, EstadoPagamento estadoPagamento, TipoPagamento tipoPagamento, Pedido pedido, String pagadorCpfOuCnpj, String pagadorNome, String pagadorEmail, String codigoDeBarras, LocalDate dataVencimento) {
        super(id, estadoPagamento, tipoPagamento, pedido);
        this.pagadorCpfOuCnpj = pagadorCpfOuCnpj;
        this.pagadorNome = pagadorNome;
        this.pagadorEmail = pagadorEmail;
        this.codigoDeBarras = codigoDeBarras;
        this.dataVencimento = dataVencimento;
    }

    @Column(nullable = false)
    private String pagadorCpfOuCnpj;

    @Column(nullable = false)
    private String pagadorNome;

    @Column(nullable = false)
    private String pagadorEmail;

    @Column(nullable = false)
    private String codigoDeBarras;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate dataVencimento;

    @Column(columnDefinition = "DATE")
    private Instant dataPagamento;

}
