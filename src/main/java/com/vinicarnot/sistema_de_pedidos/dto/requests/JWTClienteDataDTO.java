package com.vinicarnot.sistema_de_pedidos.dto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWTClienteDataDTO {

    private Long clienteId;
    private String email;

}
