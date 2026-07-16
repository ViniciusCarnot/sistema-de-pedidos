package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AtualizarProdutoRespostaDTO;
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
        return new LerProdutoRespostaDTO(produto);
    }

    @Transactional(readOnly = true)
    public Page<LerProdutoRespostaDTO> lerProdutos(String nome, BigDecimal precoMaximo, List<Long> categoriasIds, Pageable pageable) {

        Specification<Produto> spec = ProdutoSpecifications.filtrar(nome, precoMaximo, categoriasIds);

        Page<Produto> produtoPage = produtoRepository.findAll(spec, pageable);

        return produtoPage.map(produto -> new LerProdutoRespostaDTO(produto));

    }

    @Transactional(rollbackFor = Exception.class)
    public CriarProdutoRespostaDTO adicionarProduto(CriarProdutoRequisicaoDTO dtoRequest) {
        Optional<Produto> produto = produtoRepository.findByNomeIgnoreCase(dtoRequest.getNome());
        if(produto.isPresent()) {
            throw new RecursoJaExistenteException("Já existe um produto cadastrado com o nome: " + dtoRequest.getNome() + ".");
        }
        Produto novoProduto = new Produto();
        novoProduto.setNome(dtoRequest.getNome());
        novoProduto.setPreco(dtoRequest.getPreco());
        novoProduto.setStatusProduto(dtoRequest.getStatusProduto());
        return new CriarProdutoRespostaDTO(produtoRepository.save(novoProduto));
    }

    @Transactional(rollbackFor = Exception.class)
    public AtualizarProdutoRespostaDTO atualizarProduto(Long idProduto, AtualizarProdutoRequisicaoDTO dtoRequest) {
        Produto produto = produtoRepository.findById(idProduto).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto com o id: " + idProduto + " não encontrado."));
        produto.setNome(dtoRequest.getNome());
        produto.setPreco(dtoRequest.getPreco());
        produto.setStatusProduto(dtoRequest.getStatusProduto());
        return new AtualizarProdutoRespostaDTO(produtoRepository.save(produto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerProduto(Long id) {
        Produto produto = produtoRepository.getReferenceById(id);
        for(Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
        produtoRepository.delete(produto);
    }

}
