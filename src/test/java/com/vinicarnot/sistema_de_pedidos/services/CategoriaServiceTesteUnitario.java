package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminAtualizarCategoriaRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminCriarCategoriaRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.factory.CategoriaFactory;
import com.vinicarnot.sistema_de_pedidos.factory.ProdutoFactory;
import com.vinicarnot.sistema_de_pedidos.repositories.CategoriaRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
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
public class CategoriaServiceTesteUnitario {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    private Categoria categoria, categoria2;
    private PageImpl<Categoria> categoriaPage;
    private Long categoriaIdInexistente;
    private String categoriaNomeInexistente;
    private Produto produto;
    private PageImpl<Produto> produtoPage;
    private Long produtoIdInexistente;

    @BeforeEach
    void setUp() throws Exception {

        categoria = CategoriaFactory.instanciarCategoria();
        categoria2 = CategoriaFactory.instanciarCategoria();
        categoria2.setId(2L);
        categoria2.setNome("Computadores");
        categoriaPage = new PageImpl<>(List.of(categoria, categoria2));

        categoriaIdInexistente = 1000L;
        categoriaNomeInexistente = "Produtos de Limpeza";

        produto = ProdutoFactory.instanciarProduto();
        produtoPage = new PageImpl<>(List.of(produto));
        produtoIdInexistente = 1000L;

        Mockito.when(categoriaRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(categoriaPage);

        Mockito.when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));
        Mockito.when(categoriaRepository.findById(categoriaIdInexistente)).thenReturn(Optional.empty());

        Mockito.when(categoriaRepository.existsById(categoria.getId())).thenReturn(true);
        Mockito.when(categoriaRepository.existsById(categoriaIdInexistente)).thenReturn(false);

        Mockito.when(produtoRepository.findAll(ArgumentMatchers.<Specification<Produto>>any(), (Pageable) ArgumentMatchers.any())).thenReturn(produtoPage);

        Mockito.when(categoriaRepository.findByNomeIgnoreCase(categoria.getNome())).thenReturn(Optional.of(categoria));
        Mockito.when(categoriaRepository.findByNomeIgnoreCase(categoriaNomeInexistente)).thenReturn(Optional.empty());

