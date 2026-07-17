package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class AdminCriarCategoriaRequisicaoDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    private String nome;

    private Set<AdminCriarCategoriaProdutoRequisicaoDTO> produtos = new HashSet<>();

}
