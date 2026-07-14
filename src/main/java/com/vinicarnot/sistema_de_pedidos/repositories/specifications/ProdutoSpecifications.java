package com.vinicarnot.sistema_de_pedidos.repositories.specifications;

import com.vinicarnot.sistema_de_pedidos.domain.entites.Categoria;
import com.vinicarnot.sistema_de_pedidos.domain.entites.Produto;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoSpecifications {

    public static Specification<Produto> filtrar(String nome, BigDecimal precoMaximo, List<Long> categoriasIds) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            Specification<Produto> spec = (r, cq, cb) -> criteriaBuilder.conjunction();

            // Filtragem por nome (case-sensitive)
            if (nome != null && !nome.isBlank()) {
                spec = spec.and(filtrarPorNome(nome));
            }

            // Filtragem por preco maximo (menor ou igual)
            if (precoMaximo != null) {
                spec = spec.and(filtrarPorPrecoMaximo(precoMaximo));
            }

            // Filtragem por categorias (verifica se pertence a alguma da lista de IDs)
            if (categoriasIds != null && !categoriasIds.isEmpty()) {
                spec = spec.and(filtrarPorCategorias(categoriasIds));
            }

            return spec.toPredicate(root, criteriaQuery, criteriaBuilder);
        };

    }

    private static Specification<Produto> filtrarPorNome(String nome) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    private static Specification<Produto> filtrarPorPrecoMaximo(BigDecimal precoMaximo) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMaximo);
    }

    private static Specification<Produto> filtrarPorCategorias(List<Long> categoriasIds) {
        return (root, query, cb) -> {
            // Evita que registros duplicados retornem na query quando usamos JOIN de listas
            query.distinct(true);

            // Faz a junção (JOIN) entre Product e a coleção de Categories
            Join<Produto, Categoria> produtoCategoriaJoin = root.join("categorias");

            // Cria a regra SQL: WHERE category.id IN (1, 2, 3...)
            return produtoCategoriaJoin.get("id").in(categoriasIds);
        };
    }

}
