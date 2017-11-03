package br.com.empresa.model;

import java.sql.Time;
import java.util.Date;

public class Sessao {
	
	private Date dataSessao;
	private Time horaInicioSessao;
	private Filme filme;     // é usado na tela de vender ingresso para popular a tabela.
	private Sala sala;
	private Time horaFimSessao;
	private int qtdPoltronasDisponiveis;
	private double valorSessao;
	
	public Sessao(){
		
	}
	
	public void setDataSessao(Date dataSessao){
		this.dataSessao = dataSessao;
	}
	
	public Date getDataSessao(){
		return dataSessao;
	}
	
	public void setHoraInicioSessao(Time horaInicio){
		this.horaInicioSessao = horaInicio;
	}
	
	public Time getHoraInicioSessao(){
		return horaInicioSessao;
	}
	
	public void setHoraFimSessao(Time horaFim){
		this.horaFimSessao = horaFim;
	}
	
	public Time getHoraFimSessao(){
		return horaFimSessao;
	}
	
	public void setQtdPoltronasDisponiveis(int poltronasDisponiveis){
		this.qtdPoltronasDisponiveis = poltronasDisponiveis;
	}
	
	public int getQtdPoltronasDisponiveis(){
		return qtdPoltronasDisponiveis;
	}
	
	public void setValorSessao(double valorSessao){
		this.valorSessao = valorSessao;
	}
	
	public double getValorSessao(){
		return valorSessao;
	}
	 
	public void setFilme(Filme filme){
		this.filme = filme;
	}
	
	public Filme getFilme(){
		return this.filme;
	}
	
	public void setSala(Sala sala){
		this.sala = sala;
	}
	
	public Sala getSala(){
		return this.sala;
	}
	
}
