package br.com.empresa.model;

public class Filme {
	
	private int codFilme;
	private String nomeFilme;
	private String nomeDiretor;
	private String linguagemFilme;
	private String categoriaFilme;
	private int anoLancamento;
	
	public Filme(){
		
	}
	
	public void setCodFilme(int codFilme){
		this.codFilme = codFilme;
	}
	
	public int getCodFilme(){
		return this.codFilme; // o this se referencia ao atributo desta classe.
	}
	
	public void setNomeFilme(String nome){
		this.nomeFilme = nome;
	}
	
	public String getNomeFilme(){
		return nomeFilme;
	}
	
	public void setNomeDiretor(String nomeDiretor){
		this.nomeDiretor = nomeDiretor;
	}
	
	public String getNomeDiretor(){
		return nomeDiretor;
	}
	
	public void setLinguagemFilme(String idioma){
		this.linguagemFilme = idioma;
	}
	
	public String getLinguagemFilme(){
		return linguagemFilme;
	}
	
	public void setCategoriaFilme(String categoria){
		this.categoriaFilme = categoria;
	}
	
	public String getCategoriaFilme(){
		return categoriaFilme;
	}
	
	public void setAnoLancamento(int ano){
		this.anoLancamento = ano;
	}
	
	public int getAnoLancamento(){
		return this.anoLancamento;
	}
}
