package br.com.empresa.util;

import java.util.ArrayList;
import javafx.scene.control.TextField;

//ESSA CLASSE EXTENDE DE UM TEXTFIELD.
// NO ARQUIVO FXML, FOI SETADO NA MAO O TIPO DO COMPONENTE LÁ COMO  MascaraTextField.
//FOI IMPORTADA A BIBLIOTECA br.com.empresa.util lá  para poder acessar esta classe e reconhecer o componente.
//ESTA CLASSE VAI SETAR UMA MÁSCARA PARA O TEXTFIELD DE ACORDO  COM O  PARÂMENTRO DE MÁSCARA QUE FOR PASSADO.
public class MascaraTextField extends TextField {

    private String mascara;
    private ArrayList<String> patterns;

    public MascaraTextField() {
        super();
        patterns = new ArrayList<String>();
    } 

    public MascaraTextField(String text) {
        super(text);
        patterns = new ArrayList<String>();
    }

    @Override
    public void replaceText(int start, int end, String text) {

       
        String tempText = this.getText() + text;

        if(mascara == null || mascara.length() == 0){
            super.replaceText(start, end, text);
        }else if (tempText.matches(this.mascara) || tempText.length() == 0) {        //text.length == 0 representa o delete ou backspace

            super.replaceText(start, end, text);

        } else {

            String tempP = "^";

            for (String patt : this.patterns) {

                tempP += patt;

                if (tempText.matches(tempP)) {

                    super.replaceText(start, end, text);
                    break;

                }

            }

        }

    }

    /**
     * @return the Regex Mask
     */
    public String getMascara() {
        return mascara;
    }

    /**
     * @param novaMascara é a mascara que irá ser setada.
     */
    public void setarMascara(String novaMascara) {

        String tempMask = "^";

        for (int i = 0; i < novaMascara.length(); ++i) {

            char temp = novaMascara.charAt(i);
            String regex;
            int contador = 1;
            int passo = 0;

            if (i < novaMascara.length() - 1) {
                for (int j = i + 1; j < novaMascara.length(); ++j) {
                    if (temp == novaMascara.charAt(j)) {
                        ++contador;
                        passo = j;
                    } 	else if (novaMascara.charAt(j) == '!') {
                        	contador = -1;
                        	passo = j;
                    	} 	else {
                    			break;
                    		}
                }
            }
            
            switch (temp) {

                case '*':
                    regex = ".";
                    break;
                case 'S':
                    regex = "[^\\s]";
                    break;
                case 'P':
                    regex = "[A-z.]";
                    break;
                case 'M':
                    regex = "[0-z.]";
                    break;
                case 'A':
                    regex = "[0-z]";
                    break;
                case 'N':
                    regex = "[0-9]";
                    break;
                case 'L':
                    regex = "[A-z]";
                    break;
                case 'U':
                    regex = "[A-Z]";
                    break;
                case 'l':
                    regex = "[a-z]";
                    break;
                case '.':
                    regex = "\\.";
                    break;
                default:
                    regex = Character.toString(temp);
                    break;

            }

            if (contador != 1) {

                this.patterns.add(regex);

                if (contador == -1) {
                    regex += "+";
                    this.patterns.add(regex);
                } 	else {
                    	String tempRegex = regex;
                    	
                    	for (int k = 1; k < contador; ++k) {
                    		regex += tempRegex;
                    		this.patterns.add(tempRegex);
                    	}
                	}

                i = passo;

            }   else {
                	this.patterns.add(regex);
            	}

            tempMask += regex;
 
        }

        this.mascara = tempMask + "$";

    }

}