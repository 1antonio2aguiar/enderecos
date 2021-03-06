package br.com.codiub.enderecos.repository.dadosBairros;

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

import br.com.codiub.enderecos.entity.Bairros_;
import br.com.codiub.enderecos.entity.Cidades_;
import br.com.codiub.enderecos.entity.DadosBairros;
import br.com.codiub.enderecos.entity.DadosBairros_;
import br.com.codiub.enderecos.entity.Distritos_;
import br.com.codiub.enderecos.entity.Estados_;
import br.com.codiub.enderecos.entity.Paises_;
import br.com.codiub.enderecos.filter.DadosBairrosFilter;


public class DadosBairrosRepositoryImpl implements DadosBairrosRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<DadosBairros> filtrar(DadosBairrosFilter dadosBairrosFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<DadosBairros> criteria = builder.createQuery(DadosBairros.class);
    Root<DadosBairros> root = criteria.from(DadosBairros.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(dadosBairrosFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<DadosBairros> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(dadosBairrosFilter));
  }

  private Predicate[] criarRestricoes(
      DadosBairrosFilter dadosBairrosFilter, CriteriaBuilder builder, Root<DadosBairros> root) {
    List<Predicate> predicates = new ArrayList<>();

    // BAIRRO
    if (dadosBairrosFilter.getBairrosFilter() != null) {
      

      // DISTRITO
      if (dadosBairrosFilter.getBairrosFilter().getDistritosFilter() != null) {
        // CIDADE
        if (dadosBairrosFilter.getBairrosFilter().getDistritosFilter().getCidadesFilter() != null) {
          // ESTADO
          if (dadosBairrosFilter
                  .getBairrosFilter()
                  .getDistritosFilter()
                  .getCidadesFilter()
                  .getEstadosFilter()
              != null) {
            // ID
            if (dadosBairrosFilter
                    .getBairrosFilter()
                    .getDistritosFilter()
                    .getCidadesFilter()
                    .getEstadosFilter()
                    .getId()
                != null) {
              predicates.add(
                  builder.equal(
                      root.get(DadosBairros_.BAIRROS)
                          .get(Bairros_.DISTRITOS)
                          .get(Distritos_.CIDADES)
                          .get(Cidades_.ESTADOS)
                          .get(Estados_.ID),
                      dadosBairrosFilter
                          .getBairrosFilter()
                          .getDistritosFilter()
                          .getCidadesFilter()
                          .getEstadosFilter()
                          .getId()));
            }
            // NOME
            if (StringUtils.hasLength(
                dadosBairrosFilter
                    .getBairrosFilter()
                    .getDistritosFilter()
                    .getCidadesFilter()
                    .getEstadosFilter()
                    .getNome())) {
              predicates.add(
                  builder.like(
                      builder.lower(
                          root.get(DadosBairros_.BAIRROS)
                              .get(Bairros_.DISTRITOS)
                              .get(Distritos_.CIDADES)
                              .get(Cidades_.ESTADOS)
                              .get(Estados_.NOME)),
                      "%"
                          + dadosBairrosFilter
                              .getBairrosFilter()
                              .getDistritosFilter()
                              .getCidadesFilter()
                              .getEstadosFilter()
                              .getNome()
                              .toLowerCase()
                          + "%"));
            }

            // PAIS
            if (dadosBairrosFilter
                    .getBairrosFilter()
                    .getDistritosFilter()
                    .getCidadesFilter()
                    .getEstadosFilter()
                    .getPaisesFilter()
                != null) {
              // ID
              if (dadosBairrosFilter
                      .getBairrosFilter()
                      .getDistritosFilter()
                      .getCidadesFilter()
                      .getEstadosFilter()
                      .getPaisesFilter()
                      .getId()
                  != null) {
                predicates.add(
                    builder.equal(
                        root.get(DadosBairros_.BAIRROS)
                            .get(Bairros_.DISTRITOS)
                            .get(Distritos_.CIDADES)
                            .get(Cidades_.ESTADOS)
                            .get(Estados_.PAISES)
                            .get(Paises_.ID),
                        dadosBairrosFilter
                            .getBairrosFilter()
                            .getDistritosFilter()
                            .getCidadesFilter()
                            .getEstadosFilter()
                            .getPaisesFilter()
                            .getId()));
              }
              // NACIONALIDADE
              if (StringUtils.hasLength(
                  dadosBairrosFilter
                      .getBairrosFilter()
                      .getDistritosFilter()
                      .getCidadesFilter()
                      .getEstadosFilter()
                      .getPaisesFilter()
                      .getNacionalidade())) {
                predicates.add(
                    builder.like(
                        builder.lower(
                            root.get(DadosBairros_.BAIRROS)
                                .get(Bairros_.DISTRITOS)
                                .get(Distritos_.CIDADES)
                                .get(Cidades_.ESTADOS)
                                .get(Estados_.PAISES)
                                .get(Paises_.NACIONALIDADE)),
                        "%"
                            + dadosBairrosFilter
                                .getBairrosFilter()
                                .getDistritosFilter()
                                .getCidadesFilter()
                                .getEstadosFilter()
                                .getPaisesFilter()
                                .getNacionalidade()
                                .toLowerCase()
                            + "%"));
              }

              // NOME
              if (StringUtils.hasLength(
                  dadosBairrosFilter
                      .getBairrosFilter()
                      .getDistritosFilter()
                      .getCidadesFilter()
                      .getEstadosFilter()
                      .getPaisesFilter()
                      .getNome())) {
                predicates.add(
                    builder.like(
                        builder.lower(
                            root.get(DadosBairros_.BAIRROS)
                                .get(Bairros_.DISTRITOS)
                                .get(Distritos_.CIDADES)
                                .get(Cidades_.ESTADOS)
                                .get(Estados_.PAISES)
                                .get(Paises_.NOME)),
                        "%"
                            + dadosBairrosFilter
                                .getBairrosFilter()
                                .getDistritosFilter()
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
                dadosBairrosFilter
                    .getBairrosFilter()
                    .getDistritosFilter()
                    .getCidadesFilter()
                    .getEstadosFilter()
                    .getSigla())) {
              predicates.add(
                  builder.like(
                      builder.lower(
                          root.get(DadosBairros_.BAIRROS)
                              .get(Bairros_.DISTRITOS)
                              .get(Distritos_.CIDADES)
                              .get(Cidades_.ESTADOS)
                              .get(Estados_.SIGLA)),
                      "%"
                          + dadosBairrosFilter
                              .getBairrosFilter()
                              .getDistritosFilter()
                              .getCidadesFilter()
                              .getEstadosFilter()
                              .getSigla()
                              .toLowerCase()
                          + "%"));
            }
          }

          // ID
          if (dadosBairrosFilter.getBairrosFilter().getDistritosFilter().getCidadesFilter().getId()
              != null) {
            predicates.add(
                builder.equal(
                    root.get(DadosBairros_.BAIRROS)
                        .get(Bairros_.DISTRITOS)
                        .get(Distritos_.CIDADES)
                        .get(Cidades_.ID),
                    dadosBairrosFilter
                        .getBairrosFilter()
                        .getDistritosFilter()
                        .getCidadesFilter()
                        .getId()));
          }
          // NOME
          if (StringUtils.hasLength(
              dadosBairrosFilter
                  .getBairrosFilter()
                  .getDistritosFilter()
                  .getCidadesFilter()
                  .getNome())) {
            predicates.add(
                builder.like(
                    builder.lower(
                        root.get(DadosBairros_.BAIRROS)
                            .get(Bairros_.DISTRITOS)
                            .get(Distritos_.CIDADES)
                            .get(Cidades_.NOME)),
                    "%"
                        + dadosBairrosFilter
                            .getBairrosFilter()
                            .getDistritosFilter()
                            .getCidadesFilter()
                            .getNome()
                            .toLowerCase()
                        + "%"));
          }

        }

        // ID
        if (dadosBairrosFilter.getBairrosFilter().getDistritosFilter().getId() != null) {
          predicates.add(
              builder.equal(
                  root.get(DadosBairros_.BAIRROS).get(Bairros_.DISTRITOS).get(Distritos_.ID),
                  dadosBairrosFilter.getBairrosFilter().getDistritosFilter().getId()));
        }
        // NOME
        if (StringUtils.hasLength(
            dadosBairrosFilter.getBairrosFilter().getDistritosFilter().getNome())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(DadosBairros_.BAIRROS).get(Bairros_.DISTRITOS).get(Distritos_.NOME)),
                  "%"
                      + dadosBairrosFilter
                          .getBairrosFilter()
                          .getDistritosFilter()
                          .getNome()
                          .toLowerCase()
                      + "%"));
        }

      }

      // ID
      if (dadosBairrosFilter.getBairrosFilter().getId() != null) {
        predicates.add(
            builder.equal(
                root.get(DadosBairros_.BAIRROS).get(Bairros_.ID),
                dadosBairrosFilter.getBairrosFilter().getId()));
      }
      // NOME
      if (StringUtils.hasLength(dadosBairrosFilter.getBairrosFilter().getNome())) {
        predicates.add(
            builder.like(
                builder.lower(root.get(DadosBairros_.BAIRROS).get(Bairros_.NOME)),
                "%" + dadosBairrosFilter.getBairrosFilter().getNome().toLowerCase() + "%"));
      }

      // NOME_ABREVIADO
      if (StringUtils.hasLength(dadosBairrosFilter.getBairrosFilter().getNomeAbreviado())) {
        predicates.add(
            builder.like(
                builder.lower(root.get(DadosBairros_.BAIRROS).get(Bairros_.NOME_ABREVIADO)),
                "%"
                    + dadosBairrosFilter.getBairrosFilter().getNomeAbreviado().toLowerCase()
                    + "%"));
      }

    }

    
    // NOME_POPULAR
    if (StringUtils.hasLength(dadosBairrosFilter.getNomePopular())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(DadosBairros_.NOME_POPULAR)),
              "%" + dadosBairrosFilter.getNomePopular().toLowerCase() + "%"));
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

  private Long total(DadosBairrosFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<DadosBairros> root = criteria.from(DadosBairros.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
