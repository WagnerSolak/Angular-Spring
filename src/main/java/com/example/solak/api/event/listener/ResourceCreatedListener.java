package com.example.solak.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.solak.api.event.ResourceCreatedEvent;

public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent>{

	@Override
	public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {
		HttpServletResponse response = resourceCreatedEvent.getResponse();
		Long codigo = resourceCreatedEvent.getCodigo();
		
		addHeaderLocation(response, codigo);
	}

	private void addHeaderLocation(HttpServletResponse response, Long codigo) {
		//Código para o header informar em Location o value do post efetuado
				URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")  // a partir da requisição atual add o codigo
				.buildAndExpand(codigo).toUri(); // adicionando o codigo na uri
				response.setHeader("Location", uri.toASCIIString());  // setando o headerLocation na uri
	}

}
