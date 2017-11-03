package br.com.empresa.util;

public class ConversorDeValorMonetario {
	
	public double converterTextoDeValorMonetarioDaTabela(String valorString) {        
		String subString;
		double valorConvertido;        
        
		try {	// retirando da string o R$ e o espa�o ap�s o R$ que vem da tabela.
			subString = valorString.substring(3); 
			
			//Aqui j� se tem a nova string que est� setada como o seguinte exemplo: 0,00
			subString = subString.replace(",", "."); // substituindo a virgula da string pelo ponto.					
						
			valorConvertido = Double.parseDouble(subString); // convertendo a nova string com ponto para double.								
            return valorConvertido;
            
        } 	catch(Exception e) {
        		System.out.println("Erro na formata��o do valor monet�rio.");
        		System.out.println(e.getMessage());
        		e.printStackTrace();            
        	}
		
		return 0.0; 
    }
	
	public double converterTextoDeValorMonetario(String valorString) {        		
		double valorConvertido;        
        
		try {							
			//Aqui se tem a nova string que est� setada como o seguinte exemplo: 0,00
			valorString = valorString.replace(",", "."); // substituindo a virgula da string pelo ponto.					
						
			valorConvertido = Double.parseDouble(valorString); // convertendo a nova string com ponto para double.								
            return valorConvertido;
            
        } 	catch(Exception e) {
        		System.out.println("Erro na formata��o do valor monet�rio.");
        		System.out.println(e.getMessage());
        		e.printStackTrace();            
        	}
		
		return 0.0; 
    }
	
}
