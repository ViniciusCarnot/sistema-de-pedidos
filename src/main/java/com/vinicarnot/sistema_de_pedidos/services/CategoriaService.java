package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateCategoriaRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateProdutoCategoriaRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateCategoriaRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateProdutoCategoriaRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.CategoriaRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public CreateCategoriaResponseDTO adicionarCategoria(CreateCategoriaRequestDTO dtoRequest) {
        Optional<Categoria> categoria = categoriaRepository.findByNomeIgnoreCase(dtoRequest.getNome());
        if(categoria.isPresent()) {
            throw new RecursoJaExistenteException("Já existe uma categoria cadastrada com o nome: " + dtoRequest.getNome() + ".");
        }

        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(dtoRequest.getNome());
        for(CreateProdutoCategoriaRequestDTO createProdutoCategoriaRequestDTO : dtoRequest.getProdutos()) {
            Produto produto = produtoRepository.findById(createProdutoCategoriaRequestDTO.getId()).
                    orElseThrow(() -> new RecursoNaoEncontradoException("Produto com id: " + createProdutoCategoriaRequestDTO.getId() + " não encontrado."));
            novaCategoria.getProdutos().add(produto);
        }
        return new CreateCategoriaResponseDTO(categoriaRepository.save(novaCategoria));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerCategoria(Long id) {
        Categoria categoria = categoriaRepository.getReferenceById(id);
        categoriaRepository.delete(categoria);
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdateCategoriaResponseDTO atualizarCategoria(Long id, UpdateCategoriaRequestDTO dtoRequest) {
        Categoria categoria = categoriaRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com id: " + id + "não encontrada."));
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNomeIgnoreCase(dtoRequest.getNome());
        if(categoriaOptional.isPresent()) {
            throw new RecursoJaExistenteException("Já existe uma categoria cadastrada com o nome: " + dtoRequest.getNome() + ".");
        }
        categoria.setNome(dtoRequest.getNome());
        categoria.getProdutos().clear();
        for(UpdateProdutoCategoriaRequestDTO updateProdutoCategoriaRequestDTO : dtoRequest.getProdutos()) {
            Produto produto = produtoRepository.findById(updateProdutoCategoriaRequestDTO.getId()).
                    orElseThrow(() -> new RecursoNaoEncontradoException("Produto com id: " + updateProdutoCategoriaRequestDTO.getId() + " não encontrado."));
            categoria.getProdutos().add(produto);
        }
        return new UpdateCategoriaResponseDTO(categoriaRepository.save(categoria));
    }

    @Transactional(readOnly = true)
    public ReadCategoriaResponseAdminDTO adminLerCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id: " + id + "."));
        return new ReadCategoriaResponseAdminDTO(categoria);
    }

    @Transactional(readOnly = true)
    public Page<ReadCategoriaResponseAdminDTO> adminLerCategorias(Pageable pageable) {
        Page<Categoria> pageCategoria = categoriaRepository.findAll(pageable);
        return pageCategoria.map(categoria -> new ReadCategoriaResponseAdminDTO(categoria));
    }

}
