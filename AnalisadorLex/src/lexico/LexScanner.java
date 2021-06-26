package lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LexScanner {
	private char[] content;
	private int estado;
	private int pos;

	public LexScanner(String filename) {
		try {
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
			System.out.println(txtConteudo);
			System.out.println("---------------------- \n");
			content = txtConteudo.toCharArray();
			pos=0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public Token nextToken() {
		char currentChar;
		Token token;
		String term="";
		if (fim()) {
			return null;
		}
		estado = 0;
		while (true) {
			currentChar = prox();

			switch(estado) {
			case 0:
				if (letra(currentChar)) {
					term += currentChar;
					estado = 1;
				}
				else if (digito(currentChar)) {
					term += currentChar;
					estado = 3;
				}
				else if (espaco(currentChar)) {
					estado = 0;
				}
				break;
			case 1:
				if (letra(currentChar) || digito(currentChar)) {
					term += currentChar;
					estado = 1;
				}
				else if (espaco(currentChar) || ponto(currentChar)){
					term += currentChar;
					estado = 2;
				}
				else if (relacional(currentChar)) {
					term += currentChar;
					estado = 6;
				}
				else if (igual(currentChar)) {
					term += currentChar;
					estado = 5;
				}
				break;
			case 2:
				if(relacional(currentChar)) {
					term += currentChar;
					estado = 6;
					break;
				}
				back();
				token = new Token();
				token.setType(Token.TK_IDT);
				token.setText(term);
				return token;
			case 3:
				if (digito(currentChar)) {
					estado = 3;
					term += currentChar;
				}
				else if (espaco(currentChar) || ponto(currentChar)) {
					estado = 4;
				}
				break;
				
			case 4:
				token = new Token();
				token.setType(Token.TK_NUM);
				token.setText(term);
				back();
				return token;
				
			case 5:
				if(igual(currentChar)) {
					term += currentChar;
					estado = 6;
				}
				else if(letra(currentChar)) {
					term += currentChar;
					estado = 9;
				}
				break;
				
			case 6:
				if(letra(currentChar)) {
					term += currentChar;
					estado = 7;
				}
				break;
			case 7:
				if (letra(currentChar) || digito(currentChar)) {
					term += currentChar;
					estado = 7;
				}
				else if (espaco(currentChar) || relacional(currentChar) || ponto(currentChar)){
					term += currentChar;
					estado = 8;
				}
				break;
				
			case 8:
				back();
				token = new Token();
				token.setType(Token.TK_OPREL);
				token.setText(term);
				return token;
				
			
			case 9:
				if (letra(currentChar) || digito(currentChar)) {
					term += currentChar;
					estado = 9;
				}
				else if (espaco(currentChar) || relacional(currentChar) || ponto(currentChar)){
					term += currentChar;
					estado = 10;
				}
				break;
				
			case 10:
				back();
				token = new Token();
				token.setType(Token.TK_OPATRI);
				token.setText(term);
				return token;
		    }
		}
	}

	private boolean digito(char c) {
		return c >= '0' && c <= '9';
	}

	private boolean letra(char c) {
		return (c >= 'a' && c <= 'z') || (c>='A' && c <= 'Z');
	}

	private boolean relacional(char c) {
		return c == '>' || c == '<' || c=='!';
	}
	
	private boolean igual(char c) {
		return c=='=';
	}
	
	private boolean espaco(char c) {
		return c == ' '; 
	}

	private char prox() {
		return content[pos++];
	}
	
	private boolean ponto(char c) {
		return c == ';';
	}
	
	private boolean fim() {
		return pos == content.length;
	}

    private void back() {
    	pos--;
    }
}
