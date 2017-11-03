package br.com.empresa.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Conexao {
	
	public Connection criarConexao(){
		try{			
			//Conseguimos recuperar uma conexão ao banco de dados através do DriverManager.getConn..
			return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/banco_cinema", "root", "root");
		}	catch(SQLException e){
				System.out.println("Não foi possível se conectar ao banco de dados.");
				throw new RuntimeException(e);
			}		
	}
}

// O objeto Statement é usado para encaminhar requisições 
// para o servidor (no caso o BD aqui).

// O objeto ResultSet é o objeto que traz a lista de valores 
// que retornaram do banco de dados.