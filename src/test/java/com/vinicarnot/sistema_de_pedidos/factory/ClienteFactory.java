package com.vinicarnot.sistema_de_pedidos.factory;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Cliente;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Role;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import com.vinicarnot.sistema_de_pedidos.domain.enums.TipoCliente;

public class ClienteFactory {

    public static Cliente instanciarClienteAdmin() {
        Cliente cliente = new Cliente(
                1L,
                "Alberto",
                "alberto@email.com",
                "123",
                "111.111.111-11",
                TipoCliente.PESSOA_FISICA,
                true
        );
        cliente.setTelefone(new Telefone(1L, "(11) 11111-1111", cliente));
        cliente.adicionarRole(new Role(1L, "ROLE_ADMIN"));
        cliente.adicionarRole(new Role(2L, "ROLE_NORMAL"));
        return cliente;
    }

    public static Cliente instanciarClienteNormal() {
        Cliente cliente = new Cliente(
                2L,
                "Bruno",
                "bruno@email.com",
                "123",
                "222.222.222-22",
                TipoCliente.PESSOA_FISICA,
                true
        );
        cliente.setTelefone(new Telefone(2L, "(22) 22222-2222", cliente));
        cliente.adicionarRole(new Role(2L, "ROLE_NORMAL"));
        return cliente;
    }

}
