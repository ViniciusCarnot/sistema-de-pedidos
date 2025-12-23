package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Categoria;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoDTO adicionarProduto(ProdutoDTO dto) {
        Optional<Produto> entity = produtoRepository.procurarPorNome(dto.getNome());
        if(entity.isPresent()) {
            throw new RecursoJaExistenteException("Já existe um produto cadastrado com esse nome.");
        }
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        return new ProdutoDTO(produtoRepository.save(produto));
    }

    @Transactional
    public void removerProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if(produtoOptional.isEmpty()) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com esse id.");
        }
        for(Categoria categoria : produtoOptional.get().getCategorias()) {
            categoria.getProdutos().remove(produtoOptional.get());
        }
        produtoRepository.delete(produtoOptional.get());
    }

    @Transactional(readOnly = true)
    public ProdutoDTO lerProduto(Long id) {
        Optional<Produto> entity = produtoRepository.findById(id);
        if(entity.isEmpty()) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com esse id.");
        }
        return new ProdutoDTO(entity.get());
    }

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> lerProdutos(Pageable pageable) {
        return produtoRepository.lerProdutos(pageable);
    }

    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO dto) {
        Optional<Produto> entity = produtoRepository.findById(id);
        if(entity.isEmpty()) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com esse id.");
        }
        entity.get().setNome(dto.getNome());
        entity.get().setPreco(dto.getPreco());
        return new ProdutoDTO(produtoRepository.save(entity.get()));
    }


}
