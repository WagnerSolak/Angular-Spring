package com.example.solak.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.solak.api.config.property.FinanceiroApiProperty;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private FinanceiroApiProperty financeiroApiProperty;

	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse res){
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		//cookie.setSecure(false);
		cookie.setSecure(financeiroApiProperty.getSeguranca().isEnableHttp());
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		res.addCookie(cookie);
		res.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
