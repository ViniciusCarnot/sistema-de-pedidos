package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.entities.Cidade;
import com.vinicarnot.sistema_de_pedidos.entities.Cliente;
import com.vinicarnot.sistema_de_pedidos.entities.Endereco;
import com.vinicarnot.sistema_de_pedidos.repositories.CidadeRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EnderecoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Endereco criarEndereco(Cliente cliente, CreateEnderecoRequestDTO createEnderecoRequestDTO) {
        Cidade cidade = cidadeRepository.findById(createEnderecoRequestDTO.getCidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cidade não encontrada."));

        String logradouro = createEnderecoRequestDTO.getLogradouro();
        String numero = createEnderecoRequestDTO.getNumero();
        String bairro = createEnderecoRequestDTO.getBairro();

        return enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidade(logradouro, numero, bairro, cidade)
                .orElseGet(() -> {
                    Endereco novoEndereco = new Endereco();
                    novoEndereco.setLogradouro(logradouro);
                    novoEndereco.setNumero(numero);
                    novoEndereco.setBairro(bairro);
                    novoEndereco.setCidade(cidade);
                    novoEndereco.getClientes().add(cliente);
                    return enderecoRepository.save(novoEndereco);
                });
    }

    @Transactional(rollbackFor = Exception.class)
    public Endereco atualizarEndereco(Cliente cliente, UpdateEnderecoRequestDTO updateEnderecoRequestDTO) {
        Cidade cidade = cidadeRepository.findById(updateEnderecoRequestDTO.getCidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cidade não encontrada."));

        String logradouro = updateEnderecoRequestDTO.getLogradouro();
        String numero = updateEnderecoRequestDTO.getNumero();
        String bairro = updateEnderecoRequestDTO.getBairro();

        return enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidade(logradouro,
                        numero, bairro, cidade).
                orElseGet(() -> {
                    Endereco novoEndereco = new Endereco();
                    novoEndereco.setLogradouro(logradouro);
                    novoEndereco.setNumero(numero);
                    novoEndereco.setBairro(bairro);
                    novoEndereco.setCidade(cidade);
                    novoEndereco.getClientes().add(cliente);
                    return enderecoRepository.save(novoEndereco);
                });
    }

}
