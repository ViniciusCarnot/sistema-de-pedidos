package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    @Query(value = "SELECT tel FROM Telefone tel WHERE tel.cliente.email = :emailCliente")
    List<Telefone> procurarTodosOsTelefonesPorEmailCliente(String emailCliente);

}
