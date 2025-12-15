package com.vinicarnot.sistema_de_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class ErroCustomizado {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

}
