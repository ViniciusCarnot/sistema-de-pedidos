package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarMeuEnderecoRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cidade;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Estado;
import com.vinicarnot.sistema_de_pedidos.dto.responses.*;
import com.vinicarnot.sistema_de_pedidos.projections.AdminLerEnderecoRespostaProjecao;
import com.vinicarnot.sistema_de_pedidos.projections.LerEnderecoRespostaProjecao;
import com.vinicarnot.sistema_de_pedidos.repositories.CidadeRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EnderecoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EstadoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    private final EstadoRepository estadoRepository;

    private final ClienteRepository clienteRepository;

    private final ClienteService clienteService;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EstadoRepository estadoRepository, ClienteRepository clienteRepository, ClienteService clienteService) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    /*
    @Transactional(rollbackFor = Exception.class)
    public Endereco criarEndereco(Cliente cliente, CriarPedidoEnderecoRequisicaoDTO dtoRequest) {
        Cidade cidade = cidadeRepository.findById(dtoRequest.getCidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar uma cidade com o id: " + dtoRequest.getCidade().getId() + "."));

        Estado estado = estadoRepository.findById(dtoRequest.getEstado().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar um estado com o id: " + dtoRequest.getEstado().getId() + "."));

        String logradouro = dtoRequest.getLogradouro();
        String numero = dtoRequest.getNumero();
        String bairro = dtoRequest.getBairro();

        return enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(logradouro, numero, bairro, cidade, estado)
                .orElseGet(() -> {
                    Endereco novoEndereco = new Endereco();
                    novoEndereco.setLogradouro(logradouro);
                    novoEndereco.setNumero(numero);
                    novoEndereco.setBairro(bairro);
                    novoEndereco.setCidade(cidade);
                    novoEndereco.getClientes().add(cliente);
                    return enderecoRepository.save(novoEndereco);
                });
    }

    @Transactional(rollbackFor = Exception.class)
    public Endereco atualizarEndereco(Cliente cliente, UpdateEnderecoRequestDTO dtoRequest) {
        Cidade cidade = cidadeRepository.findById(dtoRequest.getCidade().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar uma cidade com o id: " + dtoRequest.getCidade().getId() + "."));

        Estado estado = estadoRepository.findById(dtoRequest.getEstado().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar um estado com o id: " + dtoRequest.getEstado().getId() + "."));

        String logradouro = dtoRequest.getLogradouro();
        String numero = dtoRequest.getNumero();
        String bairro = dtoRequest.getBairro();

        return enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(logradouro, numero, bairro, cidade, estado)
                        .orElseGet(() -> {
                    Endereco novoEndereco = new Endereco();
                    novoEndereco.setLogradouro(logradouro);
                    novoEndereco.setNumero(numero);
                    novoEndereco.setBairro(bairro);
                    novoEndereco.setCidade(cidade);
                    novoEndereco.getClientes().add(cliente);
                    return enderecoRepository.save(novoEndereco);
                });
    }

     */

    @Transactional(readOnly = true)
    public List<AdminLerEnderecoRespostaDTO> adminLerEnderecosDoCliente(String emailCliente) {

        if(!clienteRepository.existsByEmail(emailCliente)) {
            throw new RecursoNaoEncontradoException("Não foi encontrada uma conta cadastrada com o email: " + emailCliente + ".");
        }

        List<AdminLerEnderecoRespostaProjecao> enderecoRespostaProjecaoLista = enderecoRepository.adminProcurarEnderecosPorCliente(emailCliente);

        List<AdminLerEnderecoRespostaDTO> enderecoRespostaDTOLista = new ArrayList<>();

        for(AdminLerEnderecoRespostaProjecao enderecoRespostaProjection : enderecoRespostaProjecaoLista) {

            AdminLerEnderecoRespostaDTO enderecoRespostaDTO = new AdminLerEnderecoRespostaDTO();
            AdminLerCidadeRespostaDTO cidadeRespostaDTO = new AdminLerCidadeRespostaDTO();
            AdminLerEstadoRespostaDTO estadoRespostaDTO = new AdminLerEstadoRespostaDTO();

            enderecoRespostaDTO.setId(enderecoRespostaProjection.getEnderecoId());
            enderecoRespostaDTO.setLogradouro(enderecoRespostaProjection.getLogradouro());
            enderecoRespostaDTO.setNumero(enderecoRespostaProjection.getNumero());
            enderecoRespostaDTO.setBairro(enderecoRespostaProjection.getBairro());

            cidadeRespostaDTO.setId(enderecoRespostaProjection.getCidadeId());
            cidadeRespostaDTO.setNome(enderecoRespostaProjection.getCidadeNome());

            enderecoRespostaDTO.setCidade(cidadeRespostaDTO);

            estadoRespostaDTO.setId(enderecoRespostaProjection.getEstadoId());
            estadoRespostaDTO.setNome(enderecoRespostaProjection.getEstadoNome());

            enderecoRespostaDTO.setEstado(estadoRespostaDTO);

            enderecoRespostaDTOLista.add(enderecoRespostaDTO);

        }

        return  enderecoRespostaDTOLista;

    }

    @Transactional(readOnly = true)
    public List<LerEnderecoRespostaDTO> verMeusEnderecos() {

        Cliente cliente = clienteService.autenticado();

        List<LerEnderecoRespostaProjecao> enderecoRespostaProjecaoLista = enderecoRepository.procurarEnderecosPorCliente(cliente.getEmail());

        List<LerEnderecoRespostaDTO> enderecoRespostaDTOLista = new ArrayList<>();

        for(LerEnderecoRespostaProjecao enderecoRespostaProjecao : enderecoRespostaProjecaoLista) {

            enderecoRespostaDTOLista
                    .add(new LerEnderecoRespostaDTO
                            (
                                enderecoRespostaProjecao.getLogradouro(),
                                enderecoRespostaProjecao.getNumero(),
                                enderecoRespostaProjecao.getBairro(),
                                enderecoRespostaProjecao.getCidadeNome(),
                                enderecoRespostaProjecao.getEstadoNome()
                            )
                    );

        }

        return enderecoRespostaDTOLista;

    }

    @Transactional(rollbackFor = Exception.class)
    public Set<AtualizarMeuEnderecoRespostaDTO> atualizarMeusEnderecos(List<AtualizarMeuEnderecoRequisicaoDTO> dtoRequisicaoLista) {

        Cliente cliente = clienteService.autenticado();

        cliente.getEnderecos().clear();

        for(AtualizarMeuEnderecoRequisicaoDTO enderecoRequisicaoDTO : dtoRequisicaoLista) {

            Cidade cidade = cidadeRepository.findById(enderecoRequisicaoDTO.getCidadeId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Cidade com o id: " + enderecoRequisicaoDTO.getCidadeId() + ", não encontrada."));

            Estado estado = estadoRepository.findById(enderecoRequisicaoDTO.getEstadoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Estado com o id: " + enderecoRequisicaoDTO.getEstadoId() + ", não encontrado."));

            Optional<Endereco> enderecoOptional = enderecoRepository.findByLogradouroAndNumeroAndBairroAndCidadeAndCidadeEstado(
                    enderecoRequisicaoDTO.getLogradouro(),
                    enderecoRequisicaoDTO.getNumero(),
                    enderecoRequisicaoDTO.getBairro(),
                    cidade,
                    estado
            );

            if(enderecoOptional.isPresent()) {
                cliente.getEnderecos().add(enderecoOptional.get());
            } else {
                Endereco endereco = new Endereco(enderecoRequisicaoDTO.getLogradouro(),
                        enderecoRequisicaoDTO.getNumero(),
                        enderecoRequisicaoDTO.getBairro(),
                        cidade);

                cliente.getEnderecos().add(endereco);
            }

        }

        Set<AtualizarMeuEnderecoRespostaDTO> enderecoRespostaDTOSet = new HashSet<>();

        for(Endereco endereco : cliente.getEnderecos()) {
            enderecoRespostaDTOSet.add(new AtualizarMeuEnderecoRespostaDTO(endereco));
        }

        return enderecoRespostaDTOSet;

    }

}
