package symboltable;

public class Klass {
	public String name;
	public String parentName;
	public KlassScope scope;
	
	public Klass(String name, String parentName) {
		this.name = name;
		this.parentName = parentName;
		this.scope = null; // set by new ClasseScope
	}
}
