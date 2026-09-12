package com.vinicarnot.sistema_de_pedidos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarCadastroClienteRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarCadastroClienteTelefoneRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoItemPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoRequisicaoDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class AuthenticationControllerE2E {

    private CriarCadastroClienteRequisicaoDTO dtoRequisicao;
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUpAll() {

        RestAssured.baseURI = "http://localhost:8081";

    }

    @BeforeEach
    void setUp() {

        dtoRequisicao = new CriarCadastroClienteRequisicaoDTO();

        objectMapper = new ObjectMapper();

        dtoRequisicao.setNome("Enzo");
        dtoRequisicao.setEmail("enzo@email.com");
        dtoRequisicao.setSenha("EEE");
        dtoRequisicao.setCpfOuCnpj("512.999.234-67");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_FISICA);
        dtoRequisicao.setTelefone(new CriarCadastroClienteTelefoneRequisicaoDTO("(55) 55555-5555"));

    }

    @Test
    public void cadastrarClienteDeveriaRetornarCriarCadastroClienteRespostaDTO() throws JsonProcessingException {

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(201)
                    .body("$", hasKey("id"))
                    .body("nome", equalTo(dtoRequisicao.getNome()))
                    .body("email", equalTo(dtoRequisicao.getEmail()))
                    .body("cpfOuCnpj", equalTo(dtoRequisicao.getCpfOuCnpj()))
                    .body("tipo", equalTo("PESSOA_FISICA"))
                    .body("roles.nome", hasItem("ROLE_NORMAL"))
                    .body("telefone.numero", equalTo(dtoRequisicao.getTelefone().getNumero()));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoNomeEmBranco() throws JsonProcessingException {

        dtoRequisicao.setNome("");

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("nome"))
                    .body("campos.mensagem", hasItem("O campo 'nome' é obrigatório."))
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoEmailEmBranco() throws JsonProcessingException {

        dtoRequisicao.setEmail("");

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("email"))
                    .body("campos.mensagem", hasItem("O campo 'email' é obrigatório."))
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoSenhaEmBranco() throws JsonProcessingException {

        dtoRequisicao.setSenha("");

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("senha"))
                    .body("campos.mensagem", hasItem("O campo 'senha' é obrigatório."))
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoCpfOuCnpjEmBranco() throws JsonProcessingException {

        dtoRequisicao.setCpfOuCnpj("");

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("cpfOuCnpj"))
                    .body("campos.mensagem", notNullValue())
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoCpfOuCnpjMenorQue14() throws JsonProcessingException {

        dtoRequisicao.setCpfOuCnpj("512.999.234-6");

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("cpfOuCnpj"))
                    .body("campos.mensagem", hasItem("O campo 'cpfOuCnpj' deve ter entre 14 e 18 caracteres."))
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoCpfOuCnpjMaiorQue18() throws JsonProcessingException {

        dtoRequisicao.setCpfOuCnpj("2222222222222222222222");

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("cpfOuCnpj"))
                    .body("campos.mensagem", hasItem("O campo 'cpfOuCnpj' deve ter entre 14 e 18 caracteres."))
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoTipoNulo() throws JsonProcessingException {

        dtoRequisicao.setTipo(null);

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("tipo"))
                    .body("campos.mensagem", hasItem("O campo 'tipo' é obrigatório."))
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoTelefoneNumeroEmBranco() throws JsonProcessingException {

        dtoRequisicao.setTelefone(new CriarCadastroClienteTelefoneRequisicaoDTO(""));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("telefone.numero"))
                    .body("campos.mensagem", notNullValue())
                    .body("path", equalTo("/auth/cadastro"));

    }

    @Test
    public void cadastrarClienteDeveriaLancar422QuandoTelefoneNumeroDiferenteDe15() throws JsonProcessingException {

        dtoRequisicao.setTelefone(new CriarCadastroClienteTelefoneRequisicaoDTO("(00)  00000-0000"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post("/auth/cadastro")
                .then()
                    .statusCode(422)
                    .body("$", hasKey("timestamp"))
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("campos.nomeCampo", hasItem("telefone.numero"))
                    .body("campos.mensagem", notNullValue())
                    .body("path", equalTo("/auth/cadastro"));

    }

}
