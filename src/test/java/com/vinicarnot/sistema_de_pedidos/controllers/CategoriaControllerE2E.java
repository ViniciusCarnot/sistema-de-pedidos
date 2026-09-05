package com.vinicarnot.sistema_de_pedidos.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class CategoriaControllerE2E {

    @BeforeAll
    static void setUpAll() {

        RestAssured.baseURI = "http://localhost:8081";

    }

    @Test
    public void lerCategoriasDeveriaRetornarLerCategoriaRespostaDTOPage() {

        RestAssured
                .given()
                .when()
                    .get("/categorias")
                .then()
                    .statusCode(200)
                    .body("content.id", hasItems(1, 2, 3, 4, 5))
                    .body("content.nome", hasItems("PC", "Jogos", "Jogos Antigos", "Eletrônicos", "Monitores"));

    }

    @Test
    public void lerCategoriaDeveriaRetornarLerCategoriaRespostaDTOQuandoCategoriaIdExiste() {

        String categoriaId = String.valueOf(1);

        RestAssured
                .given()
                .when()
                    .get("/categorias/{idCategoria}", categoriaId)
                .then()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("nome", equalTo("PC"));

    }

    @Test
    public void lerCategoriaDeveriaLancar404QuandoCategoriaIdNaoExiste() {

        String categoriaId = String.valueOf(1000);

        RestAssured
                .given()
                .when()
                    .get("/categorias/{idCategoria}", categoriaId)
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("Categoria com o id: " + categoriaId + ", não encontrada."))
                    .body("path", equalTo("/categorias/" + categoriaId));

    }

    @Test
    public void lerProdutosDeUmaCategoriaDeveriaRetornarLerProdutoRespostaDTOPageQuandoCategoriaIdExiste() {

        String categoriaId = String.valueOf(2);

        RestAssured
                .given()
                .when()
                    .get("/categorias/{idCategoria}/produtos", categoriaId)
                .then()
                    .statusCode(200)
                    .body("content.nome", hasItems("GTA San Andreas", "Uncharted 4", "Need For Speed Most Wanted [2005] PS2"));

    }

    @Test
    public void lerProdutosDeUmaCategoriaDeveriaLancar404QuandoCategoriaIdNaoExiste() {

        String categoriaId = String.valueOf(1000);

        RestAssured
                .given()
                .when()
                    .get("/categorias/{idCategoria}/produtos", categoriaId)
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("Categoria com o id: " + categoriaId + ", não encontrada."))
                    .body("path", equalTo("/categorias/" + categoriaId + "/produtos"));

    }

}
