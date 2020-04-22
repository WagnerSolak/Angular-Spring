package com.example.solak.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.solak.api.event.ResourceCreatedEvent;
import com.example.solak.api.model.Categoria;
import com.example.solak.api.repository.CategoriaRepository;

@RestController  //retorno poderá ser convertido em JSON por ex.
@RequestMapping("/categorias") //mapeando a requisição com url categorias
public class CategoriaResource { //recurso de categoria

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping //somente um mapeamento por método, ao menos que seja informado incremento ex.: /diversas, ai ele busca em categorias e depois em diversas
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED) não preciso mais da anotação, porque o created(uri) já define o status 
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
		Categoria cataegoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new ResourceCreatedEvent(this, response, cataegoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cataegoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo){
		Categoria categoria = categoriaRepository.findOne(codigo);
		
			return categoria!= null ? ResponseEntity.ok().body(categoria) : ResponseEntity.notFound().build();
		
		
	}
	
	
	
}
