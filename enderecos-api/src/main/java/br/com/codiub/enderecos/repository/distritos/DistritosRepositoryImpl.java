package br.com.codiub.enderecos.repository.distritos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import br.com.codiub.enderecos.entity.Cidades_;
import br.com.codiub.enderecos.entity.Distritos;
import br.com.codiub.enderecos.entity.Distritos_;
import br.com.codiub.enderecos.entity.Estados_;
import br.com.codiub.enderecos.entity.Paises_;
import br.com.codiub.enderecos.filter.DistritosFilter;

public class DistritosRepositoryImpl implements DistritosRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<Distritos> filtrar(DistritosFilter distritosFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Distritos> criteria = builder.createQuery(Distritos.class);
    Root<Distritos> root = criteria.from(Distritos.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(distritosFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<Distritos> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(distritosFilter));
  }

  private Predicate[] criarRestricoes(
      DistritosFilter distritosFilter, CriteriaBuilder builder, Root<Distritos> root) {
    List<Predicate> predicates = new ArrayList<>();

    // CIDADE
    if (distritosFilter.getCidadesFilter() != null) {

      // ESTADO
      if (distritosFilter.getCidadesFilter().getEstadosFilter() != null) {
        // ID
        if (distritosFilter.getCidadesFilter().getEstadosFilter().getId() != null) {
          predicates.add(
              builder.equal(
                  root.get(Distritos_.CIDADES).get(Cidades_.ESTADOS).get(Estados_.ID),
                  distritosFilter.getCidadesFilter().getEstadosFilter().getId()));
        }
        // NOMES
        if (StringUtils.hasLength(
            distritosFilter.getCidadesFilter().getEstadosFilter().getNome())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(Distritos_.CIDADES).get(Cidades_.ESTADOS).get(Estados_.NOME)),
                  "%"
                      + distritosFilter
                          .getCidadesFilter()
                          .getEstadosFilter()
                          .getNome()
                          .toLowerCase()
                      + "%"));
        }

        // PAIS
        if (distritosFilter.getCidadesFilter().getEstadosFilter().getPaisesFilter() != null) {

          // ID
          if (distritosFilter.getCidadesFilter().getEstadosFilter().getPaisesFilter().getId()
              != null) {
            predicates.add(
                builder.equal(
                    root.get(Distritos_.CIDADES)
                        .get(Cidades_.ESTADOS)
                        .get(Estados_.PAISES)
                        .get(Paises_.ID),
                    distritosFilter
                        .getCidadesFilter()
                        .getEstadosFilter()
                        .getPaisesFilter()
                        .getId()));
          }
          // NACIONALIDADE
          if (StringUtils.hasLength(
              distritosFilter
                  .getCidadesFilter()
                  .getEstadosFilter()
                  .getPaisesFilter()
                  .getNacionalidade())) {
            predicates.add(
                builder.like(
                    builder.lower(
                        root.get(Distritos_.CIDADES)
                            .get(Cidades_.ESTADOS)
                            .get(Estados_.PAISES)
                            .get(Paises_.NACIONALIDADE)),
                    "%"
                        + distritosFilter
                            .getCidadesFilter()
                            .getEstadosFilter()
                            .getPaisesFilter()
                            .getNacionalidade()
                            .toLowerCase()
                        + "%"));
          }

          // NOME
          if (StringUtils.hasLength(
              distritosFilter.getCidadesFilter().getEstadosFilter().getPaisesFilter().getNome())) {
            predicates.add(
                builder.like(
                    builder.lower(
                        root.get(Distritos_.CIDADES)
                            .get(Cidades_.ESTADOS)
                            .get(Estados_.PAISES)
                            .get(Paises_.NOME)),
                    "%"
                        + distritosFilter
                            .getCidadesFilter()
                            .getEstadosFilter()
                            .getPaisesFilter()
                            .getNome()
                            .toLowerCase()
                        + "%"));
          }

        }

        // SIGLA
        if (StringUtils.hasLength(
            distritosFilter.getCidadesFilter().getEstadosFilter().getSigla())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(Distritos_.CIDADES).get(Cidades_.ESTADOS).get(Estados_.SIGLA)),
                  "%"
                      + distritosFilter
                          .getCidadesFilter()
                          .getEstadosFilter()
                          .getSigla()
                          .toLowerCase()
                      + "%"));
        }
      }

      // ID
      if (distritosFilter.getCidadesFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(Distritos_.CIDADES).get(Cidades_.ID),
                distritosFilter.getCidadesFilter().getId()));
      }
      // NOME
      if (StringUtils.hasLength(distritosFilter.getCidadesFilter().getNome())) {
        predicates.add(
            builder.like(
                builder.lower(root.get(Distritos_.CIDADES).get(Cidades_.NOME)),
                "%" + distritosFilter.getCidadesFilter().getNome().toLowerCase() + "%"));
      }

    }

    // ID
    if (distritosFilter.getId() != null) {
      predicates.add(builder.equal(root.get(Distritos_.ID), distritosFilter.getId()));
    }
    // NOME
    if (StringUtils.hasLength(distritosFilter.getNome())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(Distritos_.NOME)),
              "%" + distritosFilter.getNome().toLowerCase() + "%"));
    }

    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
    int paginaAtual = pageable.getPageNumber();
    int totalRegistrosPorPagina = pageable.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPorPagina);
  }

  private Long total(DistritosFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Distritos> root = criteria.from(Distritos.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
