package br.com.empresa.util;

import br.com.empresa.alertasMensagens.MensagemDeAlerta;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

	// CLASSE ONDE � FEITA AS VALIDA��ES DE COMPONENTES TextField do SISTEMA.
	// A TELA DO SISTEMA ONDE EST� O COMPONENTE LAN�A O EVENTO QUE CHAMA ESTA CLASSE PARA REALIZAR AS VALIDA��ES.

public class ValidadorDeComponente {
	private TextField tf = new TextField();
	
	// -------------------------- EVENTOS DE TECLA PRESSIONADA NO TECLADO -------------------------------- //
	
	public void validarCampoNumerico(KeyEvent event, TextField textField){    	
        tf = textField;	 // recebendo o textField da tela onde est� o campo que ser� feita a valida��o.
		String caracteres="0987654321";     // lista de caracteres.
		
		//Verificando se a tecla pressionada � diferente de capslook ou enter ou tab ou backSpace ou shift.
		if(event.getCode() != KeyCode.CAPS && event.getCode() != KeyCode.ENTER && event.getCode() != KeyCode.TAB && event.getCode() != KeyCode.BACK_SPACE && event.getCode() != KeyCode.SHIFT){   		
			
			//Se n�o houver o caracter digitado na lista de caracteres, ent�o ele retorna a msg de erro.
	        if(!caracteres.contains(event.getText()) || event.getText().isEmpty()){
	        	tf.setText("");
	    		MensagemDeAlerta.enviarMensagemDeCaracterInvalidoNoComponenteNumerico();
	        }    
		}	
    }
	 
	public void validarCampoAlfabetico(KeyEvent event, TextField textField){    	
        tf = textField;	 // recebendo o textField da tela onde est� o campo que ser� feita a valida��o.
		String caracteres="0987654321";     // lista de caracteres.         
		
		//Verificando se a tecla pressionada � diferente de capslook ou enter ou tab ou backSpace ou shift.
		if(event.getCode() != KeyCode.CAPS && event.getCode() != KeyCode.ENTER && event.getCode() != KeyCode.TAB && event.getCode() != KeyCode.BACK_SPACE && event.getCode() != KeyCode.SHIFT){   
			
			//Se houver o caracter digitado (algum n�mero) na lista de caract, ent�o ele retorna a msg de erro.
	        if(caracteres.contains(event.getText())){
	        	tf.setText("");
	    		MensagemDeAlerta.enviarMensagemDeCaracterInvalidoNoComponenteAlfabetico();
	        } 
		}				                
    }
	
}
