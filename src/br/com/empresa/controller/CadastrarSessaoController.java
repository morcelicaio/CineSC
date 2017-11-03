package br.com.empresa.controller;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import com.jfoenix.controls.JFXTimePicker;
import br.com.empresa.alertasMensagens.MensagemDeAlerta;
import br.com.empresa.dao.FilmeDAO;
import br.com.empresa.dao.SalaDAO;
import br.com.empresa.dao.SessaoDAO;
import br.com.empresa.model.Filme;
import br.com.empresa.model.Sala;
import br.com.empresa.model.Sessao;
import br.com.empresa.modelTabela.TabelaFilme;
import br.com.empresa.modelTabela.TabelaSala;
import br.com.empresa.modelTabela.TabelaSessao;
import br.com.empresa.util.ConversorDeValorMonetario;
import br.com.empresa.util.MascaraTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
 
public class CadastrarSessaoController {	
	@FXML private Button btnCadastrar;
	@FXML private Button btnAlterarSessao;
	@FXML private Button btnExcluirSessao;
	@FXML private Button btnSelecionarSessao;
	@FXML private Button btnCancelar;
	@FXML private Label lbAlteracao;
	@FXML private DatePicker dpData;		
	@FXML private MascaraTextField tfValorSessao;
	@FXML private TextField tfBuscarSessao; 	
	@FXML private JFXTimePicker jfxtpHorarioInicio;  
	@FXML private JFXTimePicker jfxtpHorarioFim;
	
	//O tipo de dado que vamos trabalhar nessa TableView � do tipo TabelaFilme
	@FXML private TableView<TabelaFilme> tvTabelaFilme;	
	//O tipo de dado que vamos trabalhar nessa TableView � do tipo TabelaSala
	@FXML private TableView<TabelaSala> tvTabelaSala;	
	//O tipo de dado que vamos trabalhar nessa TableView � do tipo TabelaSessao.
	@FXML private TableView<TabelaSessao> tvTabelaSessao;
	
	//O tipo de dado que vamos trabalhar nessa TableView � do tipo TabelaFilme, 
		//essa tabela vai possuir o campo int(codFilme) que ficar� invisivel na tabela e dois campos string
	@FXML private TableColumn<TabelaFilme, Integer> tcCodFilme;
	@FXML private TableColumn<TabelaFilme, String> tcNomeFilme;
	@FXML private TableColumn<TabelaFilme, String> tcCategoriaFilme;
	@FXML private TableColumn<TabelaFilme, String> tcLinguagemFilme;
	
	//O tipo de dado que vamos trabalhar nessa TableView � do tipo TabelaSala. 		
	@FXML private TableColumn<TabelaSala, Integer> tcCodSala;     // coluna visible false na tabela.
	@FXML private TableColumn<TabelaSala, Integer> tcNroSala;
	@FXML private TableColumn<TabelaSala, Integer> tcQtdAssentos;				
	
	//O tipo de dado que vamos trabalhar nessa TableView � do tipo TabelaSessao.
	@FXML private TableColumn<TabelaSessao, Integer> tcCodigoFilme; // coluna visible false na tabela.
	@FXML private TableColumn<TabelaSessao, String> tcDataSessao;
	@FXML private TableColumn<TabelaSessao, Integer> tcNumSala;
	@FXML private TableColumn<TabelaSessao, String> tcNomeDoFilme;
	@FXML private TableColumn<TabelaSessao, String> tcLinguagemDoFilme;
	@FXML private TableColumn<TabelaSessao, Integer> tcPoltronasDisponiveis;
	@FXML private TableColumn<TabelaSessao, String> tcHoraInicio;
	@FXML private TableColumn<TabelaSessao, String> tcHoraFim;
	@FXML private TableColumn<TabelaSessao, Double> tcPreco;
	@FXML private TableColumn<TabelaSessao, Integer> tcCodigoSala; // coluna visible false na tabela.
	
	private boolean alterarSessao = false;
	
