package com.vinicarnot.sistema_de_pedidos.domain.entites;

import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_pedido")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Setter
    private Instant instanteDaCompra;

    @Enumerated(EnumType.STRING)
    @Setter
    private StatusPedido statusPedido;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @Setter
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "enderecoDeEntrega_id")
    @Setter
    private Endereco enderecoDeEntrega;

    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemPedido> itemsPedidos = new HashSet<>();

    public Pedido(Long id, Instant instanteDaCompra, StatusPedido statusPedido, Pagamento pagamento, Cliente cliente, Endereco enderecoDeEntrega) {
        this.id = id;
        this.instanteDaCompra = instanteDaCompra;
        this.statusPedido = statusPedido;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public List<Produto> getProdutos() {
        return itemsPedidos.stream().map(itemPedido -> itemPedido.getProduto()).toList();
    }

}
