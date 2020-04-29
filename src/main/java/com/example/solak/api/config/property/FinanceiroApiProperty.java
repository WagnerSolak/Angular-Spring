package com.example.solak.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("financeiro")
public class FinanceiroApiProperty {

	private String origemPermitida = "http://localhost:8085";
	
	private final Seguranca seguranca = new Seguranca();
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	public String getOrigemPermitida() {
		return origemPermitida;
	}
	
	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}
	
	
	
	public static class Seguranca{
		
		private boolean enableHttp;

		public boolean isEnableHttp() {
			return enableHttp;
		}

		public void setEnableHttp(boolean enableHttp) {
			this.enableHttp = enableHttp;
		}

	}
	
	
	
}
