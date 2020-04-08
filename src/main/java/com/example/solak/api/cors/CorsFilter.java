package com.example.solak.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	private String origemPermitida = "http://localhost:????";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
	
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		response.setHeader("Acess-Control-Allow-Origin", origemPermitida);
		response.setHeader("Acess-Control-Allow-Credentials", "true");
		
		if("OPTIONS".equals(request.getMethod()) && origemPermitida.equals(request.getHeader("Origin"))){
			response.setHeader("Acess-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
			response.setHeader("Acess-Control-Allow-Methods", "Authorization, Content-Type, Accpet");
			response.setHeader("Acess-Control-Max-Age", "7200");
			
			response.setStatus(HttpServletResponse.SC_OK);
		}else{
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	

}
