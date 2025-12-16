package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoDTO adicionarProduto(ProdutoDTO dto) {
        Produto entity = new Produto(dto);
        if(validacaoExitenciaProduto(entity.getNome())) {
            throw new RecursoJaExistenteException("Já existe um produto cadastrado com esse nome.");
        }
        entity = produtoRepository.save(entity);
        return new ProdutoDTO(entity);
    }

    @Transactional
    public void removerProduto(String nomeProduto) {
        if(validacaoExitenciaProduto(nomeProduto) == false) {
            throw new RecursoNaoEncontradoException("Não existe um produto cadastrado com esse nome.");
        }
        Produto entity = produtoRepository.procurarPorNome(nomeProduto).get();
        produtoRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public ProdutoDTO lerProduto(String nomeProduto) {
        if(validacaoExitenciaProduto(nomeProduto) == false) {
            throw new RecursoNaoEncontradoException("Não existe um produto cadastrado com esse nome.");
        }
        Produto entity = produtoRepository.procurarPorNome(nomeProduto).get();
        return new ProdutoDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> lerProdutos(Pageable pageable) {
        return produtoRepository.lerProdutos(pageable);
    }

    public boolean validacaoExitenciaProduto(String nomeProduto) {
        if(produtoRepository.procurarPorNome(nomeProduto).isPresent()) {
            return true;
        }
        return false;
    }

}
