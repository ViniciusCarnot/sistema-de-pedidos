package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseAdminDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusProduto;
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

    @Transactional(rollbackFor = Exception.class)
    public CreateProdutoResponseDTO adicionarProduto(CreateProdutoRequestDTO dtoRequest) {
        Optional<Produto> produto = produtoRepository.findByNomeIgnoreCase(dtoRequest.getNome());
        if(produto.isPresent()) {
            throw new RecursoJaExistenteException("Já existe um produto cadastrado com esse nome.");
        }
        Produto novoProduto = new Produto();
        novoProduto.setNome(dtoRequest.getNome());
        novoProduto.setPreco(dtoRequest.getPreco());
        novoProduto.setStatusProduto(dtoRequest.getStatusProduto());
        return new CreateProdutoResponseDTO(produtoRepository.save(novoProduto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerProduto(Long id) {
        Produto produto = produtoRepository.getReferenceById(id);
        for(Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
        produto.setStatusProduto(StatusProduto.INATIVO);
        produtoRepository.save(produto);
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdateProdutoResponseDTO atualizarProduto(Long id, UpdateProdutoRequestDTO dtoRequest) {
        Produto produto = produtoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com esse id."));
        produto.setPreco(dtoRequest.getPreco());
        produto.setStatusProduto(dtoRequest.getStatusProduto());
        return new UpdateProdutoResponseDTO(produtoRepository.save(produto));
    }

    @Transactional(readOnly = true)
    public ReadProdutoResponseDTO lerProduto(Long id) {
        Produto produto = produtoRepository.procurarPorIdComStatusAtivo(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com esse id."));
        return new ReadProdutoResponseDTO(produto);
    }

    @Transactional(readOnly = true)
    public Page<ReadProdutoResponseDTO> lerProdutos(Pageable pageable) {
        return produtoRepository.lerProdutosComStatusAtivo(pageable);
    }

    @Transactional(readOnly = true)
    public ReadProdutoResponseAdminDTO adminLerProduto(Long id) {
        Produto produto = produtoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com esse id."));
        return new ReadProdutoResponseAdminDTO(produto);
    }

    @Transactional(readOnly = true)
    public Page<ReadProdutoResponseAdminDTO> adminLerProdutos(Pageable pageable) {
        Page<Produto> pageProdutos = produtoRepository.findAll(pageable);
        return pageProdutos.map(produto -> new ReadProdutoResponseAdminDTO(produto));
    }

}
