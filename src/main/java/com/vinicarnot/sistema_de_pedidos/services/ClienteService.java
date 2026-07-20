package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.projections.UserDetailsProjecao;
import com.vinicarnot.sistema_de_pedidos.repositories.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import com.vinicarnot.sistema_de_pedidos.util.CustomUserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    private final RoleRepository roleRepository;

    private final CustomUserUtil customUserUtil;

    private final TelefoneRepository telefoneRepository;

    public ClienteService(ClienteRepository clienteRepository, RoleRepository roleRepository, CustomUserUtil customUserUtil, TelefoneRepository telefoneRepository) {
        this.clienteRepository = clienteRepository;
        this.roleRepository = roleRepository;
        this.customUserUtil = customUserUtil;
        this.telefoneRepository = telefoneRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjecao> result = clienteRepository.procurarUserDetailsProjectionPorEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email não encontrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setEmail(result.get(0).getUsername());
        cliente.setSenha(result.get(0).getSenha());
        cliente.setAtivo(result.get(0).getAtivo());
        for (UserDetailsProjecao projection : result) {
            cliente.adicionarRole(new Role(projection.getRoleId(), projection.getRoleNome()));
        }

        return cliente;
    }

    public Cliente autenticado() {
        try {
            String username = customUserUtil.getClienteLogado().get();
            return clienteRepository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Usuário inválido.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public CriarCadastroClienteRespostaDTO cadastrarCliente(CriarCadastroClienteRequisicaoDTO dtoRequisicao) {

        if(clienteRepository.existsByEmail(dtoRequisicao.getEmail())) {
            throw new RecursoJaExistenteException("Já existe uma conta cadastrada com o email: " + dtoRequisicao.getEmail() + ".");
        }

        if(telefoneRepository.existsTelefoneByNumero(dtoRequisicao.getTelefone().getNumero())) {
            throw new RecursoJaExistenteException("Já existe uma conta cadastrada com o telefone: " + dtoRequisicao.getTelefone().getNumero() + ".");
        }

        Role role = roleRepository.findByNome("ROLE_NORMAL")
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível atribuir a role 'ROLE_NORMAL' ao novo cliente."));

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dtoRequisicao.getSenha());

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(dtoRequisicao.getNome());
        novoCliente.setEmail(dtoRequisicao.getEmail());
        novoCliente.setSenha(senhaCriptografada);
        novoCliente.setCpfOuCnpj(dtoRequisicao.getCpfOuCnpj());
        novoCliente.setTipo(dtoRequisicao.getTipo());
        novoCliente.setAtivo(true);
        novoCliente.adicionarRole(role);
        novoCliente.setTelefone(new Telefone(dtoRequisicao.getTelefone().getNumero(), novoCliente));

        return new CriarCadastroClienteRespostaDTO(clienteRepository.save(novoCliente));

    }

    @Transactional(readOnly = true)
    public Page<AdminLerClienteRespostaDTO> adminLerClientes(Pageable pageable) {
        Page<Cliente> clientePage = clienteRepository.procurarTodosOsClientesERoles(pageable);
        return clientePage.map(cliente -> new AdminLerClienteRespostaDTO(cliente));
    }

    @Transactional(readOnly = true)
    public AdminLerClienteRespostaDTO adminLerCliente(String emailCliente) {
        Cliente cliente = clienteRepository.procurarClienteERolesPorEmail(emailCliente)
                .orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrar uma conta cadastrada com o email: " + emailCliente + "."));
        return new AdminLerClienteRespostaDTO(cliente);
    }

    @Transactional(readOnly = true)
    public LerMinhaContaRespostaDTO verMinhaConta() {
        Cliente cliente = autenticado();
        return new LerMinhaContaRespostaDTO(cliente);
    }

    @Transactional(rollbackFor = Exception.class)
    public AtualizarMinhaContaRespostaDTO atualizarMinhaConta(AtualizarMinhaContaRequisicaoDTO dtoRequisicao) {

        Cliente cliente = autenticado();

        // Usuário quer mudar o email da conta
        if(!(dtoRequisicao.getEmail().equals(cliente.getEmail()))) {

            // Verifica se o email desejado está disponível
            if(clienteRepository.existsByEmail(dtoRequisicao.getEmail())) {
                throw new RecursoJaExistenteException("Já existe uma conta cadastrada com o email: " + dtoRequisicao.getEmail() + ".");
            }

        }

        // Usuário quer mudar o telefone da conta
        if(!(dtoRequisicao.getTelefone().getNumero().equals(cliente.getTelefone().getNumero()))) {

            // Verifica se o telefone desejado está disponível
            if(telefoneRepository.existsTelefoneByNumero(dtoRequisicao.getTelefone().getNumero())) {
                throw new RecursoJaExistenteException("Já existe uma conta cadastrada com o telefone: " + dtoRequisicao.getTelefone().getNumero());
            }

        }


        cliente.setNome(dtoRequisicao.getNome());
        cliente.setEmail(dtoRequisicao.getEmail());

        String senhaCriptografada = new BCryptPasswordEncoder().encode(dtoRequisicao.getSenha());

        cliente.setSenha(senhaCriptografada);

        cliente.setCpfOuCnpj(dtoRequisicao.getCpfOuCnpj());
        cliente.setTipo(dtoRequisicao.getTipo());

        cliente.getTelefone().setNumero(dtoRequisicao.getTelefone().getNumero());

        return new AtualizarMinhaContaRespostaDTO(cliente);

    }

}
