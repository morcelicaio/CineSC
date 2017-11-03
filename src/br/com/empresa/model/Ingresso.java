package br.com.empresa.model;

import java.sql.Time;
import java.util.Date;

public class Ingresso {
	
	private int codIngresso;
	private Date dataSessao;
	private String tipoIngresso;
	private Time horaInicioSessao;
	private Time horaFimSessao;
	private int codSala;
	private int codFilme;
	private double valorTotal;
	private int numPoltrona;
	
	public Ingresso(){
		
	}
	
	public void setCodIngresso(int codigoIngresso){
		this.codIngresso = codigoIngresso;
	}
	
	public int getCodIngresso(){
		return this.codIngresso;
	}
	
	public void setDataSessao(Date dataDaSessao){
		this.dataSessao = dataDaSessao;
	}
	
	public Date getDataSessao(){
		return this.dataSessao;
	}
	
	public void setTipoIngresso(String tipoIng){
		this.tipoIngresso = tipoIng;
	}
	
	public String getTipoIngresso(){
		return this.tipoIngresso;
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
	
	public void setCodSala(int codigoSala){
		this.codSala = codigoSala;
	}
	
	public int getCodSala(){
		return codSala;
	}
	
	public void setCodFilme(int codigoFilme){
		this.codFilme = codigoFilme;
	}
	
	public int getCodFilme(){
		return this.codFilme;
	}
	
	public void setValorTotal(double valorTotal){
		this.valorTotal = valorTotal;
	}
	
	public double getValorTotal(){
		return this.valorTotal;
	}
	
	public void setNumPoltrona(int numeroDaPoltrona){
		this.numPoltrona = numeroDaPoltrona;
	}
	
	public int getNumPoltrona(){
		return this.numPoltrona;
	}
}
