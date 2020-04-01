package com.example.solak.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.solak.api.event.ResourceCreatedEvent;
import com.example.solak.api.exception.FinanceiroExceptionHandler.Error;
import com.example.solak.api.model.Lancamento;
import com.example.solak.api.repository.LancamentoRepository;
import com.example.solak.api.repository.filter.LancamentoFilter;
import com.example.solak.api.service.LancamentoService;
import com.example.solak.api.service.exception.PessoaInexistenteOuComStatusInativoException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Lancamento> consultar(LancamentoFilter lancamentoFilter){
		return lancamentoRepository.filtrar(lancamentoFilter);
	}
	
	@GetMapping("/{codigo}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> buscaPorCodigo(@PathVariable Long codigo){
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		
		return lancamento != null ? ResponseEntity.ok().body(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		
		publisher.publishEvent(new ResourceCreatedEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
		
	}
	
	@ExceptionHandler({ PessoaInexistenteOuComStatusInativoException.class })
	public ResponseEntity<Object> handlerPessoaInexistenteOuComStatusInativoException(PessoaInexistenteOuComStatusInativoException ex){
		String messageForUser = messageSource.getMessage("pessoa.inexistente.ou.com.status.inativo", null, LocaleContextHolder.getLocale());
		String messageForDevelopment = ex.toString();
		List<Error> errors = Arrays.asList(new Error(messageForDevelopment, messageForUser));
		return ResponseEntity.badRequest().body(errors);
	}

}
