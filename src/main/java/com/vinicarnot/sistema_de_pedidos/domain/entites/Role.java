package com.vinicarnot.sistema_de_pedidos.domain.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tb_role")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "nome")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
