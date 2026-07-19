package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.dto.requests.CreateEnderecoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.dto.requests.UpdateEnderecoRequestDTO;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cidade;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Endereco;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Estado;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerCidadeRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerEnderecoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerEstadoRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.projections.AdminLerEnderecoRespostaProjection;
import com.vinicarnot.sistema_de_pedidos.repositories.CidadeRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EnderecoRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.EstadoRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final CidadeRepository cidadeRepository;

    private final EstadoRepository estadoRepository;

    private final ClienteRepository clienteRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository, EstadoRepository estadoRepository, ClienteRepository clienteRepository) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Endereco criarEndereco(Cliente cliente, CreateEnderecoRequestDTO dtoRequest) {
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

    @Transactional(readOnly = true)
    public List<AdminLerEnderecoRespostaDTO> adminLerEnderecosDoCliente(String emailCliente) {

        if(!clienteRepository.existsByEmail(emailCliente)) {
            throw new RecursoNaoEncontradoException("Não foi encontrada uma conta cadastrada com o email: " + emailCliente + ".");
        }

        List<AdminLerEnderecoRespostaProjection> resultado = enderecoRepository.procurarEnderecosPorCliente(emailCliente);

        List<AdminLerEnderecoRespostaDTO> listaDtoResposta = new ArrayList<>();

        for(AdminLerEnderecoRespostaProjection enderecoRespostaProjection : resultado) {

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

            listaDtoResposta.add(enderecoRespostaDTO);

        }

        return  listaDtoResposta;

    }

}
