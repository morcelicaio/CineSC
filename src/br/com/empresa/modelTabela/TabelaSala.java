package br.com.empresa.modelTabela;

import javafx.beans.property.SimpleIntegerProperty;

public class TabelaSala {
	
	private SimpleIntegerProperty codSala;
	private SimpleIntegerProperty nroSala;
	private SimpleIntegerProperty qtdAssentos;
	
	public TabelaSala(int codigoSala, int numeroSala, int quantidadePoltronas){
		super();
		
		this.codSala = new SimpleIntegerProperty(codigoSala);
		this.nroSala = new SimpleIntegerProperty(numeroSala);
		this.qtdAssentos = new SimpleIntegerProperty(quantidadePoltronas);
	}
	
	public int getCodSala(){
		return codSala.get();	
	}
	
	public int getNroSala(){
		return nroSala.get();	
	}
	
	public int getQtdAssentos(){
		return qtdAssentos.get();
	}
	
}
