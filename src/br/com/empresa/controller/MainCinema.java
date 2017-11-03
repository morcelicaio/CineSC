package br.com.empresa.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainCinema extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try{			
			Parent root = FXMLLoader.load(getClass().getResource("/br/com/empresa/view/MenuPrincipal.fxml"));
			
			primaryStage.setTitle("Menu");
			primaryStage.setScene(new Scene(root));									// e nem maximizar a tela.
			primaryStage.setResizable(false); //não permite arrastar a janela para aumentar o seu tamanho.
			primaryStage.getIcons().add(new Image("/br/com/empresa/icones/PipocaIcon.png")); //seta na barra superior.
			primaryStage.centerOnScreen();
			primaryStage.show();
					
		}	catch(IOException e){
				System.out.println("Erro ao inicializar o sitema.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}			
	}

	public static void main(String[] args) {
		launch(args);
	}
}


