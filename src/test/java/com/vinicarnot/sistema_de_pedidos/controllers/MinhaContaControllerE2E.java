package com.vinicarnot.sistema_de_pedidos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarMinhaContaRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarTelefoneRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.util.TokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class MinhaContaControllerE2E {

    private static String clienteNormalToken, clienteNormalToken2, clienteAdminToken, clienteAdminToken2, tokenInvalido;
    private static Long clienteNormalId, clienteNormalId2, clienteAdminId, clienteAdminId2;
    private static String clienteNormalEmail, clienteNormalEmail2, clienteAdminEmail, clienteAdminEmail2;
    private static String clienteNormalSenha, clienteNormalSenha2, clienteAdminSenha, clienteAdminSenha2;
    private static String clienteNormalNome, clienteNormalNome2, clienteAdminNome, clienteAdminNome2;
    private static String clienteNormalCpfOuCnpj, clienteNormalCpfOuCnpj2, clienteAdminCpfOnCnpj, clienteAdminCpfOnCnpj2;
    private static String clienteNormalTelefoneNumero, clienteNormalTelefoneNumero2, clienteAdminTelefoneNumero, clienteAdminTelefoneNumero2;
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUpAll() {

        RestAssured.baseURI = "http://localhost:8081";

        clienteNormalId = 3L;
        clienteNormalId2 = 5L;
        clienteAdminId = 1L;
        clienteAdminId2 = 6L;

        clienteNormalEmail = "carlos@email.com";
        clienteNormalEmail2 = "enzo@email.com";
        clienteAdminEmail = "alberto@email.com";
        clienteAdminEmail2 = "satocompany@email.com";

        clienteNormalSenha = "CCC";
        clienteNormalSenha2 = "EEE";
        clienteAdminSenha = "AAA";
        clienteAdminSenha2 = "FFF";

        clienteNormalNome = "Carlos Alves";
        clienteNormalNome2 = "Enzo Santos";
        clienteAdminNome = "Alberto Rodrigues";
        clienteAdminNome2 = "Sato Company";

        clienteNormalCpfOuCnpj = "418.502.610-33";
        clienteNormalCpfOuCnpj2 = "444.253.769-12";
        clienteAdminCpfOnCnpj = "842.107.660-31";
        clienteAdminCpfOnCnpj2 = "12.345.678/0001-95";

        clienteNormalTelefoneNumero = "(33) 33333-3333";
        clienteNormalTelefoneNumero2 = "(55) 55555-5555";
        clienteAdminTelefoneNumero = "(11) 11111-1111";
        clienteAdminTelefoneNumero2 = "(66) 66666-6666";

        clienteNormalToken = TokenUtil.obtainAccessToken(clienteNormalEmail, clienteNormalSenha);
        clienteNormalToken2 = TokenUtil.obtainAccessToken(clienteNormalEmail2, clienteNormalSenha2);
        clienteAdminToken = TokenUtil.obtainAccessToken(clienteAdminEmail, clienteAdminSenha);
        clienteAdminToken2 = TokenUtil.obtainAccessToken(clienteAdminEmail2, clienteAdminSenha2);
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

    @Test
    public void atualizarMinhaContaDeveriaRetornarAtualizarMinhaContaRespostaDTOQuandoClienteNormalLogado() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Enzo Santos2");
        dtoRequisicao.setEmail("enzo2@email.com");
        dtoRequisicao.setSenha("EEE2");
        dtoRequisicao.setCpfOuCnpj("111.111.111-11");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_FISICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(55) 55555-5555"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteNormalToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(200)
                    .body("$", hasKey("id"))
                    .body("nome", equalTo(dtoRequisicao.getNome()))
                    .body("email", equalTo(dtoRequisicao.getEmail()))
                    .body("cpfOuCnpj", equalTo(dtoRequisicao.getCpfOuCnpj()))
                    .body("tipo", equalTo("PESSOA_FISICA"))
                    .body("telefone.numero", equalTo(dtoRequisicao.getTelefone().getNumero()));

    }

    @Test
    public void atualizarMinhaContaDeveriaRetornarAtualizarMinhaContaRespostaDTOQuandoClienteAdminLogado() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("12.345.678/0001-95");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(200)
                    .body("$", hasKey("id"))
                    .body("nome", equalTo(dtoRequisicao.getNome()))
                    .body("email", equalTo(dtoRequisicao.getEmail()))
                    .body("cpfOuCnpj", equalTo(dtoRequisicao.getCpfOuCnpj()))
                    .body("tipo", equalTo("PESSOA_JURIDICA"))
                    .body("telefone.numero", equalTo(dtoRequisicao.getTelefone().getNumero()));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar401QuandoClienteNaoAutenticado() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer ")
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(401);

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoNomeEmBranco() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("   ");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("12.345.678/0001-95");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("nome"))
                    .body("campos.mensagem", hasItem("O campo 'nome' é obrigatório."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoEmailEmBranco() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail(""); // Nao pode ter espaços na String, pois ativa outro erro
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("12.345.678/0001-95");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("email"))
                    .body("campos.mensagem", hasItem("O campo 'email' é obrigatório."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoEmailInvalido() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompanyemail.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("12.345.678/0001-95");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("email"))
                    .body("campos.mensagem", hasItem("O campo 'email' deve ser válido."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoSenhaEmBranco() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("   ");
        dtoRequisicao.setCpfOuCnpj("12.345.678/0001-95");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("senha"))
                    .body("campos.mensagem", hasItem("O campo 'senha' é obrigatório."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoCpfOuCnpjEmBranco() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("               "); // Precisa ter entre 14 e 18 espaçamentos para ativar o erro certo
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("cpfOuCnpj"))
                    .body("campos.mensagem", hasItem("O campo 'cpfOuCnpj' é obrigatório."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoCpfOuCnpjNaoUltrapassaAQuantidadeMinimaDeCaracteres() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("111");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("cpfOuCnpj"))
                    .body("campos.mensagem", hasItem("O campo 'cpfOuCnpj' deve ter entre 14 e 18 caracteres."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoCpfOuCnpjUltrapassaAQuantidadeMaximaDeCaracteres() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("111111111111111111111111111");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("cpfOuCnpj"))
                    .body("campos.mensagem", hasItem("O campo 'cpfOuCnpj' deve ter entre 14 e 18 caracteres."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoTipoNulo() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("111111111111111111111111111");
        dtoRequisicao.setTipo(null);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-6666"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("tipo"))
                    .body("campos.mensagem", hasItem("O campo 'tipo' é obrigatório."));

    }

    @Test
    public void atualizarMinhaContaDeveriaLancar422QuandoTelefoneNumeroInvalido() throws JsonProcessingException {

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO();
        dtoRequisicao.setNome("Sato Company");
        dtoRequisicao.setEmail("satocompany@email.com");
        dtoRequisicao.setSenha("FFF");
        dtoRequisicao.setCpfOuCnpj("12.345.678/0001-95");
        dtoRequisicao.setTipo(TipoCliente.PESSOA_JURIDICA);
        dtoRequisicao.setTelefone(new AtualizarTelefoneRequisicaoDTO("(66) 66666-66667"));

        String jsonDTORequisicao = objectMapper.writeValueAsString(dtoRequisicao);

        RestAssured
                .given()
                    .header("Authorization", "Bearer " + clienteAdminToken2)
                    .header("Content-Type", "application/json")
                    .body(jsonDTORequisicao)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .put("/minha-conta")
                .then()
                    .statusCode(422)
                    .body("status", is(422))
                    .body("error", equalTo("Dados inválidos."))
                    .body("path", equalTo("/minha-conta"))
                    .body("campos.nomeCampo", hasItem("telefone.numero"))
                    .body("campos.mensagem", hasItem("O campo 'numero' deve ter 15 caracteres '(XX) XXXXX-XXXX'."));

    }

}
