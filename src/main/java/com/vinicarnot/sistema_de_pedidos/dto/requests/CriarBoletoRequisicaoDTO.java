package com.vinicarnot.sistema_de_pedidos.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CriarBoletoRequisicaoDTO extends CriarPagamentoRequisicaoDTO {

    @NotBlank(message = "O campo 'pagadorCpfOuCnpj' é obrigatório")
    private String pagadorCpfOuCnpj;

    @NotBlank(message = "O campo 'pagadorNome' é obrigatório.")
    private String pagadorNome;

    @NotBlank(message = "O campo 'pagadorEmail' é obrigatório.")
    @Email(message = "Email inválido.")
    private String pagadorEmail;

}
