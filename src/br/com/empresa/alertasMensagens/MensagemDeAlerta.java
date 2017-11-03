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
		Alert alerta = new Alert(AlertType.CONFIRMATION, "Alteração realizada com sucesso!");
		alerta.setTitle(" Alteração confirmada");		
		alerta.show();
	}
	
	public static void  enviarMensagemDeSucessoNaExclusao(){
		Alert alerta = new Alert(AlertType.CONFIRMATION, "Exclusão realizada com sucesso!");
		alerta.setTitle(" Exclusão confirmada");		
		alerta.show();
	}
	
	public static void  enviarMensagemDeErroNoCadastro(){
		Alert alerta = new Alert(AlertType.ERROR, "O cadastro não foi realizado. Verifique os dados novamente!");
		alerta.setTitle("Alerta de erro no cadastro");
		alerta.show();
	}
	
	public static void  enviarMensagemDeErroNaExclusaoDoFilme(Sessao s){
		Alert alerta = new Alert(AlertType.ERROR, "O filme não pode ser excluído porque está dentro de uma sessão no momento.\n"
				+ "\nFilme: "+s.getFilme().getNomeFilme()
				+ "\nSala "+s.getSala().getNroSala()
		        +"\nHorário de início da sessão: "+s.getHoraInicioSessao().toString()
		        +"\nHorário de fim da sessão: "+s.getHoraFimSessao()
			    +"\n\nExclua esta sessão primeiro para depois excluir este filme.");
		alerta.setTitle("Alerta de conflito na exclusão de filme");				
		alerta.show();
	}
	
	public static void  enviarMensagemDeErroNaExclusaoDaSala(Sessao s){
		Alert alerta = new Alert(AlertType.ERROR, "A sala não pode ser excluída porque está dentro de uma sessão no momento.\n"
				+ "\nFilme: "+s.getFilme().getNomeFilme()
				+ "\nSala "+s.getSala().getNroSala()
		        +"\nHorário de início da sessão: "+s.getHoraInicioSessao().toString()
		        +"\nHorário de fim da sessão: "+s.getHoraFimSessao()
			    +"\n\nExclua esta sessão primeiro para depois excluir esta sala.");
		alerta.setTitle("Alerta de conflito na exclusão de sala");				
		alerta.show();
	}
	
	public static void enviarMensagemDeCampoVazio(){
		Alert alerta = new Alert(AlertType.ERROR, "Preencha todos os campos do formulário corretamente antes de realizar a operação!");
		alerta.setTitle(" Alerta de campo vazio");				
		alerta.show();
	}
	
	public static void enviarMensagemDeCaracterInvalidoNoComponenteNumerico(){
		Alert alerta = new Alert(AlertType.ERROR, "Um caracter inválido foi inserido. Insira apenas números neste campo!");
		alerta.setTitle(" Alerta de caracter inválido no campo");				
		alerta.show();
	}
	
	public static void enviarMensagemDeCaracterInvalidoNoComponenteAlfabetico(){
		Alert alerta = new Alert(AlertType.ERROR, "Um caracter inválido foi inserido. Insira apenas letras neste campo!");
		alerta.setTitle(" Alerta de caracter inválido no campo");				
		alerta.show();
	}
	
	public static void enviarMensagemDeAnoFilmeInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um ano de lançamento positivo para o filme!");
		alerta.setTitle(" Alerta de ano inválido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeAnoFilmeMenorQueOAnoAtual(){
		Alert alerta = new Alert(AlertType.ERROR, "O ano de lançamento do filme é menor que o ano atual.\n Insira um ano de lançamento válido!");
		alerta.setTitle(" Alerta de ano menor que o ano atual");				
		alerta.show();
	}
	
	public static void enviarMensagemDeNumeroDaSalaInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um número positivo para a sala!");
		alerta.setTitle(" Alerta de número de sala inválido");				
		alerta.show();
	}	
	
	public static void enviarMensagemDeSalaExistente(){
		Alert alerta = new Alert(AlertType.ERROR, "Já existe uma sala com esse número!\nInsira um número diferente para a sala.");
		alerta.setTitle(" Alerta de número de sala já existente");				
		alerta.show();
	}
	
	public static void enviarMensagemDeLinhasOuColunasDeAssentosDaSalaInvalidos(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um número positivo para o número de linhas/colunas da sala!");
		alerta.setTitle(" Alerta de número de linhas/colunas inválido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeAssentosInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um número de assentos positivo para a sala!");
		alerta.setTitle(" Alerta de número de assentos inválido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeDataInvalida(){
		Alert alerta = new Alert(AlertType.ERROR, "A data da sessão é inferior a data atual. Informe uma data igual ou posterio a data de hoje.");
		alerta.setTitle(" Alerta de data inválida");				
		alerta.show();
	}
	
	public static void enviarMensagemDeConflitoNoHorarioDaSessao(Sessao s){
		Alert alerta = new Alert(AlertType.ERROR, "Já existe uma sessão ocupando essa faixa de horário.\n"
												+ "\nFilme: "+s.getFilme().getNomeFilme()
												+ "\nSala "+s.getSala().getNroSala()
										      +"\nHorário de início da sessão: "+s.getHoraInicioSessao().toString()
										      +"\nHorário de fim da sessão: "+s.getHoraFimSessao()
											  +"\n\nForneça um horário fora desse intervalo de tempo para a nova sessão.");
		alerta.setTitle("Alerta de conflito no horário da sessão");				
		alerta.show();
	}
	
	public static void enviarMensagemDeHorarioDaSessaoInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "O horário da sessão é inválido. Informe um horário válido para a sessão.");
		alerta.setTitle(" Alerta de horário inválido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeValorDaSessaoInvalido(){
		Alert alerta = new Alert(AlertType.ERROR, "Insira um valor positivo para o preço da sessão!");
		alerta.setTitle(" Alerta de valor inválido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeIngressoVendido(){
		Alert alerta = new Alert(AlertType.CONFIRMATION, "Ingresso vendido com sucesso!");
		alerta.setTitle(" Alerta de ingresso vendido");				
		alerta.show();
	}
	
	public static void enviarMensagemDeMarcacaoDePoltronaInvalida(){
		Alert alerta = new Alert(AlertType.ERROR, "Não é possível escolher duas poltronas seguidas.\n"
				                                + "Para desfazer a escolha clique em << e escolha a nova poltrona.\n"
				                                + "Caso queira confirmar a poltrona escolhida clique em OK.");
		alerta.setTitle(" Alerta de marcação de poltrona inválida");				
		alerta.show();
	}
	
	public static void enviarMensagemDePoltronasOcupadas(){
		Alert alerta = new Alert(AlertType.ERROR, "Não é possível realizar a venda. Todos os ingressos já foram vendidos para esta sessão!");
		alerta.setTitle(" Alerta de sessão cheia");				
		alerta.show();
	}
}