	private int qtdPoltonasDisponiveis; //vari�vel que vai receber a qtd de assentos selecionado na linha da tabela. 
	private int codFilme; //vari�vel que vai receber o cod do filme selecionado na linha da tabela.
	private int codSala; //vari�vel que vai receber o cod da sala selecionado na linha da tabela.
	private int nroSala;  //vari�vel que vai receber o nro da sala selecionado na linha da tabela.  
	private LocalDate ld;	// vari�vel que vai receber a data do datePicker
	private String horasInicioString;   // vari�vel que vai receber a hora do comboBox	
	private String horasFimString;   // vari�vel que vai receber a hora do comboBox	
	private double valorSessao;
	
	private String dataSessaoString; //Usada para preencher a tabela sess�es e usada na busca de sess�es.
	private Date horasInicio; //Var. Usada na hora de formatar a data do tipo string q veio convertida do relogio.
	private Date horasFim;    //Var. Usada na hora de formatar a data do tipo string q veio convertida do relogio.
	private Time horarioInicioFilme;  //Var. usada para pegar a var. horasInicio formatada e mandar para o banco.
	private Time horarioTerminoFilme; //Mesmo funcionamento. horasFim.getTime() retorna um valor do tipo Time.
	
	private SimpleDateFormat formatadorDeDatas = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formatadorDeHoras = new SimpleDateFormat("HH:mm");
	
	//Construtor do controller
	@FXML 
	private void initialize(){
		tfValorSessao.setarMascara("NN.NN"); //seta a m�scara para a pessoa digitar 2 n�meros um ponto  e + 2 n�meros.
		preencherTabelaSessoes();
		preencherTabelaFilmes();
		preencherTabelaSalas();		
		desabilitarComponentesDaTela();	
	}
	
	// Listas que ir�o popular as tabelas.
	ObservableList<TabelaFilme> listaTabelaFilme = FXCollections.observableArrayList();		
	ObservableList<TabelaSala> listaTabelaSala = FXCollections.observableArrayList();			
	ObservableList<TabelaSessao> listaTabelaSessao = FXCollections.observableArrayList();
	
