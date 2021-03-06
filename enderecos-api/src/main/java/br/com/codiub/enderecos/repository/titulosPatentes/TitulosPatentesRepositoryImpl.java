package br.com.codiub.enderecos.repository.titulosPatentes;

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

import br.com.codiub.enderecos.entity.TitulosPatentes;
import br.com.codiub.enderecos.entity.TitulosPatentes_;
import br.com.codiub.enderecos.filter.TitulosPatentesFilter;

public class TitulosPatentesRepositoryImpl implements TitulosPatentesRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TitulosPatentes> filtrar(
      TitulosPatentesFilter titulosPatentesFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TitulosPatentes> criteria = builder.createQuery(TitulosPatentes.class);
    Root<TitulosPatentes> root = criteria.from(TitulosPatentes.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(titulosPatentesFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TitulosPatentes> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(titulosPatentesFilter));
  }
  
  //Aqui da lista sem paginacao
  @Override
  public List<TitulosPatentes> filtrar(TitulosPatentesFilter titulosPatentesFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TitulosPatentes> criteria = builder.createQuery(TitulosPatentes.class);
    Root<TitulosPatentes> root = criteria.from(TitulosPatentes.class);

    Predicate[] predicates = criarRestricoes(titulosPatentesFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<TitulosPatentes> query = manager.createQuery(criteria);
    return query.getResultList();
  }

  private Predicate[] criarRestricoes(
      TitulosPatentesFilter titulosPatentesFilter,
      CriteriaBuilder builder,
      Root<TitulosPatentes> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(titulosPatentesFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TitulosPatentes_.DESCRICAO)),
              "%" + titulosPatentesFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
    if (titulosPatentesFilter.getId() != null) {
      predicates.add(builder.equal(root.get(TitulosPatentes_.ID), titulosPatentesFilter.getId()));
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

  private Long total(TitulosPatentesFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TitulosPatentes> root = criteria.from(TitulosPatentes.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
