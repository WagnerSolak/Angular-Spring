/**
 * 
 */
package com.example.solak.api.repository.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.solak.api.model.TipoLancamentoContabil;

/**
 * @author wagner
 *
 */
public class SinteseLancamento {

	private Long codigo;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private Boolean statusAberto;
	private TipoLancamentoContabil tipo;
	private String categoria;
	private String pessoa;
	

	public SinteseLancamento(Long codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, Boolean statusAberto, TipoLancamentoContabil tipo, String categoria, String pessoa) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.statusAberto = statusAberto;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
		
	}
	

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
	public Boolean getStatusAberto() {
		return statusAberto;
	}


	public void setStatusAberto(Boolean statusAberto) {
		this.statusAberto = statusAberto;
	}


	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPessoa() {
		return pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	public TipoLancamentoContabil getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamentoContabil tipo) {
		this.tipo = tipo;
	}

}
