package com.vinicarnot.sistema_de_pedidos.entities;

import com.vinicarnot.sistema_de_pedidos.dto.TelefoneDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_telefone")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "numero"})
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Telefone(TelefoneDTO dto) {
        id = dto.getId();
        numero = dto.getNumero();
    }

}
