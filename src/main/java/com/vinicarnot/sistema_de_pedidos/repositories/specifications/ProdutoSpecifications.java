package com.vinicarnot.sistema_de_pedidos.repositories.specifications;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecifications {

    public static Specification<Produto> filtrar(String nome, BigDecimal precoMaximo) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            Specification<Produto> spec = (r, cq, cb) -> criteriaBuilder.conjunction();

            if (nome != null && !nome.isBlank()) {
                spec = spec.and(filtrarPorNome(nome));
            }

            if (precoMaximo != null) {
                spec = spec.and(filtrarPorPrecoMinimo(precoMaximo));
            }

            return spec.toPredicate(root, criteriaQuery, criteriaBuilder);
        };

    }

    private static Specification<Produto> filtrarPorNome(String nome) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    private static Specification<Produto> filtrarPorPrecoMinimo(BigDecimal precoMaximo) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMaximo);
    }

}
