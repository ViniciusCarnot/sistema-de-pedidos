package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.*;
import com.vinicarnot.sistema_de_pedidos.domain.enums.EstadoPagamento;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoPagamento;
import com.vinicarnot.sistema_de_pedidos.dto.requests.*;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarBoletoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarCartaoDeCreditoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CriarPagamentoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.repositories.PagamentoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.PedidoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ForbiddenException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ProcessamentoPagamentoExcessao;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    private final PedidoRepository pedidoRepository;

    private final ClienteService clienteService;

    public PagamentoService(PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository, ClienteService clienteService) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
    }

    @Transactional(rollbackFor = Exception.class)
    public CriarPagamentoRespostaDTO realizarPagamentoDoPedido(Long pedidoId, CriarPagamentoRequisicaoDTO dtoRequisicao) {

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);

        Cliente cliente = clienteService.autenticado();

        if(!(pedido.getCliente().getId().equals(cliente.getId()))) {
            throw new ForbiddenException("Cliente com o id: " + cliente.getId() + ", não é dono do pedido com o id: " + pedidoId);
        }

        if(dtoRequisicao instanceof CriarBoletoRequisicaoDTO boletoRequisicaoDTO) {
            Boleto boleto = new Boleto();
            boleto.setEstadoPagamento(EstadoPagamento.PENDENTE);
            boleto.setTipoPagamento(TipoPagamento.BOLETO);
            boleto.setPedido(pedido);
            boleto.setPagadorCpfOuCnpj(boletoRequisicaoDTO.getPagadorCpfOuCnpj());
            boleto.setPagadorNome(boletoRequisicaoDTO.getPagadorNome());
            boleto.setPagadorEmail(boletoRequisicaoDTO.getPagadorEmail());
            boleto.setCodigoDeBarras("Código de Barras ...");
            // Boleto vence em 3 dias
            boleto.setDataVencimento(LocalDate.now().plusDays(3));
            boleto.setDataPagamento(null);
            pedido.setPagamento(boleto);
            return new CriarBoletoRespostaDTO(pagamentoRepository.save(boleto));
        }
        if(dtoRequisicao instanceof CriarCartaoDeCreditoRequisicaoDTO cartaoDeCreditoRequisicaoDTO) {
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setEstadoPagamento(EstadoPagamento.PENDENTE);
            cartaoDeCredito.setTipoPagamento(TipoPagamento.CARTAO_DE_CREDITO);
            cartaoDeCredito.setPedido(pedido);
            cartaoDeCredito.setQuantidadeParcelas(cartaoDeCreditoRequisicaoDTO.getQuantidadeDeParacelas());
            cartaoDeCredito.setSalvarCartaoParaProximasCompras(cartaoDeCreditoRequisicaoDTO.isSalvarCartaoParaProximasCompras());
            // Pagamento do cartão vence de acordo com a quantidade de parcelas escolhida
            cartaoDeCredito.setDataVencimento(LocalDate.now().plusMonths(cartaoDeCreditoRequisicaoDTO.getQuantidadeDeParacelas()));
            pedido.setPagamento(cartaoDeCredito);
            return new CriarCartaoDeCreditoRespostaDTO(pagamentoRepository.save(cartaoDeCredito));
        } else {
            throw new ProcessamentoPagamentoExcessao("Erro ao processar o pagamento.");
        }

    }

}
