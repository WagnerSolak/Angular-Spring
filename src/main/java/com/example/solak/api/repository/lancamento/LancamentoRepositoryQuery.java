package com.example.solak.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.solak.api.model.Lancamento;
import com.example.solak.api.repository.filter.LancamentoFilter;
import com.example.solak.api.repository.projections.SinteseLancamento;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<SinteseLancamento> sintetizar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
