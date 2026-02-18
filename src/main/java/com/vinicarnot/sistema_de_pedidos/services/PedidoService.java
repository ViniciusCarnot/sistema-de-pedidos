package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pagamento;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Pedido;
import com.vinicarnot.sistema_de_pedidos.domain.enums.StatusPedido;
import com.vinicarnot.sistema_de_pedidos.dto.requests.CreatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdatePedidoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.repositories.*;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.ClienteComDadosIncompletosException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoModificavelException;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNegadoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class PedidoService {

    private final ClienteRepository clienteRepository;

    private final PedidoRepository pedidoRepository;

    private final EnderecoService enderecoService;

    private final PagamentoService pagamentoService;

    private final ItemPedidoService itemPedidoService;

    private final PagamentoRepository pagamentoRepository;

    public PedidoService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EnderecoService enderecoService, PagamentoService pagamentoService, ItemPedidoService itemPedidoService, PagamentoRepository pagamentoRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.enderecoService = enderecoService;
        this.pagamentoService = pagamentoService;
        this.itemPedidoService = itemPedidoService;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public CreatePedidoResponseDTO realizarPedido(CreatePedidoRequestDTO dtoRequest) {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível achar uma conta cadastrada com esse email e senha. Verifique ambos."));

        if(cliente.getCpfOuCnpj() == null ||
                cliente.getTelefones() == null ||
                cliente.getTipo() == null ||
                cliente.getEnderecos() ==  null
        ) {
            throw new ClienteComDadosIncompletosException("Os dados da sua conta estão incompletos para prosseguir com o pedido. " +
                    "Termine de preenche-los e tente novamente.");
        }

        Pedido pedido = new Pedido();
        pedido.setInstanteDaCompra(Instant.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);


        Endereco endereco = enderecoService.criarEndereco(cliente, dtoRequest.getEnderecoDeEntrega());
        if(cliente.getEnderecos().size() < 2) {
            cliente.getEnderecos().add(endereco);
        }
        pedido.setEnderecoDeEntrega(endereco);

        itemPedidoService.adicionarItemPedido(pedido, dtoRequest);

        Pagamento pagamento = pagamentoService.criarFormaDePagamento(pedido, dtoRequest.getPagamento());
        pedido.setPagamento(pagamento);

        return new CreatePedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelarProprioPedido(Long idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar o pedido com o id: " + idPedido + "."));

        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível achar uma conta cadastrada com esse email e senha. Verifique ambos."));

        if(!(pedido.getCliente().equals(cliente))) {
            throw new RecursoNegadoException("Você não tem permissão para cancelar esse pedido.");
        }

        if(pedido.getStatusPedido().equals(StatusPedido.ENTREGUE)) {
            throw new RecursoNaoModificavelException("Não é possível cancelar esse pedido, porque o status do pedido consta como entregue.");
        }

        pedido.setStatusPedido(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);

    }

    @Transactional(rollbackFor = Exception.class)
    public void adminCancelarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar o pedido com o id: " + idPedido + "."));
        if(pedido.getStatusPedido().equals(StatusPedido.ENTREGUE)) {
            throw new RecursoNaoModificavelException("Não é possível cancelar esse pedido, porque o status do pedido consta como entregue.");
        }
        pedido.setStatusPedido(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdatePedidoResponseDTO atualizarProprioPedido(Long idPedido, UpdatePedidoRequestDTO dtoRequest) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar o pedido com o id: " + idPedido + "."));

        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível achar uma conta cadastrada com esse email e senha. Verifique ambos."));

        if(!(pedido.getCliente().equals(cliente))) {
            throw new RecursoNegadoException("Você não tem permissão para alterar os dados desse pedido.");
        }

        StatusPedido statusPedido = pedido.getStatusPedido();

        if(statusPedido.equals(StatusPedido.AGUARDANDO_PAGAMENTO)) {

            Pagamento pagamentoAntigo = pedido.getPagamento();

            if(pagamentoAntigo != null) {
                //Necessário pois estamos utilizando @MapsId
                pedido.setPagamento(null);
                pagamentoRepository.flush();
            }
            Pagamento novoPagamento = pagamentoService.atualizarFormaDePagamento(pedido, dtoRequest.getPagamento());
            pedido.setPagamento(novoPagamento);

        }

        if(statusPedido.equals(StatusPedido.AGUARDANDO_PAGAMENTO) ||
        statusPedido.equals(StatusPedido.PAGAMENTO_APROVADO) ||
        statusPedido.equals(StatusPedido.EM_SEPARACAO) ||
        statusPedido.equals(StatusPedido.PRONTO_PARA_ENVIO)) {
            Endereco endereco = enderecoService.atualizarEndereco(cliente, dtoRequest.getEnderecoDeEntrega());
            if(cliente.getEnderecos().size() < 2) {
                cliente.getEnderecos().add(endereco);
            }
            pedido.setEnderecoDeEntrega(endereco);
        }

        return new UpdatePedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional(rollbackFor = Exception.class)
    public UpdatePedidoResponseAdminDTO adminAtualizarPedido(Long idPedido, UpdatePedidoRequestDTO dtoRequest) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar o pedido com o id: " + idPedido + "."));

        StatusPedido statusPedido = pedido.getStatusPedido();

        if(statusPedido.equals(StatusPedido.AGUARDANDO_PAGAMENTO)) {

            Pagamento pagamentoAntigo = pedido.getPagamento();

            if(pagamentoAntigo != null) {
                //Necessário pois estamos utilizando @MapsId
                pedido.setPagamento(null);
                pagamentoRepository.flush();
            }
            Pagamento novoPagamento = pagamentoService.atualizarFormaDePagamento(pedido, dtoRequest.getPagamento());
            pedido.setPagamento(novoPagamento);

        }

        if(statusPedido.equals(StatusPedido.AGUARDANDO_PAGAMENTO) ||
                statusPedido.equals(StatusPedido.PAGAMENTO_APROVADO) ||
                statusPedido.equals(StatusPedido.EM_SEPARACAO) ||
                statusPedido.equals(StatusPedido.PRONTO_PARA_ENVIO)) {
            Cliente cliente = pedido.getCliente();
            Endereco endereco = enderecoService.atualizarEndereco(cliente, dtoRequest.getEnderecoDeEntrega());
            if(cliente.getEnderecos().size() < 2) {
                cliente.getEnderecos().add(endereco);
            }
            pedido.setEnderecoDeEntrega(endereco);
        }

        return new UpdatePedidoResponseAdminDTO(pedidoRepository.save(pedido));
    }

    @Transactional(readOnly = true)
    public ReadPedidoResponseDTO lerProprioPedido(Long idPedido) {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível achar uma conta cadastrada com esse email e senha. Verifique ambos."));
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar o pedido com o id: " + idPedido + "."));
        if(!(cliente.equals(pedido.getCliente()))) {
            throw new RecursoNegadoException("Você não tem permissão para acessar os dados desse pedido.");
        }
        return new ReadPedidoResponseDTO(pedido);
    }

    @Transactional(readOnly = true)
    public Page<ReadPedidoResponseDTO> lerPropriosPedidos(Pageable pageable) {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cliente cliente = clienteRepository.findById(clienteLogado.getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível achar uma conta cadastrada com esse email e senha. Verifique ambos."));
        Page<Pedido> pagePedido = pedidoRepository.findByClienteId(cliente.getId(), pageable);
        return pagePedido.map(pedido -> new ReadPedidoResponseDTO(pedido));
    }

    @Transactional(readOnly = true)
    public ReadPedidoResponseAdminDTO adminLerPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar o pedido com o id: " + idPedido + "."));
        return new ReadPedidoResponseAdminDTO(pedido);
    }

    @Transactional(readOnly = true)
    public Page<ReadPedidoResponseAdminDTO> adminLerPedidos(Pageable pageable) {
        Page<Pedido> pagePedido = pedidoRepository.adminFindAll(pageable);
        return pagePedido.map(pedido -> new ReadPedidoResponseAdminDTO(pedido));
    }

}
