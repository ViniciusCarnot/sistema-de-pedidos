package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtualizarTelefoneRequisicaoDTO {

    @NotBlank(message = "O campo 'numero' é obrigatório.")
    @Size(min = 15, max = 15, message = "O campo 'numero' deve ter 15 caracteres '(XX) XXXXX-XXXX'.")
    private String numero;

}
