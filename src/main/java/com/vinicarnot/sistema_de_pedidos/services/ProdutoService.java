package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminCriarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminAtualizarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminCriarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminAtualizarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.CategoriaRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.specifications.ProdutoSpecifications;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ForbiddenException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final CategoriaRepository categoriaRepository;

    private final ClienteService clienteService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, ClienteService clienteService) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.clienteService = clienteService;
    }

    @Transactional(readOnly = true)
    public LerProdutoRespostaDTO lerProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com o id: " + id + " não foi encontrado."));
        if(produto.getVisibilidade().booleanValue() == false) {
            throw new ForbiddenException("Produto com o id: " + id + " não está visível para acesso.");
        }
        return new LerProdutoRespostaDTO(produto);
    }

    @Transactional(readOnly = true)
    public Page<LerProdutoRespostaDTO> lerProdutos(String nome, BigDecimal precoMaximo, List<Long> categoriasIds, Pageable pageable) {

        Boolean visibilidadeProduto = true;

        Specification<Produto> spec = ProdutoSpecifications.filtrar(nome, precoMaximo, categoriasIds, visibilidadeProduto);

        Page<Produto> produtoPage = produtoRepository.findAll(spec, pageable);

        return produtoPage.map(produto -> new LerProdutoRespostaDTO(produto));

    }

    @Transactional(readOnly = true)
    public AdminLerProdutoRespostaDTO adminLerProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com o id: " + id + " não foi encontrado."));
        return new AdminLerProdutoRespostaDTO(produto);
    }

    @Transactional(readOnly = true)
    public Page<AdminLerProdutoRespostaDTO> adminLerProdutos(String nome, BigDecimal precoMaximo, List<Long> categoriasIds, Pageable pageable) {

        Boolean visibilidadeProduto = null;

        Specification<Produto> spec = ProdutoSpecifications.filtrar(nome, precoMaximo, categoriasIds, visibilidadeProduto);

        Page<Produto> produtoPage = produtoRepository.findAll(spec, pageable);

        return produtoPage.map(produto -> new AdminLerProdutoRespostaDTO(produto));

    }

    @Transactional(rollbackFor = Exception.class)
    public AdminCriarProdutoRespostaDTO adminAdicionarProduto(AdminCriarProdutoRequisicaoDTO dtoRequisicao) {
        Optional<Produto> produto = produtoRepository.findByNomeIgnoreCase(dtoRequisicao.getNome());
        if(produto.isPresent()) {
            throw new RecursoJaExistenteException("Já existe um produto cadastrado com o nome: " + dtoRequisicao.getNome() + ".");
        }
        Produto novoProduto = new Produto();
        novoProduto.setNome(dtoRequisicao.getNome());
        novoProduto.setPreco(dtoRequisicao.getPreco());
        novoProduto.setDisponibilidade(dtoRequisicao.getDisponibilidade());
        novoProduto.setVisibilidade(dtoRequisicao.getVisibilidade());
        return new AdminCriarProdutoRespostaDTO(produtoRepository.save(novoProduto));
    }

    @Transactional(rollbackFor = Exception.class)
    public AdminAtualizarProdutoRespostaDTO adminAtualizarProduto(Long idProduto, AdminAtualizarProdutoRequisicaoDTO dtoRequisicao) {
        Produto produto = produtoRepository.findById(idProduto).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto com o id: " + idProduto + " não encontrado."));
        produto.setNome(dtoRequisicao.getNome());
        produto.setPreco(dtoRequisicao.getPreco());
        produto.setDisponibilidade(dtoRequisicao.getDisponibilidade());
        produto.setVisibilidade(dtoRequisicao.getVisibilidade());
        return new AdminAtualizarProdutoRespostaDTO(produtoRepository.save(produto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void adminRemoverProduto(Long idProduto) {
        Produto produto = produtoRepository.getReferenceById(idProduto);
        for(Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
        produto.setDisponibilidade(Disponibilidade.INDISPONIVEL);
        produto.setVisibilidade(Boolean.FALSE);
        produtoRepository.save(produto);
    }

}
