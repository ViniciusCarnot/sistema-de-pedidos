package com.vinicarnot.sistema_de_pedidos.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class ProdutoControllerE2E {

    private String clienteNormalToken, clienteAdminToken, tokenInvalido;

    @BeforeAll
    static void setUpAll() {

        RestAssured.baseURI = "http://localhost:8081";

    }

    @Test
    public void lerProdutoDeveriaRetornarLerProdutoRespostaDTO() {

        Long produtoId = 1L;

        RestAssured
                .given()
                .when()
                    .get("/produtos/{idProduto}", produtoId)
                .then()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("nome", equalTo("PC Gamer Entrada"))
                    .body("preco", is(3300.00F))
                    .body("disponibilidade", equalTo("Indisponivel"));

    }

    @Test
    public void lerProdutoDeveriaLancar404QuandoProdutoIdNaoExiste() {

        Long produtoId = 1000L;

        RestAssured
                .given()
                .when()
                    .get("/produtos/{idProduto}", produtoId)
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("Produto com o id: 1000 não foi encontrado."))
                    .body("path", equalTo("/produtos/1000"));

    }

    @Test
    public void lerProdutoDeveriaLancar403QuandoProdutoNaoVisivel() {

        Long produtoId = 3L;

        RestAssured
                .given()
                .when()
                    .get("/produtos/{idProduto}", produtoId)
                .then()
                    .statusCode(403)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(403))
                    .body("error", equalTo("Produto com o id: 3 não está visível para acesso."))
                    .body("path", equalTo("/produtos/3"));

    }

    @Test
    public void lerProdutosDeveriaRetornarLerProdutoRespostaDTOPageQuandoParametroNomePresente() {

        String paramentroNome = "teclado";

        RestAssured
                .given()
                .when()
                    .get("/produtos?nome=" + paramentroNome)
                .then()
                    .body("content.id", hasItems(10, 11, 12))
                    .body("content.nome", hasItems("Teclado Aula F75", "Teclado Mancer", "Teclado Raze"));


    }

    @Test
    public void lerProdutosDeveriaRetornarLerProdutoRespostaDTOPageQuandoParametroNomePresenteEParamentroPrecoMaximoPresente() {

        String paramentroNome = "teclado";
        BigDecimal precoMaximo = BigDecimal.valueOf(350.00);

        RestAssured
                .given()
                .when()
                    .get("/produtos?nome=" + paramentroNome + "&precoMaximo=" + precoMaximo)
                .then()
                    .body("content.id", hasItems(10, 11))
                    .body("content.nome", hasItems("Teclado Aula F75", "Teclado Mancer"));


    }

    @Test
    public void lerProdutosDeveriaRetornarLerProdutoRespostaDTOPageQuandoParametroCategoriasIdsPresente() {

        String categoriasIds = "1,4";

        RestAssured
                .given()
                .when()
                    .get("/produtos?categoriasIds=" + categoriasIds)
                .then()
                    .body("content.id", hasItems(1, 4, 7, 10, 11, 12))
                    .body("content.nome", hasItems("PC Gamer Entrada", "Monitor 27pol QHD 165Hz", "Impressora de Entrada","Teclado Aula F75", "Teclado Mancer"));


    }

}
