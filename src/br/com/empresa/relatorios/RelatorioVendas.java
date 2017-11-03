package br.com.empresa.relatorios;

import java.sql.ResultSet;
import java.util.HashMap;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.empresa.dao.Conexao;
import br.com.empresa.dao.RelatorioDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioVendas {
	Connection con;
	ResultSet rs;
	PreparedStatement ps; 
	
	public RelatorioVendas(){
		con = new Conexao().criarConexao();
		
		RelatorioDAO dao = new RelatorioDAO();
		rs = dao.gerarRelatorioDeVendas();    // recuperando o objeto resultSet da consulta.							
	}
	
	public void gerarRelatorioDeVendas() {
		try{
			JRResultSetDataSource relatorio = new JRResultSetDataSource(rs); // Recebendo um resultSet preenchido p/ o relat�rio. 
			
			JasperPrint jpPrint = JasperFillManager.fillReport("C:/Instalacoes--Utilitarios/projetos_javaFX_caio/ProjetoCinema/iReport_relatoriosDoSistema/RelatorioDeVendaDeIngressos.jasper", new HashMap<String, Object>(), relatorio);						
			
			//o segundo par�metro (false)  serve para depois que vc fechar o relat�rio, a aplica��o n�o seja fechada junto.
			JasperViewer jv = new JasperViewer(jpPrint, false); // Cria uma instancia para a impress�o na tela.
			jv.setVisible(true);  // deixa o relat�rio vis�vel na tela.
			jv.toFront();  // serve para mostrar o relat�rio por cima da sua aplica��o.					
			
		}	catch(JRException e){
				System.out.println("Erro ao gerar o relat�rio de vendas de ingressos.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}	
	}		
}
