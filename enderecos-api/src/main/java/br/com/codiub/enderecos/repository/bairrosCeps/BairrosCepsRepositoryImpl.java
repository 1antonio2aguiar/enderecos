package br.com.codiub.enderecos.repository.bairrosCeps;

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

import br.com.codiub.enderecos.entity.BairrosCeps;
import br.com.codiub.enderecos.entity.BairrosCeps_;
import br.com.codiub.enderecos.filter.BairrosCepsFilter;

public class BairrosCepsRepositoryImpl implements BairrosCepsRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<BairrosCeps> filtrar(BairrosCepsFilter bairrosCepsFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<BairrosCeps> criteria = builder.createQuery(BairrosCeps.class);
    Root<BairrosCeps> root = criteria.from(BairrosCeps.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(bairrosCepsFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<BairrosCeps> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(bairrosCepsFilter));
  }

  private Predicate[] criarRestricoes(
      BairrosCepsFilter bairrosCepsFilter, CriteriaBuilder builder, Root<BairrosCeps> root) {
    List<Predicate> predicates = new ArrayList<>();

    // BAIRRO
    if (bairrosCepsFilter.getBairro() != null) {
      predicates.add(builder.equal(root.get(BairrosCeps_.BAIRRO), bairrosCepsFilter.getBairro()));
    }
    // BANCO
    if (StringUtils.hasLength(bairrosCepsFilter.getBanco())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(BairrosCeps_.BANCO)),
              "%" + bairrosCepsFilter.getBanco().toLowerCase() + "%"));
    }

    // CD_CCM_BAIRRO
    if (bairrosCepsFilter.getCdCcmBairro() != null) {
      predicates.add(
          builder.equal(root.get(BairrosCeps_.CD_CCM_BAIRRO), bairrosCepsFilter.getCdCcmBairro()));
    }
    // CD_CCM_CEP
    if (bairrosCepsFilter.getCdCcmCep() != null) {
      predicates.add(
          builder.equal(root.get(BairrosCeps_.CD_CCM_CEP), bairrosCepsFilter.getCdCcmCep()));
    }
    // CD_CCM_LOGRADOURO
    if (bairrosCepsFilter.getCdCcmLogradouro() != null) {
      predicates.add(
          builder.equal(
              root.get(BairrosCeps_.CD_CCM_LOGRADOURO), bairrosCepsFilter.getCdCcmLogradouro()));
    }
    // CIDADE
    if (bairrosCepsFilter.getCidade() != null) {
      predicates.add(builder.equal(root.get(BairrosCeps_.CIDADE), bairrosCepsFilter.getCidade()));
    }
    // DISTRITO
    if (bairrosCepsFilter.getDistrito() != null) {
      predicates.add(
          builder.equal(root.get(BairrosCeps_.DISTRITO), bairrosCepsFilter.getDistrito()));
    }
    // ID
    if (bairrosCepsFilter.getId() != null) {
      predicates.add(builder.equal(root.get(BairrosCeps_.ID), bairrosCepsFilter.getId()));
    }
    // IDENTIFICACAO
    if (StringUtils.hasLength(bairrosCepsFilter.getIdentificacao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(BairrosCeps_.IDENTIFICACAO)),
              "%" + bairrosCepsFilter.getIdentificacao().toLowerCase() + "%"));
    }

    // LOGRADOURO
    if (bairrosCepsFilter.getLogradouro() != null) {
      predicates.add(
          builder.equal(root.get(BairrosCeps_.LOGRADOURO), bairrosCepsFilter.getLogradouro()));
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

  private Long total(BairrosCepsFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<BairrosCeps> root = criteria.from(BairrosCeps.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
