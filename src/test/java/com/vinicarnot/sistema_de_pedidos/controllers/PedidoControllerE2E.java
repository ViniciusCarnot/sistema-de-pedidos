package com.vinicarnot.sistema_de_pedidos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.factory.ClienteFactory;
import com.vinicarnot.sistema_de_pedidos.util.TokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

public class PedidoControllerE2E {

    private static String clienteNormalEmail, clienteAdminEmail;
    private static String clienteNormalSenha, clienteAdminSenha;
    private static String clienteNormalToken, clienteAdminToken, tokenInvalido;
    private static String clienteNormalCpfOuCnpj, clienteAdminCpfOuCnpj;
    private static String clienteNormalNome, clienteAdminNome;
    private static Long clienteNormalId, clienteAdminId;
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

        clienteNormalCpfOuCnpj = "418.502.610-33";
        clienteAdminCpfOuCnpj = "842.107.660-31";

        clienteNormalNome = "Carlos Alves";
        clienteAdminNome = "Alberto Rodrigues";

        clienteNormalId = 3L;
        clienteAdminId = 1L;

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
    public void realizarPedidoDeveriaLancar422QuandoEnderecoDeEntregaIdNulo() throws JsonProcessingException {

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
    public void realizarPedidoDeveriaLancar422QuandoListaItemsVazia() throws JsonProcessingException {

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
    public void realizarPedidoDeveriaLancar422QuandoAlgumProdutoIdNulo() throws JsonProcessingException {

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
    public void realizarPedidoDeveriaLancar422QuandoAlgumaQuantidadeNula() throws JsonProcessingException {

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
    public void realizarPedidoDeveriaLancar422QuandoAlgumaQuantidadeNegativa() throws JsonProcessingException {

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
                    .body("campos.mensagem", hasItem("O valor do campo 'quantidade' do Produto, da lista 'items' deve ser positivo."));

    }

    @Test
    public void realizarPedidoDeveriaLancar422QuandoAlgumaQuantidadeIgualAZero() throws JsonProcessingException {

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
                    .body("campos.mensagem", hasItem("O valor do campo 'quantidade' do Produto, da lista 'items' deve ser positivo."));

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

    @Test
    public void realizarPagamentoDoPedidoDeveriaRetornarCriarBoletoRequisicaoDTOQuandoClienteAdminLogado() throws JsonProcessingException {

        CriarBoletoRequisicaoDTO dtoRequisicao = new CriarBoletoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.BOLETO);
        dtoRequisicao.setPagadorCpfOuCnpj(clienteAdminCpfOuCnpj);
        dtoRequisicao.setPagadorNome(clienteAdminNome);
        dtoRequisicao.setPagadorEmail(clienteAdminEmail);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(201)
                    .body("id", is(pedidoId.intValue()))
                    .body("tipoPagamento", equalTo("BOLETO"))
                    .body("estadoPagamento", equalTo("PENDENTE"))
                    .body("pagadorCpfOuCnpj", equalTo(clienteAdminCpfOuCnpj))
                    .body("pagadorNome", equalTo(clienteAdminNome))
                    .body("pagadorEmail", equalTo(clienteAdminEmail))
                    .body("$", hasKey("codigoDeBarras"))
                    .body("$", hasKey("dataVencimento"))
                    .body("$", hasKey("dataPagamento"));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaRetornarCriarCartaoDeCreditoRequisicaoDTOQuandoClienteAdminLogado() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        int quantidadeDeParcelas = 1;

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(quantidadeDeParcelas);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 5L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(201)
                    .body("id", is(pedidoId.intValue()))
                    .body("tipoPagamento", equalTo("CARTAO_DE_CREDITO"))
                    .body("estadoPagamento", equalTo("PENDENTE"))
                    .body("quantidadeParcelas", is(quantidadeDeParcelas))
                    .body("$", hasKey("dataVencimento"));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaRetornarCriarBoletoRequisicaoDTOQuandoClienteNormalLogado() throws JsonProcessingException {

        CriarBoletoRequisicaoDTO dtoRequisicao = new CriarBoletoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.BOLETO);
        dtoRequisicao.setPagadorCpfOuCnpj(clienteNormalCpfOuCnpj);
        dtoRequisicao.setPagadorNome(clienteNormalNome);
        dtoRequisicao.setPagadorEmail(clienteNormalEmail);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 6L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(201)
                    .body("id", is(pedidoId.intValue()))
                    .body("tipoPagamento", equalTo("BOLETO"))
                    .body("estadoPagamento", equalTo("PENDENTE"))
                    .body("pagadorCpfOuCnpj", equalTo(clienteNormalCpfOuCnpj))
                    .body("pagadorNome", equalTo(clienteNormalNome))
                    .body("pagadorEmail", equalTo(clienteNormalEmail))
                    .body("$", hasKey("codigoDeBarras"))
                    .body("$", hasKey("dataVencimento"))
                    .body("$", hasKey("dataPagamento"));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaRetornarCriarCartaoDeCreditoRequisicaoDTOQuandoClienteNormalLogado() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        int quantidadeDeParcelas = 1;

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(quantidadeDeParcelas);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 7L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(201)
                    .body("id", is(pedidoId.intValue()))
                    .body("tipoPagamento", equalTo("CARTAO_DE_CREDITO"))
                    .body("estadoPagamento", equalTo("PENDENTE"))
                    .body("quantidadeParcelas", is(quantidadeDeParcelas))
                    .body("$", hasKey("dataVencimento"));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar422QuandoTipoPagamentoNulo() throws JsonProcessingException {

        CriarBoletoRequisicaoDTO dtoRequisicao = new CriarBoletoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(null);
        dtoRequisicao.setPagadorCpfOuCnpj(clienteAdminCpfOuCnpj);
        dtoRequisicao.setPagadorNome(clienteAdminNome);
        dtoRequisicao.setPagadorEmail(clienteAdminEmail);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                .header("Authorization", "Bearer " + clienteAdminToken)
                .header("Content-Type", "application/json")
                .body(jsonDTORequisicao)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                .statusCode(422)
                .body("status", is(422))
                .body("error", equalTo("Dados inválidos."))
                .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"))
                .body("campos.nomeCampo", hasItem("tipoPagamento"))
                .body("campos.mensagem", hasItem("O campo 'tipoPagamento' é obrigatório."));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar422QuandoPagadorCpfOuCnpjEmBranco() throws JsonProcessingException {

        CriarBoletoRequisicaoDTO dtoRequisicao = new CriarBoletoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.BOLETO);
        dtoRequisicao.setPagadorCpfOuCnpj("  ");
        dtoRequisicao.setPagadorNome(clienteAdminNome);
        dtoRequisicao.setPagadorEmail(clienteAdminEmail);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                .header("Authorization", "Bearer " + clienteAdminToken)
                .header("Content-Type", "application/json")
                .body(jsonDTORequisicao)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                .statusCode(422)
                .body("status", is(422))
                .body("error", equalTo("Dados inválidos."))
                .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"))
                .body("campos.nomeCampo", hasItem("pagadorCpfOuCnpj"))
                .body("campos.mensagem", hasItem("O campo 'pagadorCpfOuCnpj' é obrigatório."));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar422QuandoPagadorNomeEmBranco() throws JsonProcessingException {

        CriarBoletoRequisicaoDTO dtoRequisicao = new CriarBoletoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.BOLETO);
        dtoRequisicao.setPagadorCpfOuCnpj(clienteAdminCpfOuCnpj);
        dtoRequisicao.setPagadorNome("");
        dtoRequisicao.setPagadorEmail(clienteAdminEmail);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                .header("Authorization", "Bearer " + clienteAdminToken)
                .header("Content-Type", "application/json")
                .body(jsonDTORequisicao)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                .statusCode(422)
                .body("status", is(422))
                .body("error", equalTo("Dados inválidos."))
                .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"))
                .body("campos.nomeCampo", hasItem("pagadorNome"))
                .body("campos.mensagem", hasItem("O campo 'pagadorNome' é obrigatório."));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar422QuandoPagadorEmailEmBranco() throws JsonProcessingException {

        CriarBoletoRequisicaoDTO dtoRequisicao = new CriarBoletoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.BOLETO);
        dtoRequisicao.setPagadorCpfOuCnpj(clienteAdminCpfOuCnpj);
        dtoRequisicao.setPagadorNome(clienteAdminNome);
        dtoRequisicao.setPagadorEmail("");

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                .header("Authorization", "Bearer " + clienteAdminToken)
                .header("Content-Type", "application/json")
                .body(jsonDTORequisicao)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                .statusCode(422)
                .body("status", is(422))
                .body("error", equalTo("Dados inválidos."))
                .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"))
                .body("campos.nomeCampo", hasItem("pagadorEmail"))
                .body("campos.mensagem", hasItem("O campo 'pagadorEmail' é obrigatório."));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar422QuandoQuantidadeDeParacelasNula() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(null);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);


        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"))
                    .body("campos.nomeCampo", hasItem("quantidadeDeParacelas"))
                    .body("campos.mensagem", hasItem("O campo 'quantidadeDeParcelas' é obrigatório."));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar422QuandoQuantidadeDeParacelasMenorQueUm() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(0);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);


        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"))
                    .body("campos.nomeCampo", hasItem("quantidadeDeParacelas"))
                    .body("campos.mensagem", hasItem("O mínimo de parcelas deve ser 1."));

    }

    /*
    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar422QuandoSalvarCartaoParaProximasComprasNulo() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(1);


        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 4L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"))
                    .body("campos.nomeCampo", hasItem("salvarCartaoParaProximasCompras"))
                    .body("campos.mensagem", hasItem("O campo 'salvarCartaoParaProximasCompras' é obrigatório."));

    }
     */

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar401QuandoClienteNaoAutenticado() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(1);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 1L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + tokenInvalido)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(401);

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar404QuandoPedidoIdNaoExiste() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(1);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 1000L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                .statusCode(404)
                .body("$", hasKey("timestamp"))
                .body("status", is(404))
                .body("error", equalTo("Recurso não encontrado no nosso banco de dados."))
                .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar400QuandoPagamentoIdExiste() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(1);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 1L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(400)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(400))
                    .body("error", equalTo("A cobrança do pagamento já foi emitida para esse pedido."))
                    .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"));

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancar403QuandoClienteNaoDonoDoPedido() throws JsonProcessingException {

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();

        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(1);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(true);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        Long pedidoId = 8L;

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/pedidos/{pedidoId}/pagamento", pedidoId)
                .then()
                    .statusCode(403)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(403))
                    .body("error", equalTo("Cliente com o id: " + clienteAdminId + ", não é dono do pedido com o id: " + pedidoId))
                    .body("path", equalTo("/pedidos/" + pedidoId + "/pagamento"));

    }

}
