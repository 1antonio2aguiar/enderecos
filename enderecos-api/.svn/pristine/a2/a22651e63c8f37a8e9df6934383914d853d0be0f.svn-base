package br.com.codiub.enderecos.repository.pessoas;

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

import br.com.codiub.enderecos.entity.Pessoas;
import br.com.codiub.enderecos.entity.Pessoas_;
import br.com.codiub.enderecos.filter.PessoasFilter;

public class PessoasRepositoryImpl implements PessoasRepositoryQuery {
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Pessoas> filtrar(PessoasFilter pessoasFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Pessoas> criteria = builder.createQuery(Pessoas.class);
	    Root<Pessoas> root = criteria.from(Pessoas.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(pessoasFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Pessoas> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(pessoasFilter));
	}
	
	@SuppressWarnings("unchecked")
	private Predicate[] criarRestricoes(
		PessoasFilter pessoasFilter, CriteriaBuilder builder, Root<Pessoas> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if (pessoasFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Pessoas_.ID), pessoasFilter.getId()));
		}
		
		// NOME
	    if (StringUtils.hasLength(pessoasFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(Pessoas_.NOME)),
	              "%" + pessoasFilter.getNome().toLowerCase() + "%"));
	    }
	    
	    
	    
	    // CPF
        if (pessoasFilter.getCpf() != null) {
          predicates.add(
              builder.equal(
                  root.get(Pessoas_.CPF),
                  pessoasFilter.getCpf()));
        }
        
        // CNPJ
        if (pessoasFilter.getCnpj() != null) {
          predicates.add(
              builder.equal(
                  root.get(Pessoas_.CNPJ),
                  pessoasFilter.getCnpj()));
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
	
	private Long total(PessoasFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Pessoas> root = criteria.from(Pessoas.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
