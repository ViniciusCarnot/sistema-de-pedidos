package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.CategoriaDTO;
import com.vinicarnot.sistema_de_pedidos.entities.Categoria;
import com.vinicarnot.sistema_de_pedidos.repositories.CategoriaRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public CategoriaDTO adicionarCategoria(CategoriaDTO dto) {
        if(validacaoExitenciaCategoria(dto.getNome())) {
            throw new RecursoJaExistenteException("Já existe uma categoria cadastrada com esse nome.");
        }
        Categoria entity = new Categoria(dto);
        return new CategoriaDTO(categoriaRepository.save(entity));
    }

    public boolean validacaoExitenciaCategoria(String nomeCategoria) {
        if(categoriaRepository.procurarPorNome(nomeCategoria).isPresent()) {
            return true;
        }
        return false;
    }

}
