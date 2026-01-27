package com.vinicarnot.sistema_de_pedidos.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_cliente")
@NoArgsConstructor
@Getter
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

    @Column(unique = true)
    @Setter
    private String cpfOuCnpj;

    @Setter
    @Enumerated(EnumType.STRING)
    private TipoCliente tipo;

    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role;

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
                   TipoCliente tipo, UserRole role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipo = tipo;
        this.role = role;
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    public void removerTelefone(String numeroTelefone) {
        telefones.removeIf(telefone -> telefone.getNumero().equals(numeroTelefone));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_NORMAL"));
        }
        else {
            return List.of(new SimpleGrantedAuthority("ROLE_NORMAL"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}
