package com.example.solak.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.solak.api.model.Lancamento;
import com.example.solak.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
