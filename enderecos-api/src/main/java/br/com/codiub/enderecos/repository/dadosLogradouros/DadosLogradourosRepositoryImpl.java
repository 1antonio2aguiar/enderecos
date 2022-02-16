package br.com.codiub.enderecos.repository.dadosLogradouros;

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
import br.com.codiub.enderecos.entity.DadosLogradouros;
import br.com.codiub.enderecos.entity.DadosLogradouros_;
import br.com.codiub.enderecos.entity.Distritos_;
import br.com.codiub.enderecos.entity.Estados_;
import br.com.codiub.enderecos.entity.Logradouros_;
import br.com.codiub.enderecos.entity.Paises_;
import br.com.codiub.enderecos.entity.TiposLogradouros_;
import br.com.codiub.enderecos.filter.DadosLogradourosFilter;

public class DadosLogradourosRepositoryImpl implements DadosLogradourosRepositoryQuery {

	  @PersistenceContext private EntityManager manager;

	  @Override
	  public Page<DadosLogradouros> filtrar(
	      DadosLogradourosFilter dadosLogradourosFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<DadosLogradouros> criteria = builder.createQuery(DadosLogradouros.class);
	    Root<DadosLogradouros> root = criteria.from(DadosLogradouros.class);

	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

	    Predicate[] predicates = criarRestricoes(dadosLogradourosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);

	    TypedQuery<DadosLogradouros> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(dadosLogradourosFilter));
	  }

	  private Predicate[] criarRestricoes(
	      DadosLogradourosFilter dadosLogradourosFilter,
	      CriteriaBuilder builder,
	      Root<DadosLogradouros> root) {
	    List<Predicate> predicates = new ArrayList<>();

	    // ID
	    if (dadosLogradourosFilter.getId() != null) {
	      predicates.add(builder.equal(root.get(DadosLogradouros_.ID), dadosLogradourosFilter.getId()));
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

	  private Long total(DadosLogradourosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<DadosLogradouros> root = criteria.from(DadosLogradouros.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	  }
	}