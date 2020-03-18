package com.example.solak.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
