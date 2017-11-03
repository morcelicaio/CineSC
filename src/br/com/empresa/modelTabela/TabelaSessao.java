package br.com.empresa.modelTabela;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TabelaSessao {
	
	private SimpleIntegerProperty codFilme;
	private SimpleStringProperty dataSessao;
	private SimpleIntegerProperty nroSala;
	private SimpleStringProperty nomeFilme;
	private SimpleStringProperty linguagemFilme;
	private SimpleStringProperty horaInicioSessao;
	private SimpleStringProperty horaFimSessao;
	private SimpleIntegerProperty qtdPoltronasDisponiveis;
	private SimpleStringProperty valorSessao;
	private SimpleIntegerProperty codSala;
	
	// construtor da classe
			// Aqui será necessário converter a data e o campo time para String.
	public TabelaSessao(int codigoFilme, String dataDaSessao, int numeroSala, String nomeDoFilme,
			            String linguagemDoFilme, String HoraDeInicio, String HoraDeFim,
			            int qtdeDePoltronasLivres, String valorDaSessao, int codigoSala){
		super();
		
		this.codFilme = new SimpleIntegerProperty(codigoFilme);
		this.dataSessao = new SimpleStringProperty(dataDaSessao);
		this.nroSala = new SimpleIntegerProperty(numeroSala);
		this.nomeFilme = new SimpleStringProperty(nomeDoFilme);
		this.linguagemFilme = new SimpleStringProperty(linguagemDoFilme);
		this.horaInicioSessao = new SimpleStringProperty(HoraDeInicio);
		this.horaFimSessao = new SimpleStringProperty(HoraDeFim);
		this.qtdPoltronasDisponiveis = new SimpleIntegerProperty(qtdeDePoltronasLivres);
		this.valorSessao = new SimpleStringProperty(valorDaSessao);
		this.codSala = new SimpleIntegerProperty(codigoSala);
	}
	
	public int getCodFilme(){
		return codFilme.get();
	}
	
	public String getDataSessao(){
		return dataSessao.get();
	}
	
	public int getNroSala(){
		return nroSala.get();	
	}
	
	public String getNomeFilme(){
		return nomeFilme.get();
	}
	
	public String getLinguagemFilme(){
		return linguagemFilme.get();
	}
	
	public String getHoraInicioSessao(){
		return horaInicioSessao.get();
	}
	
	public String getHoraFimSessao(){
		return horaFimSessao.get();
	}
	
	public int getQtdPoltronasDisponiveis(){
		return this.qtdPoltronasDisponiveis.get();
	}
	
	public String getValorSessao(){
		return this.valorSessao.get();
	}
	
	public int getCodSala(){
		return this.codSala.get();
	}	
}
