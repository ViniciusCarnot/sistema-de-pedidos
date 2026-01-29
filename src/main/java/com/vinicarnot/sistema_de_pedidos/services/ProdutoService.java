package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateProdutoResponseDTO;
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

    @Transactional(rollbackFor = Exception.class)
    public CreateProdutoResponseDTO adicionarProduto(CreateProdutoRequestDTO createProdutoRequestDTO) {
        Optional<Produto> produto = produtoRepository.procurarPorNome(createProdutoRequestDTO.getNome());
        if(produto.isPresent()) {
            throw new RecursoJaExistenteException("Já existe um produto cadastrado com esse nome.");
        }
        Produto novoProduto = new Produto();
        novoProduto.setNome(createProdutoRequestDTO.getNome());
        novoProduto.setPreco(createProdutoRequestDTO.getPreco());
        return new CreateProdutoResponseDTO(produtoRepository.save(novoProduto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerProduto(Long id) {
        Produto produto = produtoRepository.getReferenceById(id);
        for(Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
        produtoRepository.delete(produto);
    }

    @Transactional(readOnly = true)
    public ReadProdutoResponseDTO lerProduto(Long id) {
        Produto produto = produtoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com esse id."));
        return new ReadProdutoResponseDTO(produto);
    }

    @Transactional(readOnly = true)
    public Page<ReadProdutoResponseDTO> lerProdutos(Pageable pageable) {
        return produtoRepository.lerProdutos(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdateProdutoResponseDTO atualizarProduto(Long id, UpdateProdutoRequestDTO updateProdutoRequestDTO) {
        Produto produto = produtoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com esse id."));
        produto.setNome(updateProdutoRequestDTO.getNome());
        produto.setPreco(updateProdutoRequestDTO.getPreco());
        return new UpdateProdutoResponseDTO(produtoRepository.save(produto));
    }


}
