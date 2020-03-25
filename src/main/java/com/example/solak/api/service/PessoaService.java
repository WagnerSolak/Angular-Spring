package com.example.solak.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.solak.api.model.Pessoa;
import com.example.solak.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa){
		Pessoa pessoaSalva = buscarPessoaPorCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo"); // copia as propriedades, passando dequal objeto? para qual objeto? sem pegar o código, pois este é nulo!
		return pessoaRepository.save(pessoaSalva);
	}

	private Pessoa buscarPessoaPorCodigo(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		if(pessoaSalva == null){
			throw new EmptyResultDataAccessException(1); //lança exceção (1) -> esperava ao menos 1 elemento, e assim retorna 404
		}
		return pessoaSalva;
	}

	public void atualizarStatus(Long codigo, Boolean status) {
		Pessoa pessoaSalva = buscarPessoaPorCodigo(codigo);
		pessoaSalva.setStatusAtivo(status);
		pessoaRepository.save(pessoaSalva);
		
	}
}
