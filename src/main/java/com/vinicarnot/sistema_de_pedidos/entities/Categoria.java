package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "tb_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "tb_categoria_produto",
            joinColumns = @JoinColumn(name = "tb_categoria"),
            inverseJoinColumns = @JoinColumn(name = "tb_produto")
    )
    private Set<Produto> produtos;

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionaProdutos(Produto produto) {
        produtos.add(produto);
    }

    public void removeProdutos(String nomeProduto) {
        produtos.removeIf(produto -> produto.getNome().equalsIgnoreCase(nomeProduto));
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Categoria categoria)) return false;

        return id.equals(categoria.id) && nome.equals(categoria.nome);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        return result;
    }
}
