package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminAtualizarCategoriaProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminAtualizarCategoriaRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminCriarCategoriaProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AdminCriarCategoriaRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.CategoriaRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.specifications.ProdutoSpecifications;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public Page<LerCategoriaRespostaDTO> lerCategorias(Pageable pageable) {
        Page<Categoria> pageCategoria = categoriaRepository.findAll(pageable);
        return pageCategoria.map(categoria -> new LerCategoriaRespostaDTO(categoria));
    }

    @Transactional(readOnly = true)
    public LerCategoriaRespostaDTO lerCategoria(Long idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com o id: " + idCategoria + ", não encontrada."));
        return new LerCategoriaRespostaDTO(categoria);
    }

    @Transactional(readOnly = true)
    public Page<LerProdutoRespostaDTO> lerProdutosDeUmaCategoria(Long idCategoria, Pageable pageable) {
        if(!categoriaRepository.existsById(idCategoria)) {
            throw new RecursoNaoEncontradoException("Categoria com o id: " + idCategoria + ", não encontrada.");
        }
        Specification<Produto> spec = ProdutoSpecifications.filtrar(
                null,
                null,
                List.of(idCategoria),
                true
        );
        Page<Produto> produtoPage = produtoRepository.findAll(spec, pageable);
        return produtoPage.map(produto -> new LerProdutoRespostaDTO(produto));
    }

    @Transactional(readOnly = true)
    public Page<AdminLerProdutoRespostaDTO> adminLerProdutosDeUmaCategoria(Long idCategoria, Pageable pageable) {
        if(!categoriaRepository.existsById(idCategoria)) {
            throw new RecursoNaoEncontradoException("Categoria com o id: " + idCategoria + ", não encontrada.");
        }
        Specification<Produto> spec = ProdutoSpecifications.filtrar(
                null,
                null,
                List.of(idCategoria),
                null
        );
        Page<Produto> produtoPage = produtoRepository.findAll(spec, pageable);
        return produtoPage.map(produto -> new AdminLerProdutoRespostaDTO(produto));
    }

    @Transactional(rollbackFor = Exception.class)
    public AdminCriarCategoriaRespostaDTO adminAdicionarCategoria(AdminCriarCategoriaRequisicaoDTO dtoRequisicao) {
        Optional<Categoria> categoria = categoriaRepository.findByNomeIgnoreCase(dtoRequisicao.getNome());
        if(categoria.isPresent()) {
            throw new RecursoJaExistenteException("Já existe uma categoria cadastrada com o nome: " + dtoRequisicao.getNome() + ".");
        }

        Categoria novaCategoria = new Categoria();
        for(AdminCriarCategoriaProdutoRequisicaoDTO produtoDTORequisicao : dtoRequisicao.getProdutos()) {
            Produto produto = produtoRepository.findById(produtoDTORequisicao.getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com id: " + produtoDTORequisicao.getId() + ", não encontrado."));
            novaCategoria.getProdutos().add(produto);
        }
        novaCategoria.setNome(dtoRequisicao.getNome());
        return new AdminCriarCategoriaRespostaDTO(categoriaRepository.save(novaCategoria));
    }

    @Transactional(rollbackFor = Exception.class)
    public AdminAtualizarCategoriaRespostaDTO adminAtualizarCategoria(Long idCategoria, AdminAtualizarCategoriaRequisicaoDTO dtoRequisicao) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com id: " + idCategoria + ", não encontrada."));
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNomeIgnoreCase(dtoRequisicao.getNome());
        if(categoriaOptional.isPresent()) {
            if(!dtoRequisicao.getNome().equalsIgnoreCase(categoria.getNome())) {
                throw new RecursoJaExistenteException("Já existe uma categoria cadastrada com o nome: " + dtoRequisicao.getNome() + ".");
            }
        }
        categoria.getProdutos().clear();
        for(AdminAtualizarCategoriaProdutoRequisicaoDTO produtoDTORequisicao : dtoRequisicao.getProdutos()) {
            Produto produto = produtoRepository.findById(produtoDTORequisicao.getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com id: " + produtoDTORequisicao.getId() + ", não encontrado."));
            categoria.getProdutos().add(produto);
        }
        categoria.setNome(dtoRequisicao.getNome());
        return new AdminAtualizarCategoriaRespostaDTO(categoriaRepository.save(categoria));
    }

    @Transactional(rollbackFor = Exception.class)
    public void adminRemoverCategoria(Long idCategoria) {
        if(!categoriaRepository.existsById(idCategoria)) {
            throw new RecursoNaoEncontradoException("Categoria com id: " + idCategoria + ", não encontrada.");
        }
        categoriaRepository.deleteById(idCategoria);
    }

}
