package br.com.codiub.enderecos.repository.ceps;

import br.com.codiub.enderecos.entity.Bairros_;
import br.com.codiub.enderecos.entity.Ceps;
import br.com.codiub.enderecos.entity.Ceps_;
import br.com.codiub.enderecos.entity.Cidades_;
import br.com.codiub.enderecos.entity.Distritos_;
import br.com.codiub.enderecos.entity.Logradouros_;
import br.com.codiub.enderecos.filter.CepsFilter;
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

public class CepsRepositoryImpl implements CepsRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<Ceps> filtrar(CepsFilter cepsFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Ceps> criteria = builder.createQuery(Ceps.class);
    Root<Ceps> root = criteria.from(Ceps.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(cepsFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<Ceps> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(cepsFilter));
  }

  private Predicate[] criarRestricoes(
    CepsFilter cepsFilter, CriteriaBuilder builder, Root<Ceps> root) {
    List<Predicate> predicates = new ArrayList<>();

    // BAIRRO
    if (cepsFilter.getBairrosFilter() != null) {
    
      // DISTRITO
      if (cepsFilter.getBairrosFilter().getDistritosFilter() != null) {
        // CIDADE
        if (cepsFilter.getBairrosFilter().getDistritosFilter().getCidadesFilter() != null) {
          
          // NOME
          if (StringUtils.hasLength(
              cepsFilter.getBairrosFilter().getDistritosFilter().getCidadesFilter().getNome())) {
            predicates.add(
                builder.like(
                    builder.lower(
                        root.get(Ceps_.BAIRROS)
                            .get(Bairros_.DISTRITOS)
                            .get(Distritos_.CIDADES)
                            .get(Cidades_.NOME)),
                    "%"
                        + cepsFilter
                            .getBairrosFilter()
                            .getDistritosFilter()
                            .getCidadesFilter()
                            .getNome()
                            .toLowerCase()
                        + "%"));
          }

        }

        // ID
        if (cepsFilter.getBairrosFilter().getDistritosFilter().getId() != null) {
          predicates.add(
              builder.equal(
                  root.get(Ceps_.BAIRROS).get(Bairros_.DISTRITOS).get(Distritos_.ID),
                  cepsFilter.getBairrosFilter().getDistritosFilter().getId()));
        }
        // NOME
        if (StringUtils.hasLength(cepsFilter.getBairrosFilter().getDistritosFilter().getNome())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(Ceps_.BAIRROS).get(Bairros_.DISTRITOS).get(Distritos_.NOME)),
                  "%"
                      + cepsFilter.getBairrosFilter().getDistritosFilter().getNome().toLowerCase()
                      + "%"));
        }

      }

      // NOME
      if (StringUtils.hasLength(cepsFilter.getBairrosFilter().getNome())) {
        predicates.add(
            builder.like(
                builder.lower(root.get(Ceps_.BAIRROS).get(Bairros_.NOME)),
                "%" + cepsFilter.getBairrosFilter().getNome().toLowerCase() + "%"));
      }

    }

    // CEP
    if (StringUtils.hasLength(cepsFilter.getCep())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(Ceps_.CEP)), "%" + cepsFilter.getCep().toLowerCase() + "%"));
    }

    // ID
    if (cepsFilter.getId() != null) {
      predicates.add(builder.equal(root.get(Ceps_.ID), cepsFilter.getId()));
    }

    // LOGRADOURO
    if (cepsFilter.getLogradourosFilter() != null) {

      // DISTRITO
      if (cepsFilter.getLogradourosFilter().getDistritosFilter() != null) {
        // CIDADE
        if (cepsFilter.getLogradourosFilter().getDistritosFilter().getCidadesFilter() != null) {
        
          // NOME
          if (StringUtils.hasLength(
              cepsFilter
                  .getLogradourosFilter()
                  .getDistritosFilter()
                  .getCidadesFilter()
                  .getNome())) {
            predicates.add(
                builder.like(
                    builder.lower(
                        root.get(Ceps_.LOGRADOUROS)
                            .get(Logradouros_.DISTRITOS)
                            .get(Distritos_.CIDADES)
                            .get(Cidades_.NOME)),
                    "%"
                        + cepsFilter
                            .getLogradourosFilter()
                            .getDistritosFilter()
                            .getCidadesFilter()
                            .getNome()
                            .toLowerCase()
                        + "%"));
          }
        }

        // NOME
        if (StringUtils.hasLength(
            cepsFilter.getLogradourosFilter().getDistritosFilter().getNome())) {
          predicates.add(
              builder.like(
                  builder.lower(
                      root.get(Ceps_.LOGRADOUROS).get(Logradouros_.DISTRITOS).get(Distritos_.NOME)),
                  "%"
                      + cepsFilter
                          .getLogradourosFilter()
                          .getDistritosFilter()
                          .getNome()
                          .toLowerCase()
                      + "%"));
        }

      }

      // NOME
      if (StringUtils.hasLength(cepsFilter.getLogradourosFilter().getNome())) {
        predicates.add(
            builder.like(
                builder.lower(root.get(Ceps_.LOGRADOUROS).get(Logradouros_.NOME)),
                "%" + cepsFilter.getLogradourosFilter().getNome().toLowerCase() + "%"));
      }
      
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

  private Long total(CepsFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Ceps> root = criteria.from(Ceps.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
