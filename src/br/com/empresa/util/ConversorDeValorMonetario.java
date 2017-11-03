package br.com.empresa.util;

public class ConversorDeValorMonetario {
	
	public double converterTextoDeValorMonetarioDaTabela(String valorString) {        
		String subString;
		double valorConvertido;        
        
		try {	// retirando da string o R$ e o espaço após o R$ que vem da tabela.
			subString = valorString.substring(3); 
			
			//Aqui já se tem a nova string que está setada como o seguinte exemplo: 0,00
			subString = subString.replace(",", "."); // substituindo a virgula da string pelo ponto.					
						
			valorConvertido = Double.parseDouble(subString); // convertendo a nova string com ponto para double.								
            return valorConvertido;
            
        } 	catch(Exception e) {
        		System.out.println("Erro na formatação do valor monetário.");
        		System.out.println(e.getMessage());
        		e.printStackTrace();            
        	}
		
		return 0.0; 
    }
	
	public double converterTextoDeValorMonetario(String valorString) {        		
		double valorConvertido;        
        
		try {							
			//Aqui se tem a nova string que está setada como o seguinte exemplo: 0,00
			valorString = valorString.replace(",", "."); // substituindo a virgula da string pelo ponto.					
						
			valorConvertido = Double.parseDouble(valorString); // convertendo a nova string com ponto para double.								
            return valorConvertido;
            
        } 	catch(Exception e) {
        		System.out.println("Erro na formatação do valor monetário.");
        		System.out.println(e.getMessage());
        		e.printStackTrace();            
        	}
		
		return 0.0; 
    }
	
}
