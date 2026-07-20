package com.vinicarnot.sistema_de_pedidos.repositories;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Telefone;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    @Query(value = "SELECT tel FROM Telefone tel WHERE tel.cliente.email = :emailCliente")
    Telefone procurarTodosOsTelefonesPorEmailCliente(String emailCliente);

    Optional<Telefone> findTelefoneByNumero(String numero);

    boolean existsTelefoneByNumero(String numero);
}
