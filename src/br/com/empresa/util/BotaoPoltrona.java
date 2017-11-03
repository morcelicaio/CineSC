package br.com.empresa.util;

import javafx.scene.control.Button;

public class BotaoPoltrona extends Button{
	
	private boolean ocupado;
	
	public void setOcupado(boolean ocup){
		this.ocupado = ocup;
	}
	
	public boolean isOcupado(){				
		return ocupado;
	}
	
}
