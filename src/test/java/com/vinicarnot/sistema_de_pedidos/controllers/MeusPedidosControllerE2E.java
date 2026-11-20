package com.vinicarnot.sistema_de_pedidos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicarnot.sistema_de_pedidos.util.TokenUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class MeusPedidosControllerE2E {

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUpAll() {

        RestAssured.baseURI = "http://localhost:8081";

    }

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

    }

    @Test
    public void verMeuPedidoDeveriaRetornarPedidoRespostaDTOQuandoClienteNormalLogado() {

        String token = TokenUtil.obtainAccessToken("carlos@email.com", "CCC");
        Long pedidoId = 3L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + token)
                .when()
                    .get("/meus-pedidos/{pedidoId}", pedidoId)
                .then()
                    .statusCode(200)
                    .body("pedidoId", is(3))
                    .body("compradorNome", equalTo("Carlos Alves"))
                    .body("$", hasKey("dataDeEmissao"))
                    .body("pedidoStatus", equalTo("AGUARDANDO_PAGAMENTO"))
                    .body("items.produtoId", hasItems(4))
                    .body("enderecoDeEntrega.id", is(3))
                    .body("enderecoDeEntrega.logradouro", equalTo("Rua das Isis"))
                    .body("enderecoDeEntrega.cidade.id", is(3))
                    .body("enderecoDeEntrega.estado.id", is(3))
                    .body("valorTotal", is(850.00F));

    }

    @Test
    public void verMeuPedidoDeveriaRetornarPedidoRespostaDTOQuandoClienteAdminLogado() {

        String token = TokenUtil.obtainAccessToken("bernardo@email.com", "BBB");
        Long pedidoId = 2L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + token)
                .when()
                    .get("/meus-pedidos/{pedidoId}", pedidoId)
                .then()
                    .statusCode(200)
                    .body("pedidoId", is(2))
                    .body("compradorNome", equalTo("Bernardo Silva"))
                    .body("$", hasKey("dataDeEmissao"))
                    .body("pedidoStatus", equalTo("ENTREGUE"))
                    .body("items.produtoId", hasItems(1, 12))
                    .body("enderecoDeEntrega.id", is(2))
                    .body("enderecoDeEntrega.logradouro", equalTo("Rua Boa Morte"))
                    .body("enderecoDeEntrega.cidade.id", is(2))
                    .body("enderecoDeEntrega.estado.id", is(2))
                    .body("valorTotal", is(3750.00F));

    }

    @Test
    public void verMeuPedidoDeveriaLancar401QuandoClienteNaoAutenticado() {

        Long pedidoId = 2L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer ")
                .when()
                    .get("/meus-pedidos/{pedidoId}", pedidoId)
                .then()
                    .statusCode(401);

    }

    @Test
    public void verMeuPedidoDeveriaLancar404QuandoPedidoIdNaoExiste() {

        String token = TokenUtil.obtainAccessToken("carlos@email.com", "CCC");
        Long pedidoId = 1000L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + token)
                .when()
                    .get("/meus-pedidos/{pedidoId}", pedidoId)
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("Pedido com o id: " + pedidoId + ", não foi encontrado."))
                    .body("path", equalTo("/meus-pedidos/" + pedidoId));

    }

    @Test
    public void verMeuPedidoDeveriaLancar404QuandoClienteNaoDono() {

        String token = TokenUtil.obtainAccessToken("carlos@email.com", "CCC");
        Long pedidoId = 2L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + token)
                .when()
                    .get("/meus-pedidos/{pedidoId}", pedidoId)
                .then()
                    .statusCode(403)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(403))
                    .body("error", equalTo("Você não tem permissão para acessar esse pedido."))
                    .body("path", equalTo("/meus-pedidos/" + pedidoId));

    }

    @Test
    public void verMeusPedidosDeveriaRetornarPedidoRespostaDTOPageQuandoClienteNormalLogado() {

        String token = TokenUtil.obtainAccessToken("carlos@email.com", "CCC");

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + token)
                .when()
                    .get("/meus-pedidos")
                .then()
                    .statusCode(200)
                    .body("content.pedidoId", hasItems(3, 6, 7, 8))
                    .body("content.enderecoDeEntrega.id", hasItems(3))
                    .body("content.enderecoDeEntrega.logradouro", hasItems("Rua das Isis"))
                    .body("content.enderecoDeEntrega.cidade.id", hasItems(3))
                    .body("content.enderecoDeEntrega.estado.id", hasItems(3))
                    .body("content.valorTotal", hasItems(850.00F, 3300.00F, 55.00F, 35.00F));

    }

    @Test
    public void verMeusPedidosDeveriaRetornarPedidoRespostaDTOPageQuandoClienteAdminLogado() {

        String token = TokenUtil.obtainAccessToken("bernardo@email.com", "BBB");

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + token)
                .when()
                    .get("/meus-pedidos")
                .then()
                    .statusCode(200)
                    .body("content.pedidoId", hasItems(2))
                    .body("content.enderecoDeEntrega.id", hasItems(2))
                    .body("content.enderecoDeEntrega.logradouro", hasItems("Rua Boa Morte"))
                    .body("content.enderecoDeEntrega.cidade.id", hasItems(2))
                    .body("content.enderecoDeEntrega.estado.id", hasItems(2))
                    .body("content.valorTotal", hasItems(3750.00F));

    }

    @Test
    public void verMeusPedidosDeveriaRetornarPedidoRespostaDTOPageQuandoExistirFiltrosDePagina() {

        String token = TokenUtil.obtainAccessToken("carlos@email.com", "CCC");

        RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/meus-pedidos?sort=id,desc")
                .then()
                .statusCode(200)
                .body("content.pedidoId[0]", is(8))
                .body("content.pedidoId[3]", is(3));

    }

    @Test
    public void verMeusPedidosDeveriaLancar401QuandoClienteNaoAutenticado() {

        RestAssured
                .given()
                    .header("Authorization", "Bearer ")
                .when()
                    .get("/meus-pedidos")
                .then()
                    .statusCode(401);

    }


}
