package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class AdminCriarCategoriaRequisicaoDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Setter
    private String nome;

    private Set<Long> produtos = new HashSet<>();

}
