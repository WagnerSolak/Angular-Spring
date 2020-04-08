package com.example.solak.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter{

	@Override
	public void destroy() {	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//pegar o HttpFilterRequest
		HttpServletRequest req = (HttpServletRequest) request;
		
		//ver se a req é oauth/token
		if("/oauth/token".equalsIgnoreCase(req.getRequestURI()) 
				&& "refresh_token".equals(req.getParameter("grant_type"))
				&& req.getCookies() != null){
			for(Cookie cookie : req.getCookies()){
				if(cookie.getName().equals("refreshToken")){
					String refreshToken = cookie.getValue(); //nesta etapa ele pegou o refresh_token dentro do cookie
					req = new MyServletRequestWrapper(req, refreshToken);
				}
			}
		}
		chain.doFilter(req, response);
	}
	
	static class MyServletRequestWrapper extends HttpServletRequestWrapper{

		private String refreshToken;
		
		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken });
			map.setLocked(true); 
			return map;
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
