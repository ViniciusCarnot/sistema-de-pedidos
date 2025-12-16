package com.vinicarnot.sistema_de_pedidos.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErroCustomizadoValidacao extends ErroCustomizado {

    private List<CampoCustomizadoErro> campos = new ArrayList<>();

    public ErroCustomizadoValidacao(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void adicionarErro(String nomeCampo, String mensagem) {
        campos.removeIf(campoCustomizadoErro -> campoCustomizadoErro.getNomeCampo().equals(nomeCampo));
        campos.add(new CampoCustomizadoErro(nomeCampo, mensagem));
    }
}
