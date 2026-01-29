package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.*;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.CreatePedidoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.UpdatePedidoResponseDTO;
import com.vinicarnot.sistema_de_pedidos.entities.*;
import com.vinicarnot.sistema_de_pedidos.repositories.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ClienteComDadosIncompletosException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class PedidoService {

    private final ClienteRepository clienteRepository;

    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    private final EnderecoService enderecoService;

    private final PagamentoService pagamentoService;

    private final ItemPedidoService itemPedidoService;

    public PedidoService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EnderecoService enderecoService, PagamentoService pagamentoService, ItemPedidoService itemPedidoService) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoService = enderecoService;
        this.pagamentoService = pagamentoService;
        this.itemPedidoService = itemPedidoService;
    }

    @Transactional(rollbackFor = Exception.class)
    public CreatePedidoResponseDTO realizarPedido(CreatePedidoRequestDTO dtoRequest) {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível achar uma conta cadastrada com esse email e senha. Verifique ambos."));

        if(cliente.getCpfOuCnpj() == null || cliente.getTelefones() == null || cliente.getTipo() == null) {
            throw new ClienteComDadosIncompletosException("Seus dados estão incompletos para prosseguir com o pedido. " +
                    "Termine de preenche-los e tente novamente.");
        }

        Pedido pedido = new Pedido();
        pedido.setInstanteDaCompra(Instant.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

        Endereco endereco = enderecoService.criarEndereco(cliente, dtoRequest.getEnderecoDeEntrega());
        cliente.getEnderecos().add(endereco);
        pedido.setEnderecoDeEntrega(endereco);

        itemPedidoService.adicionarItemPedido(pedido, dtoRequest.getItems());

        Pagamento pagamento = pagamentoService.criarFormaDePagamento(pedido, dtoRequest.getPagamento());
        pedido.setPagamento(pagamento);

        return new CreatePedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdatePedidoResponseDTO atualizarPedido(Long idPedido, UpdatePedidoRequestDTO dtoRequest) {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Cliente cliente = clienteRepository.getReferenceById(clienteLogado.getId());

        Pedido pedido = pedidoRepository.getReferenceById(idPedido);
        StatusPedido statusPedido = pedido.getStatusPedido();

        if(statusPedido.equals(StatusPedido.AGUARDANDO_PAGAMENTO)) {
            //Necessário pois estamos utilizando @MapsId
            pedido.setPagamento(null);
            Pagamento pagamento = pagamentoService.atualizarFormaDePagamento(pedido, dtoRequest.getPagamento());
            pedido.setPagamento(pagamento);
        }

        if(statusPedido.equals(StatusPedido.AGUARDANDO_PAGAMENTO) ||
        statusPedido.equals(StatusPedido.PAGAMENTO_APROVADO) ||
        statusPedido.equals(StatusPedido.EM_SEPARACAO) ||
        statusPedido.equals(StatusPedido.PRONTO_PARA_ENVIO)) {
            Endereco endereco = enderecoService.atualizarEndereco(cliente, dtoRequest.getEnderecoDeEntrega());
            pedido.setEnderecoDeEntrega(endereco);
        }

        return new UpdatePedidoResponseDTO(pedidoRepository.save(pedido));
    }
}
