package com.vinicarnot.sistema_de_pedidos.services;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import com.vinicarnot.sistema_de_pedidos.dto.requests.AtualizarTelefoneRequisicaoDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AdminLerTelefoneRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.AtualizarTelefoneRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.dto.responses.LerTelefoneRespostaDTO;
import com.vinicarnot.sistema_de_pedidos.repositories.ClienteRepository;
import com.vinicarnot.sistema_de_pedidos.repositories.TelefoneRepository;
import com.vinicarnot.sistema_de_pedidos.services.exceptions.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    public TelefoneService(TelefoneRepository telefoneRepository, ClienteRepository clienteRepository, ClienteService clienteService) {
        this.telefoneRepository = telefoneRepository;
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }


}
