package symboltable;

public class KlassScope extends Scope {
	public Klass klass;

	public KlassScope(Scope parent, Klass klass){
		super(parent,klass.name);
		this.klass = klass;
		klass.scope=this;
	}
		
	public Klass superKlass(){ // ...
		if(klass.parentName == null)	return null;
		return lookupClass(klass.parentName);  
	}
	
	public String toPrint(){
		String temp="class " + klass.name;
		if(klass.parentName != null) // or ! "Object" ?
			temp=temp+" extends " + klass.parentName;
		return temp;
	}
		
}
