package br.com.empresa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class RelatorioDAO {
	private Connection con;
	
	public RelatorioDAO(){
		con = new Conexao().criarConexao();
	}
	
	
	/* ESTA FUNCIONANDO MAS AQUI É PARA ABRIR NA TABELA. O OUTRO MÉTODO IGUAL ESTE É PARA ABRIR PELO JASPER.
	public ArrayList<Relatorio> gerarRelatorioDeVendas(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Relatorio> vendas = new ArrayList<>();
		String sql;
		
		//Na variável sql abaixo, na função SUM(I.valorTotal), é necessário usar a cláusula AS para dar um nome
		//p a coluna, pois senao na hora de setar o valor desta coluna no codigo, irá gerar 1 exceção, pois o java
		//não irá conseguir entender qual coluna está sendo chamada ali.
		//Neste caso esta coluna está sendo setada em  " r.setValorArrecadadoFilme(rs.getDouble("valorTotal")); "
		
		sql = "SELECT I.codIngresso, F.codFilme, I.codSala, F.nomeFilme, F.anoLancamento, "+
			  "F.categoriaFilme, SUM(I.valorTotal) AS valorTotal "+
			  "FROM Filme F "+
			  "JOIN Ingresso I "+
			  "ON F.codFilme = I.codFilme "+
			  "GROUP BY F.codFilme";
		
		try{
			ps = (PreparedStatement) this.con.prepareStatement(sql);
			rs = ps.executeQuery();					
			
			while(rs.next()){
				Relatorio r = new Relatorio();
				
				r.setCodIngresso(rs.getInt("codIngresso"));
				r.setCodFilme(rs.getInt("codFilme"));
				r.setCodSala(rs.getInt("codSala"));
				r.setNomeFilme(rs.getString("nomeFilme"));
				r.setAnoLancamentoFilme(rs.getInt("anoLancamento"));
				r.setCategoriaFilme(rs.getString("categoriaFilme"));
				r.setValorArrecadadoFilme(rs.getDouble("valorTotal"));  //COLUNA RENOMEADA PELA CLÁUSULA 'AS'
								
				vendas.add(r);
			}
			
			return vendas;
			
		}	catch(SQLException e){
				System.out.println("Erro ao preencher o relatório de vendas.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	
				
		return null;
	}	
	      */
	
	public ResultSet gerarRelatorioDeVendas(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql;
		
		//Na variável sql abaixo, na função SUM(I.valorTotal), é necessário usar a cláusula AS para dar um nome
		//p a coluna, pois senao na hora de setar o valor desta coluna no codigo, irá gerar 1 exceção, pois o java
		//não irá conseguir entender qual coluna está sendo chamada ali.
		//Neste caso esta coluna está sendo setada em  " r.setValorArrecadadoFilme(rs.getDouble("valorTotal")); "
				 
		 sql = "SELECT I.codFilme, F.nomeFilme, F.anoLancamento, SUM(I.valorTotal) AS valorTotal "+
			   "FROM Filme F "+
			   "JOIN Ingresso I "+
			   "ON F.codFilme = I.codFilme "+
			   "GROUP BY F.codFilme";
		
		try{
			ps = (PreparedStatement) this.con.prepareStatement(sql);
			rs = ps.executeQuery();	 // lá no relatório ele recebe este resultSet para printar o relatório na tela.									
						
			return rs;
			
		}	catch(SQLException e){
				System.out.println("Erro ao preencher o relatório de vendas.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	
		
		return null;
	}	
}