package br.com.empresa.model;

public class Sala {
	
	private int codSala;
	private int nroSala;  // É o código da sala no banco de dados.
	private int qtdeDeLinhasDePoltronas;
	private int qtdeDeColunasDePoltronas;
	private int qtdAssentos;
	
public Sala(){
		
	}
	
	public void setCodSala(int codSala){
		this.codSala = codSala;
	}
	
	public int getCodSala(){
		return codSala;
	}

	public void setNroSala(int numeroDaSala){
		this.nroSala = numeroDaSala;
	}
	
	public int getNroSala(){
		return nroSala;
	}
	
	public void setQtdeDeLinhasDePoltronas(int qtdLinhas){
		this.qtdeDeLinhasDePoltronas = qtdLinhas;
	}
	
	public int getQtdeDeLinhasDePoltronas(){
		return qtdeDeLinhasDePoltronas;
	}
	
	public void setQtdeDeColunasDePoltronas(int qtdColunas){
		this.qtdeDeColunasDePoltronas = qtdColunas;
	} 
	
	public int getQtdeDeColunasDePoltronas(){
		return qtdeDeColunasDePoltronas;
	}
	
	public void setQtdAssentos(int qtdDeAssentos){
		this.qtdAssentos = qtdDeAssentos;
	}
	
	public int getQtdAssentos(){
		return this.qtdAssentos;
	}
}
