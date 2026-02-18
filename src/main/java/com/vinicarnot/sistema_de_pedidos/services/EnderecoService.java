package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateEnderecoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateEnderecoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cidade;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Estado;
import com.vinicarnot.sistema_de_pedidos.repositories.CidadeRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EnderecoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EstadoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    private final EstadoRepository estadoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Endereco criarEndereco(Cliente cliente, CreateEnderecoRequestDTO dtoRequest) {
        Cidade cidade = cidadeRepository.findById(dtoRequest.getCidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar uma cidade com o id: " + dtoRequest.getCidade().getId() + "."));

        Estado estado = estadoRepository.findById(dtoRequest.getEstado().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar um estado com o id: " + dtoRequest.getEstado().getId() + "."));

        String logradouro = dtoRequest.getLogradouro();
        String numero = dtoRequest.getNumero();
        String bairro = dtoRequest.getBairro();

        return enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(logradouro, numero, bairro, cidade, estado)
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
    public Endereco atualizarEndereco(Cliente cliente, UpdateEnderecoRequestDTO dtoRequest) {
        Cidade cidade = cidadeRepository.findById(dtoRequest.getCidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar uma cidade com o id: " + dtoRequest.getCidade().getId() + "."));

        Estado estado = estadoRepository.findById(dtoRequest.getEstado().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar um estado com o id: " + dtoRequest.getEstado().getId() + "."));

        String logradouro = dtoRequest.getLogradouro();
        String numero = dtoRequest.getNumero();
        String bairro = dtoRequest.getBairro();

        return enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(logradouro, numero, bairro, cidade, estado)
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

}
