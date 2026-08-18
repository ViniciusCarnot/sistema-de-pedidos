package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarMeuEnderecoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerEnderecoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AtualizarMeuEnderecoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerEnderecoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.factory.ClienteFactory;
import com.vinicarnot.sistema_de_pedidos.factory.EnderecoFactory;
import com.vinicarnot.sistema_de_pedidos.projections.AdminLerEnderecoRespostaProjecao;
import com.vinicarnot.sistema_de_pedidos.projections.LerEnderecoRespostaProjecao;
import com.vinicarnot.sistema_de_pedidos.repositories.CidadeRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EnderecoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EstadoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTesteUnitario {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private EstadoRepository estadoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteService clienteService;

    private Endereco endereco1, endereco2;
    private Cliente clienteNormal, clienteAdmin;

    @BeforeEach
    void setUp() throws Exception {

        endereco1 = EnderecoFactory.instanciarEndereco();
        endereco2 = EnderecoFactory.instanciarEndereco2();

        clienteNormal = ClienteFactory.instanciarClienteNormal();
        clienteAdmin = ClienteFactory.instanciarClienteAdmin();

    }


    @Test
    public void adminLerEnderecosDoClienteDeveriaRetornarAdminLerEnderecoRespostaDTOListQuandoClienteEmailExiste() {

        AdminLerEnderecoRespostaProjecao projecaoMock = Mockito.mock(AdminLerEnderecoRespostaProjecao.class);
        Mockito.when(projecaoMock.getEnderecoId()).thenReturn(endereco1.getId());
        Mockito.when(projecaoMock.getLogradouro()).thenReturn(endereco1.getLogradouro());
        Mockito.when(projecaoMock.getNumero()).thenReturn(endereco1.getNumero());
        Mockito.when(projecaoMock.getBairro()).thenReturn(endereco1.getBairro());
        Mockito.when(projecaoMock.getCidadeId()).thenReturn(endereco1.getCidade().getId());
        Mockito.when(projecaoMock.getCidadeNome()).thenReturn(endereco1.getCidade().getNome());
        Mockito.when(projecaoMock.getEstadoId()).thenReturn(endereco1.getCidade().getEstado().getId());
        Mockito.when(projecaoMock.getEstadoNome()).thenReturn(endereco1.getCidade().getEstado().getNome());

        List<AdminLerEnderecoRespostaProjecao> enderecoRespostaProjecaoLista = new ArrayList<>(List.of(projecaoMock));

        AdminLerEnderecoRespostaDTO endereco1DTOResposta = new AdminLerEnderecoRespostaDTO(endereco1);

        List<AdminLerEnderecoRespostaDTO> enderecoRespostaDTOLista = new ArrayList<>(List.of(endereco1DTOResposta));

        Mockito.when(clienteRepository.existsByEmail(clienteNormal.getEmail())).thenReturn(true);
        Mockito.when(enderecoRepository.adminProcurarEnderecosPorCliente(clienteNormal.getEmail())).thenReturn(enderecoRespostaProjecaoLista);

        Assertions.assertNotNull(enderecoRespostaDTOLista);
        Assertions.assertEquals(1, enderecoRespostaDTOLista.size());
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getId().equals(endereco1.getId())));
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getLogradouro().equals(endereco1.getLogradouro())));
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getCidade().getNome().equals(endereco1.getCidade().getNome())));
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getEstado().getNome().equals(endereco1.getCidade().getEstado().getNome())));

    }

    @Test
    public void adminLerEnderecosDoClienteDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteEmailNaoExiste() {

        Mockito.when(clienteRepository.existsByEmail(clienteNormal.getEmail())).thenReturn(false);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            enderecoService.adminLerEnderecosDoCliente(clienteNormal.getEmail());

        });

        Mockito.verify(clienteRepository, Mockito.times(1)).existsByEmail(clienteNormal.getEmail());

    }

    @Test
    public void verMeusEnderecosDeveriaRetornarLerEnderecoRespostaDTOListQuandoClienteAutenticado() {

        Mockito.when(clienteService.autenticado()).thenReturn(clienteNormal);

        LerEnderecoRespostaProjecao projecaoMock = Mockito.mock(LerEnderecoRespostaProjecao.class);
        Mockito.when(projecaoMock.getEnderecoId()).thenReturn(endereco1.getId());
        Mockito.when(projecaoMock.getLogradouro()).thenReturn(endereco1.getLogradouro());
        Mockito.when(projecaoMock.getNumero()).thenReturn(endereco1.getNumero());
        Mockito.when(projecaoMock.getBairro()).thenReturn(endereco1.getBairro());
        Mockito.when(projecaoMock.getCidadeNome()).thenReturn(endereco1.getCidade().getNome());
        Mockito.when(projecaoMock.getEstadoNome()).thenReturn(endereco1.getCidade().getEstado().getNome());

        List<LerEnderecoRespostaProjecao> enderecoRespostaProjecaoLista = new ArrayList<>(List.of(projecaoMock));

        Mockito.when(enderecoRepository.procurarEnderecosPorCliente(clienteNormal.getEmail())).thenReturn(enderecoRespostaProjecaoLista);

        List<LerEnderecoRespostaDTO> enderecoRespostaDTOLista = new ArrayList<>(List.of(new LerEnderecoRespostaDTO(endereco1)));

        Assertions.assertNotNull(enderecoRespostaDTOLista);
        Assertions.assertEquals(1, enderecoRespostaDTOLista.size());
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getLogradouro().equals(endereco1.getLogradouro())));
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getNumero().equals(endereco1.getNumero())));
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getBairro().equals(endereco1.getBairro())));
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getCidadeNome().equals(endereco1.getCidade().getNome())));
        Assertions.assertTrue(enderecoRespostaDTOLista.stream().anyMatch(endereco -> endereco.getEstadoNome().equals(endereco1.getCidade().getEstado().getNome())));

    }

    @Test
    public void verMeusEnderecosDeveriaRetornarLerEnderecoRespostaDTOListQuandoClienteNaoAutenticado() {

        Mockito.when(clienteService.autenticado()).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            enderecoService.verMeusEnderecos();

        });

    }

    @Test
    public void atualizarMeusEnderecosDeveriaRetornarAtualizarMeuEnderecoRespostaDTOSetQuandoClienteAutenticadoECidadeIdExisteEEstadoIdExisteEEnderecoNovo() {

        Mockito.when(clienteService.autenticado()).thenReturn(clienteNormal);
        Mockito.when(cidadeRepository.findById(endereco1.getCidade().getId())).thenReturn(Optional.of(endereco1.getCidade()));
        Mockito.when(estadoRepository.findById(endereco1.getCidade().getEstado().getId())).thenReturn(Optional.of(endereco1.getCidade().getEstado()));
        Mockito.when(enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(
                endereco1.getLogradouro(),
                endereco1.getNumero(),
                endereco1.getBairro(),
                endereco1.getCidade(),
                endereco1.getCidade().getEstado()
        )).thenReturn(Optional.empty());

        List<AtualizarMeuEnderecoRequisicaoDTO> dtoRequisicao = new ArrayList<>(List.of(new AtualizarMeuEnderecoRequisicaoDTO(
                endereco1.getLogradouro(),
                endereco1.getNumero(),
                endereco1.getBairro(),
                endereco1.getCidade().getId(),
                endereco1.getCidade().getEstado().getId()
        )));

        Set<AtualizarMeuEnderecoRespostaDTO> dtoResposta = enderecoService.atualizarMeusEnderecos(dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getLogradouro().equals(endereco1.getLogradouro())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getNumero().equals(endereco1.getNumero())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getBairro().equals(endereco1.getBairro())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getCidade().getNome().equals(endereco1.getCidade().getNome())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getEstado().getNome().equals(endereco1.getCidade().getEstado().getNome())));

    }

    @Test
    public void atualizarMeusEnderecosDeveriaRetornarAtualizarMeuEnderecoRespostaDTOSetQuandoClienteAutenticadoECidadeIdExisteEEstadoIdExisteEEnderecoJaExistente() {

        Mockito.when(clienteService.autenticado()).thenReturn(clienteNormal);
        Mockito.when(cidadeRepository.findById(endereco1.getCidade().getId())).thenReturn(Optional.of(endereco1.getCidade()));
        Mockito.when(estadoRepository.findById(endereco1.getCidade().getEstado().getId())).thenReturn(Optional.of(endereco1.getCidade().getEstado()));
        Mockito.when(enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(
                endereco1.getLogradouro(),
                endereco1.getNumero(),
                endereco1.getBairro(),
                endereco1.getCidade(),
                endereco1.getCidade().getEstado()
        )).thenReturn(Optional.of(endereco1));

        List<AtualizarMeuEnderecoRequisicaoDTO> dtoRequisicao = new ArrayList<>(List.of(new AtualizarMeuEnderecoRequisicaoDTO(
                endereco1.getLogradouro(),
                endereco1.getNumero(),
                endereco1.getBairro(),
                endereco1.getCidade().getId(),
                endereco1.getCidade().getEstado().getId()
        )));

        Set<AtualizarMeuEnderecoRespostaDTO> dtoResposta = enderecoService.atualizarMeusEnderecos(dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getLogradouro().equals(endereco1.getLogradouro())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getNumero().equals(endereco1.getNumero())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getBairro().equals(endereco1.getBairro())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getCidade().getNome().equals(endereco1.getCidade().getNome())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(endereco -> endereco.getEstado().getNome().equals(endereco1.getCidade().getEstado().getNome())));

    }

    @Test
    public void atualizarMeusEnderecosDeveriaLancarUsernameNotFoundExceptionQuandoClienteNaoAutenticado() {

        Mockito.when(clienteService.autenticado()).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            enderecoService.atualizarMeusEnderecos(new ArrayList<>());

        });

    }

    @Test
    public void atualizarMeusEnderecosDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteAutenticadoECidadeIdNaoExiste() {

        Mockito.when(clienteService.autenticado()).thenReturn(clienteNormal);
        Mockito.when(cidadeRepository.findById(endereco1.getCidade().getId())).thenReturn(Optional.empty());

        List<AtualizarMeuEnderecoRequisicaoDTO> dtoRequisicao = new ArrayList<>(List.of(new AtualizarMeuEnderecoRequisicaoDTO(
                endereco1.getLogradouro(),
                endereco1.getNumero(),
                endereco1.getBairro(),
                endereco1.getCidade().getId(),
                endereco1.getCidade().getEstado().getId()
        )));

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            enderecoService.atualizarMeusEnderecos(dtoRequisicao);

        });

        Mockito.verify(cidadeRepository, Mockito.times(1)).findById(endereco1.getCidade().getId());

    }

    @Test
    public void atualizarMeusEnderecosDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteAutenticadoECidadeIdExisteEEstadoIdNaoExiste() {

        Mockito.when(clienteService.autenticado()).thenReturn(clienteNormal);
        Mockito.when(cidadeRepository.findById(endereco1.getCidade().getId())).thenReturn(Optional.of(endereco1.getCidade()));
        Mockito.when(estadoRepository.findById(endereco1.getCidade().getEstado().getId())).thenReturn(Optional.empty());

        List<AtualizarMeuEnderecoRequisicaoDTO> dtoRequisicao = new ArrayList<>(List.of(new AtualizarMeuEnderecoRequisicaoDTO(
                endereco1.getLogradouro(),
                endereco1.getNumero(),
                endereco1.getBairro(),
                endereco1.getCidade().getId(),
                endereco1.getCidade().getEstado().getId()
        )));

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            enderecoService.atualizarMeusEnderecos(dtoRequisicao);

        });

        Mockito.verify(cidadeRepository, Mockito.times(1)).findById(endereco1.getCidade().getId());
        Mockito.verify(estadoRepository, Mockito.times(1)).findById(endereco1.getCidade().getEstado().getId());


    }

}
