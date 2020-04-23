package com.example.solak.api.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


import com.example.solak.api.model.Pessoa;
import com.example.solak.api.model.Pessoa_;
import com.example.solak.api.repository.filter.PessoaFilter;
import com.example.solak.api.repository.projections.SintesePessoa;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{

	
	@PersistenceContext
	private EntityManager manager;
	

	@Override
	public Page<SintesePessoa> sintetizar (PessoaFilter pessoaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<SintesePessoa> criteria = builder.createQuery(SintesePessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		criteria.select(builder.construct(SintesePessoa.class
				, root.get(Pessoa_.codigo)
				, root.get(Pessoa_.nome)
				, root.get(Pessoa_.email)));
		
		Predicate[] predicates = adicionarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<SintesePessoa> query = manager.createQuery(criteria);
		
		adicionarDetalhesDaPaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
	}
	
	private void adicionarDetalhesDaPaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
		
	}

	private Predicate[] adicionarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(pessoaFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get(Pessoa_.nome)),
					"%" + pessoaFilter.getNome().toLowerCase() + "%"));
		}

		if (!StringUtils.isEmpty(pessoaFilter.getEmail())) {
			predicates.add(builder.like(builder.lower(root.get(Pessoa_.email)),
					"%" + pessoaFilter.getEmail().toLowerCase() + "%"));
		}


		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private Long total(PessoaFilter pessoaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class); 
		
		Predicate[] predicates = adicionarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates); 
		
		criteria.select(builder.count(root));  
		return manager.createQuery(criteria).getSingleResult();
	}

}
