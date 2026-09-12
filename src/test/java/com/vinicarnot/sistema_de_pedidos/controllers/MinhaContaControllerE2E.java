package com.vinicarnot.sistema_de_pedidos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicarnot.sistema_de_pedidos.util.TokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class MinhaContaControllerE2E {

    private static String clienteNormalToken, clienteAdminToken, tokenInvalido;
    private static Long clienteNormalId, clienteAdminId;
    private static String clienteNormalEmail, clienteAdminEmail;
    private static String clienteNormalSenha, clienteAdminSenha;
    private static String clienteNormalNome, clienteAdminNome;
    private static String clienteNormalCpfOuCnpj, clienteAdminCpfOnCnpj;
    private static String clienteNormalTelefoneNumero, clienteAdminTelefoneNumero;
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUpAll() {

        RestAssured.baseURI = "http://localhost:8081";

        clienteNormalId = 3L;
        clienteAdminId = 1L;

        clienteNormalEmail = "carlos@email.com";
        clienteAdminEmail = "alberto@email.com";

        clienteNormalSenha = "CCC";
        clienteAdminSenha = "AAA";

        clienteNormalNome = "Carlos Alves";
        clienteAdminNome = "Alberto Rodrigues";

        clienteNormalCpfOuCnpj = "418.502.610-33";
        clienteAdminCpfOnCnpj = "842.107.660-31";

        clienteNormalTelefoneNumero = "(33) 33333-3333";
        clienteAdminTelefoneNumero = "(11) 11111-1111";

        clienteNormalToken = TokenUtil.obtainAccessToken(clienteNormalEmail, clienteNormalSenha);
        clienteAdminToken = TokenUtil.obtainAccessToken(clienteAdminEmail, clienteAdminSenha);
        tokenInvalido = "";

    }

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

    }

    @Test
    public void verMinhaContaDeveriaRetornarLerMinhaContaRespostaDTOQuandoClienteNormalLogado() {

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                .when()
                    .get("/minha-conta")
                .then()
                    .statusCode(200)
                    .body("id", is(clienteNormalId.intValue()))
                    .body("nome", equalTo(clienteNormalNome))
                    .body("email", equalTo(clienteNormalEmail))
                    .body("cpfOuCnpj", equalTo(clienteNormalCpfOuCnpj))
                    .body("tipo", equalTo("PESSOA_FISICA"))
                    .body("telefone.numero", equalTo(clienteNormalTelefoneNumero));

    }

    @Test
    public void verMinhaContaDeveriaRetornarLerMinhaContaRespostaDTOQuandoClienteAdminLogado() {

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                .when()
                    .get("/minha-conta")
                .then()
                    .statusCode(200)
                    .body("id", is(clienteAdminId.intValue()))
                    .body("nome", equalTo(clienteAdminNome))
                    .body("email", equalTo(clienteAdminEmail))
                    .body("cpfOuCnpj", equalTo(clienteAdminCpfOnCnpj))
                    .body("tipo", equalTo("PESSOA_FISICA"))
                    .body("telefone.numero", equalTo(clienteAdminTelefoneNumero));

    }

    @Test
    public void verMinhaContaDeveriaLancar401QuandoClienteNaoAutenticado() {

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + tokenInvalido)
                    .header("Content-Type", "application/json")
                .when()
                    .get("/minha-conta")
                .then()
                    .statusCode(401);

    }

    @Test
    public void verMeusEnderecosDeveriaRetornarLerEnderecoRespostaDTOListaQuandoClienteNormalLogado() {

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                .when()
                    .get("/minha-conta/enderecos")
                .then()
                    .statusCode(200)
                    .body("logradouro", hasItems("Rua das Isis", "Avenida Jaraguá"))
                    .body("numero", hasItems("77", "36"));

    }

    @Test
    public void verMeusEnderecosDeveriaRetornarLerEnderecoRespostaDTOListaQuandoClienteAdminLogado() {

        RestAssured
                .given()
                .header("Authorization", "Bearer " + clienteAdminToken)
                .header("Content-Type", "application/json")
                .when()
                .get("/minha-conta/enderecos")
                .then()
                .statusCode(200)
                .body("logradouro", hasItems("Avenida Floriano", "Rua Boa Morte"))
                .body("numero", hasItems("54C", "1459"));

    }

    @Test
    public void verMeusEnderecosDeveriaLancar401QuandoClienteNaoAutenticado() {

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + tokenInvalido)
                    .header("Content-Type", "application/json")
                .when()
                    .get("/minha-conta/enderecos")
                .then()
                    .statusCode(401);

    }

}
