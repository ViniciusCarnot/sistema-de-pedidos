package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_endereco")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "logradouro", "numero", "bairro", "cidade"})
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    private String logradouro;

    @Setter
    private String numero;

    @Setter
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    @Setter
    private Cidade cidade;

    @ManyToMany(mappedBy = "enderecos")
    private Set<Cliente> clientes = new HashSet<>();

    @OneToMany(mappedBy = "enderecoDeEntrega")
    private Set<Pedido> pedidos = new HashSet<>();

    public Endereco(Long id, String logradouro, String numero, String bairro, Cidade cidade) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
    }
}
