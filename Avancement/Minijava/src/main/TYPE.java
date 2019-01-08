package main;

/** Enumeration des Types Primitifs et mapping vers une chaine */
public enum TYPE {
	BOOL ("boolean"),		INT ("int"),	INT_ARRAY ("int[]"),
	UNDEF ("undef");

	String name;
	TYPE(String name){  this.name=name; }
	public String getname(){ return name; }
}
