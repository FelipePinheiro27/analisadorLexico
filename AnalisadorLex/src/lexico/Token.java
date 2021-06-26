package lexico;

public class Token {
	public static final String TK_IDT  = "identificador";
	public static final String TK_NUM = "numero";
	public static final String TK_OPREL = "relacional";
	public static final int TK_PONT = 3;
	public static final String TK_OPATRI = "atribuicao";

	private String type;
	private String text;

	public Token(String type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

	public Token() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String tkIdt) {
		this.type = tkIdt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Token [type=" + type + ", text=" + text + "]";
	}
}
