package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminAtualizarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminCriarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminAtualizarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminCriarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.factory.ProdutoFactory;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ForbiddenException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProdutoServiceTesteUnitario {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Long produtoIdExistente, produtoIdNaoExistente;
    private String produtoNomeExistente, produtoNomeNaoExistente;
    private Produto produto, produto2;
    private LerProdutoRespostaDTO lerProdutoRespostaDTO;
    private PageImpl<Produto> produtoPage;


    @BeforeEach
    void setUp() throws Exception {

        produtoIdExistente = 1L;
        produtoIdNaoExistente = 2L;

        produto = ProdutoFactory.instanciarProduto();
        lerProdutoRespostaDTO = ProdutoFactory.instanciarLerProdutoRespostaDTO();

        produto2 = ProdutoFactory.instanciarProduto();
        produto2.setId(3L);
        produto2.setNome("Air Pods 3");
        produto2.setPreco(BigDecimal.valueOf(1899.99));

        produtoPage = new PageImpl<>(List.of(produto, produto2));

        produtoNomeExistente = produto.getNome();
        produtoNomeNaoExistente = "XBOX SERIES X";

        Mockito.when(produtoRepository.findById(produtoIdExistente)).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.findById(produtoIdNaoExistente)).thenReturn(Optional.empty());

        Mockito.when(produtoRepository.findAll(ArgumentMatchers.<Specification<Produto>>any(), (Pageable) ArgumentMatchers.any())).thenReturn(produtoPage);

        Mockito.when(produtoRepository.findByNomeIgnoreCase(produtoNomeExistente)).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.findByNomeIgnoreCase(produtoNomeNaoExistente)).thenReturn(Optional.empty());

        Mockito.when(produtoRepository.save(ArgumentMatchers.any())).thenReturn(produto2);

        Mockito.when(produtoRepository.getReferenceById(produtoIdExistente)).thenReturn(produto);
        Mockito.when(produtoRepository.getReferenceById(produtoIdNaoExistente)).thenThrow(EntityNotFoundException.class);

    }

    @Test
    public void lerProdutoDeveriaRetornarLerProdutoRespostaDTOQuandoProdutoIdExisteEProdutoVisibilidadeVerdadeiro() {

        LerProdutoRespostaDTO dtoResposta = produtoService.lerProduto(produtoIdExistente);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(lerProdutoRespostaDTO.getId(), dtoResposta.getId());
        Assertions.assertEquals(lerProdutoRespostaDTO.getNome(), dtoResposta.getNome());
        Assertions.assertEquals(lerProdutoRespostaDTO.getPreco(), dtoResposta.getPreco());
        Assertions.assertEquals(lerProdutoRespostaDTO.getDisponibilidade(), dtoResposta.getDisponibilidade());

    }

    @Test
    public void lerProdutoDeveriaLancarRecursoNaoEncontradoExcecaoQuandoProdutoIdNaoExiste() {

        Assertions.assertThrows(RecursoNaoEncontradoException.class,() -> {
            produtoService.lerProduto(produtoIdNaoExistente);
        });

    }

    @Test
    public void lerProdutoDeveriaLancarForbiddenExcecaoQuandoProdutoIdExisteEProdutoVisibilidadeFalso() {

        produto.setVisibilidade(false);

        Assertions.assertThrows(ForbiddenException.class,() -> {
            produtoService.lerProduto(produtoIdExistente);
        });

    }

    @Test
    public void lerProdutosDeveriaRetornarLerProdutoRespostaDTOPage() {

        Pageable pageable = PageRequest.of(0, 12);

        Page<LerProdutoRespostaDTO> lerProdutoRespostaDTOPage = produtoService.lerProdutos(null, null, null, pageable);

        Assertions.assertNotNull(lerProdutoRespostaDTOPage);
        Assertions.assertEquals(2, lerProdutoRespostaDTOPage.getSize());

    }

    @Test
    public void adminLerProdutoDeveriaRetornarAdminLerProdutoRespostaDTOQuandoProdutoIdExiste() {

        AdminLerProdutoRespostaDTO dtoResposta = produtoService.adminLerProduto(produtoIdExistente);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(produto.getId(), dtoResposta.getId());
        Assertions.assertEquals(produto.getPreco(), dtoResposta.getPreco());
        Assertions.assertEquals(produto.getDisponibilidade().getStatus(), dtoResposta.getDisponibilidade());
        Assertions.assertEquals(produto.getVisibilidade(), dtoResposta.getVisibilidade());

    }

    @Test
    public void adminLerProdutoDeveriaLancarRecursoNaoEncontradoExcecaoQuandoProdutoIdNaoExiste() {

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            produtoService.adminLerProduto(produtoIdNaoExistente);

        });

    }

    @Test
    public void adminLerProdutosDeveriaRetornarAdminLerProdutoRespostaDTOPage() {

        Pageable pageable = PageRequest.of(0, 12);

        Page<AdminLerProdutoRespostaDTO> adminLerProdutoRespostaDTOPage = produtoService.adminLerProdutos(null, null, null, pageable);

        Assertions.assertNotNull(adminLerProdutoRespostaDTOPage);
        Assertions.assertEquals(2, adminLerProdutoRespostaDTOPage.getSize());
        Assertions.assertEquals(adminLerProdutoRespostaDTOPage.iterator().next().getNome(), produto.getNome());

    }

    @Test
    public void adminAdicionarProdutoDeveriaRetornarAdminCriarProdutoRespostaDTOQuandoProdutoNomeDisponivel() {

        AdminCriarProdutoRequisicaoDTO dtoRequisicao = new AdminCriarProdutoRequisicaoDTO();

        dtoRequisicao.setNome(produto2.getNome());
        dtoRequisicao.setPreco(produto2.getPreco());
        dtoRequisicao.setDisponibilidade(produto2.getDisponibilidade());
        dtoRequisicao.setVisibilidade(produto2.getVisibilidade());

        AdminCriarProdutoRespostaDTO dtoResposta = produtoService.adminAdicionarProduto(dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(dtoRequisicao.getNome(), dtoResposta.getNome());
        Assertions.assertEquals(dtoRequisicao.getPreco(), dtoResposta.getPreco());
        Assertions.assertEquals(dtoRequisicao.getDisponibilidade().getStatus(), dtoResposta.getDisponibilidade());
        Assertions.assertEquals(dtoRequisicao.getDisponibilidade().getStatus(), dtoResposta.getDisponibilidade());
        Assertions.assertEquals(dtoRequisicao.getVisibilidade(), dtoResposta.getVisibilidade());

    }

    @Test
    public void adminAdicionarProdutoDeveriaLancarRecursoJaExistenteExcecaoQuandoProdutoNomeIndisponivel() {

        AdminCriarProdutoRequisicaoDTO dtoRequisicao = new AdminCriarProdutoRequisicaoDTO();

        dtoRequisicao.setNome(produto.getNome());
        dtoRequisicao.setPreco(produto2.getPreco());
        dtoRequisicao.setDisponibilidade(produto2.getDisponibilidade());
        dtoRequisicao.setVisibilidade(produto2.getVisibilidade());

        Assertions.assertThrows(RecursoJaExistenteException.class, () -> {
           produtoService.adminAdicionarProduto(dtoRequisicao);
        });

    }

    @Test
    public void adminAtualizarProdutoDeveriaRetornarAdminAtualizarProdutoRespostaDTOQuandoProdutoIdExisteEProdutoDadosSaoValidos() {

        AdminAtualizarProdutoRequisicaoDTO dtoRequisicao = new AdminAtualizarProdutoRequisicaoDTO();

        dtoRequisicao.setNome("Alexa Echo Dot");
        dtoRequisicao.setPreco(BigDecimal.valueOf(233.40));
        dtoRequisicao.setDisponibilidade(Disponibilidade.INDISPONIVEL);
        dtoRequisicao.setVisibilidade(false);

        AdminAtualizarProdutoRespostaDTO dtoResposta = produtoService.adminAtualizarProduto(produto.getId(), dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(produto.getId(), dtoResposta.getId());
        Assertions.assertEquals(dtoRequisicao.getNome(), dtoResposta.getNome());
        Assertions.assertEquals(dtoRequisicao.getPreco(), dtoResposta.getPreco());
        Assertions.assertEquals(dtoRequisicao.getDisponibilidade().getStatus(), dtoResposta.getDisponibilidade());
        Assertions.assertEquals(dtoRequisicao.getVisibilidade(), dtoResposta.getVisibilidade());

    }

    @Test
    public void adminAtualizarProdutoDeveriaLancarRecursoNaoEncontradoExcecaoQuandoProdutoIdNaoExiste() {

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            produtoService.adminAtualizarProduto(produtoIdNaoExistente, new AdminAtualizarProdutoRequisicaoDTO());

        });

    }

    @Test
    public void adminAtualizarProdutoDeveriaLancarRecursoJaExistenteExcecaoQuandoProdutoIdExisteEProdutoNomeIndisponivel() {

        AdminAtualizarProdutoRequisicaoDTO dtoRequisicao = new AdminAtualizarProdutoRequisicaoDTO();

        Mockito.when(produtoRepository.findByNomeIgnoreCase(ArgumentMatchers.any())).thenReturn(Optional.of(produto2));

        dtoRequisicao.setNome(produto2.getNome());

        Assertions.assertThrows(RecursoJaExistenteException.class, () -> {

            produtoService.adminAtualizarProduto(produtoIdExistente, dtoRequisicao);

        });

    }

    @Test
    public void adminRemoverProdutoDeveriaRetornarVoidQuandoProdutoIdExiste() {

        Assertions.assertDoesNotThrow(() -> {

            produtoService.adminRemoverProduto(produtoIdExistente);

        });

    }

    @Test
    public void adminRemoverProdutoDeveriaLancarEntityNotFoundExceptionQuandoProdutoIdNaoExiste() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> {

            produtoService.adminRemoverProduto(produtoIdNaoExistente);

        });

    }

}
