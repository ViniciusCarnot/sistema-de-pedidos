package com.vinicarnot.sistema_de_pedidos.domain.entites;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoPK that = (ItemPedidoPK) o;

        // Compara os IDs dos objetos sem inicializar os proxies inteiros
        Long produtoIdThis = (this.produto != null) ? this.produto.getId() : null;
        Long produtoIdThat = (that.produto != null) ? that.produto.getId() : null;

        Long pedidoIdThis = (this.pedido != null) ? this.pedido.getId() : null;
        Long pedidoIdThat = (that.pedido != null) ? that.pedido.getId() : null;

        return Objects.equals(produtoIdThis, produtoIdThat) &&
                Objects.equals(pedidoIdThis, pedidoIdThat);
    }

    @Override
    public int hashCode() {
        // Usa apenas os IDs (ou valores padrão se forem nulos) para não disparar SELECT
        Long produtoId = (this.produto != null) ? this.produto.getId() : null;
        Long pedidoId = (this.pedido != null) ? this.pedido.getId() : null;
        return Objects.hash(produtoId, pedidoId);
    }

}
