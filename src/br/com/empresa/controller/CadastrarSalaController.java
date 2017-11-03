package br.com.empresa.controller;

import java.util.ArrayList;
import br.com.empresa.alertasMensagens.MensagemDeAlerta;
import br.com.empresa.dao.SalaDAO;
import br.com.empresa.dao.SessaoDAO;
import br.com.empresa.model.Sala;
import br.com.empresa.model.Sessao;
import br.com.empresa.modelTabela.TabelaSala;
import br.com.empresa.util.ValidadorDeComponente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CadastrarSalaController {
			
	@FXML private Button btnCadastrarSala;
	@FXML private Button btnSelecionarSala;
	@FXML private Button btnAlterarSala;
	@FXML private Button btnExcluirSala;
	@FXML private Button btnCancelar;
	@FXML private TextField tfNroSala;
	@FXML private TextField tfNroLinhas;
	@FXML private TextField tfNroColunas;
	@FXML private Label lbQtdPoltronas;	
	@FXML private Label lbAlteracao;
	
	@FXML private TableView<TabelaSala> tvTabelaSala;
	@FXML private TableColumn<TabelaSala, Integer> tcCodSala;
	@FXML private TableColumn<TabelaSala, Integer> tcNroSala;
	@FXML private TableColumn<TabelaSala, Integer> tcQtdAssentos;			
	
	ObservableList<TabelaSala> listaTabelaSala = FXCollections.observableArrayList();	
	
	private boolean permitirOperacao = false;
	private int nroSala, nroLinhas, nroColunas, qtdAssentos;
	
	public void initialize() { 
		desabilitarComponentes();
		btnSelecionarSala.setDisable(true);
		preencherTabelaSalas();
	}	
	
	private void limparCampos(){
		tfNroSala.clear();
		tfNroLinhas.clear();
		tfNroColunas.clear();
		lbQtdPoltronas.setText("");
	}	
	
	private void desabilitarComponentes(){
		btnAlterarSala.setDisable(true);
		btnExcluirSala.setDisable(true);
		btnSelecionarSala.setDisable(true);
		tfNroLinhas.setDisable(true);
		tfNroColunas.setDisable(true);
	}
	
	private void preencherTabelaSalas(){
		SalaDAO dao = new SalaDAO();		
		ArrayList<Sala> salas = dao.listarSalas();
		
		//se a lista da tabela sala não estiver vazia ele limpa os campos primeiros.
		if(!listaTabelaSala.isEmpty()){
			listaTabelaSala.clear();  // primeiro limpa a tabela para depois carregar novamente.
		}
		
		for(Sala s: salas){      //convertendo da classe Sala para o tipo TabelaSala
			TabelaSala ts = new TabelaSala(s.getCodSala(), s.getNroSala(), s.getQtdAssentos());
			listaTabelaSala.add(ts);					
		}
		
		tcCodSala.setCellValueFactory(new PropertyValueFactory<TabelaSala, Integer>("codSala"));
		tcNroSala.setCellValueFactory(new PropertyValueFactory<TabelaSala, Integer>("nroSala"));
		tcQtdAssentos.setCellValueFactory(new PropertyValueFactory<TabelaSala, Integer>("qtdAssentos"));
		
		tvTabelaSala.setItems(listaTabelaSala);  // preenchendo a lista do tipo TabelaSala.	
	}	
	
	private Sala preencherObjetoSala(Sala s, ActionEvent event){  // recebendo um objeto sala, e um obj botão.
		Button btn = (Button) event.getTarget(); //Recuperando (o objeto botão) qual botão da tela foi clicado.
		
		//se não foi clicado no botão de cadastrar, então é necessário pegar o codigo do filme selecionado na tabela.
		if(!btn.getText().equals("Cadastrar")){  // verifica se NAO é o botao cadastrar que foi clicado.
			int codigoSala = tvTabelaSala.getSelectionModel().getSelectedItem().getCodSala(); // pegando o código da tabela.
			s.setCodSala(codigoSala);
		}			
		
		s.setNroSala(nroSala);
		s.setQtdeDeLinhasDePoltronas(nroLinhas);
		s.setQtdeDeColunasDePoltronas(nroColunas);
		s.setQtdAssentos(qtdAssentos);			
		
		return s;   // preencheu o objeto e está mandando de volta para ser realizada a operação seguinte.
	}
	
	public boolean validarCampos(String numeroSala, String numLinhas, String numColunas){				
		int nroSala, qtdAssentos;		
		ArrayList<Sala> salas;
		
		SalaDAO dao = new SalaDAO();
		salas = dao.listarSalas();
		
		//Verificando se os componentes da tela não são vazios.   Se algum for vazio retorna falso.
		if(numeroSala.isEmpty() || numLinhas.isEmpty() || numColunas.isEmpty()){			
			MensagemDeAlerta.enviarMensagemDeCampoVazio();
			return false;
		}
				//Se nenhum campo da tela for vazaio, as variáveis são preenchidas com os valores inseridos na tela.
		nroSala = Integer.parseInt(tfNroSala.getText());
		nroLinhas = Integer.parseInt(tfNroLinhas.getText());
		nroColunas = Integer.parseInt(tfNroColunas.getText());
		qtdAssentos = Integer.parseInt(lbQtdPoltronas.getText()); 
								
		//A validação é feita apenas com 0 pq no evento do textField ele nao deixa colocar o sinal de menos.
		//Logo, a validação de números negativos ja é feita no evento do textField.
		if(nroSala == 0){
			MensagemDeAlerta.enviarMensagemDeNumeroDaSalaInvalido();
			tfNroSala.clear();
			return false;
		}		
		
		if(nroLinhas == 0 || nroColunas == 0){
			MensagemDeAlerta.enviarMensagemDeLinhasOuColunasDeAssentosDaSalaInvalidos();
			tfNroLinhas.clear();
			tfNroColunas.clear();
			return false;
		}		
		
		if(qtdAssentos == 0){ 
			MensagemDeAlerta.enviarMensagemDeAssentosInvalido();
			lbQtdPoltronas.setText("");
			return false;
		}	    		
		
		for(Sala s: salas){ 	             //Validando se está cadastrando a sala com o número igual ao de uma sala já existente.
			if(nroSala == s.getNroSala()){
				MensagemDeAlerta.enviarMensagemDeSalaExistente();
				tfNroSala.clear();
				return false;
			}
		}												
		return true;        
	}
	
	//evento do botão cadastrarSala
	@FXML
	public void cadastrarSala(ActionEvent event){						
		permitirOperacao = validarCampos(tfNroSala.getText(), tfNroLinhas.getText(), tfNroColunas.getText());
		
		if(permitirOperacao){
			nroSala = Integer.parseInt(tfNroSala.getText());
			nroLinhas = Integer.parseInt(tfNroLinhas.getText());
			nroColunas = Integer.parseInt(tfNroColunas.getText());
			qtdAssentos = Integer.parseInt(lbQtdPoltronas.getText());
			
			Sala sala = new Sala();
			sala = preencherObjetoSala(sala, event);
						
			SalaDAO dao = new SalaDAO();		
			dao.cadastrarSala(sala);
			
			limparCampos();
			preencherTabelaSalas();
			MensagemDeAlerta.enviarMensagemDeSucessoNoCadastro();			
		}				
	}	
	
	@FXML
	public void selecionarFilme(ActionEvent event){				
		lbAlteracao.setVisible(true);
		btnCadastrarSala.setDisable(true);
		btnAlterarSala.setDisable(false);
		btnExcluirSala.setDisable(false);
	}
	
	@FXML
	public void alterarSala(ActionEvent event){					
		permitirOperacao = validarCampos(tfNroSala.getText(), tfNroLinhas.getText(), tfNroColunas.getText());
		
		if(permitirOperacao){
			nroSala = Integer.parseInt(tfNroSala.getText());
			qtdAssentos = Integer.parseInt(lbQtdPoltronas.getText());
			
			Sala sala = new Sala();
			sala = preencherObjetoSala(sala, event);
						
			SalaDAO dao = new SalaDAO();		
			dao.alterarSala(sala);

			limparCampos();
			MensagemDeAlerta.enviarMensagemDeSucessoNaAlteracao(); //chamando o método estático da classe AlertaMensagem.
			desabilitarComponentes();
			preencherTabelaSalas();
			btnCadastrarSala.setDisable(false);
			lbAlteracao.setVisible(false);
		} 
	}
	
	@FXML
	public void selecionarSala(ActionEvent event){	
		lbAlteracao.setVisible(true);
		btnCadastrarSala.setDisable(true);
		btnAlterarSala.setDisable(false);
		btnExcluirSala.setDisable(false);
	}
	
	@FXML
	public void excluirSala(ActionEvent event){	
		boolean permitirExclusao = true;
		int codigoSala = tvTabelaSala.getSelectionModel().getSelectedItem().getCodSala(); // pegando o código da tabela.
		
		// PARA REMOVER UMA SALA, PRECISA-SE ANTES RECUPERAR TODAS AS SESSOES PARA FAZER UMA VALIDAÇÃO.
		// PORQUE  SE A SALA QUE VAI SER EXCLUÍDA AINDA ESTIVER EM ALGUMA SESSÃO, NÃO É POSSÍVEL EXCLUI-LA
		// PORQUE EXISTE UMA CHAVE ESTRANGEIRA NO BANCO, ASSIM FAZENDO COM QUE A SESSAO ONDE ESTA SALA ESTÁ
		// IMPEÇA QUE A SALA SEJA EXCLUÍDA. É NECESSÁRIO PRIMEIRO EXCLUIR A SESSÃO E AI SIM DEPOIS A SALA.
		
		ArrayList<Sessao> sessoes;
		SessaoDAO sdao = new SessaoDAO();
		sessoes = sdao.listarSessoes();   //recuperando as sessoes cadastradas.
		
		for(Sessao s: sessoes){   // verificando se o filme que vai ser excluido está inserido em alguma sessão cadastrada.
			if(s.getSala().getCodSala() == codigoSala){
				permitirExclusao = false;
				MensagemDeAlerta.enviarMensagemDeErroNaExclusaoDaSala(s);
			} 
		}			
		
		if(permitirExclusao){
			SalaDAO dao = new SalaDAO();
			dao.excluirSala(codigoSala);		
			
			MensagemDeAlerta.enviarMensagemDeSucessoNaExclusao(); //chamando o método estático da classe AlertaMensagem.
			desabilitarComponentes();
			preencherTabelaSalas();
			
			btnCadastrarSala.setDisable(false);
			lbAlteracao.setVisible(false);
		}			
	}
	
		//Botão que fecha a janela funcionando
	@FXML	
	public void voltarAoMenu(ActionEvent event){		
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}	
	 
	
	//   --------------------------------   EVENTOS DE COMPONENTES  ------------------------------- //
	
	@FXML       //Evento para formatar o campo textField para pegar apenas números no campo.
    private void verificarCaracterNumerico(KeyEvent event){    			
		int nrolinhas = 0;
		int nroColunas = 0;
		int resultado = 0;				
		
		TextField tf = (TextField) event.getTarget(); //Recuperando o objeto textField que foi preenchido na tela.
		
		ValidadorDeComponente vc = new ValidadorDeComponente();
		vc.validarCampoNumerico(event, tf); // Essa classe irá validar esse textField.
		
		try{
			if(!tfNroSala.getText().isEmpty()){ // se o text field de nro da sala não for vazio ele entra no if
				tfNroLinhas.setDisable(false);				
			}	else{
					tfNroLinhas.setDisable(true);
					tfNroColunas.setDisable(true);
				}
												    //se o text field de nro linhas não for vazio ele entra no if 
			if(!tfNroLinhas.getText().isEmpty() && !tfNroSala.getText().isEmpty()){
					tfNroColunas.setDisable(false);
					
					if(!tfNroColunas.getText().isEmpty()){   // se o text field de nro colunas não for vazio ele entra no if
						nrolinhas = Integer.parseInt(tfNroLinhas.getText());
						nroColunas = Integer.parseInt(tfNroColunas.getText());
						resultado = nrolinhas * nroColunas;																	
					
					lbQtdPoltronas.setText(Integer.toString(resultado));
				}							
				
			}	else{
					tfNroColunas.setDisable(true);
				}
			
			if(tfNroLinhas.getText().isEmpty() || tfNroColunas.getText().isEmpty()){
				lbQtdPoltronas.setText("");
			}
			
		}	catch(Exception e){
				System.out.println("Erro no preenchimento dos textFields ou erro ao calcular a quantidade de poltronas da sala.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}				
	}
	
	@FXML   // Evento da tabela de salas no clique do mouse.
	private void pressionarItemTabelaSala(MouseEvent event){			
		try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
			if(tvTabelaSala.getSelectionModel().getSelectedItem() != null){
				// Ao pressionar um item da tabela de filmes, a tabela das salas fica habilitada.
				btnSelecionarSala.setDisable(false);	
			}	
			
		}	catch(Exception e){
				System.out.println("Erro ao selecionar o item na tabela de filmes.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}	
	
}