package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class AdminAtualizarCategoriaRequisicaoDTO {

    @Setter
    @NotBlank(message = "O campo 'nome' é obrigatório.")
    private String nome;

    private Set<AdminAtualizarCategoriaProdutoRequisicaoDTO> produtos = new HashSet<>();

}
