package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Boleto;
import com.vinicarnot.sistema_de_pedidos.domain.entites.CartaoDeCredito;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarBoletoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarCartaoDeCreditoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPagamentoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarBoletoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarCartaoDeCreditoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.factory.ClienteFactory;
import com.vinicarnot.sistema_de_pedidos.factory.PagamentoFactory;
import com.vinicarnot.sistema_de_pedidos.factory.PedidoFactory;
import com.vinicarnot.sistema_de_pedidos.repositories.PagamentoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.PedidoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ForbiddenException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ProcessamentoPagamentoExcessao;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
public class PagamentoServiceTesteUnitario {

    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PedidoRepository pedidoRepository;

    private Long pedidoIdExistente, pedidoIdInexistente;
    private Pedido pedido;
    private CartaoDeCredito cartaoDeCredito;
    private Boleto boleto;
    private Cliente clienteAdmin;

    @BeforeEach
    void setUp() throws Exception {

        cartaoDeCredito = PagamentoFactory.instanciarCartaoDeCredito();
        boleto = PagamentoFactory.instanciarBoleto();

        pedido = cartaoDeCredito.getPedido();

        pedidoIdExistente = pedido.getId();
        pedidoIdInexistente = 1000L;

        clienteAdmin = ClienteFactory.instanciarClienteAdmin();

        Mockito.when(pedidoRepository.getReferenceById(pedidoIdExistente)).thenReturn(pedido);
        Mockito.when(pedidoRepository.getReferenceById(pedidoIdInexistente)).thenThrow(EntityNotFoundException.class);

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaRetornarCriarCartaoDeCreditoRespostaDTOQuandoPedidoIdExisteEClienteAutenticadoEClienteDonoDoPedidoEPagamentoValido() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido.getCliente());
        Mockito.when(pagamentoRepository.save(ArgumentMatchers.any())).thenReturn(cartaoDeCredito);

        CriarCartaoDeCreditoRequisicaoDTO dtoRequisicao = new CriarCartaoDeCreditoRequisicaoDTO();
        dtoRequisicao.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
        dtoRequisicao.setQuantidadeDeParacelas(3);
        dtoRequisicao.setSalvarCartaoParaProximasCompras(false);

        CriarCartaoDeCreditoRespostaDTO dtoResposta = (CriarCartaoDeCreditoRespostaDTO) pagamentoService.realizarPagamentoDoPedido(pedidoIdExistente, dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(cartaoDeCredito.getId(), dtoResposta.getId());
        Assertions.assertEquals(cartaoDeCredito.getTipoPagamento(), dtoResposta.getTipoPagamento());
        Assertions.assertEquals(cartaoDeCredito.getEstadoPagamento(), dtoResposta.getEstadoPagamento());
        Assertions.assertEquals(cartaoDeCredito.getQuantidadeParcelas(), dtoResposta.getQuantidadeParcelas());
        Assertions.assertEquals(cartaoDeCredito.getDataVencimento(), dtoResposta.getDataVencimento());


    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaRetornarCriarBoletoRespostaDTOQuandoPedidoIdExisteEClienteAutenticadoEClienteDonoDoPedidoEPagamentoValido() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido.getCliente());
        Mockito.when(pagamentoRepository.save(ArgumentMatchers.any())).thenReturn(boleto);

        CriarBoletoRequisicaoDTO dtoRequisicao = new CriarBoletoRequisicaoDTO();
        dtoRequisicao.setTipoPagamento(TipoPagamento.BOLETO);
        dtoRequisicao.setPagadorCpfOuCnpj(pedido.getCliente().getCpfOuCnpj());
        dtoRequisicao.setPagadorNome(pedido.getCliente().getNome());
        dtoRequisicao.setPagadorEmail(pedido.getCliente().getEmail());

        CriarBoletoRespostaDTO dtoResposta = (CriarBoletoRespostaDTO) pagamentoService.realizarPagamentoDoPedido(pedidoIdExistente, dtoRequisicao);

        Assertions.assertNotNull(dtoResposta);
        Assertions.assertEquals(boleto.getId(), dtoResposta.getId());
        Assertions.assertEquals(boleto.getTipoPagamento(), dtoResposta.getTipoPagamento());
        Assertions.assertEquals(boleto.getEstadoPagamento(), dtoResposta.getEstadoPagamento());
        Assertions.assertEquals(boleto.getPagadorCpfOuCnpj(), dtoResposta.getPagadorCpfOuCnpj());
        Assertions.assertEquals(boleto.getPagadorNome(), dtoResposta.getPagadorNome());
        Assertions.assertEquals(boleto.getPagadorEmail(), dtoResposta.getPagadorEmail());
        Assertions.assertEquals(boleto.getCodigoDeBarras(), dtoResposta.getCodigoDeBarras());
        Assertions.assertEquals(boleto.getDataVencimento(), dtoResposta.getDataVencimento());
        Assertions.assertNull(boleto.getDataPagamento());

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancarEntityNotFoundExceptionQuandoPedidoIdNaoExiste() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> {

            pagamentoService.realizarPagamentoDoPedido(pedidoIdInexistente, new CriarBoletoRequisicaoDTO());

        });

        Mockito.verify(pedidoRepository, Mockito.times(1)).getReferenceById(pedidoIdInexistente);

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancarUsernameNotFoundExceptionQuandoPedidoIdExisteEClienteNaoAutenticado() {

        Mockito.when(clienteService.autenticado()).thenThrow(UsernameNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {

            pagamentoService.realizarPagamentoDoPedido(pedidoIdExistente, new CriarBoletoRequisicaoDTO());

        });

        Mockito.verify(pedidoRepository, Mockito.times(1)).getReferenceById(pedidoIdExistente);
        Mockito.verify(clienteService, Mockito.times(1)).autenticado();

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancarForbiddenExceptionQuandoPedidoIdExisteEClienteAutenticadoEClienteNaoDonoDoPedido() {

        Mockito.when(clienteService.autenticado()).thenReturn(clienteAdmin);

        Assertions.assertThrows(ForbiddenException.class, () -> {

            pagamentoService.realizarPagamentoDoPedido(pedidoIdExistente, new CriarBoletoRequisicaoDTO());

        });

        Mockito.verify(pedidoRepository, Mockito.times(1)).getReferenceById(pedidoIdExistente);
        Mockito.verify(clienteService, Mockito.times(1)).autenticado();

    }

    @Test
    public void realizarPagamentoDoPedidoDeveriaLancarProcessamentoPagamentoExcessaoQuandoPedidoIdExisteEClienteAutenticadoEClienteDonoDoPedidoEPagamentoInvalido() {

        Mockito.when(clienteService.autenticado()).thenReturn(pedido.getCliente());

        Assertions.assertThrows(ProcessamentoPagamentoExcessao.class, () -> {

            pagamentoService.realizarPagamentoDoPedido(pedidoIdExistente, null);

        });

        Mockito.verify(pedidoRepository, Mockito.times(1)).getReferenceById(pedidoIdExistente);
        Mockito.verify(clienteService, Mockito.times(1)).autenticado();

    }

}
