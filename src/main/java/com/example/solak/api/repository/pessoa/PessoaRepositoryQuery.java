package com.example.solak.api.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.solak.api.repository.filter.PessoaFilter;
import com.example.solak.api.repository.projections.SintesePessoa;

public interface PessoaRepositoryQuery {

	public Page<SintesePessoa> sintetizar(PessoaFilter pessoaFilter, Pageable pageable);
}
