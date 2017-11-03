package br.com.empresa.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.empresa.model.Filme;
import br.com.empresa.model.Sala;
import br.com.empresa.model.Sessao;

public class SessaoDAO {	
	private Connection con;
	
	public SessaoDAO(){
		con = new Conexao().criarConexao(); // criando a conexao a partir do momento em q o DAO é chamado.
	}
	
	public void cadastrarSessao(Sessao s){
		PreparedStatement ps = null;
		String sql;				
		
		sql = "INSERT INTO Sessao (dataSessao, codSala, codFilme, horaInicioSessao, horaFimSessao,"+
								 " qtdPoltronasDisponiveis, valorSessao)" +
			  " VALUES (?, ?, ?, ?, ?, ?, ?)";
				
		try{
			ps = (PreparedStatement) con.prepareStatement(sql);
						
			ps.setDate(1, (Date) s.getDataSessao()); //fazendo um cast para Date.
			ps.setInt(2, s.getSala().getCodSala()); 
			ps.setInt(3, s.getFilme().getCodFilme()); 
			ps.setTime(4, s.getHoraInicioSessao());			
			ps.setTime(5, s.getHoraFimSessao());
			ps.setInt(6, s.getQtdPoltronasDisponiveis());
			ps.setDouble(7, s.getValorSessao());
			
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro no cadastro da sessão.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
		
	}
	
	//    
	 public void alterarSessao(Sessao s, Sessao s2){
		PreparedStatement ps = null;			
		JOptionPane.showMessageDialog(null, "codSala na clausula where do banco = "+s.getSala().getCodSala());
		try{		// esta dando erro na sintaxe sql agora... arrumar aqui
			String sql = "UPDATE Sessao "+                              
					 	 "SET dataSessao = ?, codSala = ?, codFilme = ?, horaInicioSessao = ?, "
					 	 +   "horaFimSessao = ?, qtdPoltronasDisponiveis = ?, valorSessao = ? "+
					 	 "WHERE dataSessao = '"+s2.getDataSessao()+"' AND horaInicioSessao = '"+s2.getHoraInicioSessao()+"'"+
					 	                     " AND codSala = "+s.getSala().getCodSala()+";";
			
			ps = (PreparedStatement) con.prepareStatement(sql);					
			
			ps.setDate(1, (Date) s.getDataSessao()); //fazendo um cast para Date.			 		
			ps.setInt(2, s2.getSala().getCodSala());  // numero da nova sala 			
			ps.setInt(3, s.getFilme().getCodFilme()); 
			ps.setTime(4, s.getHoraInicioSessao());			
			ps.setTime(5, s.getHoraFimSessao());
			ps.setInt(6, s.getQtdPoltronasDisponiveis());
			ps.setDouble(7, s.getValorSessao());
			
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro na alteração da sessão.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	} 
	 
	 
	
			// Traz todas as sessões cadastradas no sistema. Tanto as que já estão com a data expirada quanto as que não estão.
	public ArrayList<Sessao> listarSessoes(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Sessao> sessoes = null;
		String sql;
		
		try{
			sql = "SELECT SE.dataSessao, SA.codSala, SE.codFilme, F.nomeFilme, F.linguagemFilme, "+
				         "SE.horaInicioSessao, SE.horaFimSessao, SE.qtdPoltronasDisponiveis, "+
				         "SE.valorSessao "+
				         "FROM Sessao SE "+
				         "INNER JOIN Filme F "+
				         "ON SE.codFilme = F.codFilme "+
				         "INNER JOIN SALA SA "+
				         "ON SE.codSala = SA.codSala";
			
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			sessoes = new ArrayList<>();
			
			//a sessao possui um objeto filme contido nela também.			
			while(rs.next()){
				
				Sessao sessao = new Sessao();
				Filme filme = new Filme(); FilmeDAO filmeDAO = new FilmeDAO();
				Sala sala = new Sala(); SalaDAO salaDAO = new SalaDAO();
				
				int codFilme = rs.getInt("codFilme");
				int codSala = rs.getInt("codSala");
				
				filme = filmeDAO.buscarFilmeSelecionado(codFilme);
				sala = salaDAO.buscarSalaSelecionada(codSala);
							
				sessao.setDataSessao(rs.getDate("dataSessao"));
				sessao.setSala(sala);
				sessao.setFilme(filme);
				sessao.setHoraInicioSessao(rs.getTime("horaInicioSessao"));
				sessao.setHoraFimSessao(rs.getTime("horaFimSessao"));
				sessao.setValorSessao(rs.getDouble("valorSessao"));
				sessao.setQtdPoltronasDisponiveis(rs.getInt("qtdPoltronasDisponiveis"));									
				
				sessoes.add(sessao);
			}
			
			return sessoes;
			
		}	catch(SQLException e){
				System.out.println("Erro ao listar as sessões.");
				System.out.println(e.getMessage());
			}	finally {
					FechaRecursosDaConexao.liberarRecursos(con, ps, rs);
				}
		
		return null;
	}
				//BUSCA AS SESSÕES QUE AINDA ESTÃO EM CARTAZ NO CINEMA.   AS OUTRAS JA ESTÃO COM SUAS DATAS EXPIRADAS.
	public ArrayList<Sessao> listarSessoesComDataValidaParaVendaDeIngresso(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Sessao> sessoes = null;
		String sql;
		
		try{
			sql = "SELECT SE.dataSessao, SA.codSala, SE.codFilme, F.nomeFilme, F.linguagemFilme, "+
				         "SE.horaInicioSessao, SE.horaFimSessao, SE.qtdPoltronasDisponiveis, "+
				         "SE.valorSessao "+
				         "FROM Sessao SE "+
				         "INNER JOIN Filme F "+
				         "ON SE.codFilme = F.codFilme "+
				         "INNER JOIN SALA SA "+
				         "ON SE.codSala = SA.codSala "+
				         "WHERE SE.dataSessao >= CURDATE()";  
			
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			sessoes = new ArrayList<>();
			
			//a sessao possui um objeto filme contido nela também.			
			while(rs.next()){
				
				Sessao sessao = new Sessao();
				Filme filme = new Filme(); FilmeDAO filmeDAO = new FilmeDAO();
				Sala sala = new Sala(); SalaDAO salaDAO = new SalaDAO();
				
				int codFilme = rs.getInt("codFilme");
				int codSala = rs.getInt("codSala");
				
				filme = filmeDAO.buscarFilmeSelecionado(codFilme);
				sala = salaDAO.buscarSalaSelecionada(codSala);
							
				sessao.setDataSessao(rs.getDate("dataSessao"));
				sessao.setSala(sala);
				sessao.setFilme(filme);
				sessao.setHoraInicioSessao(rs.getTime("horaInicioSessao"));
				sessao.setHoraFimSessao(rs.getTime("horaFimSessao"));
				sessao.setValorSessao(rs.getDouble("valorSessao"));
				sessao.setQtdPoltronasDisponiveis(rs.getInt("qtdPoltronasDisponiveis"));									
				
				sessoes.add(sessao);
			}
			
			return sessoes;
			
		}	catch(SQLException e){
				System.out.println("Erro ao listar as sessões.");
				System.out.println(e.getMessage());
			}	finally {
					FechaRecursosDaConexao.liberarRecursos(con, ps, rs);
				}
		
		return null;
	}
	
	public void atualizarPoltronas(int qtdPoltronasLivres, int codigoSala, Sessao s){
		PreparedStatement ps = null;
		String sql;
				
		sql = "UPDATE Sessao "+
			  "SET qtdPoltronasDisponiveis = ? "+
			  "WHERE dataSessao = ? AND horaInicioSessao = ? AND codSala = ?";				
		
		try{
			ps = (PreparedStatement) con.prepareStatement(sql);
			
			ps.setInt(1, qtdPoltronasLivres);
			ps.setDate(2, (Date) s.getDataSessao());
			ps.setTime(3, s.getHoraInicioSessao());
			ps.setInt(4, codigoSala);
			
			ps.executeUpdate();	
			
		} catch(Exception e){
				System.out.println("Erro ao atualizar as poltronas disponíveis.");
				System.out.println(e.getMessage());
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	}
		
	//Recuperando a sessão que foi escolhida na hora de vender o ingresso.
	public Sessao buscarSessaoSelecionada(Date dataDaSessao, Time horaInicioDaSessao, int codigoSala, int codFilme){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Sessao s;
		String sql;
		
		try{
			sql = "SELECT * "+
				  "FROM Sessao "+
				  "WHERE dataSessao = '"+dataDaSessao+"' AND horaInicioSessao = '"+horaInicioDaSessao+ "' AND codSala = "+codigoSala+" AND codFilme = "+codFilme;
			
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			s = new Sessao();
			
			while(rs.next()){				
				Filme filme = new Filme(); FilmeDAO filmeDAO = new FilmeDAO();
				Sala sala = new Sala(); SalaDAO salaDAO = new SalaDAO();
				
				int codigoDoFilme = rs.getInt("codFilme");
				int codigoDaSala = rs.getInt("codSala");
				
				filme = filmeDAO.buscarFilmeSelecionado(codigoDoFilme);
				sala = salaDAO.buscarSalaSelecionada(codigoDaSala);
				
				s.setDataSessao(rs.getDate("dataSessao"));
				s.setSala(sala);
				s.setFilme(filme);
				s.setHoraInicioSessao(rs.getTime("horaInicioSessao"));
				s.setHoraFimSessao(rs.getTime("horaFimSessao"));
				s.setQtdPoltronasDisponiveis(rs.getInt("qtdPoltronasDisponiveis"));
				s.setValorSessao(rs.getDouble("valorSessao"));
			}
			
			return s;
			
		}	catch(SQLException e){
				System.out.println("Erro au buscar a sessão selecionada.");
				System.out.println(e.getMessage());
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps, rs);
				}
		
		return null;
	}
	
	
	
	//É usado na tela de cadastrar sessão.   Traz todas as sessões cadastradas no sitema independente da data.
	public ArrayList<Sessao> buscarSessoes(String s){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Sessao> sessoes = null;
		String sql;
		
		try{		
			sql = "SELECT SE.dataSessao, SA.codSala, SE.codFilme, F.nomeFilme, F.linguagemFilme, "+
			         "SE.horaInicioSessao, SE.horaFimSessao, SE.qtdPoltronasDisponiveis, "+
			         "SE.valorSessao "+
			         "FROM Sessao SE "+
			         "INNER JOIN Filme F "+
			         "ON SE.codFilme = F.codFilme "+
			         "INNER JOIN SALA SA "+
			         "ON SE.codSala = SA.codSala "+
			         "WHERE F.nomeFilme LIKE ?"; 
						
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, s+"%");
			
			rs = ps.executeQuery();
			
			sessoes = new ArrayList<>();
			
			//a sessao possui um objeto filme e um obj sala contido nela também.			
			while(rs.next()){
				
				Sessao sessao = new Sessao();
				Filme filme = new Filme(); FilmeDAO filmeDAO = new FilmeDAO();
				Sala sala = new Sala(); SalaDAO salaDAO = new SalaDAO();
				
				int codFilme = rs.getInt("codFilme");
				int codSala = rs.getInt("codSala");
				
				filme = filmeDAO.buscarFilmeSelecionado(codFilme);
				sala = salaDAO.buscarSalaSelecionada(codSala);
							
				sessao.setDataSessao(rs.getDate("dataSessao"));
				sessao.setSala(sala);
				sessao.setFilme(filme);
				sessao.setHoraInicioSessao(rs.getTime("horaInicioSessao"));
				sessao.setHoraFimSessao(rs.getTime("horaFimSessao"));
				sessao.setValorSessao(rs.getDouble("valorSessao"));
				sessao.setQtdPoltronasDisponiveis(rs.getInt("qtdPoltronasDisponiveis"));									
				
				sessoes.add(sessao);
			}
			
			return sessoes;
			
		}	catch(SQLException e){
				System.out.println("Erro na busca das sessões baseadas na string digitada.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally {
					FechaRecursosDaConexao.liberarRecursos(con, ps, rs);
				}
		
		return null;
	}
	
	
	//Vai ser usado no evento do text field de busca na tela de vender ingresso.
		public ArrayList<Sessao> buscarSessoesComDataValidaParaVendaDeIngresso(String s){ 
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<Sessao> sessoes = null;
			String sql;
			
			try{		//buscando as sessoes que podem ter seus ingressos vendidos. 
					    //As sessões que já tiveram suas datas expiradas não aparecem aqui.
				sql = "SELECT SE.dataSessao, SA.codSala, SE.codFilme, F.nomeFilme, F.linguagemFilme, "+
				         "SE.horaInicioSessao, SE.horaFimSessao, SE.qtdPoltronasDisponiveis, "+
				         "SE.valorSessao "+
				         "FROM Sessao SE "+
				         "INNER JOIN Filme F "+
				         "ON SE.codFilme = F.codFilme "+
				         "INNER JOIN SALA SA "+
				         "ON SE.codSala = SA.codSala "+				
				         "WHERE SE.dataSessao >= CURDATE() AND F.nomeFilme LIKE ?";    // CURDATE()   retorna a dataAtual.
							
				ps = (PreparedStatement) con.prepareStatement(sql);
				ps.setString(1, s+"%");
				
				rs = ps.executeQuery();
				
				sessoes = new ArrayList<>();
				
				//a sessao possui um objeto filme e um obj sala contido nela também.			
				while(rs.next()){
					
					Sessao sessao = new Sessao();
					Filme filme = new Filme(); FilmeDAO filmeDAO = new FilmeDAO();
					Sala sala = new Sala(); SalaDAO salaDAO = new SalaDAO();
					
					int codFilme = rs.getInt("codFilme");
					int codSala = rs.getInt("codSala");
					
					filme = filmeDAO.buscarFilmeSelecionado(codFilme);
					sala = salaDAO.buscarSalaSelecionada(codSala);
								
					sessao.setDataSessao(rs.getDate("dataSessao"));
					sessao.setSala(sala);
					sessao.setFilme(filme);
					sessao.setHoraInicioSessao(rs.getTime("horaInicioSessao"));
					sessao.setHoraFimSessao(rs.getTime("horaFimSessao"));
					sessao.setValorSessao(rs.getDouble("valorSessao"));
					sessao.setQtdPoltronasDisponiveis(rs.getInt("qtdPoltronasDisponiveis"));									
					
					sessoes.add(sessao);
				}
				
				return sessoes;
				
			}	catch(SQLException e){
					System.out.println("Erro na busca das sessões baseadas na string digitada.");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}	finally {
						FechaRecursosDaConexao.liberarRecursos(con, ps, rs);
					}
			
			return null;
		}
	
}
