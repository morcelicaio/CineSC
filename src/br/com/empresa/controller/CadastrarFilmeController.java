package br.com.empresa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import br.com.empresa.alertasMensagens.MensagemDeAlerta;
import br.com.empresa.dao.FilmeDAO;
import br.com.empresa.dao.SessaoDAO;
import br.com.empresa.model.Filme;
import br.com.empresa.model.Sessao;
import br.com.empresa.modelTabela.TabelaFilme;
import br.com.empresa.util.ValidadorDeComponente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CadastrarFilmeController {
	
	@FXML private TextField tfNomeFilme;
	@FXML private TextField tfNomeDiretor;
	@FXML private TextField tfCategoriaFilme;	
	@FXML private TextField tfAnoLancamentoFilme;
	@FXML private TextField tfBuscarFilme;
	@FXML private Button btnCadastrarFilme;
	@FXML private Button btnAlterarFilme;
	@FXML private Button btnExcluirFilme;
	@FXML private Button btnSelecionarFilme;
	@FXML private Button btnCancelar;
	@FXML private Label lbAlteracao;
	@FXML private ChoiceBox<String> cbLinguagem;
	
	@FXML private TableView<TabelaFilme> tvTabelaFilme;
	
	@FXML private TableColumn<TabelaFilme, Integer> tcCodFilme;
	@FXML private TableColumn<TabelaFilme, String> tcNomeFilme;
	@FXML private TableColumn<TabelaFilme, String> tcNomeDiretorFilme;
	@FXML private TableColumn<TabelaFilme, String> tcCategoriaFilme;	
	@FXML private TableColumn<TabelaFilme, Integer> tcAnoLancamentoFilme;
	@FXML private TableColumn<TabelaFilme, String> tcLinguagemFilme;
	
	ObservableList<TabelaFilme> listaTabelaFilme = FXCollections.observableArrayList();
	ObservableList<String> linguagens = (ObservableList<String>) FXCollections.observableArrayList("Dublado","Legendado");	
	
	private String nomeFilme, nomeDiretor, categoriaFilme, linguagemFilme;
	private int anoFilme;
	private boolean permitirOperacao = false;
	
	
	@FXML   //CONSTRUTOR DO CONTROLLER
	public void initialize() {	
		cbLinguagem.setValue("Dublado");
		cbLinguagem.setItems(linguagens);
		desabilitarComponentes();
		preencherTabelaFilmes();
	}
	
	private void limparCampos(){
		tfNomeFilme.clear();
		tfNomeDiretor.clear();
		tfAnoLancamentoFilme.clear();
		tfCategoriaFilme.clear();
	}
	
	private void desabilitarComponentes(){
		btnAlterarFilme.setDisable(true);
		btnExcluirFilme.setDisable(true);
		btnSelecionarFilme.setDisable(true);
	}
	
	//setando os valores das colunas pelos seus atributos conforme estão na Classe TabelaFilme.
	private void setarValoresDasColunasDaTabela(){		//
		tcCodFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, Integer>("codFilme"));			
		tcNomeFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("nomeFilme"));
		tcNomeDiretorFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("nomeDiretorFilme"));
		tcCategoriaFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("categoriaFilme"));
		tcAnoLancamentoFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, Integer>("anoLancamentoFilme"));
		tcLinguagemFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("linguagemFilme"));
	}
	
	private void preencherTabelaFilmes(){
		FilmeDAO dao = new FilmeDAO();
		ArrayList<Filme> filmes = null;
		
		filmes = dao.listarFilmes();					
				
		//se a lista nao for vazia
		if(!listaTabelaFilme.isEmpty()){
			listaTabelaFilme.clear(); // primeiro limpa a tabela para depois carregar novamente.			
		}
		
		for(Filme f: filmes){         //convertendo da classe filme para o tipo TabelaFilme
			TabelaFilme tf = new TabelaFilme(f.getCodFilme(), f.getNomeFilme(), f.getNomeDiretor(),
											 f.getCategoriaFilme(), f.getAnoLancamento(), f.getLinguagemFilme());
			listaTabelaFilme.add(tf); // preenchendo a lista que vai ser colocada na tabela.
		}
		
		
		setarValoresDasColunasDaTabela();		
		tvTabelaFilme.setItems(listaTabelaFilme);  // setando a lista que foi preenchida na tabela TabelaFilme.
	}	
	
	//Conforme o usuário digita os filmes iniciados com essa string são buscados no banco de dados.
	private void preencherTabelaRealizandoBuscaDeFilme(ArrayList<Filme> filmes){
		//se a lista nao for vazia
		if(!listaTabelaFilme.isEmpty()){
			listaTabelaFilme.clear(); // primeiro limpa a tabela para depois carregar novamente.			
		}
		
		for(Filme f: filmes){         //convertendo da classe filme para o tipo TabelaFilme
			TabelaFilme tf = new TabelaFilme(f.getCodFilme(), f.getNomeFilme(), f.getNomeDiretor(),
											 f.getCategoriaFilme(), f.getAnoLancamento(), f.getLinguagemFilme());
			listaTabelaFilme.add(tf); // preenchendo a lista que vai ser colocada na tabela.
		}
				
		setarValoresDasColunasDaTabela();		
		tvTabelaFilme.setItems(listaTabelaFilme);  // setando a lista que foi preenchida na tabela TabelaFilme.
	}
	
	private Filme preencherObjetoFilme(Filme f, ActionEvent event){  // recebendo um objeto filme, e um obj botão.
		Button btn = (Button) event.getTarget(); //Recuperando (o objeto botão) qual botão da tela foi clicado.
		//se não foi clicado no botão de cadastrar, então é necessário pegar o codigo do filme selecionado na tabela.

		if(!btn.getText().equals("Cadastrar")){  // verifica se NAO é o botao cadastrar que foi clicado.
			int codigoFilme = tvTabelaFilme.getSelectionModel().getSelectedItem().getCodFilme(); // pegando o código da tabela.
			f.setCodFilme(codigoFilme);
		}			
				
		f.setNomeFilme(nomeFilme);
		f.setNomeDiretor(nomeDiretor);
		f.setLinguagemFilme(linguagemFilme);
		f.setCategoriaFilme(categoriaFilme);
		f.setAnoLancamento(anoFilme);
		
		return f;   // preencheu o objeto e está mandando de volta para ser realizada a operação seguinte.
	}
	
	@FXML
	public void cadastrarFilme(ActionEvent event){		
		nomeFilme = tfNomeFilme.getText();
		nomeDiretor = tfNomeDiretor.getText();
		categoriaFilme = tfCategoriaFilme.getText();
		linguagemFilme = cbLinguagem.getValue();
		
		permitirOperacao = validarCampos(nomeFilme, nomeDiretor, categoriaFilme, tfAnoLancamentoFilme.getText());
		
		if(permitirOperacao){
			anoFilme = Integer.parseInt(tfAnoLancamentoFilme.getText());
			
			Filme f = new Filme();
			f = preencherObjetoFilme(f, event);
									
			FilmeDAO dao = new FilmeDAO();					
			dao.cadastrarFilme(f);
			
			limparCampos();
			MensagemDeAlerta.enviarMensagemDeSucessoNoCadastro(); //chamando o método estático da classe AlertaMensagem.
			preencherTabelaFilmes();
		}	
	}
	
	@FXML
	public void selecionarFilme(ActionEvent event){				
		lbAlteracao.setVisible(true);
		btnCadastrarFilme.setDisable(true);
		btnAlterarFilme.setDisable(false);
		btnExcluirFilme.setDisable(false);
	}
	
	@FXML
	public void alterarFilme(ActionEvent event){	
		nomeFilme = tfNomeFilme.getText();
		nomeDiretor = tfNomeDiretor.getText();
		categoriaFilme = tfCategoriaFilme.getText();
		linguagemFilme = cbLinguagem.getValue();
		
		permitirOperacao = validarCampos(nomeFilme, nomeDiretor, categoriaFilme, tfAnoLancamentoFilme.getText());
		
		if(permitirOperacao){
			anoFilme = Integer.parseInt(tfAnoLancamentoFilme.getText());
			
			Filme f = new Filme();
			f = preencherObjetoFilme(f, event);
									
			FilmeDAO dao = new FilmeDAO();					
			dao.alterarFilme(f);
			
			limparCampos();
			MensagemDeAlerta.enviarMensagemDeSucessoNaAlteracao(); //chamando o método estático da classe AlertaMensagem.
			desabilitarComponentes();
			preencherTabelaFilmes();
			
			btnCadastrarFilme.setDisable(false);
			lbAlteracao.setVisible(false);
		} 
	}
	
	@FXML
	public void excluirFilme(ActionEvent event){	
		boolean permitirExclusao = true;
		int codigoFilme = tvTabelaFilme.getSelectionModel().getSelectedItem().getCodFilme(); // pegando o código da tabela.
		
		// PARA REMOVER UM FILME, PRECISA-SE ANTES RECUPERAR TODAS AS SESSOES PARA FAZER UMA VALIDAÇÃO.
		// PORQUE  SE O FILME QUE VAI SER EXCLUÍDO  AINDA ESTIVER EM ALGUMA SESSÃO, NÃO É POSSÍVEL EXCLUI-LO
		// PORQUE EXISTE UMA CHAVE ESTRANGEIRA NO BANCO, ASSIM FAZENDO COM QUE A SESSAO ONDE ESTE FILME ESTÁ
		// IMPEÇA QUE O FILME SEJA EXCLUÍDO. É NECESSÁRIO PRIMEIRO EXCLUIR A SESSÃO E AI SIM DEPOIS O FILME.
		
		ArrayList<Sessao> sessoes;
		SessaoDAO sdao = new SessaoDAO();
		sessoes = sdao.listarSessoes();   //recuperando as sessoes cadastradas.
		
		for(Sessao s: sessoes){   // verificando se o filme que vai ser excluido está inserido em alguma sessão cadastrada.
			if(s.getFilme().getCodFilme() == codigoFilme){
				permitirExclusao = false;
				MensagemDeAlerta.enviarMensagemDeErroNaExclusaoDoFilme(s);
			}
		}
		
		if(permitirExclusao){
			FilmeDAO dao = new FilmeDAO();
			dao.excluirFilme(codigoFilme);			
			MensagemDeAlerta.enviarMensagemDeSucessoNaExclusao(); //chamando o método estático da classe AlertaMensagem.
			desabilitarComponentes();
			preencherTabelaFilmes();
			btnCadastrarFilme.setDisable(false);
			lbAlteracao.setVisible(false);
		}			
	}
	
	public boolean validarCampos(String nomeFilme, String nomeDiretor, String categoriaFilme, String anoFilmeString ){
		boolean camposCorretos = false;
		int anoFilme;
		Calendar c = GregorianCalendar.getInstance(); // Instanciando um objeto do tipo calendário.
		
		int anoAtual = c.get(Calendar.YEAR);    // recuperando o ano da data atual.
		
		//Verificando se os campos não estão vazios.
		if(nomeFilme.isEmpty() || nomeDiretor.isEmpty() || categoriaFilme.isEmpty() || anoFilmeString.isEmpty()){
			MensagemDeAlerta.enviarMensagemDeCampoVazio();			
		}	else{
				anoFilme = Integer.parseInt(anoFilmeString);  //convertendo o ano do filme para inteiro.				
				
				if(anoFilme < anoAtual){
					MensagemDeAlerta.enviarMensagemDeAnoFilmeMenorQueOAnoAtual();
					tfAnoLancamentoFilme.clear();
				}	else{		
						camposCorretos = true;
					}
			} 			
				
		return camposCorretos;		
	}
	
		//fechando o stage atual e voltando para o menu.
	@FXML	
	public void voltarAoMenu(ActionEvent event){		
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}
		
	//  --------------------------------   EVENTOS DE COMPONENTES  ------------------------------- //
	 
	@FXML       //Evento para formatar o campo textField para pegar apenas números no campo.
    private void verificarCaracterNumerico(KeyEvent event){    			
		TextField tf = (TextField) event.getTarget(); //Recuperando o objeto textField que foi preenchido na tela.
		
		ValidadorDeComponente vc = new ValidadorDeComponente();
		vc.validarCampoNumerico(event, tf); // Essa classe irá validar esse textField.		
	}
	
	
	@FXML       //Evento para formatar o campo textField para pegar apenas números no campo.
    private void verificarCaracterAlfabetico(KeyEvent event){		
		TextField tf = (TextField) event.getTarget(); //Recuperando o objeto textField que foi preenchido na tela.
		
		ValidadorDeComponente vc = new ValidadorDeComponente();
		vc.validarCampoAlfabetico(event, tf); // Essa classe irá validar esse textField.		
	}
	
	@FXML   //Evento de tecla digitada  no componente textField de buscar filme da tela
	private void buscarFilmePeloNome(KeyEvent ke){    //ke é a a tecla digitada			
		ArrayList<Filme> filmes;
		FilmeDAO dao = new FilmeDAO();
		
		if(ke.getCode() != KeyCode.CAPS && ke.getCode() != KeyCode.ENTER && ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.SPACE){		
			String s = tfBuscarFilme.getText(); // recbendo o texto escrito na busca.	
			filmes = dao.buscarFilme(s);
			preencherTabelaRealizandoBuscaDeFilme(filmes);
		}		
	}
	
	@FXML   // Evento da tabela de filmes no clique do mouse.
	private void pressionarItemTabelaFilme(MouseEvent event){			
		try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
			if(tvTabelaFilme.getSelectionModel().getSelectedItem() != null){
				// Ao pressionar um item da tabela de filmes, a tabela das salas fica habilitada.
				btnSelecionarFilme.setDisable(false);	
			}	
			
		}	catch(Exception e){
				System.out.println("Erro ao selecionar o item na tabela de filmes.");
				System.out.println(e.getMessage());
			}
	}	
}
