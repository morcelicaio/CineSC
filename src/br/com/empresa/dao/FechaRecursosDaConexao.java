package br.com.empresa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class FechaRecursosDaConexao {
		//Aqui é onde se fecha os recursos que foram usados 
		//para realizar as ações no banco de dados.
	public static void liberarRecursos(Connection conn, PreparedStatement ps){		
		try{
			if(ps != null){
				ps.close();
			}
		}	catch(SQLException e){
				throw new RuntimeException(e);
			}								
					
		try{
			if(conn != null){
				conn.close();
			}
		}	catch(SQLException e){
				throw new RuntimeException(e);
			}		
	}
	
	public static void liberarRecursos(Connection conn, PreparedStatement ps, ResultSet rs){
		try{
			if(ps != null){
				ps.close();
			}
		}	catch(SQLException e){
				throw new RuntimeException(e);
			}
		
		try{
			if(rs != null){
				rs.close();
			}
		}	catch(SQLException e){
				throw new RuntimeException(e);
			}
		
		try{
			if(conn != null){
				conn.close();
			}
		}	catch(SQLException e){
				throw new RuntimeException(e);
			}		
	}	
}
