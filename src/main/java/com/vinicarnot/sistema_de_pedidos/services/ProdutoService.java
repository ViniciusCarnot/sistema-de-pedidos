package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ProdutoRepetidoException;
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
        validacaoProdutoRepetido(entity);
        entity = produtoRepository.save(entity);
        return new ProdutoDTO(entity);
    }

    public void validacaoProdutoRepetido(Produto entity) {
        if(produtoRepository.procurarPorNome(entity.getNome()).isPresent()) {
            throw new ProdutoRepetidoException("Já existe um produto cadastrado com esse nome.");
        }
    }
}
