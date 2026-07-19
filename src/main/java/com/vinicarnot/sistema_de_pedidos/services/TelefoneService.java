package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerTelefoneRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.TelefoneRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final ClienteRepository clienteRepository;

    public TelefoneService(TelefoneRepository telefoneRepository, ClienteRepository clienteRepository) {
        this.telefoneRepository = telefoneRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<AdminLerTelefoneRespostaDTO> adminLerTelefonesDoCliente(String emailCliente) {

        if(!clienteRepository.existsByEmail(emailCliente)) {
            throw new RecursoNaoEncontradoException("Não existe uma conta cadastrada com o email: " + emailCliente + ".");
        }

        List<Telefone> telefoneLista = telefoneRepository.procurarTodosOsTelefonesPorEmailCliente(emailCliente);

        List<AdminLerTelefoneRespostaDTO> telefoneListaRespostaDTO = new ArrayList<>();

        for(Telefone telefone : telefoneLista) {
            telefoneListaRespostaDTO.add(new AdminLerTelefoneRespostaDTO(telefone));
        }

        return telefoneListaRespostaDTO;

    }
}
