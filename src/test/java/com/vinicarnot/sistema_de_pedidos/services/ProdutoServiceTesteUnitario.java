package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminCriarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminCriarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.factory.ProdutoFactory;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ForbiddenException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
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
    public void adminAdicionarProdutoDeveriaRetornarAdminCriarProdutoRespostaDTOQuandoProdutoNomeEstaDisponivel() {

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
    public void adminAdicionarProdutoDeveriaLancarRecursoJaExistenteExcecaoQuandoProdutoNomeNaoEstaDisponivel() {

        AdminCriarProdutoRequisicaoDTO dtoRequisicao = new AdminCriarProdutoRequisicaoDTO();

        dtoRequisicao.setNome(produto.getNome());
        dtoRequisicao.setPreco(produto2.getPreco());
        dtoRequisicao.setDisponibilidade(produto2.getDisponibilidade());
        dtoRequisicao.setVisibilidade(produto2.getVisibilidade());

        Assertions.assertThrows(RecursoJaExistenteException.class, () -> {
           produtoService.adminAdicionarProduto(dtoRequisicao);
        });

    }


}
