package main;

/** Enumeration des operateurs, et mapping vers une String */
public enum OPER {
	PLUS ("+"),		MINUS ("-"),	TIMES ("*"),
	AND ("&&"),		LESS ("<"),		NOT ("!"),
	UNDEF ("undef");
	
	String name;
	OPER(String name){ this.name=name; }
	public String getname(){ return name; }
}
