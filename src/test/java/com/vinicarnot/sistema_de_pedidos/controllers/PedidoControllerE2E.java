package com.vinicarnot.sistema_de_pedidos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoItemPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.factory.ClienteFactory;
import com.vinicarnot.sistema_de_pedidos.util.TokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class PedidoControllerE2E {

    private static String clienteNormalEmail, clienteAdminEmail;
    private static String clienteNormalSenha, clienteAdminSenha;
    private static String clienteNormalToken, clienteAdminToken, tokenInvalido;
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUpAll() {

        RestAssured.baseURI = "http://localhost:8081";

        clienteNormalEmail = "carlos@email.com";
        clienteNormalSenha = "CCC";

        clienteAdminEmail = "alberto@email.com";
        clienteAdminSenha = "AAA";

        clienteNormalToken = TokenUtil.obtainAccessToken(clienteNormalEmail, clienteNormalSenha);
        clienteAdminToken = TokenUtil.obtainAccessToken(clienteAdminEmail, clienteAdminSenha);
        tokenInvalido = "xxx";

    }

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

    }

    @Test
    public void realizarPedidoDeveriaRetornarPedidoRespostaDTOQuandoClienteNormalLogado() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(4L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(201)
                    .body("$", hasKey("pedidoId"))
                    .body("compradorNome", equalTo("Carlos Alves"))
                    .body("$", hasKey("dataDeEmissao"))
                    .body("pedidoStatus", equalTo("AGUARDANDO_PAGAMENTO"))
                    .body("items.produtoId", hasItems(2, 4))
                    .body("enderecoDeEntrega.id", is(3))
                    .body("$", hasKey("valorTotal"));

    }

    @Test
    public void realizarPedidoDeveriaRetornarPedidoRespostaDTOQuandoClienteAdminLogado() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(4L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(1L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(201)
                    .body("$", hasKey("pedidoId"))
                    .body("compradorNome", equalTo("Alberto Rodrigues"))
                    .body("$", hasKey("dataDeEmissao"))
                    .body("pedidoStatus", equalTo("AGUARDANDO_PAGAMENTO"))
                    .body("items.produtoId", hasItems(2, 4))
                    .body("enderecoDeEntrega.id", is(1))
                    .body("$", hasKey("valorTotal"));

    }

    @Test
    public void realizarPedidoDeveriaLancar422QuandoClienteNormalLogadoEEnderecoDeEntregaIdDaRequisicaoNulo() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(4L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(null);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos"))
                    .body("campos.nomeCampo", hasItem("enderecoDeEntregaId"))
                    .body("campos.mensagem", hasItem("O campo 'enderecoDeEntregaId' é obrigatório."));

    }

    @Test
    public void realizarPedidoDeveriaLancar422QuandoItemsDaRequisicaoVazio() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos"))
                    .body("campos.nomeCampo", hasItem("items"))
                    .body("campos.mensagem", hasItem("O pedido deve ter pelo menos um item."));

    }

    @Test
    public void realizarPedidoDeveriaLancar422QuandoAlgumProdutoIdDaRequisicaoNulo() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(null);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos"))
                    .body("campos.nomeCampo", hasItem("items[1].produtoId"))
                    .body("campos.mensagem", hasItem("O campo 'produtoId' do Produto, da lista 'items' é obrigatório."));

    }

    @Test
    public void realizarPedidoDeveriaLancar422QuandoAlgumaQuantidadeDaRequisicaoNula() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(3L);
        itemPedidoDTORequisicao2.setQuantidade(null);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos"))
                    .body("campos.nomeCampo", hasItem("items[1].quantidade"))
                    .body("campos.mensagem", hasItem("O campo 'quantidade' do Produto, da lista 'items' é obrigatório."));

    }

    @Test
    public void realizarPedidoDeveriaLancar422QuandoAlgumaQuantidadeDaRequisicaoForNegativa() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(3L);
        itemPedidoDTORequisicao2.setQuantidade(-1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos"))
                    .body("campos.nomeCampo", hasItem("items[1].quantidade"))
                    .body("campos.mensagem", hasItem("O valor do campo 'quantidade' do Produto ,da lista 'items' deve ser positivo."));

    }

    @Test
    public void realizarPedidoDeveriaLancar422QuandoAlgumaQuantidadeDaRequisicaoForZero() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(0);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(3L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos"))
                    .body("campos.nomeCampo", hasItem("items[0].quantidade"))
                    .body("campos.mensagem", hasItem("O valor do campo 'quantidade' do Produto ,da lista 'items' deve ser positivo."));

    }

    @Test
    public void realizarPedidoDeveriaLancar401QuandoClienteNaoAutenticado() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(4L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + tokenInvalido)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(401);

    }

    @Test
    public void realizarPedidoDeveriaLancar404QuandoAlgumProdutoIdNaoExiste() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(100L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(4L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("Recurso não encontrado no nosso banco de dados."))
                    .body("path", equalTo("/pedidos"));

    }

    @Test
    public void realizarPedidoDeveriaLancar404QuandoAlgumProdutoNaoVisivel() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(3L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("Produto com o id: " + 3 + ", não foi encontrado."))
                    .body("path", equalTo("/pedidos"));

    }

    @Test
    public void realizarPedidoDeveriaLancar400QuandoAlgumProdutoIndisponivel() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(1L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(3L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(400)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(400))
                    .body("error", equalTo("Produto com id: " + 1 + ", está esgotado."))
                    .body("path", equalTo("/pedidos"));

    }

    @Test
    public void realizarPedidoDeveriaLancar404QuandoEnderecoIdNaoExiste() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(4L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(100L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("Endereço de entrega com o id: " + 100 + ", não foi encontrado."))
                    .body("path", equalTo("/pedidos"));

    }

    @Test
    public void realizarPedidoDeveriaLancar404QuandoEnderecoNaoCadastrado() throws JsonProcessingException {

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao1 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao1.setProdutoId(2L);
        itemPedidoDTORequisicao1.setQuantidade(1);

        CriarPedidoItemPedidoRequisicaoDTO itemPedidoDTORequisicao2 = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoDTORequisicao2.setProdutoId(4L);
        itemPedidoDTORequisicao2.setQuantidade(1);

        dtoRequisicao.getItems().add(itemPedidoDTORequisicao1);
        dtoRequisicao.getItems().add(itemPedidoDTORequisicao2);
        dtoRequisicao.setEnderecoDeEntregaId(1L);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                    .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(404)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(404))
                    .body("error", equalTo("O endereço de entrega informado não foi previamente cadastrado."))
                    .body("path", equalTo("/pedidos"));

    }



}
