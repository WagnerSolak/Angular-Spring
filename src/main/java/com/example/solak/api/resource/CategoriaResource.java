package com.example.solak.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.solak.api.model.Categoria;
import com.example.solak.api.repository.CategoriaRepository;

@RestController  //retorno poderá ser convertido em JSON por ex.
@RequestMapping("/categorias") //mapeando a requisição com url categorias
public class CategoriaResource { //recurso de categoria

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping //somente um mapeamento por método, ao menos que seja informado incremento ex.: /diversas, ai ele busca em categorias e depois em diversas
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED) não preciso mais da anotação, porque o created(uri) já define o status 
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
		Categoria cataegoriaSalva = categoriaRepository.save(categoria);
		
		//Código para o header informar em Location o value do post efetuado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")  // a partir da requisição atual add o codigo
		.buildAndExpand(cataegoriaSalva.getCodigo()).toUri(); // adicionando o codigo na uri
		response.setHeader("Location", uri.toASCIIString());  // setando o headerLocation na uri
		
		return ResponseEntity.created(uri).body(cataegoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo){
		Categoria categoria = categoriaRepository.findOne(codigo);
		
			return categoria!= null ? ResponseEntity.ok().body(categoria) : ResponseEntity.notFound().build();
		
		
	}
	
	
	
}
