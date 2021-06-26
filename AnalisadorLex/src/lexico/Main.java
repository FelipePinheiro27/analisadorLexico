package lexico;


public class Main {
	public static void main(String[] args) {
		try {
			LexScanner sc = new LexScanner("C:\\Users\\felip\\Desktop\\text.txt");
			Token token = null;
			do {
				token = sc.nextToken();
				if (token != null) {
					System.out.println(token);
				}

			} while (token != null);
		}
		catch (Exception ex) {	
			System.out.println("Generic Error!!");
		}
	}
}