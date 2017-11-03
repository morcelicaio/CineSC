package br.com.empresa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.empresa.model.Sala;

public class SalaDAO {
	private Connection con;
	
	public SalaDAO(){		
		con = new Conexao().criarConexao();
	}
	
	public void cadastrarSala(Sala sala){								
		PreparedStatement ps = null;			   // Objeto usado para fazer requisições ao banco de dados.
		
		String sql = "INSERT INTO Sala " +
					 "(nroSala, qtdeDeLinhasDePoltronas, qtdeDeColunasDePoltronas, qtdAssentos) " +
					 "VALUES(?, ?, ?, ?)";
		try{
			ps = (PreparedStatement) this.con.prepareStatement(sql);
			
			ps.setInt(1, sala.getNroSala());			
			ps.setInt(2, sala.getQtdeDeLinhasDePoltronas());
			ps.setInt(3, sala.getQtdeDeColunasDePoltronas());
			ps.setInt(4, sala.getQtdAssentos());
			
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro no cadastro de sala.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally {
					FechaRecursosDaConexao.liberarRecursos(con, ps); 
				}		
	}
	
	public void alterarSala(Sala s){
		PreparedStatement ps = null;
		
		try{		
			String sql = "UPDATE Sala "+
					 	 "SET nroSala = ?, qtdeDeLinhasDePoltronas = ?, qtdeDeColunasDePoltronas = ?, qtdAssentos = ? "+
					 	 "WHERE codSala = ?";
			
			ps = (PreparedStatement) con.prepareStatement(sql);
			
			ps.setInt(1, s.getNroSala());
			ps.setInt(2, s.getQtdeDeLinhasDePoltronas());
			ps.setInt(3, s.getQtdeDeColunasDePoltronas());
			ps.setInt(4, s.getQtdAssentos());			
			ps.setInt(5, s.getCodSala());
			
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro na alteração da sala.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	}
	
	public void excluirSala(int codSala){
		PreparedStatement ps = null;
		Sala s = new Sala();
		s.setCodSala(codSala);  
		
		try{		
			String sql = "DELETE FROM Sala "+					 	 
					 	 "WHERE codSala = ?";
			
			ps = (PreparedStatement) con.prepareStatement(sql);					
			
			ps.setInt(1, s.getCodSala());
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro na exclusão da sala.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	}
	
	public ArrayList<Sala> listarSalas(){
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Sala> salas = null;
		String sql;
		
		try{
			sql = "SELECT * FROM Sala";
						
			ps = (PreparedStatement) con.prepareStatement(sql); 			
			rs = ps.executeQuery();
			
			salas = new ArrayList<>();
			
			while(rs.next()){
				Sala s = new Sala();
				
				s.setCodSala(rs.getInt("codSala"));
				s.setNroSala(rs.getInt("nroSala"));
				s.setQtdeDeLinhasDePoltronas(rs.getInt("qtdeDeLinhasDePoltronas"));
				s.setQtdeDeColunasDePoltronas(rs.getInt("qtdeDeColunasDePoltronas"));
				s.setQtdAssentos(rs.getInt("qtdAssentos"));
				
				salas.add(s);
			}					
			
			return salas;	
			
		}	catch(SQLException e){
				System.out.println("Erro ao listar as salas.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally {
					FechaRecursosDaConexao.liberarRecursos(con, ps); 
				}
		
		return null;				
	}
	
	public Sala buscarSalaSelecionada(int codigoSala){	
		Sala s = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql; 
		
		try{						
			sql = "SELECT * FROM Sala WHERE codSala = "+codigoSala+";";
				
			ps = (PreparedStatement) this.con.prepareStatement(sql);			
			rs = ps.executeQuery();
				
			while(rs.next()){	
				s = new Sala();
				
				s.setCodSala(rs.getInt("codSala"));
				s.setNroSala(rs.getInt("nroSala"));
				s.setQtdeDeLinhasDePoltronas(rs.getInt("qtdeDeLinhasDePoltronas"));
				s.setQtdeDeColunasDePoltronas(rs.getInt("qtdeDeColunasDePoltronas"));
				s.setQtdAssentos(rs.getInt("qtdAssentos"));			
			}
			
			return s;
			
		}	catch (SQLException e){
				System.out.println("Erro ao buscar a sala selecionada.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
		
		return null;	
	}
}
