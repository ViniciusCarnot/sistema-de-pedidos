package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.*;
import com.vinicarnot.sistema_de_pedidos.domain.enums.Disponibilidade;
import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusPedido;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoItemPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CriarPedidoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.projections.LerEnderecoRespostaProjecao;
import com.vinicarnot.sistema_de_pedidos.repositories.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final ClienteRepository clienteRepository;

    private final ClienteService clienteService;

    private final ProdutoRepository produtoRepository;

    private final PedidoRepository pedidoRepository;

    private final EnderecoService enderecoService;

    private final EnderecoRepository enderecoRepository;

    private final PagamentoService pagamentoService;

    private final ItemPedidoService itemPedidoService;

    private final PagamentoRepository pagamentoRepository;

    private final CidadeRepository cidadeRepository;

    private final EstadoRepository estadoRepository;

    public PedidoService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, ClienteService clienteService, ProdutoRepository produtoRepository1, EnderecoService enderecoService, EnderecoRepository enderecoRepository1, PagamentoService pagamentoService, ItemPedidoService itemPedidoService, PagamentoRepository pagamentoRepository, CidadeRepository cidadeRepository1, EstadoRepository estadoRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoRepository = produtoRepository1;
        this.enderecoService = enderecoService;
        this.enderecoRepository = enderecoRepository1;
        this.pagamentoService = pagamentoService;
        this.itemPedidoService = itemPedidoService;
        this.pagamentoRepository = pagamentoRepository;
        this.cidadeRepository = cidadeRepository1;
        this.estadoRepository = estadoRepository;
    }

    @Transactional(readOnly = true)
    public PedidoRespostaDTO verMeuPedido(Long pedidoId) {

        Cliente clienteLogado = clienteService.autenticado();

        Pedido pedido = pedidoRepository.procurarPedidoEPagamentoEClienteEEnderecoECidadeEEstadoEItemsPedidoPorId(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com o id: " + pedidoId + ", não foi encontrado."));

        if(!(pedido.getCliente().getId().equals(clienteLogado.getId()))) {
            throw new ForbiddenException("Você não tem permissão para acessar esse pedido.");
        }

        return new PedidoRespostaDTO(pedido);

    }

    @Transactional(readOnly = true)
    public Page<PedidoRespostaDTO> verMeusPedidos(Pageable pageable) {

        Cliente clienteLogado = clienteService.autenticado();

        Page<Pedido> pedidoPage = pedidoRepository.findByClienteId(clienteLogado.getId(), pageable);

        return pedidoPage.map(pedido -> new PedidoRespostaDTO(pedido));

    }

    @Transactional(rollbackFor = Exception.class)
    public PedidoRespostaDTO realizarPedido(CriarPedidoRequisicaoDTO dtoRequisicao) {

        Pedido pedido = new Pedido();

        Cliente cliente = clienteService.autenticado();

        for(CriarPedidoItemPedidoRequisicaoDTO itemPedidoRequisicaoDTO : dtoRequisicao.getItems()) {
            Produto produto = produtoRepository.getReferenceById(itemPedidoRequisicaoDTO.getProdutoId());
            validarProduto(produto);
            ItemPedido itemPedido = new ItemPedido(produto, pedido, itemPedidoRequisicaoDTO.getQuantidade());
            pedido.getItemsPedidos().add(itemPedido);
        }

        Endereco endereco = enderecoRepository.findById(dtoRequisicao.getEnderecoDeEntregaId())
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço de entrega com o id: " + dtoRequisicao.getEnderecoDeEntregaId() + ", não foi encontrado."));

        validarEndereco(endereco, cliente.getEmail());

        pedido.setCliente(cliente);

        pedido.setEnderecoDeEntrega(endereco);

        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

        return new PedidoRespostaDTO(pedidoRepository.save(pedido));

    }

    @Transactional(rollbackFor = Exception.class)
    public PedidoRespostaDTO atualizarMeuPedido(Long pedidoId, AtualizarPedidoRequisicaoDTO dtoRequisicao) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com o id: " + pedidoId + ", não foi encontrado."));

        Cliente clienteLogado = clienteService.autenticado();

        if(!(clienteLogado.getId().equals(pedido.getCliente().getId()))) {
            throw new ForbiddenException("Você não tem permissão para acessar esse pedido.");
        }

        StatusPedido statusAtual = pedido.getStatusPedido();

        switch (statusAtual) {
            case PRONTO_PARA_ENVIO, DESPACHADO, EM_ROTA_DE_ENTREGA, ENTREGUE, DEVOLVIDO, REEMBOLSADO, CANCELADO
                    -> throw new ProdutoCancelamentoExcecao("Pedido não pode ser atualizado.");
        }

        Endereco endereco = enderecoRepository.findById(dtoRequisicao.getEnderecoDeEntregaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço de entrega com o id: " + dtoRequisicao.getEnderecoDeEntregaId() + ", não foi encontrado."));

        validarEndereco(endereco, clienteLogado.getEmail());

        pedido.setEnderecoDeEntrega(endereco);

        return new PedidoRespostaDTO(pedido);
    }

    @Transactional(rollbackFor = Exception.class)
    public PedidoRespostaDTO cancelarMeuPedido(Long pedidoId) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com o id: " + pedidoId + ", não foi encontrado."));

        Cliente clienteLogado = clienteService.autenticado();

        if(!(clienteLogado.getId().equals(pedido.getCliente().getId()))) {
            throw new ForbiddenException("Você não tem permissão para acessar esse pedido.");
        }

        StatusPedido statusAtual = pedido.getStatusPedido();

        switch (statusAtual) {
            case PRONTO_PARA_ENVIO, DESPACHADO, EM_ROTA_DE_ENTREGA, ENTREGUE, DEVOLVIDO, REEMBOLSADO, CANCELADO
                    ->  throw new ProdutoCancelamentoExcecao("Pedido não pode ser cancelado. " +
                    "Caso deseje, recuse a entrega ou solicite a devolução.");
        }

        pedido.setStatusPedido(StatusPedido.CANCELADO);

        return new PedidoRespostaDTO(pedido);

    }

    @Transactional(readOnly = true)
    public PedidoRespostaDTO adminLerPedidoDoCliente(String clienteEmail, Long pedidoId) {

        if(!(clienteRepository.existsByEmail(clienteEmail))) {
            throw new RecursoNaoEncontradoException("Cliente com o email: " + clienteEmail + ", não foi encontrado.");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com o id: " + pedidoId + ", não foi encontrado."));

        if(!(pedido.getCliente().getEmail().equals(clienteEmail))) {
            throw new RecursoNaoEncontradoException("Cliente com o email: " + clienteEmail + ", não é dono do pedido com o id: " + pedidoId + ".");
        }

        return new PedidoRespostaDTO(pedido);

    }

    @Transactional(readOnly = true)
    public Page<AdminLerPedidoMinRespostaDTO> adminLerPedidosDoCliente(String clienteEmail, Pageable pageable) {

        if(!(clienteRepository.existsByEmail(clienteEmail))) {
            throw new RecursoNaoEncontradoException("Cliente com o email: " + clienteEmail + ", não foi encontrado.");
        }

        Page<Pedido> pedidoPage = pedidoRepository.procurarPedidoEPagamentoEClientePorClienteEmail(clienteEmail, pageable);

        return pedidoPage.map(pedido -> new AdminLerPedidoMinRespostaDTO(pedido));

    }

    public void validarProduto(Produto produto) {
        if(produto.getVisibilidade() == false) {
            throw new RecursoNaoEncontradoException("Produto com o id: " + produto.getId() + ", não foi encontrado.");
        }
        if(produto.getDisponibilidade().equals(Disponibilidade.INDISPONIVEL)) {
            throw new ProdutoEsgotadoException("Produto com id: " + produto.getId() + ", está esgotado.");
        }
    }

    public void validarEndereco(Endereco endereco, String clienteEmail) {

        List<LerEnderecoRespostaProjecao> enderecoRespostaProjecaoLista = enderecoRepository.procurarEnderecosPorCliente(clienteEmail);

        boolean enderecoCompativel = false;

        for(LerEnderecoRespostaProjecao enderecoRespostaProjecao : enderecoRespostaProjecaoLista) {
            if(enderecoRespostaProjecao.getEnderecoId().equals(endereco.getId())) {
                enderecoCompativel = true;
            }
        }

        if(enderecoCompativel == false) {
            throw new RecursoNaoEncontradoException("O endereço de entrega informado não foi previamente cadastrado.");
        }

    }

}
