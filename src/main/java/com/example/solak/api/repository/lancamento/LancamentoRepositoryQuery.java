package com.example.solak.api.repository.lancamento;

import java.util.List;

import com.example.solak.api.model.Lancamento;
import com.example.solak.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
