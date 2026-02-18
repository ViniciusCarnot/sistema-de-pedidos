package com.vinicarnot.sistema_de_pedidos.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CampoCustomizadoErro {

    private String nomeCampo;
    private String mensagem;

}
