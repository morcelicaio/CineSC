package br.com.empresa.modelTabela;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TabelaRelatorio {
	
	private final SimpleIntegerProperty codIngresso;
	private final SimpleIntegerProperty codFilme;
	private final SimpleIntegerProperty codSala;
	private final SimpleStringProperty nomeFilme;
	private final SimpleIntegerProperty anoLancamentoFilme;
	private final SimpleStringProperty categoriaFilme;
	private final SimpleStringProperty valorArrecadadoFilme;
	
	public TabelaRelatorio(int codigoIngresso, int codigoFilme, int codigoSala, String nomeFilme, int anoLancamento, String categFilme, String valorArrecadado){
		super();
		
		this.codIngresso = new SimpleIntegerProperty(codigoIngresso);
		this.codFilme = new SimpleIntegerProperty(codigoFilme);
		this.codSala = new SimpleIntegerProperty(codigoSala);
		this.nomeFilme = new SimpleStringProperty(nomeFilme);
		this.anoLancamentoFilme = new SimpleIntegerProperty(anoLancamento);
		this.categoriaFilme = new SimpleStringProperty(categFilme);
		this.valorArrecadadoFilme = new SimpleStringProperty(valorArrecadado);
		
	}
	
	public int getCodIngresso(){
		return codIngresso.get();
	}
	
	public int getCodFilme(){
		return codFilme.get();
	}
	
	public int getCodSala(){
		return codSala.get();
	}
	
	// fazendo o get dos dados puros da String sem ser o tipo SimpleStringProperty
	public String getNomeFilme(){
		return nomeFilme.get();
	}
	
	public int getAnoLancamentoFilme(){
		return anoLancamentoFilme.get();
	}
	
	public String getCategoriaFilme(){
		return categoriaFilme.get();
	}
	
	public String getValorArrecadadoFilme(){
		return valorArrecadadoFilme.get();
	}
	
	
}
