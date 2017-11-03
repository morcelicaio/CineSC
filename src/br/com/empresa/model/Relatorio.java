package br.com.empresa.model;

public class Relatorio {
	private int codIngresso;
	private int codFilme;
	private int codSala;
	private String nomeFilme;
	private int anoLancamentoFilme; 
	private String categoriaFilme;
	private double valorArrecadadoFilme; 
	
	public Relatorio(){
		
	}
	
	public void setCodIngresso(int codigoIngresso){
		this.codIngresso = codigoIngresso;
	}
	
	public int getCodIngresso(){
		return this.codIngresso;
	}
	
	public void setCodFilme(int codigoFilme){
		this.codFilme = codigoFilme;
	}
	
	public int getCodFilme(){
		return this.codFilme;
	}
	
	public void setCodSala(int codigoSala){
		this.codSala = codigoSala;
	}
	
	public int getCodSala(){
		return this.codSala;
	}
	
	public void setNomeFilme(String nomeFilme){
		this.nomeFilme = nomeFilme;
	}
	
	public String getNomeFilme(){
		return this.nomeFilme;
	}
	
	public void setAnoLancamentoFilme(int anoLancamento){
		this.anoLancamentoFilme = anoLancamento;
	}
	
	public int getAnoLancamentoFilme(){
		return this.anoLancamentoFilme;
	}
	
	public void setCategoriaFilme(String categFilme){
		this.categoriaFilme = categFilme;
	}
	
	public String getCategoriaFilme(){
		return this.categoriaFilme;
	}
	
	public void setValorArrecadadoFilme(double valorArrecadado){
		this.valorArrecadadoFilme = valorArrecadado;
	}
	
	public double getValorArrecadadoFilme(){
		return this.valorArrecadadoFilme;
	}
	
}
