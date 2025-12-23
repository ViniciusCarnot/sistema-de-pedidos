package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.CategoriaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.ProdutoDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Categoria;
import com.vinicarnot.sistema_de_pedidos.entities.Produto;
import com.vinicarnot.sistema_de_pedidos.repositories.CategoriaRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @Transactional
    public CategoriaDTO adicionarCategoria(CategoriaDTO dto) {
        Optional<Categoria> categoriaOptional = categoriaRepository.procurarPorNome(dto.getNome());
        if(categoriaOptional.isPresent()) {
            throw new RecursoJaExistenteException("Já existe uma categoria cadastrada com esse nome.");
        }
        /*Categoria entity = new Categoria();
        entity.setNome(dto.getNome());
        for(ProdutoDTO pDTO : dto.getProdutos()) {
            Produto pEntity = new Produto();
            pEntity.setId(pDTO.getId());
            entity.getProdutos().add(pEntity);
        }*/

        Categoria entity = new Categoria();
        entity.setNome(dto.getNome());
        Optional<Produto> produtoDTOptional;
        for(ProdutoDTO produtoDTO : dto.getProdutos()) {
            produtoDTOptional = produtoRepository.findById(produtoDTO.getId());
            if(produtoDTOptional.isEmpty()) {
                throw new RecursoNaoEncontradoException("Produto com id: " + produtoDTO.getId() + " não encontrado.");
            }
            Produto produto = new Produto();
            produto.setId(produtoDTO.getId());
            entity.getProdutos().add(produto);
        }
        return new CategoriaDTO(categoriaRepository.save(entity));



        /*for(ProdutoDTO pDTO : dto.getProdutos()) {
            Produto pEntity = produtoRepository.getReferenceById(pDTO.getId());
            entity.getProdutos().add(pEntity);
        }
        return new CategoriaDTO(categoriaRepository.save(entity));*/
    }

    @Transactional
    public void removerCategoria(Long id) {
        Optional<Categoria> entity = categoriaRepository.findById(id);
        if(entity.isEmpty()) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada com esse id.");
        }
        categoriaRepository.delete(entity.get());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO lerCategoria(Long id) {
        Optional<Categoria> entity = categoriaRepository.findById(id);
        if(entity.isEmpty()) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada com esse id.");
        }
        return new CategoriaDTO(entity.get());
    }

    @Transactional(readOnly = true)
    public Page<CategoriaDTO> lerCategorias(Pageable pageable) {
        Page<Categoria> paginaEntity = categoriaRepository.lerCategorias(pageable);
        if (paginaEntity.hasContent()) {
            categoriaRepository.lerCategoriasComProdutos(paginaEntity.getContent());
        }
        return paginaEntity.map(entity -> new CategoriaDTO(entity));
    }

    @Transactional
    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO dto) {
        Optional<Categoria> entity = categoriaRepository.findById(id);
        if(entity.isEmpty()) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada com esse id.");
        }
        entity.get().setNome(dto.getNome());
        entity.get().getProdutos().clear();
        Optional<Produto> produtoDTOptional;
        for(ProdutoDTO produtoDTO : dto.getProdutos()) {
            produtoDTOptional = produtoRepository.findById(produtoDTO.getId());
            if(produtoDTOptional.isEmpty()) {
                throw new RecursoNaoEncontradoException("Produto com id: " + produtoDTO.getId() + " não encontrado.");
            }
            Produto produto = new Produto();
            produto.setId(produtoDTO.getId());
            entity.get().getProdutos().add(produto);
        }
        return new CategoriaDTO(categoriaRepository.save(entity.get()));
    }

    public boolean validacaoExitenciaCategoria(String nomeCategoria) {
        if(categoriaRepository.procurarPorNome(nomeCategoria).isPresent()) {
            return true;
        }
        return false;
    }
}
