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
	
	
	/* ESTA FUNCIONANDO MAS AQUI � PARA ABRIR NA TABELA. O OUTRO M�TODO IGUAL ESTE � PARA ABRIR PELO JASPER.
	public ArrayList<Relatorio> gerarRelatorioDeVendas(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Relatorio> vendas = new ArrayList<>();
		String sql;
		
		//Na vari�vel sql abaixo, na fun��o SUM(I.valorTotal), � necess�rio usar a cl�usula AS para dar um nome
		//p a coluna, pois senao na hora de setar o valor desta coluna no codigo, ir� gerar 1 exce��o, pois o java
		//n�o ir� conseguir entender qual coluna est� sendo chamada ali.
		//Neste caso esta coluna est� sendo setada em  " r.setValorArrecadadoFilme(rs.getDouble("valorTotal")); "
		
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
				r.setValorArrecadadoFilme(rs.getDouble("valorTotal"));  //COLUNA RENOMEADA PELA CL�USULA 'AS'
								
				vendas.add(r);
			}
			
			return vendas;
			
		}	catch(SQLException e){
				System.out.println("Erro ao preencher o relat�rio de vendas.");
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
		
		//Na vari�vel sql abaixo, na fun��o SUM(I.valorTotal), � necess�rio usar a cl�usula AS para dar um nome
		//p a coluna, pois senao na hora de setar o valor desta coluna no codigo, ir� gerar 1 exce��o, pois o java
		//n�o ir� conseguir entender qual coluna est� sendo chamada ali.
		//Neste caso esta coluna est� sendo setada em  " r.setValorArrecadadoFilme(rs.getDouble("valorTotal")); "
				 
		 sql = "SELECT I.codFilme, F.nomeFilme, F.anoLancamento, SUM(I.valorTotal) AS valorTotal "+
			   "FROM Filme F "+
			   "JOIN Ingresso I "+
			   "ON F.codFilme = I.codFilme "+
			   "GROUP BY F.codFilme";
		
		try{
			ps = (PreparedStatement) this.con.prepareStatement(sql);
			rs = ps.executeQuery();	 // l� no relat�rio ele recebe este resultSet para printar o relat�rio na tela.									
						
			return rs;
			
		}	catch(SQLException e){
				System.out.println("Erro ao preencher o relat�rio de vendas.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	
		
		return null;
	}	
}