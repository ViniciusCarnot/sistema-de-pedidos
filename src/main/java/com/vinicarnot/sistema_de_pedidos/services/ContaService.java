package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.ClienteAtualizarDadosDTO;
import com.vinicarnot.sistema_de_pedidos.dto.EnderecoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.TelefoneDTO;
import com.vinicarnot.sistema_de_pedidos.entities.*;
import com.vinicarnot.sistema_de_pedidos.repositories.CidadeRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EnderecoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.TelefoneRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContaService {

    private final ClienteRepository clienteRepository;

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    private final TelefoneRepository telefoneRepository;

    public ContaService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, TelefoneRepository telefoneRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.telefoneRepository = telefoneRepository;
    }

    @Transactional
    public ClienteAtualizarDadosDTO atualizarDadosDoCliente(ClienteAtualizarDadosDTO dto) {

        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId()).
                orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpfOuCnpj(dto.getCpfOuCnpj());
        cliente.setTipo(dto.getTipo());

        cliente.getTelefones().clear();

        for(TelefoneDTO telefoneDTO : dto.getTelefones()) {
            Telefone telefone = new Telefone();
            telefone.setNumero(telefoneDTO.getNumero());
            telefone.setCliente(cliente);
            cliente.getTelefones().add(telefone);
        }

        cliente.getEnderecos().clear();

        for(EnderecoDTO enderecoDTO : dto.getEnderecos()) {
            Cidade cidade = cidadeRepository.findById(enderecoDTO.getCidade().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Cidade não encontrada."));

            Endereco endereco = enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidade(enderecoDTO.getLogradouro(),
                            enderecoDTO.getNumero(), enderecoDTO.getBairro(), cidade).
                    orElseGet(() -> {
                        Endereco novoEndereco = new Endereco();
                        novoEndereco.setLogradouro(enderecoDTO.getLogradouro());
                        novoEndereco.setNumero(enderecoDTO.getNumero());
                        novoEndereco.setBairro(enderecoDTO.getBairro());
                        novoEndereco.setCidade(cidade);
                        novoEndereco.getClientes().add(cliente);
                        return enderecoRepository.save(novoEndereco);
                    });

            cliente.getEnderecos().add(endereco);
        }

        return new ClienteAtualizarDadosDTO(clienteRepository.save(cliente));
    }

}
