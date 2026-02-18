package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class UpdateCategoriaRequestDTO {

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    private String nome;

    @NotEmpty(message = "É obrigatório ter pelo menos um produto na lista.")
    private Set<UpdateProdutoCategoriaRequestDTO> produtos = new HashSet<>();

}