	//setando os valores das colunas pelos seus atributos conforme est�o na Classe TabelaSessao.
	private void setarValoresDasColunasDaTabela(){		
		tcCodFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, Integer>("codFilme"));
		tcDataSessao.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("dataSessao"));
		tcNumSala.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Integer>("nroSala"));
		tcNomeDoFilme.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("nomeFilme"));
		tcLinguagemDoFilme.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("linguagemFilme"));
		tcHoraInicio.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("horaInicioSessao"));
		tcHoraFim.setCellValueFactory(new PropertyValueFactory<TabelaSessao, String>("horaFimSessao"));
		tcPoltronasDisponiveis.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Integer>("qtdPoltronasDisponiveis"));
		tcPreco.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Double>("valorSessao"));
		tcCodigoSala.setCellValueFactory(new PropertyValueFactory<TabelaSessao, Integer>("codSala"));
	}
	
	private void preencherTabelaSessoes(){
		SessaoDAO dao = new SessaoDAO();
		ArrayList<Sessao> sessoes = null;
		
		try{
			sessoes = dao.listarSessoes();
			
			//se a lista nao for vazia
			if(!listaTabelaSessao.isEmpty()){
				listaTabelaSessao.clear(); // primeiro limpa a tabela para depois carregar novamente.			
			}
				//foreach
			for(Sessao s: sessoes){ //convertendo da classe Sessao para o tipo TabelaSessao
				String valorTotalString;
				
				//Formatando a data da sess�o antes de inserir na tabela usando o dateFormat declarado no topo do arquivo.
				dataSessaoString = formatadorDeDatas.format(s.getDataSessao()); 
				
				valorTotalString = new DecimalFormat("R$ ##,### 0.00").format(s.getValorSessao());
				
				TabelaSessao ts = new TabelaSessao(s.getFilme().getCodFilme(), dataSessaoString, s.getSala().getNroSala(), 
												   s.getFilme().getNomeFilme(), s.getFilme().getLinguagemFilme(), 
						                           s.getHoraInicioSessao().toString(), s.getHoraFimSessao().toString(),  
						                           s.getQtdPoltronasDisponiveis(), valorTotalString, s.getSala().getCodSala());
				
				listaTabelaSessao.add(ts); // preenchendo a lista que vai popular tabela.				
				setarValoresDasColunasDaTabela();												
			}
			
			tvTabelaSessao.setItems(listaTabelaSessao);
			
		}	catch(Exception e){
				System.out.println("Erro na cria��o da tabela.");				
				System.out.println(e.getMessage());
				e.printStackTrace();
			}			
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
			TabelaFilme tf = new TabelaFilme(f.getCodFilme(), f.getNomeFilme(), f.getNomeDiretor(),f.getCategoriaFilme(), 
											 f.getAnoLancamento(), f.getLinguagemFilme());
			
			listaTabelaFilme.add(tf); // preenchendo a lista que vai ser colocada na tabela.
		}
		
		//Nesta tabela eu s� vou preencher estes atributos.
		tcCodFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, Integer>("codFilme"));
		tcNomeFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("nomeFilme"));
		tcCategoriaFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("categoriaFilme"));
		tcLinguagemFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("linguagemFilme"));
		
		tvTabelaFilme.setItems(listaTabelaFilme);  // setando a lista que foi preenchida na tabela TabelaFilme.
	}	
	
	private void preencherTabelaSalas(){
		SalaDAO dao = new SalaDAO();
		
		ArrayList<Sala> salas = dao.listarSalas();
		
		//se a lista da tabela sala n�o estiver vazia ele limpa os campos primeiros.
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
				
	private void desabilitarComponentesDaTela(){
		tvTabelaSala.setDisable(true);        // Deixando a tabela desabilitada.
		dpData.setDisable(true); 	          // Deixando o datePicker desabilitado.
		jfxtpHorarioInicio.setDisable(true);  // Deixando o JFXTimePicker desabilitado.
		jfxtpHorarioFim.setDisable(true);     // Deixando o JFXTimePicker desabilitado.
		tfValorSessao.setDisable(true);       // Deixando o textField desabilitado.
		btnCadastrar.setDisable(true);
		btnAlterarSessao.setDisable(true);
		btnExcluirSessao.setDisable(true);
		btnSelecionarSessao.setDisable(true);
	}
	
	private void limparCampos(){
		tvTabelaFilme.setSelectionModel(tvTabelaFilme.getSelectionModel()); //recebe a tabela sem nada selecionado.
		tvTabelaSala.setSelectionModel(tvTabelaSala.getSelectionModel());
		dpData.setValue(null);
		jfxtpHorarioInicio.setValue(LocalTime.of(00, 00)); // seta o campo com a hora igual a 00:00.
		jfxtpHorarioFim.setValue(LocalTime.of(00, 00));		
		tfValorSessao.clear();
	}
	
	//M�todo usado para preencher a tabela das sess�es quando se utilizar a busca para achar as sess�es do filme.
		private void preencherTabelaSessoesUtilizandoBusca(ArrayList<Sessao> sessoes){				
			try{					
				//se a lista nao for vazia
				if(!listaTabelaSessao.isEmpty()){
					listaTabelaSessao.clear(); // primeiro limpa a tabela para depois carregar novamente.			
				}
					//foreach
				for(Sessao s: sessoes){ //convertendo da classe Sessao para o tipo TabelaSessao
					String valorTotalString;
					
					//Formatando a data da sess�o antes de inserir na tabela.	
					// usando o dateFormat declarado no topo do arquivo.
					dataSessaoString = formatadorDeDatas.format(s.getDataSessao()); 
					
					valorTotalString = new DecimalFormat("R$ ##,### 0.00").format(s.getValorSessao());
					
					TabelaSessao ts = new TabelaSessao(s.getFilme().getCodFilme(), dataSessaoString, 
							                           s.getSala().getNroSala(), s.getFilme().getNomeFilme(), s.getFilme().getLinguagemFilme(), 
							                           s.getHoraInicioSessao().toString(), s.getHoraFimSessao().toString(),  
							                           s.getQtdPoltronasDisponiveis(), valorTotalString, s.getSala().getCodSala());
					
					listaTabelaSessao.add(ts); // preenchendo a lista que vai popular tabela.					
					setarValoresDasColunasDaTabela();						
				}
				
				tvTabelaSessao.setItems(listaTabelaSessao);
				
			}	catch(Exception e){
					System.out.println("Erro na cria��o da tabela a partir da busca.");				
					System.out.println(e.getMessage());
					e.printStackTrace();
				}			
		}
	 
	private boolean validarCampos(String tipoDeValidacao){   //validando os campos preenchidos antes do cadastro.				
		boolean horarioSessaoValido = false; 
		boolean conflitoNoHorarioDasSessoes = false;
		
		Date dataDaSessao = null;
		
		if(tipoDeValidacao.equals("Cadastrar")){
			ld = dpData.getValue(); //Recebendo a data selecionada no datePicker. (Retorna um dado do tipo LocalDate)
			dataDaSessao = java.sql.Date.valueOf(ld);   // recuperando a data do componente da tela.
			
			// se os pr�ximos if's retornarem falso, existe algum erro nas valida��es. 	
			if(ld == null || jfxtpHorarioInicio.getValue() == null){  // verificando se a data ou a hora de inicio est�o vazios.
				MensagemDeAlerta.enviarMensagemDeCampoVazio();
				return false;	
			}
		}	else{		// se o tipo de valida��o for Alterar
				String dataParaAlteracao = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao(); 
				
				try{
					dataDaSessao = formatadorDeDatas.parse(dataParaAlteracao); // converte de string para date.	
				}	catch(ParseException pe){
						System.out.println("Erro na convers�o da data de in�cio para altera��o.");
						System.out.println(pe.getMessage());
						pe.printStackTrace();
					}
			}
		
		// verificando se o hor�rio de fim ou o valor da sess�o s�o vazios 		
		if(jfxtpHorarioFim.getValue() == null || tfValorSessao.getText().isEmpty()){			
			MensagemDeAlerta.enviarMensagemDeCampoVazio();
			return false;			
		}						
		
		Date dataAtual = new Date(); 					 // cria o objeto e ja pega a data atual.		
		
		if(dataDaSessao.compareTo(dataAtual) == 1){	  // verifica se a data da sess�o n�o � igual a data atual.  retorna 1 para falso.
			if(dataDaSessao.before(dataAtual)){   // compara se a data Da sess�o � menor que a data atual. Se for a data � inv�lida.
				MensagemDeAlerta.enviarMensagemDeDataInvalida();
				return false;
			}
		}
		
		// pegando o valor da sess�o no textfield e vendo se ele  � igual a 0. Se for manda mensagem de erro.
		double valor = Double.parseDouble(tfValorSessao.getText());			
		if(valor == 0){
			MensagemDeAlerta.enviarMensagemDeValorDaSessaoInvalido();
			return false;
		}
		
		//se n�o forem vazias, as vari�veis s�o preenchidas com os valores inseridos na tela.
		if(!alterarSessao){			
			nroSala = tvTabelaSala.getSelectionModel().getSelectedItem().getNroSala();     // pegando da linha da tabela
			qtdPoltonasDisponiveis = tvTabelaSala.getSelectionModel().getSelectedItem().getQtdAssentos();
			codSala = tvTabelaSala.getSelectionModel().getSelectedItem().getCodSala();     // pegando da linha da tabela
		}	else{								
				nroSala = tvTabelaSessao.getSelectionModel().getSelectedItem().getNroSala();     // pegando da linha da tabela
				qtdPoltonasDisponiveis = tvTabelaSessao.getSelectionModel().getSelectedItem().getQtdPoltronasDisponiveis();
				codSala = tvTabelaSessao.getSelectionModel().getSelectedItem().getCodSala();     // pegando da linha da tabela
			}				
		
		codFilme = tvTabelaFilme.getSelectionModel().getSelectedItem().getCodFilme();		
		
		if(tipoDeValidacao.equals("Cadastrar")){
			horasInicioString = jfxtpHorarioInicio.getValue().toString();	  //pegando do jfxTimePicker
		}	else{
				horasInicioString = tvTabelaSessao.getSelectionModel().getSelectedItem().getHoraInicioSessao(); 
			}
		
		if(!alterarSessao){
			horasFimString = jfxtpHorarioFim.getValue().toString();	          //pegando do jfxTimePicker
		}	else{
				horasFimString = tvTabelaSessao.getSelectionModel().getSelectedItem().getHoraFimSessao();
			}
		
		//convertendo o valor monet�rio do tipo string que vem da tabela  para um valor double v�lido.
		ConversorDeValorMonetario cvm = new ConversorDeValorMonetario();
		double valorConvertido = cvm.converterTextoDeValorMonetario(tfValorSessao.getText());		
		valorSessao = valorConvertido;	
		
		formatarCamposDeHoras(horasInicioString, horasFimString);			
		
		//verifica se o horario inicial da sess�o � maior que o hor�rio final.
		horarioSessaoValido = validarHorariosDaNovaSessao(horarioInicioFilme, horarioTerminoFilme);
		
		if(!horarioSessaoValido){  // se o horario da sessao n�o � v�lido
			MensagemDeAlerta.enviarMensagemDeHorarioDaSessaoInvalido();
			return false;
		}
								 //Agora essa fun��o ir� validar com as sess�es j� cadastradas.
		conflitoNoHorarioDasSessoes = validarHorariosDasSessoes(horarioInicioFilme, horarioTerminoFilme);
		
		// se existir conflito entre o hor�rios das sess�es
		if(conflitoNoHorarioDasSessoes){
			return false;
		}
		
		return true;  // se todos os campos estiverem v�lidos  retorna verdadeiro.
	}
	
	private void formatarCamposDeHoras(String horaInicioString, String horaFimString){
		try{			
			horasInicio = formatadorDeHoras.parse(horaInicioString);
			horarioInicioFilme = new Time(horasInicio.getTime());
						
			horasFim = formatadorDeHoras.parse(horaFimString);
			horarioTerminoFilme = new Time(horasFim.getTime());
			
		}	catch(ParseException pe){
				System.out.println("Erro na convers�o dos hor�rios.");
				System.out.println(pe.getMessage());
				pe.printStackTrace();
			}
	}
	
	private boolean validarHorariosDasSessoes(Time horarioInicial, Time horarioFinal){		
		Date dataDaSessaoEscolhidaNaTela = null;
		
		if(!alterarSessao){ //no caso de cadastro  pega a data do componente rel�gio da tela.
			dataDaSessaoEscolhidaNaTela = java.sql.Date.valueOf(ld);
		}	else{	// caso for alteracao pega o valor da tabela de sessoes.
				String dataParaAlteracao = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao(); 
				
				try{
					dataDaSessaoEscolhidaNaTela = formatadorDeDatas.parse(dataParaAlteracao); // converte de string para date.	
				}	catch(ParseException pe){
						System.out.println("Erro na convers�o da data de in�cio para altera��o.");
						System.out.println(pe.getMessage());
						pe.printStackTrace();
					}				
			}
		
		ArrayList<Sessao> sessoes;
		SessaoDAO dao = new SessaoDAO();
		
		sessoes = dao.listarSessoes();
		
		//validando se j� existe alguma sess�o cadastrada no mesmo hor�rio na mesma sala
		//ou se existe conflitos nos hor�rios das sess�es na mesma sala.
		for(Sessao s: sessoes){
			Date dataSessao = s.getDataSessao();
			Time horarioInicialBD = s.getHoraInicioSessao(); // hor�rios das sess�es j� cadastradas no banco.
			Time horarioFinalBD = s.getHoraFimSessao();
			
			//Comparando para ver se a sess�o que ir� ser cadastrada tem a mesma data das sess�es da lista.
			//A fun��o compareTo compara 2 datas. Se forem iguais ele retorna 0. Se n�o forem ele retorna 1.			
			if(dataDaSessaoEscolhidaNaTela.compareTo(dataSessao) == 0){
				
				//Agora o pr�ximo if verifica se a sessao tem a mesma sala que a outra.
				//Se tiver a mesma sala, ent�o elas tem a mesma data e est�o na mesma sala.
				if(nroSala == s.getSala().getNroSala()){									
					
					// Agora a sess�o tendo a mesma data e estando na mesma sala que a outra,
					// � hora de verificar se existe conflito de hor�rio entre as sess�es.
					// Uma sess�o n�o pode ser cadastrada com o hor�rio em cima de outra sess�o.
					
					//se o hor�rio inicial que est� sendo cadastrado � menor que o hor�rio inicial da sessao atual
					//recuperada do banco de dados e se o hor�rio final da sess�o atual que est� sendo cadastrada
					// � maior que o hor�rio inicial da sess�o recuperada do banco de dados 
					if(horarioInicial.before(horarioInicialBD) && horarioFinal.after(horarioInicialBD)){
						MensagemDeAlerta.enviarMensagemDeConflitoNoHorarioDaSessao(s);
						return true;
					}
					
					if(horarioInicial.after(horarioInicialBD) && horarioFinal.before(horarioFinalBD)){
						MensagemDeAlerta.enviarMensagemDeConflitoNoHorarioDaSessao(s);
						return true;
					}
					
					if(horarioInicial.before(horarioInicialBD) || horarioInicial.equals(horarioInicialBD)){ //menor ou igual
						if(horarioFinal.after(horarioInicialBD) || horarioFinal.equals(horarioInicialBD)){
							
							if(alterarSessao){   // esse caso � se for para alterar e for tudo igual entao ele passa batido.
								if(horarioInicial.equals(horarioInicialBD) && horarioFinal.equals(horarioFinalBD)){
									continue;
								}
							}
							
							MensagemDeAlerta.enviarMensagemDeConflitoNoHorarioDaSessao(s);
							return true;
						}						
					}
					
					if(horarioInicial.after(horarioInicialBD) || horarioInicial.equals(horarioInicialBD)){
						if(horarioFinal.before(horarioFinalBD) || horarioFinal.equals(horarioFinalBD)){
							
							if(alterarSessao){   // esse caso � se for para alterar e for tudo igual entao ele passa batido.
								if(horarioInicial.equals(horarioInicialBD) && horarioFinal.equals(horarioFinalBD)){
									continue;
								}
							}
							
							MensagemDeAlerta.enviarMensagemDeConflitoNoHorarioDaSessao(s);
							return true;
						}
					}
					
					if(horarioInicial.after(horarioInicialBD) || horarioInicial.equals(horarioInicialBD)){
						if(horarioInicial.before(horarioFinalBD) || horarioInicial.equals(horarioFinalBD)){
							if(horarioFinal.after(horarioFinalBD) || horarioFinal.equals(horarioFinalBD)){
								
								if(alterarSessao){   // esse caso � se for para alterar e for tudo igual entao ele passa batido.
									if(horarioInicial.equals(horarioInicialBD) && horarioFinal.equals(horarioFinalBD)){
										continue;
									}
								}
								
								MensagemDeAlerta.enviarMensagemDeConflitoNoHorarioDaSessao(s);
								return true;
							}
						} 
					}
					
				}				
			}
		}
		
		return false;
	}
	
	private boolean validarHorariosDaNovaSessao(Time horarioInicial, Time horarioFinal){			
		   //Se o hor�rio inicial � maior  ou � igual que o hor�rio final
		if(horarioInicial.after(horarioFinal) || horarioInicial.equals(horarioFinal)){  
			return false;
		}	
		return true;		
	}
	
	@FXML
	private void cadastrarSessao(ActionEvent event){
		boolean permitirCadastro = false;							
		
		permitirCadastro = validarCampos("Cadastrar");	// recebe a valida��o dos campos da tela para a fase de cadastro.	
		
		if(permitirCadastro){			
			Date dataSessao = java.sql.Date.valueOf(ld);  //Convertedo uma vari�vel do tipo localDate para Date.						
			
			try{
				Sessao s = new Sessao();
				Filme filme = new Filme(); 
				FilmeDAO filmeDAO = new FilmeDAO();				
				Sala sala = new Sala(); 
				SalaDAO salaDAO = new SalaDAO();

				filme = filmeDAO.buscarFilmeSelecionado(codFilme);// recuperando o filme para setar no obj sess�o.
				sala = salaDAO.buscarSalaSelecionada(codSala);    // recuperando a sala para setar no obj sess�o.
					
				s.setDataSessao(dataSessao);
				s.setHoraInicioSessao(horarioInicioFilme);
				s.setSala(sala);
				s.setFilme(filme);
				s.setHoraFimSessao(horarioTerminoFilme);
				s.setQtdPoltronasDisponiveis(qtdPoltonasDisponiveis);
				s.setValorSessao(valorSessao);	
				
				SessaoDAO dao = new SessaoDAO();		
				dao.cadastrarSessao(s);
					
				MensagemDeAlerta.enviarMensagemDeSucessoNoCadastro(); // chamando o m�todo da classe AlertaMensagem.
				limparCampos();
				desabilitarComponentesDaTela();
				preencherTabelaSessoes();
				
			}	catch(Exception e){
					System.out.println("Erro no cadastro da sess�o.");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}			
		}															
	}	
	
	@FXML
	public void selecionarSessao(ActionEvent event){
		alterarSessao = true;
		lbAlteracao.setVisible(true);
		btnCadastrar.setDisable(true);
		btnAlterarSessao.setDisable(false);		
		limparCampos();
		desabilitarComponentesDaTela();
		btnExcluirSessao.setDisable(false);			
	}
	
	@FXML    // testar aqui para ver se est� funcionando...e fazer a parte do banco  para alterar.
	public void alterarSessao(ActionEvent event){		
		boolean permitirAlteracao = false;							
		String dataString = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao(); // pegando da linha da tabela
		String horaInicioString = tvTabelaSessao.getSelectionModel().getSelectedItem().getHoraInicioSessao();		
		Date horasInicioAlteracao = null;
		Time horasInicioAlteracao2 = null;		
		java.sql.Date dataAlteracao = null;
		
		try {
			dataAlteracao = new java.sql.Date(formatadorDeDatas.parse(dataString).getTime());							
			horasInicioAlteracao = formatadorDeHoras.parse(horaInicioString); // converte de string para date.
			horasInicioAlteracao2 = new Time (horasInicioAlteracao.getTime());  //converte do date para o Time.
						
		} 	catch (ParseException e1) {
				System.out.println("Erro ao formatar a data ou a hora de in�cio da sess�o.");
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		
		int codSalaAlteracao = tvTabelaSala.getSelectionModel().getSelectedItem().getCodSala(); // pegando da linha da tabela
				
		permitirAlteracao = validarCampos("Alterar");	// recebe a valida��o dos campos da tela para a fase de altera��o.	
		
		if(permitirAlteracao){
			/*
			Date dataSessao = new java.sql.Date(0);
			
			if(!alterarSessao){ //no caso de cadastro  pega a data do componente rel�gio da tela.
				dataSessao = java.sql.Date.valueOf(ld);  //Convertedo uma vari�vel do tipo localDate para Date.
			}	else{	// caso for alteracao pega o valor da tabela de sessoes.
					String dataParaAlteracao = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao(); 
					
					try{
						dataSessao = formatadorDeDatas.parse(dataParaAlteracao); // converte de string para date.	
					}	catch(ParseException pe){
							System.out.println("Erro na convers�o da data de in�cio para altera��o.");
							System.out.println(pe.getMessage());
							pe.printStackTrace();
						}				
				}						*/								
						
			try{
				Sessao s = new Sessao();
				Filme filme = new Filme(); 
				FilmeDAO filmeDAO = new FilmeDAO();				
				Sala sala = new Sala(); 
				SalaDAO salaDAO = new SalaDAO();
								
				filme = filmeDAO.buscarFilmeSelecionado(codFilme);// recuperando o filme para setar no obj sess�o.												
				sala = salaDAO.buscarSalaSelecionada(codSala);    // recuperando a sala para setar no obj sess�o.				
				s.setDataSessao(dataAlteracao);
				s.setHoraInicioSessao(horarioInicioFilme);
				s.setSala(sala);
				s.setFilme(filme);
				s.setHoraFimSessao(horarioTerminoFilme);
				s.setQtdPoltronasDisponiveis(qtdPoltonasDisponiveis);
				s.setValorSessao(valorSessao);	
								
				Sessao s2 = new Sessao();
				Sala sala2 = new Sala(); 
				SalaDAO salaDAO2 = new SalaDAO();
				
				sala2 = salaDAO2.buscarSalaSelecionada(codSalaAlteracao);
				
				s2.setDataSessao(dataAlteracao);
				s2.setSala(sala2);
				s2.setHoraInicioSessao(horasInicioAlteracao2);
				
				SessaoDAO dao = new SessaoDAO(); 		
				dao.alterarSessao(s, s2);
					
				MensagemDeAlerta.enviarMensagemDeSucessoNaAlteracao(); // chamando o m�todo da classe AlertaMensagem.
				limparCampos();
				desabilitarComponentesDaTela();
				preencherTabelaSessoes();
				
			}	catch(Exception e){
					System.out.println("Erro no cadastro da sess�o.");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}			
		}															
	}
	
	@FXML
	public void excluirSessao(ActionEvent event){
		
	}
	
	//Bot�o que fecha a janela
	@FXML	
	private void voltarAoMenu(ActionEvent event){			
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}
	  
	// --------------------------------  EVENTOS DOS COMPONENTES  ----------------------------------- //
	
	@FXML   // Evento da tabela de filmes no clique do mouse.
	private void pressionarItemTabelaFilme(MouseEvent event){			
		try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
			if(tvTabelaFilme.getSelectionModel().getSelectedItem() != null){
				// Ao pressionar um item da tabela de filmes, a tabela das salas fica habilitada.
				tvTabelaSala.setDisable(false);	
			}	
			
		}	catch(Exception e){
				System.out.println("Erro ao selecionar o item na tabela de filmes.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}	
	
	@FXML   // Evento da tabela de salas no clique do mouse.
	private void pressionarItemTabelaSala(MouseEvent event){
		
		if(!alterarSessao){			// se n�o estiver alterando a sessao ent�o estar� cadastrando.
			try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
				if(tvTabelaSala.getSelectionModel().getSelectedItem() != null){			
					// Ao pressionar um item da tabela de salas, o datePicker ficar� habilitado.
					dpData.setDisable(false);			
				}					
				
			}	catch(Exception e){
					System.out.println("Erro ao selecionar o item na tabela de salas.");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			
		}	else{
				tfValorSessao.setDisable(false);
				btnAlterarSessao.setDisable(false);
			}			
	}
		
	@FXML   // Evento do datePicker data no clique do mouse.
	private void cliqueDatePickerData(MouseEvent event){		
		// Ao pressionar o datePicker data, o comboBox horaInicio ficar� habilitado.
		jfxtpHorarioInicio.setDisable(false);			
	}
	
	@FXML   // Evento do JFXTimePicker horaInicio no clique do mouse.
	private void cliqueHorarioInicio(MouseEvent event){		
		// Ao pressionar o combo horasInicio, o combo minutosInicio ficar� habilitado.
		jfxtpHorarioFim.setDisable(false);			
	}
	
	@FXML   // Evento do JFXTimePicker horaFim no clique do mouse.
	private void cliqueHorarioFim(MouseEvent event){
		tfValorSessao.setDisable(false);
		
		if(!lbAlteracao.isVisible()){    // como esse evento vale p/ os 2 bot�es, ent�o verifica se foi chamada a altera��o da sess�o.
			btnCadastrar.setDisable(false);   // se o label da altera��o estiver invis�vel � porque vai fazer o cadastro
		}	else{
				btnAlterarSessao.setDisable(false);		// se estiver vis�vel, � porque vai fazer a altera��o ou excluir a sess�o.
			}
	}	  
	 
	@FXML   //Evento de tecla digitada  no componente textField da tela
	private void buscarSessaoPelaTeclaDigitada(KeyEvent ke){    //ke � a a tecla digitada			
		ArrayList<Sessao> sessoes; 
		SessaoDAO dao = new SessaoDAO();
		
		if(ke.getCode() != KeyCode.CAPS && ke.getCode() != KeyCode.ENTER && ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.SPACE){		
			String s = tfBuscarSessao.getText(); // recbendo o texto escrito na busca.				
			sessoes = dao.buscarSessoes(s);
			preencherTabelaSessoesUtilizandoBusca(sessoes);
		}		
	}
	
	@FXML   // Evento da tabela de sess�es cadastradas no clique do mouse.
	private void pressionarItemTabelaSessoesCadastradas(MouseEvent event){			
		try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
			if(tvTabelaSessao.getSelectionModel().getSelectedItem() != null){
				// Ao pressionar um item da tabela de sess�es cadastradas, o bot�o selecionar fica habilitado.
				btnSelecionarSessao.setDisable(false);	
			}	
			
		}	catch(Exception e){
				System.out.println("Erro ao selecionar o item na tabela de sess�es cadastradas.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}	

}
