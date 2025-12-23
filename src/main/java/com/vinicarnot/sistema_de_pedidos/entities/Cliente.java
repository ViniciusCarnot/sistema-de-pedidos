package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_cliente")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "email"})
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    private String nome;

    @Column(unique = true)
    @Setter
    private String email;

    private String senha;

    @Column(unique = true)
    @Setter
    private String cpfOuCnpj;

    @Setter
    private TipoCliente tipo;

    //private UserRole role;

    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tb_cliente_endereco",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private Set<Endereco> enderecos = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new HashSet<>();

    public Cliente(Long id, String nome, String email, String senha, String cpfOuCnpj,
                   TipoCliente tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo;
        //this.role = role;
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    public void removerTelefone(String numeroTelefone) {
        telefones.removeIf(telefone -> telefone.getNumero().equals(numeroTelefone));
    }

}
