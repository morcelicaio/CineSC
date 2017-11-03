package br.com.empresa.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.empresa.model.Ingresso;
import br.com.empresa.model.Sessao;

public class IngressoDAO {
	private Connection con;
	
	public IngressoDAO(){
		con = new Conexao().criarConexao();
	}
	
	public void cadastrarIngresso(Ingresso ing){
		PreparedStatement ps = null;
		String sql;
		
		sql = "INSERT INTO Ingresso "+
			  "(dataSessao, horaInicioSessao, codSala, codFilme, tipoIngresso, valorTotal, numPoltrona) "+
			  "VALUES(?, ?, ?, ?, ?, ?, ?)";			
		
		try{
			ps = (PreparedStatement) con.prepareStatement(sql);
			
			ps.setDate(1, (Date) ing.getDataSessao()); //fazendo um cast para Date.			
			ps.setTime(2, ing.getHoraInicioSessao());
			ps.setInt(3, ing.getCodSala());
			ps.setInt(4, ing.getCodFilme());
			ps.setString(5, ing.getTipoIngresso());
			ps.setDouble(6, ing.getValorTotal());
			ps.setInt(7, ing.getNumPoltrona());
			
			ps.executeUpdate();			
			
		}	catch(Exception e){
				System.out.println("Erro no cadastro do ingresso.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps);
				}
	}
	
	public ArrayList<Integer> recuperarPoltronasOcupadasDaSessao(Sessao s){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Integer> poltronasOcupadas = new ArrayList<>();
		String sql;
		
		try{		
			//ESTA CONSULTA ESTÁ FUNCIONANDO  CERTINHO  E É A CONSULTA QUE EU QUERO.... SÓ FALTA ARRUMAR A ORDEM DOS CAMPOS NA CONSULTA AGORA.
			sql = "SELECT I.numPoltrona "+
					  "FROM Ingresso I "+
					  "JOIN Sessao S "+
					  "ON I.horaInicioSessao = '"+s.getHoraInicioSessao()+"' "+
					  "WHERE I.dataSessao = '"+s.getDataSessao()+"' AND I.codSala = "+s.getSala().getCodSala();
			
			ps = (PreparedStatement) con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){ 
				int numeroDaPoltronaOcupada = rs.getInt("numPoltrona");
				
				poltronasOcupadas.add(numeroDaPoltronaOcupada);
			}
			
			return poltronasOcupadas;
			
		}	catch(SQLException e){
				System.out.println("Erro no recuperar os números das poltronas ocupadas desta sessão.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	finally{
					FechaRecursosDaConexao.liberarRecursos(con, ps, rs);
				}
		
		return null;
	}
}
