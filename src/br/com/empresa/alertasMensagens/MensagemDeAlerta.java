package br.com.empresa.alertasMensagens;

import br.com.empresa.model.Sessao;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MensagemDeAlerta {			
	
	public static void  enviarMensagemDeSucessoNoCadastro(){
		Alert alerta = new Alert(AlertType.CONFIRMATION, "Cadastro realizado com sucesso!");
		alerta.setTitle(" Cadastro confirmado");		
		alerta.show();
	}
	
	public static void  enviarMensagemDeSucessoNaAlteracao(){
		Alert alerta = new Alert(AlertType.CONFIRMATION, "Altera��o realizada com sucesso!");
		alerta.setTitle(" Altera��o confirmada");		
		alerta.show();
	}
	
	public static void  enviarMensagemDeSucessoNaExclusao(){
		Alert alerta = new Alert(AlertType.CONFIRMATION, "Exclus�o realizada com sucesso!");
		alerta.setTitle(" Exclus�o confirmada");		
		alerta.show();
	}
	
	public static void  enviarMensagemDeErroNoCadastro(){
		Alert alerta = new Alert(AlertType.ERROR, "O cadastro n�o foi realizado. Verifique os dados novamente!");
		alerta.setTitle("Alerta de erro no cadastro");
		alerta.show();
	}
	
	public static void  enviarMensagemDeErroNaExclusaoDoFilme(Sessao s){
		Alert alerta = new Alert(AlertType.ERROR, "O filme n�o pode ser exclu�do porque est� dentro de uma sess�o no momento.\n"
				+ "\nFilme: "+s.getFilme().getNomeFilme()
				+ "\nSala "+s.getSala().getNroSala()
		        +"\nHor�rio de in�cio da sess�o: "+s.getHoraInicioSessao().toString()
		        +"\nHor�rio de fim da sess�o: "+s.getHoraFimSessao()
			    +"\n\nExclua esta sess�o primeiro para depois excluir este filme.");
		alerta.setTitle("Alerta de conflito na exclus�o de filme");				
		alerta.show();
	}
	
	public static void  enviarMensagemDeErroNaExclusaoDaSala(Sessao s){
		Alert alerta = new Alert(AlertType.ERROR, "A sala n�o pode ser exclu�da porque est� dentro de uma sess�o no momento.\n"
				+ "\nFilme: "+s.getFilme().getNomeFilme()
				+ "\nSala "+s.getSala().getNroSala()
		        +"\nHor�rio de in�cio da sess�o: "+s.getHoraInicioSessao().toString()
		        +"\nHor�rio de fim da sess�o: "+s.getHoraFimSessao()
			    +"\n\nExclua esta sess�o primeiro para depois excluir esta sala.");
		alerta.setTitle("Alerta de conflito na exclus�o de sala");				
		alerta.show();
	}
	
	public static void enviarMensagemDeCampoVazio(){
		Alert alerta = new Alert(AlertType.ERROR, "Preencha todos os campos do formul�rio corretamente antes de realizar a opera��o!");
		alerta.setTitle(" Alerta de campo vazio");				
		alerta.show();
	}
	
	public static void enviarMensagemDeCaracterInvalidoNoComponenteNumerico(){
		Alert alerta = new Alert(AlertType.ERROR, "Um caracter inv�lido foi inserido. Insira apenas n�meros neste campo!");
		alerta.setTitle(" Alerta de caracter inv�lido no campo");				
		alerta.show();
	}
	
	public static void enviarMensagemDeCaracterInvalidoNoComponenteAlfabetico(){
		Alert alerta = new Alert(AlertType.ERROR, "Um caracter inv�lido foi inserido. Insira apenas letras neste campo!");
		alerta.setTitle(" Alerta de caracter inv�lido no campo");				
		alerta.show();
	}
	
	public static void enviarMensagemDeAnoFilmeInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um ano de lan�amento positivo para o filme!");
		alerta.setTitle(" Alerta de ano inv�lido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeAnoFilmeMenorQueOAnoAtual(){
		Alert alerta = new Alert(AlertType.ERROR, "O ano de lan�amento do filme � menor que o ano atual.\n Insira um ano de lan�amento v�lido!");
		alerta.setTitle(" Alerta de ano menor que o ano atual");				
		alerta.show();
	}
	
	public static void enviarMensagemDeNumeroDaSalaInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um n�mero positivo para a sala!");
		alerta.setTitle(" Alerta de n�mero de sala inv�lido");				
		alerta.show();
	}	
	
	public static void enviarMensagemDeSalaExistente(){
		Alert alerta = new Alert(AlertType.ERROR, "J� existe uma sala com esse n�mero!\nInsira um n�mero diferente para a sala.");
		alerta.setTitle(" Alerta de n�mero de sala j� existente");				
		alerta.show();
	}
	
	public static void enviarMensagemDeLinhasOuColunasDeAssentosDaSalaInvalidos(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um n�mero positivo para o n�mero de linhas/colunas da sala!");
		alerta.setTitle(" Alerta de n�mero de linhas/colunas inv�lido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeAssentosInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um n�mero de assentos positivo para a sala!");
		alerta.setTitle(" Alerta de n�mero de assentos inv�lido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeDataInvalida(){
		Alert alerta = new Alert(AlertType.ERROR, "A data da sess�o � inferior a data atual. Informe uma data igual ou posterio a data de hoje.");
		alerta.setTitle(" Alerta de data inv�lida");				
		alerta.show();
	}
	
	public static void enviarMensagemDeConflitoNoHorarioDaSessao(Sessao s){
		Alert alerta = new Alert(AlertType.ERROR, "J� existe uma sess�o ocupando essa faixa de hor�rio.\n"
												+ "\nFilme: "+s.getFilme().getNomeFilme()
												+ "\nSala "+s.getSala().getNroSala()
										      +"\nHor�rio de in�cio da sess�o: "+s.getHoraInicioSessao().toString()
										      +"\nHor�rio de fim da sess�o: "+s.getHoraFimSessao()
											  +"\n\nForne�a um hor�rio fora desse intervalo de tempo para a nova sess�o.");
		alerta.setTitle("Alerta de conflito no hor�rio da sess�o");				
		alerta.show();
	}
	
	public static void enviarMensagemDeHorarioDaSessaoInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "O hor�rio da sess�o � inv�lido. Informe um hor�rio v�lido para a sess�o.");
		alerta.setTitle(" Alerta de hor�rio inv�lido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeValorDaSessaoInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um valor positivo para o pre�o da sess�o!");
		alerta.setTitle(" Alerta de valor inv�lido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeIngressoVendido(){
		Alert alerta = new Alert(AlertType.CONFIRMATION, "Ingresso vendido com sucesso!");
		alerta.setTitle(" Alerta de ingresso vendido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeMarcacaoDePoltronaInvalida(){
		Alert alerta = new Alert(AlertType.ERROR, "N�o � poss�vel escolher duas poltronas seguidas.\n"
				                                + "Para desfazer a escolha clique em << e escolha a nova poltrona.\n"
				                                + "Caso queira confirmar a poltrona escolhida clique em OK.");
		alerta.setTitle(" Alerta de marca��o de poltrona inv�lida");				
		alerta.show();
	}
	
	public static void enviarMensagemDePoltronasOcupadas(){
		Alert alerta = new Alert(AlertType.ERROR, "N�o � poss�vel realizar a venda. Todos os ingressos j� foram vendidos para esta sess�o!");
		alerta.setTitle(" Alerta de sess�o cheia");				
		alerta.show();
	}
}