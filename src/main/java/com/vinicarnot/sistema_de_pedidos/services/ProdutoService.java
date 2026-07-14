package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarProdutoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateProdutoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadProdutoResponseAdminDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerProdutoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateProdutoResponseDTO;
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
        Produto produto = produtoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com esse id."));
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
            throw new RecursoJaExistenteException("Já existe um produto cadastrado com esse nome.");
        }
        Produto novoProduto = new Produto();
        novoProduto.setNome(dtoRequest.getNome());
        novoProduto.setPreco(dtoRequest.getPreco());
        novoProduto.setStatusProduto(dtoRequest.getStatusProduto());
        return new CriarProdutoRespostaDTO(produtoRepository.save(novoProduto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerProduto(Long id) {
        Produto produto = produtoRepository.getReferenceById(id);
        for(Categoria categoria : produto.getCategorias()) {
            categoria.getProdutos().remove(produto);
        }
        produtoRepository.delete(produto);
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdateProdutoResponseDTO atualizarProduto(Long id, UpdateProdutoRequestDTO dtoRequest) {
        Produto produto = produtoRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com esse id."));
        produto.setNome(dtoRequest.getNome());
        produto.setPreco(dtoRequest.getPreco());
        produto.setStatusProduto(dtoRequest.getStatusProduto());
        return new UpdateProdutoResponseDTO(produtoRepository.save(produto));
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
