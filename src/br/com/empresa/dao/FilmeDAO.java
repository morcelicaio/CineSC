package br.com.empresa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import br.com.empresa.model.Filme;

public class FilmeDAO {	
	private Connection con;
	
	public FilmeDAO(){
		con = new Conexao().criarConexao();
	}
	
	public void cadastrarFilme(Filme filme){		
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO Filme "+
					 "(nomeFilme, nomeDiretor, linguagemFilme, categoriaFilme, anoLancamento) "+
					 "VALUES(?, ?, ?, ?, ?)";
		
		try{
			ps = (PreparedStatement) con.prepareStatement(sql);
			
			ps.setString(1, filme.getNomeFilme());
			ps.setString(2, filme.getNomeDiretor());
			ps.setString(3, filme.getLinguagemFilme());
			ps.setString(4, filme.getCategoriaFilme());
			ps.setInt(5, filme.getAnoLancamento());
			
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro no cadastro do filme.");
				System.out.println(e.getMessage());
				e.printStackTrace();				
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	}
	
	public void alterarFilme(Filme f){
		PreparedStatement ps = null;
		
		try{		
			String sql = "UPDATE Filme "+
					 	 "SET nomeFilme = ?, nomeDiretor = ?, linguagemFilme = ?, categoriaFilme = ?, anoLancamento = ? "+
					 	 "WHERE codFilme = ?";
			
			ps = (PreparedStatement) con.prepareStatement(sql);
			
			ps.setString(1, f.getNomeFilme());
			ps.setString(2, f.getNomeDiretor());
			ps.setString(3, f.getLinguagemFilme());
			ps.setString(4, f.getCategoriaFilme());
			ps.setInt(5, f.getAnoLancamento());
			ps.setInt(6, f.getCodFilme());
			
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro na alteração do filme.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	}
	
	public void excluirFilme(int codFilme){
		PreparedStatement ps = null;
		Filme f = new Filme();
		f.setCodFilme(codFilme);  
		
		try{		
			String sql = "DELETE FROM Filme "+					 	 
					 	 "WHERE codFilme = ?";
			
			ps = (PreparedStatement) con.prepareStatement(sql);					
			
			ps.setInt(1, f.getCodFilme());
			ps.executeUpdate();
			
		}	catch(SQLException e){
				System.out.println("Erro na exclusão do filme.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	}
	
		// Aqui vou selecionar apenas os campos codFilme nome e idioma
	   //  pois vou usar só o nome e idioma p/ preencher a tabela na tela 
	  //   de cadastro de sessoes e o código vai ficar como uma coluna invisível lá.
	public ArrayList<Filme> listarFilmes(){			
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Filme> filmes = null;
		String sql;
		
		try{
			sql = "SELECT * "+
				  "FROM Filme";
			
			ps = (PreparedStatement) this.con.prepareStatement(sql);			
			rs = ps.executeQuery();
			
			filmes = new ArrayList<>();
			
			while(rs.next()){
				Filme f = new Filme();
				
				f.setCodFilme(rs.getInt("codFilme"));
				f.setNomeFilme(rs.getString("nomeFilme"));
				f.setNomeDiretor(rs.getString("nomeDiretor"));				
				f.setCategoriaFilme(rs.getString("categoriaFilme"));
				f.setAnoLancamento(rs.getInt("anoLancamento"));
				f.setLinguagemFilme(rs.getString("linguagemFilme"));
				
				filmes.add(f);							
			}
			
			return filmes;
			
		}	catch(SQLException e){
				System.out.println("Erro na listagem.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
				
		return null;
	}
	
	public Filme buscarFilmeSelecionado(int codigo){	
		Filme f = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql;
		
		try{						
			sql = "SELECT * FROM Filme WHERE codFilme = "+codigo+";";
				
			ps = (PreparedStatement) this.con.prepareStatement(sql);			
			rs = ps.executeQuery();
				
			while(rs.next()){	
				
				f = new Filme();
				
				f.setCodFilme(rs.getInt("codFilme"));
				f.setNomeFilme(rs.getString("nomeFilme"));
				f.setNomeDiretor(rs.getString("nomeDiretor"));
				f.setAnoLancamento(rs.getInt("anoLancamento"));
				f.setLinguagemFilme(rs.getString("linguagemFilme"));				
			}
			
			return f;
			
		}	catch (SQLException e){				
				System.out.println("Erro ao buscar o filme selecionado.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
		
		return null;	
	}
	
		// MÉTODO USADO NA TELA DE VENDER INGRESSO NA HORA DE BUSCAR O FILME NA SESSÃO.
	public ArrayList<Filme> buscarFilme(String s){			
		PreparedStatement ps = null;
		ResultSet rs = null;
		Filme f = null;
		String sql;
		
		ArrayList<Filme> filmes = new ArrayList<>();
		
		try{						
			sql = "SELECT * FROM Filme WHERE nomeFilme LIKE ?";
				
			ps = (PreparedStatement) this.con.prepareStatement(sql);
			ps.setString(1, s+"%");
			
			rs = ps.executeQuery();					
			
			while(rs.next()){					
				f = new Filme();
				
				f.setCodFilme(rs.getInt("codFilme"));
				f.setNomeFilme(rs.getString("nomeFilme"));
				f.setNomeDiretor(rs.getString("nomeDiretor"));
				f.setAnoLancamento(rs.getInt("anoLancamento"));
				f.setLinguagemFilme(rs.getString("linguagemFilme"));
				f.setCategoriaFilme(rs.getString("categoriaFilme"));
				
				filmes.add(f);
			}
			
			return filmes;
			
		}	catch (SQLException e){				
				System.out.println("Erro ao buscar os filmes.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps, rs);
				}
		
		return null;	
	}
}
