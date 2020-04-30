package com.example.solak.api.repository.lancamento;

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


import com.example.solak.api.model.Lancamento;
import com.example.solak.api.repository.filter.LancamentoFilter;
import com.example.solak.api.repository.projections.SinteseLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		// restrictions
		Predicate[] predicates = adicionarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		//consulta
		TypedQuery<Lancamento> query = manager.createQuery(criteria);

		// return query.getResultList();

		adicionarDetalhesDaPaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	

	private Predicate[] adicionarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get("descricao")),
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
					
			//uso descontinuado do Metamodel com JpaModelGen
			/*predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));*/
		}

		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"),
					lancamentoFilter.getDataVencimentoDe()));
			
			//uso descontinuado do Metamodel com JpaModelGen
			/*predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento),
					lancamentoFilter.getDataVencimentoDe()));*/
		}

		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"),
					lancamentoFilter.getDataVencimentoAte()));
		}

		if (lancamentoFilter.getValorDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("valor"),
					lancamentoFilter.getValorDe()));
		}

		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("valor"),
					lancamentoFilter.getValorAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarDetalhesDaPaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class); // from Lancamento
		
		Predicate[] predicates = adicionarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates); // where restrictions
		
		criteria.select(builder.count(root));  // builder.count(root) -> contador 
		return manager.createQuery(criteria).getSingleResult();
	}



	@Override
	public Page<SinteseLancamento> sintetizar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<SinteseLancamento> criteria = builder.createQuery(SinteseLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(SinteseLancamento.class
				, root.get("codigo")
				, root.get("descricao")
				, root.get("dataVencimento")
				, root.get("dataPagamento")
				, root.get("valor")
				, root.get("statusAberto")
				, root.get("tipo")
				, root.join("categoria").get("nome")
				, root.join("pessoa").get("nome")));
		
				//uso descontinuado do Metamodel com JpaModelGen
				//, root.get(Lancamento_.categoria).get(Categoria_.nome)
				//, root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		// restrictions
		Predicate[] predicates = adicionarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		//consulta
		TypedQuery<SinteseLancamento> query = manager.createQuery(criteria);

		//detalhes da paginação
		adicionarDetalhesDaPaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

}
