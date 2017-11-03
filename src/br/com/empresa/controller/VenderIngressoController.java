package br.com.empresa.controller;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import br.com.empresa.alertasMensagens.MensagemDeAlerta;
import br.com.empresa.dao.IngressoDAO;
import br.com.empresa.dao.SessaoDAO;
import br.com.empresa.model.Ingresso;
import br.com.empresa.model.Sala;
import br.com.empresa.model.Sessao;
import br.com.empresa.modelTabela.TabelaSessao;
import br.com.empresa.util.BotaoPoltrona;
import br.com.empresa.util.ConversorDeValorMonetario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VenderIngressoController {
		
	@FXML private TableView<TabelaSessao> tvTabelaSessao;
	@FXML private TableColumn<TabelaSessao, Integer> tcCodFilme; // coluna visible false na tabela.
	@FXML private TableColumn<TabelaSessao, String> tcDataSessao;
	@FXML private TableColumn<TabelaSessao, Integer> tcNroSala;
	@FXML private TableColumn<TabelaSessao, String> tcNomeFilme;
	@FXML private TableColumn<TabelaSessao, String> tcLinguagemFilme;
	@FXML private TableColumn<TabelaSessao, Integer> tcPoltronasDisponiveis;
	@FXML private TableColumn<TabelaSessao, String> tcHoraInicio;
	@FXML private TableColumn<TabelaSessao, String> tcHoraFim;
	@FXML private TableColumn<TabelaSessao, Double> tcPreco;
	@FXML private TableColumn<TabelaSessao, Integer> tcCodSala; // coluna visible false na tabela.
	
	@FXML private Button btnVenderIngresso;
	@FXML private Button btnEscolherPoltrona;
	@FXML private Button btnCancelar;
	@FXML private TextField tfBuscarSessao;
	@FXML private ChoiceBox<String> cbTipoIngresso;
	@FXML private ChoiceBox<String> cbFormaPagamento;	
	@FXML private Label lbValorIngresso;   // Este label est� oculto na tela.
	
	ObservableList<String> tiposPagamento = (ObservableList<String>) FXCollections.observableArrayList("Dinheiro", "Cart�o de Cr�dito");
	ObservableList<String> tiposIngresso = (ObservableList<String>) FXCollections.observableArrayList("Inteira", "Meia");
	
	private int codSala, codFilme, qtdPoltronasDisponiveis;      //usado no bot�o venderIngresso
	private String tipoIngresso;							     //usado no bot�o venderIngresso
	private String horaInicioString; //horaFimString;		     //usado no bot�o venderIngresso
	private double valorSessao;								     //usado no bot�o venderIngresso
	private Date dataDaSessao = new Date();					     //usado no bot�o venderIngresso
	private Time horaInicioSessao;							     //usado no bot�o venderIngresso
	
	
	private String dataSessaoString;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");
	private ConversorDeValorMonetario cvm = new ConversorDeValorMonetario();
	
	BotaoPoltrona botaoConfirmacao = new BotaoPoltrona();         // Usado na tela din�mica
	BotaoPoltrona btnDesfazerEscolha = new BotaoPoltrona();       // Usado na tela din�mica
	BotaoPoltrona btnSairTelaPoltronas = new BotaoPoltrona();       // Usado na tela din�mica
	
	private Sessao sessaoIngresso;								  // Usado na tela din�mica
	private Sala sala;											  // Usado na tela din�mica	
	private boolean permitirAlteracaoDoEstadoDaPoltrona = true;	  // Usado na tela din�mica
	private Integer numeroDoBotaoCriadoNaMatriz;				  // Usado na tela din�mica
	private int numeroDaPoltronaDaTela = -1;	                  //usado no bot�o escolherPoltrona e venderIngresso
	private ArrayList<BotaoPoltrona> botoesDaTela = new ArrayList<>();   // Usado na tela din�mica
	
	@FXML  //construtor do controller
	private void initialize() {	 
		cbTipoIngresso.setItems(tiposIngresso);		
		cbFormaPagamento.setItems(tiposPagamento);				
		
		lbValorIngresso.setText("Selecione o filme para ver o valor");
		preencherTabelaSessoes();
		
		desabilitarComponentes();
	}
	
	 // Lista que ir� popular a tabela.
	ObservableList<TabelaSessao> listaTabelaSessao = FXCollections.observableArrayList();
	
	private void limparCampos(){
		tvTabelaSessao.setSelectionModel(tvTabelaSessao.getSelectionModel());//recebe a tabela sem nada selecionado.
		cbTipoIngresso.getSelectionModel().clearSelection();  // limpa o choiceBox		
		cbFormaPagamento.getSelectionModel().clearSelection();
		tfBuscarSessao.clear();
	}
	
	private void desabilitarComponentes(){
		cbTipoIngresso.setDisable(true);
		cbFormaPagamento.setDisable(true);
		btnVenderIngresso.setDisable(true);
		btnEscolherPoltrona.setDisable(true);
		btnEscolherPoltrona.setDisable(true);
	}
	
	//setando os valores das colunas pelos seus atributos conforme est�o na Classe TabelaSessao.
	private void setarValoresDasColunasDaTabela(){		//
		tcCodFilme.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Integer>("codFilme"));
		tcDataSessao.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("dataSessao"));
		tcNroSala.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Integer>("nroSala"));
		tcNomeFilme.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("nomeFilme"));
		tcLinguagemFilme.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("linguagemFilme"));
		tcHoraInicio.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("horaInicioSessao"));
		tcHoraFim.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("horaFimSessao"));
		tcPoltronasDisponiveis.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Integer>("qtdPoltronasDisponiveis"));
		tcPreco.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Double>("valorSessao"));
		tcCodSala.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Integer>("codSala"));
	}
	
	// quando muda o valor do choiceBox  tipoIngresso, ele verifica qual foi selecionado e seta o valor daquele tipo no label.
	private void ListennerTipoIngresso(Label lbValorIngresso){ // chave 1	  	
	  cbTipoIngresso.getSelectionModel().selectedItemProperty().addListener  ( 
	    (v, oldValue, newValue) ->{ // chave 3
		    double valor;
		    String valorStringDaTabela;
		      									//1o if
		    if(tvTabelaSessao.getSelectionModel().getSelectedItem() != null){
		        valorStringDaTabela = tvTabelaSessao.getSelectionModel().getSelectedItem().getValorSessao();
		    	  
		          //converte o valor monet�rio que vem como string da tabela para uma valor do tipo double.
			    valor = cvm.converterTextoDeValorMonetarioDaTabela(valorStringDaTabela);
				  
				if(newValue == "Inteira"){		
											
				        //formata o valor double para ficar com duas casas decimais ap�s a v�rgula.							
				    lbValorIngresso.setText(String.format("%.2f", valor) );  				
					cbFormaPagamento.setDisable(false);				
					}   else{
							valor = cvm.converterTextoDeValorMonetarioDaTabela(valorStringDaTabela);
				            valor = valor/2; // como � meia entrada, o valor � dividido na metade.
					 	
				            lbValorIngresso.setText(String.format("%.2f", valor));				 	
				            cbFormaPagamento.setDisable(false);
					  	}   // else
		    } // 1o if  
			  
		} // chave 3
	    
	  );	 // primeiro abre par�nteses.		    
	} // chave 1	
	
	
	private void ListennerFormaPagamento(){ // chave 1		
        cbFormaPagamento.getSelectionModel().selectedItemProperty().addListener ( 
		    (v, oldValue, newValue) -> {    // chave 2		    	  
		    	btnEscolherPoltrona.setDisable(false);        		      
			}     // chave 2
		    
		);	 // primeiro abre par�nteses.		    
	} // chave 1	
	
	private void preencherTabelaSessoes(){
		SessaoDAO dao = new SessaoDAO();
		ArrayList<Sessao> sessoes = null;
		
		try{
			sessoes = dao.listarSessoesComDataValidaParaVendaDeIngresso();
			
			//se a lista nao for vazia
			if(!listaTabelaSessao.isEmpty()){
				listaTabelaSessao.clear(); // primeiro limpa a tabela para depois carregar novamente.			
			}
				//foreach
			for(Sessao s: sessoes){ //convertendo da classe Sessao para o tipo TabelaSessao
				String valorTotalString;
				
				//Formatando a data da sess�o antes de inserir na tabela.	
				// usando o dateFormat declarado no topo do arquivo.
				dataSessaoString = sdf.format(s.getDataSessao()); 
				
				valorTotalString = new DecimalFormat("R$ ##,### 0.00").format(s.getValorSessao());
				
				TabelaSessao ts = new TabelaSessao(s.getFilme().getCodFilme(), dataSessaoString, 
						                           s.getSala().getNroSala(), s.getFilme().getNomeFilme(), 
						                           s.getFilme().getLinguagemFilme(), 
						                           s.getHoraInicioSessao().toString(), 
						                           s.getHoraFimSessao().toString(),  
						                           s.getQtdPoltronasDisponiveis(), valorTotalString, 
						                           s.getSala().getCodSala());
				
				listaTabelaSessao.add(ts); // preenchendo a lista que vai popular tabela.
				
				setarValoresDasColunasDaTabela();												
			}
			
			tvTabelaSessao.setItems(listaTabelaSessao);
			
		}	catch(Exception e){
				e.getMessage();
				System.out.println("Erro na cria��o da tabela.");
			}			
	}
	
	//Preenche a tabela das sess�es quando se utiliza a busca(textField) para achar as sess�es do filme.
	private void preencherTabelaSessoesUtilizandoBusca(ArrayList<Sessao> sessoes){				
		try{					
			//se a lista nao for vazia
			if(!listaTabelaSessao.isEmpty()){
				listaTabelaSessao.clear(); // primeiro limpa a tabela para depois carregar novamente.			
			}
				//foreach
			for(Sessao s: sessoes){ //convertendo da classe Sessao para o tipo TabelaSessao
				String valorTotalString;
				
				//Formatando a data da sess�o antes de inserir na tabela usando o dateFormat declarado no topo do arquivo.
				dataSessaoString = sdf.format(s.getDataSessao()); 
				
				valorTotalString = new DecimalFormat("R$ #,##0.00").format(s.getValorSessao());
				
				TabelaSessao ts = new TabelaSessao(s.getFilme().getCodFilme(), dataSessaoString, s.getSala().getNroSala(), 
												   s.getFilme().getNomeFilme(), s.getFilme().getLinguagemFilme(), 
												   s.getHoraInicioSessao().toString(), s.getHoraFimSessao().toString(),  
                        						   s.getQtdPoltronasDisponiveis(), valorTotalString, s.getSala().getCodSala());
				
				listaTabelaSessao.add(ts); // preenchendo a lista que vai popular tabela.				
				setarValoresDasColunasDaTabela();						
			}
			
			tvTabelaSessao.setItems(listaTabelaSessao);
			
		}	catch(Exception e){
				e.getMessage();
				System.out.println("Erro na cria��o da tabela a partir da busca.");
			}			
	}
	
	@FXML
	private void venderIngresso(ActionEvent e){
		try{
			if(numeroDaPoltronaDaTela >= 0){    //significa que foi selecionada alguma poltrona v�lida na tela.
				//pegando a data formatada que vem como string da tabela.
				dataSessaoString = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao();	
				
				// usando o dateFormat declarado no topo do arquivo para converter o dado para o tipo Date.				
				dataDaSessao = new java.sql.Date( ((java.util.Date)sdf.parse(dataSessaoString)).getTime() );
				
				//pegando os dados da tabela para preencher o objeto ingresso.
				codFilme = tvTabelaSessao.getSelectionModel().getSelectedItem().getCodFilme();
				codSala = tvTabelaSessao.getSelectionModel().getSelectedItem().getCodSala();			
				horaInicioString = tvTabelaSessao.getSelectionModel().getSelectedItem().getHoraInicioSessao();
				//horaFimString = tvTabelaSessao.getSelectionModel().getSelectedItem().getHoraFimSessao();
				qtdPoltronasDisponiveis = tvTabelaSessao.getSelectionModel().getSelectedItem().getQtdPoltronasDisponiveis();
				valorSessao = cvm.converterTextoDeValorMonetarioDaTabela((tvTabelaSessao.getSelectionModel().getSelectedItem().getValorSessao()));							
				
				tipoIngresso = cbTipoIngresso.getValue();		
				
				Date auxiliar = new Date(); 						    //Criando um date para pegar somente a hora da vari�vel horaInicioString.			
				auxiliar = formatadorHora.parse(horaInicioString);     // Usando o dateFormat declarado no topo do arquivo.
				horaInicioSessao = new Time (auxiliar.getTime());//  Recuperando a hora.	(vem em milisegundos).		
				
				SessaoDAO dao = new SessaoDAO(); //recbendo a qtd de poltronas dispon�veis
				Sessao s = new Sessao();
				 
				// Busca a sess�o selecionada para atualizar a quantidade de poltronas daquela sess�o.
				s = dao.buscarSessaoSelecionada((java.sql.Date) dataDaSessao, horaInicioSessao, codSala, codFilme);			
				qtdPoltronasDisponiveis = s.getQtdPoltronasDisponiveis();												
												   //Validando se existem poltronas dispon�veis para a venda.
				if(qtdPoltronasDisponiveis >= 1){ // S�o vendidos um ingresso de cada vez por isso compara se � >= 1.
					qtdPoltronasDisponiveis = qtdPoltronasDisponiveis - 1;
					Ingresso ing = new Ingresso();
					
					ing.setDataSessao(dataDaSessao);
					ing.setTipoIngresso(tipoIngresso);
					ing.setHoraInicioSessao(horaInicioSessao);
					ing.setCodSala(codSala);
					ing.setCodFilme(codFilme);
					ing.setValorTotal(valorSessao);
					ing.setNumPoltrona(numeroDaPoltronaDaTela);  // recebe o n�mero da poltrona que foi selecionada na tela de escolher poltrona.
					
					if(tipoIngresso == "Meia"){
						ing.setValorTotal((valorSessao / 2));							
					}
					
					// Atualizando a quantidade de poltronas dispon�veis da sala depois de vender o ingresso.				
					dao = new SessaoDAO();       //E PARA ACESSAR O BANCO DE NOVO COM UMA CONEXAO CRIADA, �
					dao.atualizarPoltronas(qtdPoltronasDisponiveis, codSala, s);//NECESS�RIO CRIAR O OBJETO NOVAMENTE.
					
					IngressoDAO ingDao = new IngressoDAO();
					ingDao.cadastrarIngresso(ing);							
					
					preencherTabelaSessoes();
					tfBuscarSessao.setText("");
					
					limparCampos();
					lbValorIngresso.setText("Selecione o filme para ver o valor");
					desabilitarComponentes();
					MensagemDeAlerta.enviarMensagemDeIngressoVendido();
					
					numeroDaPoltronaDaTela = -1; // depois que vende o ingresso, seta para -1  para deixar o valor inv�lido.
												 // caso a pessoa fecha a tela e tente vender sem escolher a poltrona.
					botoesDaTela.clear();        // depois limpo a lista dos botoes da tela para poder carregar novamente depois.
				}	else{
						MensagemDeAlerta.enviarMensagemDePoltronasOcupadas();
					}
				
				/*caso eu for inserir este atributo no meu banco no futuro, aqui est� o c�digo pronto j� para setar.
				// usando o dateFormat declarado no topo do arquivo.
				auxiliar = formatadorHora.parse(horaFimString);
				Time horaFimSessao = new Time (auxiliar.getTime());
				*/
			}	else{
				Alert a = new Alert(AlertType.ERROR, "TENTOU VENDER SEM ESCOLHER A POLTRONA\n NUMERO DA POLTRONA DA TELA: ."+numeroDaPoltronaDaTela);
				a.show();
					//manda uma mensagem avisando que o cara precisa escolher alguma poltrona primeiro...algo do tipo.
				}
		} 	catch (Exception ex) {
				System.out.println("Erro na venda do ingresso.");
				System.out.println( ex.getMessage());
				ex.printStackTrace();
			}  
	}																			
	
	@FXML
	private void escolherPoltrona(ActionEvent event){
		btnVenderIngresso.setDisable(true);
		try{			// Recuperando os dados da tela primeiro para depois  come�ar a montar a tela din�mica de poltronas.
			codSala = tvTabelaSessao.getSelectionModel().getSelectedItem().getCodSala();
			codFilme = tvTabelaSessao.getSelectionModel().getSelectedItem().getCodFilme();
			horaInicioString = tvTabelaSessao.getSelectionModel().getSelectedItem().getHoraInicioSessao();
			String dataSessaoString = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao();	//recupera a data string da tabela.				
					
			//Date dataTeste = new java.sql.Date( ((java.util.Date)sdf.parse(dataSessaoString)).getTime() ); //converte a str da tabela para date.
			dataDaSessao = new java.sql.Date( ((java.util.Date)sdf.parse(dataSessaoString)).getTime() ); //converte a str da tabela para date.
			
			Date auxiliar = new Date();  //Criando um date para pegar somente a hora da vari�vel horaInicioString.			
			auxiliar = formatadorHora.parse(horaInicioString);  // Usando o dateFormat declarado no topo do arquivo.											
			horaInicioSessao = new Time (auxiliar.getTime());//  Recuperando a hora.	(vem em milisegundos).						   										
			
			SessaoDAO dao = new SessaoDAO(); // criando um objeto para poder recuperar a sess�o selecionada.
			sessaoIngresso = new Sessao();   // e a sala desta sess�o.
			 
			// Buscando a sess�o selecionada para recuperar a sala desta sess�o 
			// e todos os nros de poltronas ocupadas desta sess�o para guardar em uma lista.	
			sessaoIngresso = dao.buscarSessaoSelecionada((java.sql.Date) dataDaSessao, horaInicioSessao, codSala, codFilme);				
			
			sala = new Sala();				 // Recuperando o objeto sala desta sess�o. A partir deste objeto � poss�vel
			sala = sessaoIngresso.getSala(); // recuperar a quantidade de linhas e de colunas da sala p montar a tela.			
			
		}	catch(Exception e){
				System.out.println("Erro ao escolher a poltrona.");
				System.out.println( e.getMessage());
				e.printStackTrace();		
			}		
				
		//Aqui, recebe-se qual � a qtde de colunas de poltronas da sala e qual a qtde de linhas de poltronas da sala.
		//Isso servir� para poder montar a tela de acordo com quantas colunas e quantas linhas cada sala possui.
		int qtdeDeLinhas = sala.getQtdeDeLinhasDePoltronas();
		int qtdeDeColunas = sala.getQtdeDeColunasDePoltronas();
		
		int metadeDaSala = qtdeDeColunas/2;  // retorna um inteiro e ignora o valor ap�s a v�rgula.
		
		//Depois, � preciso ir ao banco e buscar os n�meros das poltronas j� ocupadas na tabela de Ingresso do bd.
		//Isso ser� guardado em uma lista para poder setar (trocar a cor) as poltronas ocupadas na nova tela.
					
		//Aqui � LISTA DAS POLTRONAS Q EST�O OCUPADAS Q VIR�O DO BANCO DA TABELA (INGRESSO) NO ATRIBUTO (numeroDaPoltrona).
		ArrayList<Integer> poltronasOcupadas = new ArrayList<>();			
		IngressoDAO ingDao = new IngressoDAO();			// Recuperando as poltronas (n�meros) ocupadas desta sess�o. 
		poltronasOcupadas = ingDao.recuperarPoltronasOcupadasDaSessao(sessaoIngresso);
		
		//  --------- COME�A  AQUI  O C�DIGO  PARA CRIAR A TELA E OS BOT�ES DA TELA DINAMICAMENTE.							
		GerenteBotao gb = new GerenteBotao();    //objeto que vai gerenciar o evento do bot�o.
	    
		GridPane root = new GridPane();			       //Foi usado um grid como painel. A grosso modo o grid � a tela.											
		root.setPadding(new Insets(15, 15, 18, 23));   // Setando um espa�amento no grid  (em cima, a direita, em baixo, a esquerda)						
		
		for (int coluna = 0; coluna < qtdeDeColunas; coluna++) {
			if( (coluna+1) == metadeDaSala){ // Encontra a metade da sala e d� um espa�amento maior(no caso � o corredor da sala).
				root.getColumnConstraints().add(new ColumnConstraints(90));//cria o valor do espa�amento de uma coluna p/ a outra. 
			}	else{
					root.getColumnConstraints().add(new ColumnConstraints(55));//cria o valor de espa�amento de 1 coluna p/ a outra.
				}			
		
			for (int linha = 0; linha < qtdeDeLinhas; linha++) {								
				root.getRowConstraints().add(new RowConstraints(45));     // cria o valor do espa�amento de uma linha para a outra.
		
				numeroDoBotaoCriadoNaMatriz = coluna * qtdeDeLinhas + linha; // c�lculo que retorna o n�mero do bot�o criado.
				
				BotaoPoltrona b = new BotaoPoltrona();	           // Esse objeto � criado p/ gerenciar os atributos do bot�o.
				b.setPrefWidth(38);      				           // setando a largura do bot�o 
				b.setPrefHeight(10);     				           // setando a altura do bot�o				
				
				b.setOnAction(gb); 		                           //Na a��o do bot�o, � passado como par�metro o obj GerenteBotao. 
														           //Esse objeto ir� conter o c�digo do evento do bot�o.				
				b.setId(numeroDoBotaoCriadoNaMatriz.toString());                     // gerando o id do bot�o.       
				b.setText(numeroDoBotaoCriadoNaMatriz.toString());                   //setando o texto do bot�o.
				b.setStyle("-fx-background-color: LIGHTGREEN; "
						 + "-fx-font-weight: bold");  			   //setando a cor e a propriedade de negrito no texto do bot�o.
				
				root.add(b, coluna, linha );	                       // adicionando o bot�o criado no grid
				botoesDaTela.add(b); //Todos os botoes de poltronas s�o adiciondos na lista para fazer a valida��o das poltronas ocupadas depois.
				
				//verificando se j� percorreu toda a matriz. Caso j� tenha percorrido toda a matriz, aqui � criado um novo bot�o
				//na linha de baixo da matriz e na coluna 0. 
				if(coluna == (qtdeDeColunas-1) && linha == (qtdeDeLinhas-1) ){					
					botaoConfirmacao.setPrefWidth(38);      				           
					botaoConfirmacao.setPrefHeight(40);
					botaoConfirmacao.setOnAction(gb);
					botaoConfirmacao.setId("btnConfirmacao");
					botaoConfirmacao.setText("OK");
					botaoConfirmacao.setStyle("-fx-font-weight: bold");
					botaoConfirmacao.setVisible(false);	
					botaoConfirmacao.setDisable(true);
					root.add(botaoConfirmacao, 0, (linha+1));   //  (coluna, linha)
					
					btnDesfazerEscolha.setPrefWidth(38);      				           
					btnDesfazerEscolha.setPrefHeight(40);
					btnDesfazerEscolha.setOnAction(gb);
					btnDesfazerEscolha.setId("btnDesfazerEscolha");
					btnDesfazerEscolha.setText("<<");
					btnDesfazerEscolha.setStyle("-fx-font-weight: bold");
					btnDesfazerEscolha.setVisible(false);	
					btnDesfazerEscolha.setDisable(true);
					root.add(btnDesfazerEscolha, 1, (linha+1));   //  (coluna, linha)	
					
					btnSairTelaPoltronas.setPrefWidth(38);      				           
					btnSairTelaPoltronas.setPrefHeight(40);
					btnSairTelaPoltronas.setOnAction(gb);
					btnSairTelaPoltronas.setId("btnSairTelaPoltronas");
					btnSairTelaPoltronas.setText("X");
					btnSairTelaPoltronas.setStyle("-fx-background-color: BLACK; -fx-text-fill: WHITE; -fx-font-weight: bold");
					root.add(btnSairTelaPoltronas, (qtdeDeColunas-1), (linha+1));   //  (coluna, linha)	
					
				}
				
				//Quando se vende um ingresso, o cliente escolhe o n�mero da sua poltrona antes e depois � salvo no banco na tabela (Ingresso).
				//Comparando as poltronas ocupadas que vieram do banco de dados da tabela (Ingresso) no atributo (numeroDaPoltrona).				
				for(int numPoltronaOcupada: poltronasOcupadas){
						numeroDaPoltronaDaTela = Integer.parseInt(numeroDoBotaoCriadoNaMatriz.toString());
					
					if(numPoltronaOcupada == numeroDaPoltronaDaTela ){
						b.setStyle("-fx-background-color: RED; "   
							     + "-fx-font-weight: bold");      //altera a cor do bot�o para red e coloca em negrito.
						b.setDisable(true); // desabilita o botao.
						b.setOcupado(true);
					}
				
				}
			} 
		}			
				
		Stage primaryStage = new Stage();		
		Scene scene;		// aqui eu instancio a cena, e dependendo do tamanho da sala (linhas e colunas) 
							//eu vou modelar o tamanho da tela e ai crio o objeto (new Scene(root, largura, altura)).
		
		if(qtdeDeLinhas < 10 && qtdeDeColunas < 10){
			scene = new Scene(root, 563, 488);                      //criando a cena e setando o tamanho do gridPane (painel)
		}	else if(qtdeDeLinhas == 10 && qtdeDeColunas == 10){ 		
				scene = new Scene(root, 610, 529);                    
			}	else if(qtdeDeLinhas == 10 && qtdeDeColunas == 20){
					scene = new Scene(root, 1161, 531);
				}	else if(qtdeDeLinhas == 15 && qtdeDeColunas == 15){
						scene = new Scene(root, 884, 752);      
					}	else if(qtdeDeLinhas == 10 && qtdeDeColunas == 15){
							scene = new Scene(root, 890, 745);       
						}	else if(qtdeDeLinhas == 15 && qtdeDeColunas > 15){
								scene = new Scene(root, 1195, 752); 
							}	else{
									scene = new Scene(root, 1195, 752);
								}
		
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL);  // deixa a tela de tr�s bloqueada para mexer.
		primaryStage.initStyle(StageStyle.UNDECORATED); // desabilitando o maximixar, o minimizar e o x (fechar) na janela.
	
		primaryStage.getIcons().add(new Image("/br/com/empresa/icones/MenuSalasIcon.png")); //seta a imagem na barra superior.			
		primaryStage.centerOnScreen();		
		primaryStage.show();			
	}
	
	//Classe que vai gerenciar as opera��es quando o bot�o for clicado.  (setOnAction  do bot�o).
		private class GerenteBotao implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {									
				String idBotao; 
				BotaoPoltrona botao = (BotaoPoltrona) event.getTarget();  // pegando qual componente foi selecionado (clicado)												
				
				idBotao = botao.getId();
				
				switch(idBotao){
					case "btnConfirmacao":
						permitirAlteracaoDoEstadoDaPoltrona = true;
						botoesDaTela.clear();
						btnVenderIngresso.setDisable(false);
						Stage stage = (Stage) botaoConfirmacao.getScene().getWindow();
						stage.close();
						break;
					
					case "btnDesfazerEscolha":														
							//percorrendo os botoes de poltrona q criei na tela p/ recuperar a poltrona q tinha sido escolhida.
							for(BotaoPoltrona botaoDaTela: botoesDaTela){
								if(botaoDaTela.getId().equals(Integer.toString(numeroDaPoltronaDaTela))){
									botaoDaTela.setStyle("-fx-background-color: LIGHTGREEN; "   
					                 		           + "-fx-font-weight: bold");//altera a cor do bot�o e coloca em negrito.
									
									botaoConfirmacao.setDisable(true);            // Desabilita o bot�o
									botaoConfirmacao.setVisible(false);			  // Deixa o bot�o invis�vel na tela.
									btnDesfazerEscolha.setDisable(true);
									btnDesfazerEscolha.setVisible(false);
									
									numeroDaPoltronaDaTela = -1;  // recebe -1  pq a contagem de poltronas come�ar s� a partir do 0.
									
									permitirAlteracaoDoEstadoDaPoltrona = true;
								}
							}																															
						break;
					
					case "btnSairTelaPoltronas":
						botoesDaTela.clear();
						numeroDaPoltronaDaTela = -1;
						permitirAlteracaoDoEstadoDaPoltrona = true;
						btnVenderIngresso.setDisable(true);
						Stage stageFechar = (Stage) btnSairTelaPoltronas.getScene().getWindow();
						stageFechar.close();
						break;
					default:						
						if(permitirAlteracaoDoEstadoDaPoltrona){
							botao.setStyle("-fx-background-color: RED; "   
								         + "-fx-font-weight: bold");      //altera a cor do bot�o para red e coloca em negrito.
						
							botaoConfirmacao.setStyle("-fx-background-color: BLACK; "
													+ "-fx-text-fill: WHITE; "
									                + "-fx-font-weight: bold");								
							botaoConfirmacao.setVisible(true);
							botaoConfirmacao.setDisable(false);
							btnDesfazerEscolha.setVisible(true);
							btnDesfazerEscolha.setDisable(false);		
							
							btnDesfazerEscolha.setStyle("-fx-background-color: BLACK; "
													  + "-fx-text-fill: WHITE; "
													  + "-fx-font-weight: bold");		
							
							//aqui no caso, vou pegar o n�mero da poltrona que o cara escolheu  e setar no  objeto ingresso.
							numeroDaPoltronaDaTela = Integer.parseInt(botao.getId());  // pega o nro do botao e guarda em um int.				
							botao.setOcupado(true);  						//deixa o bot�o setado como ocupado.
							
							permitirAlteracaoDoEstadoDaPoltrona = false;
						}	else{
								MensagemDeAlerta.enviarMensagemDeMarcacaoDePoltronaInvalida();
							}
				}			
			}		
		}
	
	//Bot�o que fecha a janela
		@FXML	
		private void voltarAoMenu(ActionEvent event){			
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();
		}
	
	
	//  ----------------------------- EVENTOS DOS COMPONENTES ---------------------------------------- //
	 
	@FXML   //Evento de tecla digitada  no componente textField da tela
	private void buscarSessaoPelaStringDigitada(KeyEvent ke){    //ke � a a tecla digitada			
		ArrayList<Sessao> sessoes;
		SessaoDAO dao = new SessaoDAO();
		
		if(ke.getCode() != KeyCode.CAPS && ke.getCode() != KeyCode.ENTER && ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.SPACE){		
			String s = tfBuscarSessao.getText(); // recbendo o texto escrito na busca.	
			sessoes = dao.buscarSessoesComDataValidaParaVendaDeIngresso(s);
			preencherTabelaSessoesUtilizandoBusca(sessoes);
		}		
	}	
	
	@FXML
	private void pressionarItemTabelaSessao(MouseEvent event){		
		String valor;
		try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
			if(tvTabelaSessao.getSelectionModel().getSelectedItem() != null){
				valor = tvTabelaSessao.getSelectionModel().getSelectedItem().getValorSessao();
				valor = valor.substring(3);       //retirando a parte 'R$ ' da string para setar no label
				lbValorIngresso.setText(valor);
				
				// Ao pressionar um item da tabela de sessoes, o choiceBox ficar� habilitado.
				cbTipoIngresso.setDisable(false);				
			}					
			
		}	catch(Exception e){
				System.out.println("Erro ao selecionar o item na tabela de sess�es.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
	
    //evento na hora que � selecionado o choiceBox tipo do ingresso.
	@FXML
	private void SelecionarTipoIngresso(MouseEvent event){
		ListennerTipoIngresso(lbValorIngresso);	  // chama o ouvidor do choiceBox tipoIngresso.
	}
	
	@FXML
	private void SelecionarFormaPagamento(MouseEvent event){
		ListennerFormaPagamento();                // chama o ouvidor do choiceBox formaPagamento.
	}
}
