package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoItemPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.PedidoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.factory.PedidoFactory;
import com.vinicarnot.sistema_de_pedidos.projections.LerEnderecoRespostaProjecao;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EnderecoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.PedidoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ProdutoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ForbiddenException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ProdutoEsgotadoException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class PedidoServiceTesteUnitario {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private Pedido pedido1, pedido2;
    private Long pedidoIdInexistente;

    @BeforeEach
    void setUp() throws Exception {

        pedido1 = PedidoFactory.instanciarPedido();
        pedido2 = PedidoFactory.instanciarPedido2();

        pedidoIdInexistente = 1000L;

    }

    @Test
    public void verMeuPedidoDeveriaRetornarPedidoRespostaDTOQuandoClienteAutenticadoEPedidoIdExisteEClienteDono() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());
        Mockito.when(pedidoRepository.procurarPedidoEPagamentoEClienteEEnderecoECidadeEEstadoEItemsPedidoPorId(pedido1.getId())).thenReturn(Optional.of(pedido1));

        PedidoRespostaDTO dtoResposta = pedidoService.verMeuPedido(pedido1.getId());

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(pedido1.getId(), dtoResposta.getPedidoId());
        Assertions.assertEquals(pedido1.getCliente().getNome(), dtoResposta.getCompradorNome());
        Assertions.assertEquals(pedido1.getInstanteDaCompra(), dtoResposta.getDataDeEmissao());
        Assertions.assertEquals(pedido1.getStatusPedido(), dtoResposta.getPedidoStatus());
        Assertions.assertEquals(2, dtoResposta.getItems().size());
        Assertions.assertEquals(pedido1.getEnderecoDeEntrega().getLogradouro(), dtoResposta.getEnderecoDeEntrega().getLogradouro());

    }

    @Test
    public void verMeuPedidoDeveriaLancarUsernameNotFoundExceptionQuandoClienteNaoAutenticado() {

        Mockito.when(clienteService.autenticado()).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            pedidoService.verMeuPedido(pedido1.getId());

        });

    }

    @Test
    public void verMeuPedidoDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteAutenticadoEPedidoIdNaoExiste() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());
        Mockito.when(pedidoRepository.procurarPedidoEPagamentoEClienteEEnderecoECidadeEEstadoEItemsPedidoPorId(pedidoIdInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            pedidoService.verMeuPedido(pedidoIdInexistente);

        });

    }

    @Test
    public void verMeuPedidoDeveriaLancarForbiddenExcecaoQuandoClienteAutenticadoEPedidoIdExisteEClienteNaoDono() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());
        Mockito.when(pedidoRepository.procurarPedidoEPagamentoEClienteEEnderecoECidadeEEstadoEItemsPedidoPorId(pedido2.getId())).thenReturn(Optional.of(pedido2));

        Assertions.assertThrows(ForbiddenException.class, () -> {

            pedidoService.verMeuPedido(pedido2.getId());

        });

    }

    @Test
    public void verMeusPedidosDeveriaRetornarPedidoRespostaDTOPageQuandoClienteAutenticado() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());

        PageRequest pageable = PageRequest.of(0, 12);

        PageImpl<Pedido> pedidoPage = new PageImpl<>(List.of(pedido1));

        Mockito.when(pedidoRepository.findByClienteId(pedido1.getCliente().getId(), pageable)).thenReturn(pedidoPage);

        Page<PedidoRespostaDTO> dtoResposta = pedidoService.verMeusPedidos(pageable);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertTrue(dtoResposta.stream().anyMatch(pedido -> pedido.getPedidoId().equals(pedido1.getId())));
        Assertions.assertTrue(dtoResposta.stream().anyMatch(pedido -> pedido.getCompradorNome().equals(pedido1.getCliente().getNome())));
        Assertions.assertEquals(1, dtoResposta.getSize());

        Mockito.verify(clienteService, Mockito.times(1)).autenticado();
        Mockito.verify(pedidoRepository, Mockito.times(1)).findByClienteId(pedido1.getCliente().getId(), pageable);

    }

    @Test
    public void verMeusPedidosDeveriaLancarUsernameNotFoundExcecaoQuandoClienteNaoAutenticado() {

        Mockito.when(clienteService.autenticado()).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            pedidoService.verMeusPedidos(Pageable.unpaged());

        });

        Mockito.verify(clienteService, Mockito.times(1)).autenticado();

    }

    @Test
    public void realizarPedidoDeveriaRetornarPedidoRespostaDTOQuandoClienteAutenticadoEProdutosIdsExistemEProdutosVisiveisEProdutosDisponiveisEEnderecoIdExisteEEnderecoCadastrado() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());
        Mockito.when(produtoRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(pedido1.getItemsPedidos().iterator().next().getProduto());
        Mockito.when(enderecoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(pedido1.getEnderecoDeEntrega()));
        Mockito.when(pedidoRepository.save(ArgumentMatchers.any())).thenReturn(pedido1);

        LerEnderecoRespostaProjecao projecaoMock = Mockito.mock(LerEnderecoRespostaProjecao.class);
        Mockito.when(projecaoMock.getEnderecoId()).thenReturn(pedido1.getEnderecoDeEntrega().getId());
        Mockito.when(projecaoMock.getLogradouro()).thenReturn(pedido1.getEnderecoDeEntrega().getLogradouro());
        Mockito.when(projecaoMock.getNumero()).thenReturn(pedido1.getEnderecoDeEntrega().getNumero());
        Mockito.when(projecaoMock.getBairro()).thenReturn(pedido1.getEnderecoDeEntrega().getBairro());
        Mockito.when(projecaoMock.getCidadeNome()).thenReturn(pedido1.getEnderecoDeEntrega().getCidade().getNome());
        Mockito.when(projecaoMock.getEstadoNome()).thenReturn(pedido1.getEnderecoDeEntrega().getCidade().getEstado().getNome());

        Mockito.when(enderecoRepository.procurarEnderecosPorCliente(ArgumentMatchers.any())).thenReturn(List.of(projecaoMock));

        PedidoService spyPedidoService = Mockito.spy(pedidoService);
        Mockito.doNothing().when(spyPedidoService).validarEndereco(pedido1.getEnderecoDeEntrega(), pedido1.getCliente().getEmail());

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();
        CriarPedidoItemPedidoRequisicaoDTO itemPedidoRequisicaoDTO = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoRequisicaoDTO.setProdutoId(1L);
        itemPedidoRequisicaoDTO.setQuantidade(1);
        dtoRequisicao.getItems().add(itemPedidoRequisicaoDTO);
        dtoRequisicao.setEnderecoDeEntregaId(pedido1.getEnderecoDeEntrega().getId());

        PedidoRespostaDTO dtoResposta = pedidoService.realizarPedido(dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(pedido1.getId(), dtoResposta.getPedidoId());
        Assertions.assertEquals(pedido1.getCliente().getNome(), dtoResposta.getCompradorNome());
        Assertions.assertEquals(pedido1.getInstanteDaCompra(), dtoResposta.getDataDeEmissao());
        Assertions.assertEquals(pedido1.getStatusPedido(), dtoResposta.getPedidoStatus());
        Assertions.assertEquals(2, dtoResposta.getItems().size());
        Assertions.assertEquals(pedido1.getEnderecoDeEntrega().getLogradouro(), dtoResposta.getEnderecoDeEntrega().getLogradouro());

    }

    @Test
    public void realizarPedidoDeveriaLancarUsernameNotFoundExcecaoQuandoClienteNaoAutenticado() {

        Mockito.when(clienteService.autenticado()).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            pedidoService.realizarPedido(new CriarPedidoRequisicaoDTO());

        });

    }

    @Test
    public void realizarPedidoDeveriaLancarEntityNotFoundExceptionQuandoClienteAutenticadoEAlgumProdutoIdNaoExiste() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());
        Mockito.when(produtoRepository.getReferenceById(ArgumentMatchers.any())).thenThrow(EntityNotFoundException.class);

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();
        CriarPedidoItemPedidoRequisicaoDTO itemPedidoRequisicaoDTO = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoRequisicaoDTO.setProdutoId(1L);
        itemPedidoRequisicaoDTO.setQuantidade(1);
        dtoRequisicao.getItems().add(itemPedidoRequisicaoDTO);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {

            pedidoService.realizarPedido(dtoRequisicao);

        });

    }

    @Test
    public void realizarPedidoDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteAutenticadoEProdutosIdsExistemEAlgumProdutoNaoVisivel() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());

        Produto produto = new Produto(1L, "Samsung A54 5G", BigDecimal.valueOf(1750.00), Disponibilidade.DISPONIVEL, false);
        Mockito.when(produtoRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(produto);

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();
        CriarPedidoItemPedidoRequisicaoDTO itemPedidoRequisicaoDTO = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoRequisicaoDTO.setProdutoId(produto.getId());
        itemPedidoRequisicaoDTO.setQuantidade(1);
        dtoRequisicao.getItems().add(itemPedidoRequisicaoDTO);

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            pedidoService.realizarPedido(dtoRequisicao);

        });

    }

    @Test
    public void realizarPedidoDeveriaLancarProdutoEsgotadoExcecaoQuandoClienteAutenticadoEProdutosIdsExistemEProdutosVisiveisEAlgumProdutoIndisponivel() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());

        Produto produto = new Produto(1L, "Samsung A54 5G", BigDecimal.valueOf(1750.00), Disponibilidade.INDISPONIVEL, true);
        Mockito.when(produtoRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(produto);

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();
        CriarPedidoItemPedidoRequisicaoDTO itemPedidoRequisicaoDTO = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoRequisicaoDTO.setProdutoId(produto.getId());
        itemPedidoRequisicaoDTO.setQuantidade(1);
        dtoRequisicao.getItems().add(itemPedidoRequisicaoDTO);

        Assertions.assertThrows(ProdutoEsgotadoException.class, () -> {

            pedidoService.realizarPedido(dtoRequisicao);

        });

    }

    @Test
    public void realizarPedidoDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteAutenticadoEProdutosIdsExistemEProdutosVisiveisEProdutosDisponiveisEEnderecoIdNaoExiste() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());
        Mockito.when(produtoRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(pedido1.getItemsPedidos().iterator().next().getProduto());
        Mockito.when(enderecoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();
        CriarPedidoItemPedidoRequisicaoDTO itemPedidoRequisicaoDTO = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoRequisicaoDTO.setProdutoId(1L);
        itemPedidoRequisicaoDTO.setQuantidade(1);
        dtoRequisicao.getItems().add(itemPedidoRequisicaoDTO);
        dtoRequisicao.setEnderecoDeEntregaId(pedido1.getEnderecoDeEntrega().getId());

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            pedidoService.realizarPedido(dtoRequisicao);

        });

        Mockito.verify(enderecoRepository, Mockito.times(1)).findById(ArgumentMatchers.any());

    }

    @Test
    public void realizarPedidoDeveriaLancarRecursoNaoEncontradoExcecaoQuandoClienteAutenticadoEProdutosIdsExistemEProdutosVisiveisEProdutosDisponiveisEEnderecoIdExisteEEnderecoNaoCadastrado() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido1.getCliente());
        Mockito.when(produtoRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(pedido1.getItemsPedidos().iterator().next().getProduto());
        Mockito.when(enderecoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(pedido2.getEnderecoDeEntrega()));
        Mockito.when(pedidoRepository.save(ArgumentMatchers.any())).thenReturn(pedido1);

        LerEnderecoRespostaProjecao projecaoMock = Mockito.mock(LerEnderecoRespostaProjecao.class);
        Mockito.when(projecaoMock.getEnderecoId()).thenReturn(pedido1.getEnderecoDeEntrega().getId());
        Mockito.when(projecaoMock.getLogradouro()).thenReturn(pedido1.getEnderecoDeEntrega().getLogradouro());
        Mockito.when(projecaoMock.getNumero()).thenReturn(pedido1.getEnderecoDeEntrega().getNumero());
        Mockito.when(projecaoMock.getBairro()).thenReturn(pedido1.getEnderecoDeEntrega().getBairro());
        Mockito.when(projecaoMock.getCidadeNome()).thenReturn(pedido1.getEnderecoDeEntrega().getCidade().getNome());
        Mockito.when(projecaoMock.getEstadoNome()).thenReturn(pedido1.getEnderecoDeEntrega().getCidade().getEstado().getNome());

        Mockito.when(enderecoRepository.procurarEnderecosPorCliente(ArgumentMatchers.any())).thenReturn(List.of(projecaoMock));

        PedidoService spyPedidoService = Mockito.spy(pedidoService);
        Mockito.doThrow(RecursoNaoEncontradoException.class).when(spyPedidoService).validarEndereco(pedido2.getEnderecoDeEntrega(), pedido1.getCliente().getEmail());

        CriarPedidoRequisicaoDTO dtoRequisicao = new CriarPedidoRequisicaoDTO();
        CriarPedidoItemPedidoRequisicaoDTO itemPedidoRequisicaoDTO = new CriarPedidoItemPedidoRequisicaoDTO();
        itemPedidoRequisicaoDTO.setProdutoId(1L);
        itemPedidoRequisicaoDTO.setQuantidade(1);
        dtoRequisicao.getItems().add(itemPedidoRequisicaoDTO);
        dtoRequisicao.setEnderecoDeEntregaId(pedido1.getEnderecoDeEntrega().getId());

        Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> {

            pedidoService.realizarPedido(dtoRequisicao);

        });

        Mockito.verify(clienteService, Mockito.times(1)).autenticado();
        Mockito.verify(enderecoRepository, Mockito.times(1)).findById(ArgumentMatchers.any());
        Mockito.verify(enderecoRepository, Mockito.times(1)).procurarEnderecosPorCliente(pedido1.getCliente().getEmail());

    }


}
