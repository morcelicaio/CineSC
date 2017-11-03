/*
package br.com.empresa.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import br.com.empresa.dao.RelatorioDAO;
import br.com.empresa.model.Relatorio;
import br.com.empresa.modelTabela.TabelaRelatorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RelatorioController {
	RelatorioDAO dao = new RelatorioDAO();
	ArrayList<Relatorio> vendas;
	
	@FXML Button btnVoltar;
	
	@FXML TableView<TabelaRelatorio> tvTabelaRelatorio;	
	
	@FXML TableColumn<TabelaRelatorio, Integer> tcCodIngresso;
	@FXML TableColumn<TabelaRelatorio, Integer> tcCodFilme;
	@FXML TableColumn<TabelaRelatorio, Integer> tcCodSala;
	@FXML TableColumn<TabelaRelatorio, String> tcNomeFilme;
	@FXML TableColumn<TabelaRelatorio, Integer> tcAnoLancamentoFilme;
	@FXML TableColumn<TabelaRelatorio, String> tcCategoriaFilme;
	@FXML TableColumn<TabelaRelatorio, String> tcValorArrecadadoFilme;		
	
	// CONSTRUTOR DA TELA.
	@FXML   
	private void initialize(){      
		preencherTabelaRelatorio();
	}	
	
	// setando os valores das colunas pelos seus atributos conforme estão na Classe TabelaRelatorio.
	private void setarValoresDasColunasDaTabela(){
		//setando os valores das colunas pelos seus nomes conforme o nome na Classe TabelaRelatório.
		tcCodIngresso.setCellValueFactory(new PropertyValueFactory<TabelaRelatorio, Integer>("codIngresso"));
		tcCodFilme.setCellValueFactory(new PropertyValueFactory<TabelaRelatorio, Integer>("codFilme"));
		tcCodSala.setCellValueFactory(new PropertyValueFactory<TabelaRelatorio, Integer>("codSala"));
		tcNomeFilme.setCellValueFactory(new PropertyValueFactory<TabelaRelatorio, String>("nomeFilme"));
		tcAnoLancamentoFilme.setCellValueFactory(new PropertyValueFactory<TabelaRelatorio, Integer>("anoLancamentoFilme"));
		tcCategoriaFilme.setCellValueFactory(new PropertyValueFactory<TabelaRelatorio, String>("categoriaFilme"));
		tcValorArrecadadoFilme.setCellValueFactory(new PropertyValueFactory<TabelaRelatorio, String>("valorArrecadadoFilme"));		
	}
	
	ObservableList<TabelaRelatorio> listaTabelaRelatorio = FXCollections.observableArrayList();
	
	private void preencherTabelaRelatorio(){
		RelatorioDAO dao = new RelatorioDAO();
		ArrayList<Relatorio> vendas = null;
		
		//vendas = dao.gerarRelatorioDeVendas();	SE QUISER USAR O RELATORIO MOSTRANDA NA TABELA, DESCOMENTAR ESTA LINHA.	
		
		//se a lista da tabela não for vazia
		if(!listaTabelaRelatorio.isEmpty()){
			listaTabelaRelatorio.clear();     // primeiro limpa a tabela para depois carregar novamente.
		}	
		
		for(Relatorio r: vendas){     //convertendo da classe Relatorio para o tipo TabelaRelatorio
			
			//Formatando o atributo do tipo double do relatorio antes de inserir na tabela.	
			// usando a classe DecimalFormat com o método format.
			String valorTotalString; 
			
			//Usando o valor 0 para formatar o número, então ele não vai ignorar os números '0' a esquerda
			//e nem a direita quando houve.
			//Usando o valor # para formatar o número, então ele vai ignorar os números '0' a esquerda e
			//só irá mostrar o número sem números 0 a esquerda.
			valorTotalString = new DecimalFormat("R$ ##,### 0.00").format(r.getValorArrecadadoFilme());
			
			TabelaRelatorio tr = new TabelaRelatorio(r.getCodIngresso(), r.getCodFilme(), r.getCodSala(), 
													 r.getNomeFilme(), r.getAnoLancamentoFilme(), 
													 r.getCategoriaFilme(), valorTotalString);
			
			listaTabelaRelatorio.add(tr);   // preenchendo a lista que vai ser colocada na tabela.
		}
		
		setarValoresDasColunasDaTabela();
		
		//setando na TableView a lista do tipo TabelaRelatorio q foi preenchida.
		tvTabelaRelatorio.setItems(listaTabelaRelatorio);
	}
	

	//Botão que fecha a janela
	@FXML	
	private void voltarAoMenu(ActionEvent event){			
		Stage stage = (Stage) btnVoltar.getScene().getWindow();
		stage.close();
	}
	
}
*/