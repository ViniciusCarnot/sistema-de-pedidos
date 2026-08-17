package com.vinicarnot.sistema_de_pedidos.factory;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminCriarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminCriarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;

import java.math.BigDecimal;

public class ProdutoFactory {

    public static Produto instanciarProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Iphone 17");
        produto.setPreco(BigDecimal.valueOf(6500.50));
        produto.setDisponibilidade(Disponibilidade.DISPONIVEL);
        produto.setVisibilidade(true);
        return produto;
    }

    public static Produto instanciarProduto2() {
        Produto produto = new Produto();
        produto.setId(2L);
        produto.setNome("Ryzen 7 5700X");
        produto.setPreco(BigDecimal.valueOf(1100.00));
        produto.setDisponibilidade(Disponibilidade.DISPONIVEL);
        produto.setVisibilidade(true);
        return produto;
    }

    public static LerProdutoRespostaDTO instanciarLerProdutoRespostaDTO() {
        return new LerProdutoRespostaDTO(instanciarProduto());
    }

    public static AdminCriarProdutoRespostaDTO instanciarAdminCriarProdutoRespostaDTO() {
        return new AdminCriarProdutoRespostaDTO(instanciarProduto());
    }

}
