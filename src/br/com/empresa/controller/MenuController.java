package br.com.empresa.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import br.com.empresa.relatorios.RelatorioVendas;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {	
	@FXML private AnchorPane apPainelPrincipal;
	@FXML private Button btnCadastrarSala;	
	@FXML private Button btnCadastrarFilme;
	@FXML private Button btnCadastrarSessao;
	@FXML private Button btnVenderIngresso;
	@FXML private Button btnRelatorios;
	@FXML private Button btnSobre;
	@FXML private Button btnSair;			
	@FXML private StackPane lbRelogio;  // componente que joga outros componentes dentro. (nós)
	
	@FXML private DatePicker dpDataAtual;    	    
	
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	@FXML 
	public void initialize() {				
		LocalDate datalocal = LocalDate.now();		
		dpDataAtual.setValue(datalocal);
		
		
		DigitalClock dc  = new DigitalClock();						
		//Aqui o lbRelogio do Tipo StackPane pode recebe outro nó (componente) dentro dele
		//Dessa forma foi colocado o relógio dentro dele.
		lbRelogio.getChildren().add(dc);		
		
		inserirEfeitoDeOpacidadeMenor();
	}		
	
	private void inserirEfeitoDeOpacidadeMenor(){
		btnCadastrarSala.setOpacity(0.8);
		btnCadastrarFilme.setOpacity(0.8);
		btnCadastrarSessao.setOpacity(0.8);
		btnVenderIngresso.setOpacity(0.8);
		btnRelatorios.setOpacity(0.8);
		btnSobre.setOpacity(0.8);				
	}
	
	private void inserirEfeitoDeOpacidadeMaior(MouseEvent event){
		Button b = (Button) event.getTarget();   // recuperado o objeto do componente que foi clicado
		b.setOpacity(1);    				     // setando a opacidade da cor dele.
	}
	
	@FXML
	private void abrirCadastrarSala(ActionEvent event){
		Stage stage = new Stage();
		Parent root = null;
		
		try{  
			root = FXMLLoader.load(getClass().getResource("/br/com/empresa/view/CadastrarSala.fxml"));
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);  // deixa a tela de trás bloqueada para mexer.
			stage.setTitle("Cadastro de Sala");    
			stage.getIcons().add(new Image("/br/com/empresa/icones/MenuSalasIcon.png")); //seta a imagem na barra superior.			
			stage.centerOnScreen();
			stage.show();
			
		}	catch(IOException e){
				System.out.println("Erro ao gerar a tela de cadastrar sala.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
	
	@FXML
	private void abrirCadastrarFilme(ActionEvent event){
		Stage stage = new Stage();
		Parent root = null; //Esse Parent é o que vai identificar seu FXML, através do FXMLLoader.load
		
		try{   
			root = FXMLLoader.load(getClass().getResource("/br/com/empresa/view/CadastrarFilme.fxml"));
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);   // deixa a tela de trás bloqueada para mexer.
			stage.setTitle("Cadastro de Filme");
			stage.getIcons().add(new Image("/br/com/empresa/icones/MenuFilmesIcon.png")); //seta a imagem na barra superior.
			stage.centerOnScreen();
			stage.show();
			
		}	catch(IOException e){
				System.out.println("Erro ao gerar a tela de cadastrar filme.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
	}
	
	@FXML
	private void abrirCadastrarSessao(ActionEvent event){
		Stage stage = new Stage();
		Parent root = null;
		
		try{
			root = FXMLLoader.load(getClass().getResource("/br/com/empresa/view/CadastrarSessao.fxml"));
			
			Scene scene = new Scene(root);			
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);  // deixa a tela de trás bloqueada para mexer.
			stage.setTitle("Cadastro de Sessão");
			stage.getIcons().add(new Image("/br/com/empresa/icones/MenuSessoesIcon.png")); //seta a imagem na barra superior.
			stage.centerOnScreen();
			stage.show();
			
		}	catch(IOException e){
				System.out.println("Erro ao gerar a tela de cadastrar sessão.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
	
	@FXML
	private void abrirVenderIngresso(ActionEvent event){
		Stage stage = new Stage();
		Parent root = null;
		
		try{
			root = FXMLLoader.load(getClass().getResource("/br/com/empresa/view/VenderIngresso.fxml"));
			
			Scene scene = new Scene(root);			
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);  // deixa a tela de trás bloqueada para mexer.
			stage.setTitle("Venda de Ingresso");
			stage.getIcons().add(new Image("/br/com/empresa/icones/MenuIngressosIcon.png")); //seta a imagem na barra superior.
			stage.centerOnScreen();
			stage.show();

		}	catch(IOException e){
				System.out.println("Erro ao gerar a tela da venda de ingresso.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
		
	
	@FXML
	private void abrirRelatorios() {
		//Aqui irá abrir o relatório a partir da criação do objeto pois o código está no construtor dele.
		RelatorioVendas rv = new RelatorioVendas();
		rv.gerarRelatorioDeVendas();
	}
	
	@FXML
	private void abrirSobre(){
		Stage stage = new Stage();
		Parent root = null;
		
		try{
			root = FXMLLoader.load(getClass().getResource("/br/com/empresa/view/Sobre.fxml"));
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);  // deixa a tela de trás bloqueada para mexer.
			stage.setTitle("Sobre");
			stage.getIcons().add(new Image("/br/com/empresa/icones/MenuSobreIcon.png")); //seta a imagem na barra superior.
			stage.centerOnScreen();
			stage.show();
			
		}	catch(IOException e){
				System.out.println("Erro ao gerar a tela Sobre.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
	
	//Botão que fecha o programa.
	@FXML
	public void fecharPrograma(ActionEvent event){
		Platform.exit();
	}
	
	@FXML
    private void mouseOverButton(MouseEvent event){    	
    	inserirEfeitoDeOpacidadeMaior(event);  	
    }
	
	@FXML
    private void mouseOverAnchorPane(MouseEvent event) {    	
    	inserirEfeitoDeOpacidadeMenor();  	
    }	
}