        Mockito.when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.findById(produtoIdInexistente)).thenReturn(Optional.empty());

        Mockito.when(categoriaRepository.existsById(categoria.getId())).thenReturn(true);
        Mockito.when(categoriaRepository.existsById(categoriaIdInexistente)).thenReturn(false);

    }

    @Test
    public void lerCategoriasDeveriaRetornarLerCategoriaRespostaDTOPage() {

        PageRequest pageRequest = PageRequest.of(0, 12);

        Page<LerCategoriaRespostaDTO> lerCategoriaRespostaDTOPage = categoriaService.lerCategorias(pageRequest);

        Assertions.assertNotNull(lerCategoriaRespostaDTOPage);
        Assertions.assertEquals(2, lerCategoriaRespostaDTOPage.getSize());

    }

    @Test
    public void lerCategoriaDeveriaRetornarLerCategoriaRespostaDTOQuandoCategoriaIdExiste() {

        LerCategoriaRespostaDTO dtoRequest = categoriaService.lerCategoria(categoria.getId());

        Assertions.assertNotNull(dtoRequest);
        Assertions.assertEquals(categoria.getId(), dtoRequest.getId());
        Assertions.assertEquals(categoria.getNome(), dtoRequest.getNome());

    }

    @Test
    public void lerCategoriaDeveriaLancarRecursoNaoEncontradoExcecaoQuandoCategoriaIdNaoExiste() {

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            categoriaService.lerCategoria(categoriaIdInexistente);

        });

    }

    @Test
    public void lerProdutosDeUmaCategoriaDeveriaRetornarLerProdutoRespostaDTOPageQuandoCategoriaIdExiste() {

        PageRequest pageRequest = PageRequest.of(0, 12);

        Page<LerProdutoRespostaDTO> lerProdutoRespostaDTOPage = categoriaService.lerProdutosDeUmaCategoria(categoria.getId(), pageRequest);

        Assertions.assertNotNull(lerProdutoRespostaDTOPage);
        Assertions.assertEquals(1, lerProdutoRespostaDTOPage.getSize());

    }

    @Test
    public void lerProdutosDeUmaCategoriaDeveriaLancarRecursoNaoEncontradoExcecaoQuandoCategoriaIdNaoExiste() {

        PageRequest pageRequest = PageRequest.of(0, 12);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            categoriaService.lerProdutosDeUmaCategoria(categoriaIdInexistente, pageRequest);

        });

    }

    @Test
    public void adminLerProdutosDeUmaCategoriaDeveriaRetornarAdminLerProdutoRespostaDTOPageQuandoCategoriaIdExiste() {

        PageRequest pageRequest = PageRequest.of(0, 12);

        Page<AdminLerProdutoRespostaDTO> adminLerProdutoRespostaDTOPage = categoriaService.adminLerProdutosDeUmaCategoria(categoria.getId(), pageRequest);

        Assertions.assertNotNull(adminLerProdutoRespostaDTOPage);
        Assertions.assertEquals(1, adminLerProdutoRespostaDTOPage.getSize());

    }

    @Test
    public void adminlerProdutosDeUmaCategoriaDeveriaLancarRecursoNaoEncontradoExcecaoQuandoCategoriaIdNaoExiste() {

        PageRequest pageRequest = PageRequest.of(0, 12);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            categoriaService.adminLerProdutosDeUmaCategoria(categoriaIdInexistente, pageRequest);

        });

    }

    @Test
    public void adminAdicionarCategoriaDeveriaRetornarAdminCriarCategoriaRespostaDTOQuandoCategoriaNomeDisponivelEProdutoIdsExistem() {

        AdminCriarCategoriaRequisicaoDTO dtoRequisicao = new AdminCriarCategoriaRequisicaoDTO();

        dtoRequisicao.setNome(categoriaNomeInexistente);
        dtoRequisicao.getProdutos().add(produto.getId());

        Categoria novaCategoria = new Categoria(3L, categoriaNomeInexistente);
        novaCategoria.getProdutos().add(produto);

        Mockito.when(categoriaRepository.save(ArgumentMatchers.any())).thenReturn(novaCategoria);

        AdminCriarCategoriaRespostaDTO dtoResposta = categoriaService.adminAdicionarCategoria(dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(3L, dtoResposta.getId());
        Assertions.assertEquals(categoriaNomeInexistente, dtoResposta.getNome());
        Assertions.assertEquals(1, dtoResposta.getProdutos().size());

    }

    @Test
    public void adminAdicionarCategoriaDeveriaLancarRecursoJaExistenteExcecaoQuandoCategoriaNomeIndisponivel() {

        AdminCriarCategoriaRequisicaoDTO dtoRequisicao = new AdminCriarCategoriaRequisicaoDTO();

        dtoRequisicao.setNome(categoria.getNome());

        Assertions.assertThrows(RecursoJaExistenteException.class, () -> {

            categoriaService.adminAdicionarCategoria(dtoRequisicao);

        });

    }

    @Test
    public void adminAdicionarCategoriaDeveriaLancarRecursoNaoEncontradoExcecaoQuandoCategoriaNomeDisponivelEAlgumProdutoIdNaoExiste() {

        AdminCriarCategoriaRequisicaoDTO dtoRequisicao = new AdminCriarCategoriaRequisicaoDTO();

        dtoRequisicao.setNome(categoriaNomeInexistente);
        dtoRequisicao.getProdutos().add(produtoIdInexistente);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            categoriaService.adminAdicionarCategoria(dtoRequisicao);

        });

    }

    @Test
    public void adminAtualizarCategoriaDeveriaRetornarAdminAtualizarCategoriaRespostaDTOQuandoCategoriaIdExisteECategoriaNomeIgualEProdutosIdsExistem() {

        Produto produto2 = ProdutoFactory.instanciarProduto();
        produto2.setId(2L);
        produto2.setNome("JBL Boombox 3");
        produto2.setPreco(BigDecimal.valueOf(2500.00));

        Categoria categoriaAtualizada = new Categoria(categoria.getId(), categoria.getNome());
        categoriaAtualizada.getProdutos().add(produto2);

        Mockito.when(produtoRepository.findById(produto2.getId())).thenReturn(Optional.of(produto2));
        Mockito.when(categoriaRepository.save(ArgumentMatchers.any())).thenReturn(categoriaAtualizada);

        AdminAtualizarCategoriaRequisicaoDTO dtoRequisicao = new AdminAtualizarCategoriaRequisicaoDTO();
        dtoRequisicao.setNome(categoria.getNome());
        dtoRequisicao.getProdutos().add(produto2.getId());

        AdminAtualizarCategoriaRespostaDTO dtoResposta = categoriaService.adminAtualizarCategoria(categoria.getId(), dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(categoria.getId(), dtoResposta.getId());
        Assertions.assertEquals(categoria.getNome(), dtoResposta.getNome());
        Assertions.assertEquals(1, dtoResposta.getProdutos().size());

    }

    @Test
    public void adminAtualizarCategoriaDeveriaRetornarAdminAtualizarCategoriaRespostaDTOQuandoCategoriaIdExisteECategoriaNomeDisponivelEProdutosIdsExistem() {

        Categoria categoriaAtualizada = new Categoria(categoria.getId(), categoriaNomeInexistente);
        categoriaAtualizada.getProdutos().add(produto);

        Mockito.when(categoriaRepository.save(ArgumentMatchers.any())).thenReturn(categoriaAtualizada);

        AdminAtualizarCategoriaRequisicaoDTO dtoRequisicao = new AdminAtualizarCategoriaRequisicaoDTO();
        dtoRequisicao.setNome(categoriaNomeInexistente);
        dtoRequisicao.getProdutos().add(produto.getId());

        AdminAtualizarCategoriaRespostaDTO dtoResposta = categoriaService.adminAtualizarCategoria(categoria.getId(), dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(categoria.getId(), dtoResposta.getId());
        Assertions.assertEquals(categoriaNomeInexistente, dtoResposta.getNome());
        Assertions.assertEquals(1, dtoResposta.getProdutos().size());

    }

    @Test
    public void adminAtualizarCategoriaDeveriaLancarRecursoNaoEncontradoExcecaoQuandoCategoriaIdNaoExiste() {

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            categoriaService.adminAtualizarCategoria(categoriaIdInexistente, new AdminAtualizarCategoriaRequisicaoDTO());

        });

    }

    @Test
    public void adminAtualizarCategoriaDeveriaLancarRecursoJaExistenteExcecaoQuandoCategoriaIdExisteECategoriaNomeIndisponivel() {

        Mockito.when(categoriaRepository.findById(categoria2.getId())).thenReturn(Optional.of(categoria2));

        AdminAtualizarCategoriaRequisicaoDTO dtoRequisicao = new AdminAtualizarCategoriaRequisicaoDTO();
        dtoRequisicao.setNome(categoria.getNome());

        Assertions.assertThrows(RecursoJaExistenteException.class, () -> {

            categoriaService.adminAtualizarCategoria(categoria2.getId(), dtoRequisicao);

        });

    }

    @Test
    public void adminAtualizarCategoriaDeveriaLancarRecursoNaoEncontradoExcecaoQuandoCategoriaIdExisteECategoriaNomeDisponivelEAlgumProdutoIdNaoExiste() {

        AdminAtualizarCategoriaRequisicaoDTO dtoRequisicao = new AdminAtualizarCategoriaRequisicaoDTO();
        dtoRequisicao.setNome(categoria.getNome());
        dtoRequisicao.getProdutos().add(produtoIdInexistente);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            categoriaService.adminAtualizarCategoria(categoria.getId(), dtoRequisicao);

        });

        Mockito.verify(produtoRepository, Mockito.times(1)).findById(produtoIdInexistente);

    }

    @Test
    public void adminRemoverCategoriaDeveriaRetornarVoidQuandoCategoriaIdExiste() {

        Assertions.assertDoesNotThrow(() -> {

            categoriaService.adminRemoverCategoria(categoria.getId());

        });

    }

    @Test
    public void adminRemoverCategoriaDeveriaLancarRecursoNaoEncontradoExcecaoQuandoCategoriaIdNaoExiste() {

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            categoriaService.adminRemoverCategoria(categoriaIdInexistente);

        });

    }

}
