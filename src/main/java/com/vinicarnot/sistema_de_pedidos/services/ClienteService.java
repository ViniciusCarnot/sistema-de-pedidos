package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateClienteRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateEnderecoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateTelefoneRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreateClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.ReadClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdateClienteResponseDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import com.vinicarnot.sistema_de_pedidos.projections.UserDetailsProjection;
import com.vinicarnot.sistema_de_pedidos.repositories.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import com.vinicarnot.sistema_de_pedidos.util.CustomUserUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    private final RoleRepository roleRepository;

    private final CustomUserUtil customUserUtil;

    private final EnderecoService enderecoService;

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, TelefoneRepository telefoneRepository, RoleRepository roleRepository, CustomUserUtil customUserUtil, EnderecoService enderecoService) {
        this.clienteRepository = clienteRepository;
        this.roleRepository = roleRepository;
        this.customUserUtil = customUserUtil;
        this.enderecoService = enderecoService;
    }

    @Transactional(rollbackFor = Exception.class)
    public CreateClienteResponseDTO registrar(CreateClienteRequestDTO dtoRequest) {
        if(clienteRepository.findByEmail(dtoRequest.getEmail()).isEmpty()) {
            throw new RecursoJaExistenteException("Já existe uma conta cadastrada com esse email.");
        }
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dtoRequest.getSenha());
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(dtoRequest.getNome());
        novoCliente.setEmail(dtoRequest.getEmail());
        novoCliente.setSenha(senhaCriptografada);
        Role role = roleRepository.findByNome("ROLE_NORMAL")
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível atribuir a role 'ROLE_NORMAL' ao novo cliente."));
        novoCliente.adicionarRole(role);

        return new CreateClienteResponseDTO(clienteRepository.save(novoCliente));

    }

    @Transactional(rollbackFor = Exception.class)
    public UpdateClienteResponseDTO atualizarDadosDoCliente(UpdateClienteRequestDTO dtoRequest) {

        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId()).
                orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        cliente.setNome(dtoRequest.getNome());
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
            endereco.getClientes().add(cliente);
        }

        return new UpdateClienteResponseDTO(clienteRepository.save(cliente));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = clienteRepository.procurarClienteERolesPorEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email não encontrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setEmail(result.get(0).getUsername());
        cliente.setSenha(result.get(0).getSenha());
        for (UserDetailsProjection projection : result) {
            cliente.adicionarRole(new Role(projection.getRoleId(), projection.getRoleNome()));
        }

        return cliente;
    }

    protected Cliente autenticado() {
        try {
            String username = customUserUtil.getClienteLogado().get();
            return clienteRepository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Usuário inválido.");
        }
    }

    @Transactional(readOnly = true)
    public ReadClienteResponseDTO getMe() {
        Cliente entity = autenticado();
        return new ReadClienteResponseDTO(entity);
    }

}
