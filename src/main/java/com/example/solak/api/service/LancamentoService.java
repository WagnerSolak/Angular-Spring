package com.example.solak.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.solak.api.model.Lancamento;
import com.example.solak.api.model.Pessoa;
import com.example.solak.api.repository.LancamentoRepository;
import com.example.solak.api.repository.PessoaRepository;
import com.example.solak.api.service.exception.PessoaInexistenteOuComStatusInativoException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isStatusInativo()){
			throw new PessoaInexistenteOuComStatusInativoException();
		}
		
		return lancamentoRepository.save(lancamento);
	}

}
