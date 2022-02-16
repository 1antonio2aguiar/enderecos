package br.com.codiub.enderecos.repository.paises;

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

import br.com.codiub.enderecos.entity.Paises;
import br.com.codiub.enderecos.entity.Paises_;
import br.com.codiub.enderecos.filter.PaisesFilter;

public class PaisesRepositoryImpl implements PaisesRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<Paises> filtrar(PaisesFilter paisesFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Paises> criteria = builder.createQuery(Paises.class);
    Root<Paises> root = criteria.from(Paises.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(paisesFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<Paises> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(paisesFilter));
  }

  @Override
  public List<Paises> filtrar(PaisesFilter paisesFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Paises> criteria = builder.createQuery(Paises.class);
    Root<Paises> root = criteria.from(Paises.class);

    Predicate[] predicates = criarRestricoes(paisesFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<Paises> query = manager.createQuery(criteria);
    return query.getResultList();
  }
  
  private Predicate[] criarRestricoes(
      PaisesFilter paisesFilter, CriteriaBuilder builder, Root<Paises> root) {
    List<Predicate> predicates = new ArrayList<>();

    // ID
    if (paisesFilter.getId() != null) {
      predicates.add(builder.equal(root.get(Paises_.ID), paisesFilter.getId()));
    }
    // NACIONALIDADE
    if (StringUtils.hasLength(paisesFilter.getNacionalidade())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(Paises_.NACIONALIDADE)),
              "%" + paisesFilter.getNacionalidade().toLowerCase() + "%"));
    }

    // NOME
    if (StringUtils.hasLength(paisesFilter.getNome())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(Paises_.NOME)),
              "%" + paisesFilter.getNome().toLowerCase() + "%"));
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

  private Long total(PaisesFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Paises> root = criteria.from(Paises.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
