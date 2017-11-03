package br.com.empresa.modelTabela;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TabelaFilme {
	
	private final SimpleIntegerProperty codFilme;
	private final SimpleStringProperty nomeFilme;
	private final SimpleStringProperty nomeDiretorFilme;
	private final SimpleStringProperty categoriaFilme;
	private final SimpleIntegerProperty anoLancamentoFilme;
	private final SimpleStringProperty linguagemFilme;
	
	public TabelaFilme(int codigoFilme, String nomeFilme, String nomeDiretor, String categoria, int ano, String linguagemFilme){
		
		super();
		this.codFilme = new SimpleIntegerProperty(codigoFilme);
		this.nomeFilme = new SimpleStringProperty(nomeFilme);
		this.nomeDiretorFilme = new SimpleStringProperty(nomeDiretor);
		this.categoriaFilme = new SimpleStringProperty(categoria);
		this.anoLancamentoFilme = new SimpleIntegerProperty(ano);
		this.linguagemFilme = new SimpleStringProperty(linguagemFilme);
		
	}
	
	public int getCodFilme(){
		return codFilme.get();
	}
	
	// fazendo o get dos dados puros da String sem ser o tipo SimpleStringProperty
	public String getNomeFilme(){
		return nomeFilme.get();
	}
	
	public String getNomeDiretorFilme(){
		return nomeDiretorFilme.get();
	}
	
	public String getCategoriaFilme(){
		return categoriaFilme.get();
	}	
	
	public int getAnoLancamentoFilme(){
		return anoLancamentoFilme.get();
	}
	
	public String getLinguagemFilme(){
		return linguagemFilme.get();
	}
}
