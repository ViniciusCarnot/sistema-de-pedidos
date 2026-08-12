package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarMinhaContaRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarTelefoneRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarCadastroClienteRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarCadastroClienteTelefoneRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerClienteRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AtualizarMinhaContaRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarCadastroClienteRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerMinhaContaRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.factory.ClienteFactory;
import com.vinicarnot.sistema_de_pedidos.projections.UserDetailsProjecao;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.RoleRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.TelefoneRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoJaExistenteException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import com.vinicarnot.sistema_de_pedidos.util.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ClienteServiceTesteUnitario {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CustomUserUtil customUserUtil;

    @Mock
    private TelefoneRepository telefoneRepository;

    private Cliente clienteAdmin, clienteNormal;
    private Role roleAdmin, roleNormal;
    private String emailInvalido;


    @BeforeEach
    void setUp() throws Exception {

        clienteAdmin = ClienteFactory.instanciarClienteAdmin();
        clienteNormal = ClienteFactory.instanciarClienteNormal();

        roleNormal = new Role(1L, "ROLE_NORMAL");
        roleAdmin = new Role(2L, "ROLE_ADMIN");

        emailInvalido = "xxx";

    }


    @Test
    public void loadUserByUsernameDeveriaRetornarUserDetailsQuandoClienteAdminLogado() {

        // Cria um mock para a interface (também poderíamos utilizar @Mock na declaração da interface)
        UserDetailsProjecao projecaoMock = Mockito.mock(UserDetailsProjecao.class);
        Mockito.when(projecaoMock.getUsername()).thenReturn(clienteAdmin.getEmail());
        Mockito.when(projecaoMock.getSenha()).thenReturn(clienteAdmin.getSenha());
        Mockito.when(projecaoMock.getRoleId()).thenReturn(roleAdmin.getId());
        Mockito.when(projecaoMock.getRoleNome()).thenReturn(roleAdmin.getAuthority());
        Mockito.when(projecaoMock.getAtivo()).thenReturn(true);

        UserDetailsProjecao projecaoMock2 = Mockito.mock(UserDetailsProjecao.class);
        Mockito.when(projecaoMock2.getUsername()).thenReturn(clienteAdmin.getEmail());
        Mockito.when(projecaoMock2.getSenha()).thenReturn(clienteAdmin.getSenha());
        Mockito.when(projecaoMock2.getRoleId()).thenReturn(roleNormal.getId());
        Mockito.when(projecaoMock2.getRoleNome()).thenReturn(roleNormal.getAuthority());
        Mockito.when(projecaoMock2.getAtivo()).thenReturn(true);

        List<UserDetailsProjecao> userDetailsProjecaoLista = new ArrayList<>(List.of(projecaoMock, projecaoMock2));

        Mockito.when(clienteRepository.procurarUserDetailsProjectionPorEmail(clienteAdmin.getEmail())).thenReturn(userDetailsProjecaoLista);

        UserDetails cliente = clienteService.loadUserByUsername(clienteAdmin.getEmail());

        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(clienteAdmin.getEmail(), cliente.getUsername());
        Assertions.assertEquals(clienteAdmin.getSenha(), cliente.getPassword());
        Assertions.assertEquals(2, cliente.getAuthorities().size());
        Assertions.assertTrue(cliente.getAuthorities().contains(roleAdmin));
        Assertions.assertTrue(cliente.getAuthorities().contains(roleNormal));

        Mockito.verify(clienteRepository, Mockito.times(1))
                .procurarUserDetailsProjectionPorEmail(clienteAdmin.getEmail());

    }

    @Test
    public void loadUserByUsernameDeveriaRetornarUserDetailsQuandoClienteNormalLogado() {

        // Cria um mock para a interface (também poderíamos utilizar @Mock na declaração da interface)
        UserDetailsProjecao projecaoMock = Mockito.mock(UserDetailsProjecao.class);
        Mockito.when(projecaoMock.getUsername()).thenReturn(clienteNormal.getEmail());
        Mockito.when(projecaoMock.getSenha()).thenReturn(clienteNormal.getSenha());
        Mockito.when(projecaoMock.getRoleId()).thenReturn(roleNormal.getId());
        Mockito.when(projecaoMock.getRoleNome()).thenReturn(roleNormal.getAuthority());
        Mockito.when(projecaoMock.getAtivo()).thenReturn(true);

        List<UserDetailsProjecao> userDetailsProjecaoLista = new ArrayList<>(List.of(projecaoMock));

        Mockito.when(clienteRepository.procurarUserDetailsProjectionPorEmail(clienteNormal.getEmail())).thenReturn(userDetailsProjecaoLista);

        UserDetails cliente = clienteService.loadUserByUsername(clienteNormal.getEmail());

        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(clienteNormal.getEmail(), cliente.getUsername());
        Assertions.assertEquals(clienteNormal.getSenha(), cliente.getPassword());
        Assertions.assertEquals(1, cliente.getAuthorities().size());
        Assertions.assertTrue(cliente.getAuthorities().contains(roleNormal));

        Mockito.verify(clienteRepository, Mockito.times(1))
                .procurarUserDetailsProjectionPorEmail(clienteNormal.getEmail());

    }

    @Test
    public void loadUserByUsernameDeveriaLancarUsernameNotFoundExceptionQuandoClienteEmailInvalido() {

        List<UserDetailsProjecao> userDetailsProjecaoLista = new ArrayList<>();

        Mockito.when(clienteRepository.procurarUserDetailsProjectionPorEmail(emailInvalido)).thenReturn(userDetailsProjecaoLista);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            clienteService.loadUserByUsername(emailInvalido);

        });

        Mockito.verify(clienteRepository, Mockito.times(1))
                .procurarUserDetailsProjectionPorEmail(emailInvalido);

    }

    @Test
    public void autenticadoDeveriaRetornarClienteQuandoClienteCredenciaisValidas() {

        Mockito.when(customUserUtil.getClienteLogado()).thenReturn(Optional.of(clienteAdmin.getEmail()));
        Mockito.when(clienteRepository.findByEmail(clienteAdmin.getEmail())).thenReturn(Optional.of(clienteAdmin));

        Assertions.assertDoesNotThrow(() -> {

            clienteService.autenticado();

        });

        Mockito.verify(customUserUtil, Mockito.times(1)).getClienteLogado();
        Mockito.verify(clienteRepository, Mockito.times(1)).findByEmail(clienteAdmin.getEmail());

    }

    @Test
    public void autenticadoDeveriaRetornarClienteQuandoClienteCredenciaisInvalidas() {

        Mockito.when(customUserUtil.getClienteLogado()).thenReturn(Optional.empty());
        Mockito.when(clienteRepository.findByEmail(emailInvalido)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            clienteService.autenticado();

        });

        Mockito.verify(customUserUtil, Mockito.times(1)).getClienteLogado();

    }

    @Test
    public void cadastrarClienteDeveriaRetornarCriarCadastroClienteRespostaDTOQuandoClienteEmailDisponivelEClienteTelefoneDisponivelERoleNomeExiste() {

        Mockito.when(clienteRepository.existsByEmail(clienteNormal.getEmail())).thenReturn(false);
        Mockito.when(telefoneRepository.existsTelefoneByNumero(clienteNormal.getTelefone().getNumero())).thenReturn(false);
        Mockito.when(roleRepository.findByNome(roleNormal.getAuthority())).thenReturn(Optional.of(roleNormal));
        Mockito.when(clienteRepository.save(ArgumentMatchers.any())).thenReturn(clienteNormal);

        CriarCadastroClienteRequisicaoDTO dtoRequisicao = new CriarCadastroClienteRequisicaoDTO();
        dtoRequisicao.setNome(clienteNormal.getNome());
        dtoRequisicao.setEmail(clienteNormal.getEmail());
        dtoRequisicao.setSenha(clienteNormal.getSenha());
        dtoRequisicao.setCpfOuCnpj(clienteNormal.getCpfOuCnpj());
        dtoRequisicao.setTipo(clienteNormal.getTipo());
        dtoRequisicao.setTelefone(new CriarCadastroClienteTelefoneRequisicaoDTO(clienteNormal.getTelefone().getNumero()));

        CriarCadastroClienteRespostaDTO dtoResposta = clienteService.cadastrarCliente(dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);

        Assertions.assertEquals(clienteNormal.getNome(), dtoResposta.getNome());
        Assertions.assertEquals(clienteNormal.getEmail(), dtoResposta.getEmail());
        Assertions.assertEquals(clienteNormal.getCpfOuCnpj(), dtoResposta.getCpfOuCnpj());
        Assertions.assertEquals(clienteNormal.getTipo(), dtoResposta.getTipo());
        Assertions.assertEquals(clienteNormal.getTelefone().getNumero(), dtoResposta.getTelefone().getNumero());
        Assertions.assertTrue(dtoResposta.getRoles().get(0).getNome().contains(roleNormal.getAuthority()));

    }

    @Test
    public void cadastrarClienteDeveriaLancarRecursoJaExistenteExcecaoQuandoClienteEmailIndisponivel() {

        Mockito.when(clienteRepository.existsByEmail(clienteNormal.getEmail())).thenReturn(true);

        CriarCadastroClienteRequisicaoDTO dtoRequisicao = new CriarCadastroClienteRequisicaoDTO();
        dtoRequisicao.setNome(clienteNormal.getNome());
        dtoRequisicao.setEmail(clienteNormal.getEmail());
        dtoRequisicao.setSenha(clienteNormal.getSenha());
        dtoRequisicao.setCpfOuCnpj(clienteNormal.getCpfOuCnpj());
        dtoRequisicao.setTipo(clienteNormal.getTipo());
        dtoRequisicao.setTelefone(new CriarCadastroClienteTelefoneRequisicaoDTO(clienteNormal.getTelefone().getNumero()));

        Assertions.assertThrows(RecursoJaExistenteException.class, () -> {

            clienteService.cadastrarCliente(dtoRequisicao);

        });

        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(clienteNormal.getEmail());

    }

    @Test
    public void cadastrarClienteDeveriaLancarRecursoJaExistenteExcecaoQuandoClienteEmailDisponivelEClienteTelefoneIndisponivel() {

        Mockito.when(clienteRepository.existsByEmail(clienteNormal.getEmail())).thenReturn(false);
        Mockito.when(telefoneRepository.existsTelefoneByNumero(clienteNormal.getTelefone().getNumero())).thenReturn(true);

        CriarCadastroClienteRequisicaoDTO dtoRequisicao = new CriarCadastroClienteRequisicaoDTO();
        dtoRequisicao.setNome(clienteNormal.getNome());
        dtoRequisicao.setEmail(clienteNormal.getEmail());
        dtoRequisicao.setSenha(clienteNormal.getSenha());
        dtoRequisicao.setCpfOuCnpj(clienteNormal.getCpfOuCnpj());
        dtoRequisicao.setTipo(clienteNormal.getTipo());
        dtoRequisicao.setTelefone(new CriarCadastroClienteTelefoneRequisicaoDTO(clienteNormal.getTelefone().getNumero()));

        Assertions.assertThrows(RecursoJaExistenteException.class, () -> {

            clienteService.cadastrarCliente(dtoRequisicao);

        });

        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(clienteNormal.getEmail());
        Mockito.verify(telefoneRepository, Mockito.times(1)).existsTelefoneByNumero(clienteNormal.getTelefone().getNumero());

    }

    @Test
    public void cadastrarClienteDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteEmailDisponivelEClienteTelefoneDisponivelERoleNomeNaoExiste() {

        Mockito.when(clienteRepository.existsByEmail(clienteNormal.getEmail())).thenReturn(false);
        Mockito.when(telefoneRepository.existsTelefoneByNumero(clienteNormal.getTelefone().getNumero())).thenReturn(false);
        Mockito.when(roleRepository.findByNome(roleNormal.getAuthority())).thenReturn(Optional.empty());

        CriarCadastroClienteRequisicaoDTO dtoRequisicao = new CriarCadastroClienteRequisicaoDTO();
        dtoRequisicao.setNome(clienteNormal.getNome());
        dtoRequisicao.setEmail(clienteNormal.getEmail());
        dtoRequisicao.setSenha(clienteNormal.getSenha());
        dtoRequisicao.setCpfOuCnpj(clienteNormal.getCpfOuCnpj());
        dtoRequisicao.setTipo(clienteNormal.getTipo());
        dtoRequisicao.setTelefone(new CriarCadastroClienteTelefoneRequisicaoDTO(clienteNormal.getTelefone().getNumero()));

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            clienteService.cadastrarCliente(dtoRequisicao);

        });

        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(clienteNormal.getEmail());
        Mockito.verify(telefoneRepository, Mockito.times(1)).existsTelefoneByNumero(clienteNormal.getTelefone().getNumero());
        Mockito.verify(roleRepository, Mockito.times(1)).findByNome(roleNormal.getAuthority());

    }

    @Test
    public void adminLerClientesDeveriaRetornarAdminLerClienteRespostaDTOPage() {

        PageImpl<Cliente> clientePage = new PageImpl<>(List.of(clienteAdmin, clienteNormal));

        Mockito.when(clienteRepository.procurarTodosOsClientesERoles((Pageable) ArgumentMatchers.any())).thenReturn(clientePage);

        Page<AdminLerClienteRespostaDTO> adminLerClienteRespostaDTOPage =
                new PageImpl<>(List.of(new AdminLerClienteRespostaDTO(clienteNormal), new AdminLerClienteRespostaDTO(clienteAdmin)));

        Assertions.assertNotNull(adminLerClienteRespostaDTOPage);
        Assertions.assertEquals(2, adminLerClienteRespostaDTOPage.getSize());
        Assertions.assertTrue(adminLerClienteRespostaDTOPage.stream().anyMatch(cliente -> clienteAdmin.getEmail().equals(cliente.getEmail())));
        Assertions.assertTrue(adminLerClienteRespostaDTOPage.stream().anyMatch(cliente -> clienteNormal.getEmail().equals(cliente.getEmail())));
        Assertions.assertTrue(adminLerClienteRespostaDTOPage.stream().anyMatch(cliente -> clienteAdmin.getTelefone().getNumero().equals(cliente.getTelefone().getNumero())));
        Assertions.assertTrue(adminLerClienteRespostaDTOPage.stream().anyMatch(cliente -> clienteNormal.getTelefone().getNumero().equals(cliente.getTelefone().getNumero())));

    }

    @Test
    public void adminLerClienteDeveriaRetornarAdminLerClienteRespostaDTOQuandoClienteEmailExiste() {

        Mockito.when(clienteRepository.procurarClienteERolesPorEmail(ArgumentMatchers.any())).thenReturn(Optional.of(clienteNormal));

        AdminLerClienteRespostaDTO dtoResposta = new AdminLerClienteRespostaDTO(clienteNormal);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(clienteNormal.getId(), dtoResposta.getId());
        Assertions.assertEquals(clienteNormal.getEmail(), dtoResposta.getEmail());
        Assertions.assertEquals(clienteNormal.getTelefone().getNumero(), dtoResposta.getTelefone().getNumero());

    }

    @Test
    public void adminLerClienteDeveriaLancarUsernameNotFoundExceptionQuandoClienteEmailNaoExiste() {

        Mockito.when(clienteRepository.procurarClienteERolesPorEmail(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            clienteService.adminLerCliente(clienteNormal.getEmail());

        });

    }

    @Test
    public void verMinhaContaDeveriaRetornarLerMinhaContaRespostaDTOQuandocClienteAutenticado() {

        ClienteService spyClienteService = Mockito.spy(clienteService);

        //Mockito.when(spyClienteService.autenticado()).thenReturn(clienteNormal);
        Mockito.doReturn(clienteNormal).when(spyClienteService).autenticado();

        LerMinhaContaRespostaDTO dtoResposta = spyClienteService.verMinhaConta();

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(clienteNormal.getEmail(), dtoResposta.getEmail());

    }

    @Test
    public void verMinhaContaDeveriaLancarUsernameNotFoundExceptionQuandocClienteNaoAutenticado() {

        ClienteService spyClienteService = Mockito.spy(clienteService);

        //Mockito.when(spyClienteService.autenticado()).thenReturn(clienteNormal);
        Mockito.doThrow(UsernameNotFoundException.class).when(spyClienteService).autenticado();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            spyClienteService.verMinhaConta();

        });

    }

    @Test
    public void atualizarMinhaContaDeveriaRetornarAtualizarMinhaContaRespostaDTOQuandoClienteAutenticadoEClienteEmailDisponivelEClienteTelefoneDisponivel() {

        ClienteService spyClienteService = Mockito.spy(clienteService);

        String novoEmail = "novoemail@email.com";
        String novoTelefone = "(00) 00000-0000";

        Mockito.doReturn(clienteNormal).when(spyClienteService).autenticado();
        Mockito.when(clienteRepository.existsByEmail(novoEmail)).thenReturn(false);
        Mockito.when(telefoneRepository.existsTelefoneByNumero(novoTelefone)).thenReturn(false);

        AtualizarMinhaContaRequisicaoDTO dtoRequisicao = new AtualizarMinhaContaRequisicaoDTO(
                clienteNormal.getNome(),
                novoEmail,
                clienteNormal.getSenha(),
                clienteNormal.getCpfOuCnpj(),
                clienteNormal.getTipo(),
                new AtualizarTelefoneRequisicaoDTO(novoTelefone)
        );

        AtualizarMinhaContaRespostaDTO dtoResposta = spyClienteService.atualizarMinhaConta(dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(clienteNormal.getId(), dtoResposta.getId());
        Assertions.assertEquals(clienteNormal.getNome(), dtoResposta.getNome());
        Assertions.assertEquals(novoEmail, dtoResposta.getEmail());
        Assertions.assertEquals(clienteNormal.getCpfOuCnpj(), dtoResposta.getCpfOuCnpj());
        Assertions.assertEquals(clienteNormal.getTipo(), dtoResposta.getTipo());
        Assertions.assertEquals(novoTelefone, dtoResposta.getTelefone().getNumero());

        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(novoEmail);
        Mockito.verify(telefoneRepository, Mockito.times(1)).existsTelefoneByNumero(novoTelefone);

    }

    @Test
    public void atualizarMinhaContaDeveriaRetornarAtualizarMinhaContaRespostaDTOQuandoClienteNaoAutenticado() {

        ClienteService spyClienteService = Mockito.spy(clienteService);

        Mockito.doThrow(UsernameNotFoundException.class).when(spyClienteService).autenticado();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            spyClienteService.atualizarMinhaConta(ArgumentMatchers.any());

        });

    }

    @Test
    public void atualizarMinhaContaDeveriaRetornarAtualizarMinhaContaRespostaDTOQuandoClienteNaoAutenticado() {

        ClienteService spyClienteService = Mockito.spy(clienteService);

        Mockito.doThrow(UsernameNotFoundException.class).when(spyClienteService).autenticado();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            spyClienteService.atualizarMinhaConta(ArgumentMatchers.any());

        });

    }

}
