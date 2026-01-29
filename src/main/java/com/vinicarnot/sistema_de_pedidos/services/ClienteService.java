package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateEnderecoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateTelefoneRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateClienteResponseDTO;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    private final TelefoneRepository telefoneRepository;

    private final EnderecoService enderecoService;

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, TelefoneRepository telefoneRepository, EnderecoService enderecoService) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.telefoneRepository = telefoneRepository;
        this.enderecoService = enderecoService;
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdateClienteResponseDTO atualizarDadosDoCliente(UpdateClienteRequestDTO dtoRequest) {

        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId()).
                orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        cliente.setNome(dtoRequest.getNome());
        cliente.setEmail(dtoRequest.getEmail());
        cliente.setCpfOuCnpj(dtoRequest.getCpfOuCnpj());
        cliente.setTipo(dtoRequest.getTipoPessoa());

        cliente.getTelefones().clear();

        for(UpdateTelefoneRequestDTO updateTelefoneRequestDTO : dtoRequest.getTelefones()) {
            Telefone telefone = new Telefone();
            telefone.setNumero(updateTelefoneRequestDTO.getNumero());
            telefone.setCliente(cliente);
            cliente.getTelefones().add(telefone);
        }

        cliente.getEnderecos().clear();

        for(UpdateEnderecoRequestDTO updateEnderecoRequestDTO : dtoRequest.getEnderecos()) {
            Endereco endereco = enderecoService.atualizarEndereco(cliente, updateEnderecoRequestDTO);
            cliente.getEnderecos().add(endereco);
        }

        return new UpdateClienteResponseDTO(clienteRepository.save(cliente));
    }

}
