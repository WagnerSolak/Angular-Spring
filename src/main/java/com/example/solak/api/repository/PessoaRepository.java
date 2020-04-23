package com.example.solak.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.solak.api.model.Pessoa;
import com.example.solak.api.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery{
	
	
}
