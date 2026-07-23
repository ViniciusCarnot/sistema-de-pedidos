package com.vinicarnot.sistema_de_pedidos.domain.entites;

import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_cliente")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "email"})
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Setter
    private String nome;

    @Column(unique = true)
    @Setter
    private String email;

    @Setter
    private String senha;

    @Setter
    private String cpfOuCnpj;

    @Enumerated(EnumType.STRING)
    @Setter
    private TipoCliente tipo;

    @Column(nullable = false)
    @Setter
    private boolean ativo;

    @ManyToMany
    @JoinTable(name = "tb_cliente_role",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tb_cliente_endereco",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private Set<Endereco> enderecos = new HashSet<>();

    @Setter
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Telefone telefone;

    public Cliente(Long id, String nome, String email, String senha, String cpfOuCnpj,
                   TipoCliente tipo, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    public void adicionarRole(Role role) {
        roles.add(role);
    }

    public boolean temRole(String nomeRole) {
        for(Role role : roles) {
            if(role.getAuthority().equals(nomeRole)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}
