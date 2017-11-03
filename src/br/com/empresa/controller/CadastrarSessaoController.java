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
	
	//O tipo de dado que vamos trabalhar nessa TableView é do tipo TabelaFilme
	@FXML private TableView<TabelaFilme> tvTabelaFilme;	
	//O tipo de dado que vamos trabalhar nessa TableView é do tipo TabelaSala
	@FXML private TableView<TabelaSala> tvTabelaSala;	
	//O tipo de dado que vamos trabalhar nessa TableView é do tipo TabelaSessao.
	@FXML private TableView<TabelaSessao> tvTabelaSessao;
	
	//O tipo de dado que vamos trabalhar nessa TableView é do tipo TabelaFilme, 
		//essa tabela vai possuir o campo int(codFilme) que ficará invisivel na tabela e dois campos string
	@FXML private TableColumn<TabelaFilme, Integer> tcCodFilme;
	@FXML private TableColumn<TabelaFilme, String> tcNomeFilme;
	@FXML private TableColumn<TabelaFilme, String> tcCategoriaFilme;
	@FXML private TableColumn<TabelaFilme, String> tcLinguagemFilme;
	
	//O tipo de dado que vamos trabalhar nessa TableView é do tipo TabelaSala. 		
	@FXML private TableColumn<TabelaSala, Integer> tcCodSala;     // coluna visible false na tabela.
	@FXML private TableColumn<TabelaSala, Integer> tcNroSala;
	@FXML private TableColumn<TabelaSala, Integer> tcQtdAssentos;				
	
	//O tipo de dado que vamos trabalhar nessa TableView é do tipo TabelaSessao.
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
	
	private int qtdPoltonasDisponiveis; //variável que vai receber a qtd de assentos selecionado na linha da tabela. 
	private int codFilme; //variável que vai receber o cod do filme selecionado na linha da tabela.
	private int codSala; //variável que vai receber o cod da sala selecionado na linha da tabela.
	private int nroSala;  //variável que vai receber o nro da sala selecionado na linha da tabela.  
	private LocalDate ld;	// variável que vai receber a data do datePicker
	private String horasInicioString;   // variável que vai receber a hora do comboBox	
	private String horasFimString;   // variável que vai receber a hora do comboBox	
	private double valorSessao;
	
	private String dataSessaoString; //Usada para preencher a tabela sessões e usada na busca de sessões.
	private Date horasInicio; //Var. Usada na hora de formatar a data do tipo string q veio convertida do relogio.
	private Date horasFim;    //Var. Usada na hora de formatar a data do tipo string q veio convertida do relogio.
	private Time horarioInicioFilme;  //Var. usada para pegar a var. horasInicio formatada e mandar para o banco.
	private Time horarioTerminoFilme; //Mesmo funcionamento. horasFim.getTime() retorna um valor do tipo Time.
	
	private SimpleDateFormat formatadorDeDatas = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formatadorDeHoras = new SimpleDateFormat("HH:mm");
	
	//Construtor do controller
	@FXML 
	private void initialize(){
		tfValorSessao.setarMascara("NN.NN"); //seta a máscara para a pessoa digitar 2 números um ponto  e + 2 números.
		preencherTabelaSessoes();
		preencherTabelaFilmes();
		preencherTabelaSalas();		
		desabilitarComponentesDaTela();	
	}
	
	// Listas que irão popular as tabelas.
	ObservableList<TabelaFilme> listaTabelaFilme = FXCollections.observableArrayList();		
	ObservableList<TabelaSala> listaTabelaSala = FXCollections.observableArrayList();			
	ObservableList<TabelaSessao> listaTabelaSessao = FXCollections.observableArrayList();
	
	//setando os valores das colunas pelos seus atributos conforme estão na Classe TabelaSessao.
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
				
				//Formatando a data da sessão antes de inserir na tabela usando o dateFormat declarado no topo do arquivo.
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
				System.out.println("Erro na criação da tabela.");				
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
		
		//Nesta tabela eu só vou preencher estes atributos.
		tcCodFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, Integer>("codFilme"));
		tcNomeFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("nomeFilme"));
		tcCategoriaFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("categoriaFilme"));
		tcLinguagemFilme.setCellValueFactory(new PropertyValueFactory<TabelaFilme, String>("linguagemFilme"));
		
		tvTabelaFilme.setItems(listaTabelaFilme);  // setando a lista que foi preenchida na tabela TabelaFilme.
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
	
	//Método usado para preencher a tabela das sessões quando se utilizar a busca para achar as sessões do filme.
		private void preencherTabelaSessoesUtilizandoBusca(ArrayList<Sessao> sessoes){				
			try{					
				//se a lista nao for vazia
				if(!listaTabelaSessao.isEmpty()){
					listaTabelaSessao.clear(); // primeiro limpa a tabela para depois carregar novamente.			
				}
					//foreach
				for(Sessao s: sessoes){ //convertendo da classe Sessao para o tipo TabelaSessao
					String valorTotalString;
					
					//Formatando a data da sessão antes de inserir na tabela.	
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
					System.out.println("Erro na criação da tabela a partir da busca.");				
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
			
			// se os próximos if's retornarem falso, existe algum erro nas validações. 	
			if(ld == null || jfxtpHorarioInicio.getValue() == null){  // verificando se a data ou a hora de inicio estão vazios.
				MensagemDeAlerta.enviarMensagemDeCampoVazio();
				return false;	
			}
		}	else{		// se o tipo de validação for Alterar
				String dataParaAlteracao = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao(); 
				
				try{
					dataDaSessao = formatadorDeDatas.parse(dataParaAlteracao); // converte de string para date.	
				}	catch(ParseException pe){
						System.out.println("Erro na conversão da data de início para alteração.");
						System.out.println(pe.getMessage());
						pe.printStackTrace();
					}
			}
		
		// verificando se o horário de fim ou o valor da sessão são vazios 		
		if(jfxtpHorarioFim.getValue() == null || tfValorSessao.getText().isEmpty()){			
			MensagemDeAlerta.enviarMensagemDeCampoVazio();
			return false;			
		}						
		
		Date dataAtual = new Date(); 					 // cria o objeto e ja pega a data atual.		
		
		if(dataDaSessao.compareTo(dataAtual) == 1){	  // verifica se a data da sessão não é igual a data atual.  retorna 1 para falso.
			if(dataDaSessao.before(dataAtual)){   // compara se a data Da sessão é menor que a data atual. Se for a data é inválida.
				MensagemDeAlerta.enviarMensagemDeDataInvalida();
				return false;
			}
		}
		
		// pegando o valor da sessão no textfield e vendo se ele  é igual a 0. Se for manda mensagem de erro.
		double valor = Double.parseDouble(tfValorSessao.getText());			
		if(valor == 0){
			MensagemDeAlerta.enviarMensagemDeValorDaSessaoInvalido();
			return false;
		}
		
		//se não forem vazias, as variáveis são preenchidas com os valores inseridos na tela.
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
		
		//convertendo o valor monetário do tipo string que vem da tabela  para um valor double válido.
		ConversorDeValorMonetario cvm = new ConversorDeValorMonetario();
		double valorConvertido = cvm.converterTextoDeValorMonetario(tfValorSessao.getText());		
		valorSessao = valorConvertido;	
		
		formatarCamposDeHoras(horasInicioString, horasFimString);			
		
		//verifica se o horario inicial da sessão é maior que o horário final.
		horarioSessaoValido = validarHorariosDaNovaSessao(horarioInicioFilme, horarioTerminoFilme);
		
		if(!horarioSessaoValido){  // se o horario da sessao não é válido
			MensagemDeAlerta.enviarMensagemDeHorarioDaSessaoInvalido();
			return false;
		}
								 //Agora essa função irá validar com as sessões já cadastradas.
		conflitoNoHorarioDasSessoes = validarHorariosDasSessoes(horarioInicioFilme, horarioTerminoFilme);
		
		// se existir conflito entre o horários das sessões
		if(conflitoNoHorarioDasSessoes){
			return false;
		}
		
		return true;  // se todos os campos estiverem válidos  retorna verdadeiro.
	}
	
	private void formatarCamposDeHoras(String horaInicioString, String horaFimString){
		try{			
			horasInicio = formatadorDeHoras.parse(horaInicioString);
			horarioInicioFilme = new Time(horasInicio.getTime());
						
			horasFim = formatadorDeHoras.parse(horaFimString);
			horarioTerminoFilme = new Time(horasFim.getTime());
			
		}	catch(ParseException pe){
				System.out.println("Erro na conversão dos horários.");
				System.out.println(pe.getMessage());
				pe.printStackTrace();
			}
	}
	
	private boolean validarHorariosDasSessoes(Time horarioInicial, Time horarioFinal){		
		Date dataDaSessaoEscolhidaNaTela = null;
		
		if(!alterarSessao){ //no caso de cadastro  pega a data do componente relógio da tela.
			dataDaSessaoEscolhidaNaTela = java.sql.Date.valueOf(ld);
		}	else{	// caso for alteracao pega o valor da tabela de sessoes.
				String dataParaAlteracao = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao(); 
				
				try{
					dataDaSessaoEscolhidaNaTela = formatadorDeDatas.parse(dataParaAlteracao); // converte de string para date.	
				}	catch(ParseException pe){
						System.out.println("Erro na conversão da data de início para alteração.");
						System.out.println(pe.getMessage());
						pe.printStackTrace();
					}				
			}
		
		ArrayList<Sessao> sessoes;
		SessaoDAO dao = new SessaoDAO();
		
		sessoes = dao.listarSessoes();
		
		//validando se já existe alguma sessão cadastrada no mesmo horário na mesma sala
		//ou se existe conflitos nos horários das sessões na mesma sala.
		for(Sessao s: sessoes){
			Date dataSessao = s.getDataSessao();
			Time horarioInicialBD = s.getHoraInicioSessao(); // horários das sessões já cadastradas no banco.
			Time horarioFinalBD = s.getHoraFimSessao();
			
			//Comparando para ver se a sessão que irá ser cadastrada tem a mesma data das sessões da lista.
			//A função compareTo compara 2 datas. Se forem iguais ele retorna 0. Se não forem ele retorna 1.			
			if(dataDaSessaoEscolhidaNaTela.compareTo(dataSessao) == 0){
				
				//Agora o próximo if verifica se a sessao tem a mesma sala que a outra.
				//Se tiver a mesma sala, então elas tem a mesma data e estão na mesma sala.
				if(nroSala == s.getSala().getNroSala()){									
					
					// Agora a sessão tendo a mesma data e estando na mesma sala que a outra,
					// é hora de verificar se existe conflito de horário entre as sessões.
					// Uma sessão não pode ser cadastrada com o horário em cima de outra sessão.
					
					//se o horário inicial que está sendo cadastrado é menor que o horário inicial da sessao atual
					//recuperada do banco de dados e se o horário final da sessão atual que está sendo cadastrada
					// é maior que o horário inicial da sessão recuperada do banco de dados 
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
							
							if(alterarSessao){   // esse caso é se for para alterar e for tudo igual entao ele passa batido.
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
							
							if(alterarSessao){   // esse caso é se for para alterar e for tudo igual entao ele passa batido.
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
								
								if(alterarSessao){   // esse caso é se for para alterar e for tudo igual entao ele passa batido.
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
		   //Se o horário inicial é maior  ou é igual que o horário final
		if(horarioInicial.after(horarioFinal) || horarioInicial.equals(horarioFinal)){  
			return false;
		}	
		return true;		
	}
	
	@FXML
	private void cadastrarSessao(ActionEvent event){
		boolean permitirCadastro = false;							
		
		permitirCadastro = validarCampos("Cadastrar");	// recebe a validação dos campos da tela para a fase de cadastro.	
		
		if(permitirCadastro){			
			Date dataSessao = java.sql.Date.valueOf(ld);  //Convertedo uma variável do tipo localDate para Date.						
			
			try{
				Sessao s = new Sessao();
				Filme filme = new Filme(); 
				FilmeDAO filmeDAO = new FilmeDAO();				
				Sala sala = new Sala(); 
				SalaDAO salaDAO = new SalaDAO();

				filme = filmeDAO.buscarFilmeSelecionado(codFilme);// recuperando o filme para setar no obj sessão.
				sala = salaDAO.buscarSalaSelecionada(codSala);    // recuperando a sala para setar no obj sessão.
					
				s.setDataSessao(dataSessao);
				s.setHoraInicioSessao(horarioInicioFilme);
				s.setSala(sala);
				s.setFilme(filme);
				s.setHoraFimSessao(horarioTerminoFilme);
				s.setQtdPoltronasDisponiveis(qtdPoltonasDisponiveis);
				s.setValorSessao(valorSessao);	
				
				SessaoDAO dao = new SessaoDAO();		
				dao.cadastrarSessao(s);
					
				MensagemDeAlerta.enviarMensagemDeSucessoNoCadastro(); // chamando o método da classe AlertaMensagem.
				limparCampos();
				desabilitarComponentesDaTela();
				preencherTabelaSessoes();
				
			}	catch(Exception e){
					System.out.println("Erro no cadastro da sessão.");
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
	
	@FXML    // testar aqui para ver se está funcionando...e fazer a parte do banco  para alterar.
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
				System.out.println("Erro ao formatar a data ou a hora de início da sessão.");
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		
		int codSalaAlteracao = tvTabelaSala.getSelectionModel().getSelectedItem().getCodSala(); // pegando da linha da tabela
				
		permitirAlteracao = validarCampos("Alterar");	// recebe a validação dos campos da tela para a fase de alteração.	
		
		if(permitirAlteracao){
			/*
			Date dataSessao = new java.sql.Date(0);
			
			if(!alterarSessao){ //no caso de cadastro  pega a data do componente relógio da tela.
				dataSessao = java.sql.Date.valueOf(ld);  //Convertedo uma variável do tipo localDate para Date.
			}	else{	// caso for alteracao pega o valor da tabela de sessoes.
					String dataParaAlteracao = tvTabelaSessao.getSelectionModel().getSelectedItem().getDataSessao(); 
					
					try{
						dataSessao = formatadorDeDatas.parse(dataParaAlteracao); // converte de string para date.	
					}	catch(ParseException pe){
							System.out.println("Erro na conversão da data de início para alteração.");
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
								
				filme = filmeDAO.buscarFilmeSelecionado(codFilme);// recuperando o filme para setar no obj sessão.												
				sala = salaDAO.buscarSalaSelecionada(codSala);    // recuperando a sala para setar no obj sessão.				
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
					
				MensagemDeAlerta.enviarMensagemDeSucessoNaAlteracao(); // chamando o método da classe AlertaMensagem.
				limparCampos();
				desabilitarComponentesDaTela();
				preencherTabelaSessoes();
				
			}	catch(Exception e){
					System.out.println("Erro no cadastro da sessão.");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}			
		}															
	}
	
	@FXML
	public void excluirSessao(ActionEvent event){
		
	}
	
	//Botão que fecha a janela
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
		
		if(!alterarSessao){			// se não estiver alterando a sessao então estará cadastrando.
			try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
				if(tvTabelaSala.getSelectionModel().getSelectedItem() != null){			
					// Ao pressionar um item da tabela de salas, o datePicker ficará habilitado.
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
		// Ao pressionar o datePicker data, o comboBox horaInicio ficará habilitado.
		jfxtpHorarioInicio.setDisable(false);			
	}
	
	@FXML   // Evento do JFXTimePicker horaInicio no clique do mouse.
	private void cliqueHorarioInicio(MouseEvent event){		
		// Ao pressionar o combo horasInicio, o combo minutosInicio ficará habilitado.
		jfxtpHorarioFim.setDisable(false);			
	}
	
	@FXML   // Evento do JFXTimePicker horaFim no clique do mouse.
	private void cliqueHorarioFim(MouseEvent event){
		tfValorSessao.setDisable(false);
		
		if(!lbAlteracao.isVisible()){    // como esse evento vale p/ os 2 botões, então verifica se foi chamada a alteração da sessão.
			btnCadastrar.setDisable(false);   // se o label da alteração estiver invisível é porque vai fazer o cadastro
		}	else{
				btnAlterarSessao.setDisable(false);		// se estiver visível, é porque vai fazer a alteração ou excluir a sessão.
			}
	}	  
	 
	@FXML   //Evento de tecla digitada  no componente textField da tela
	private void buscarSessaoPelaTeclaDigitada(KeyEvent ke){    //ke é a a tecla digitada			
		ArrayList<Sessao> sessoes; 
		SessaoDAO dao = new SessaoDAO();
		
		if(ke.getCode() != KeyCode.CAPS && ke.getCode() != KeyCode.ENTER && ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.SPACE){		
			String s = tfBuscarSessao.getText(); // recbendo o texto escrito na busca.				
			sessoes = dao.buscarSessoes(s);
			preencherTabelaSessoesUtilizandoBusca(sessoes);
		}		
	}
	
	@FXML   // Evento da tabela de sessões cadastradas no clique do mouse.
	private void pressionarItemTabelaSessoesCadastradas(MouseEvent event){			
		try{       // VERIFICANDO SE EXISTE ALGUMA LINHA SELECIONADA NA TABELA.
			if(tvTabelaSessao.getSelectionModel().getSelectedItem() != null){
				// Ao pressionar um item da tabela de sessões cadastradas, o botão selecionar fica habilitado.
				btnSelecionarSessao.setDisable(false);	
			}	
			
		}	catch(Exception e){
				System.out.println("Erro ao selecionar o item na tabela de sessões cadastradas.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}	

}
