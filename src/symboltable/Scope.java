package symboltable;

import java.util.ArrayList;
import java.util.Hashtable;

public class Scope {
	public String name;
	public Scope parent;
	private ArrayList<Scope> scopes;
	public Hashtable<String, Variable> vars;
	public Hashtable<String, Method> methods;	
	public Hashtable<String, Klass> classes;

	public Scope(Scope parent,String name){
		this.name=name;
		this.parent = parent;
		this.scopes = new ArrayList<Scope>();
		this.vars = new Hashtable<String, Variable>();
		this.methods = new Hashtable<String, Method>();
		this.classes = new Hashtable<String,  Klass>();
	}
	
	public Scope newScope(){
		Scope sc=new Scope(this, "B"+scopes.size());
		scopes.add(sc);
		return sc;
	}
	
	public Scope newScope(Klass cl){
		KlassScope sc=new KlassScope(this, cl);
		scopes.add(sc);
		classes.put(cl.name, cl);
		return sc;
	}
	
	public Scope newScope(Method m){
		MethodScope sc= new MethodScope(this,m);
		scopes.add(sc);
		methods.put(m.name, m);
		return sc;
	}
	
	public Method currentMethod(){ // not used
		if (this instanceof MethodScope)
			return ((MethodScope)this).method;
		if (parent!=null)
			return parent.currentMethod();
		return null;
	}
	
	public Klass currentKlass(){ //used for "this" -> Type mapping
		if (this instanceof KlassScope)
			return ((KlassScope)this).klass;
		if (parent!=null)
			return parent.currentKlass();
		return null;
	}

	public void addVariable(Variable v){		
	       vars.put(v.name, v);
	}
	
	public Variable lookupArgs(String name) {return null;}  //overide in MethodScope
	public Klass superKlass() {return null;}  //overide in ClassScope

	public Variable lookupVariable(String s){
		//local
		Variable v = vars.get(s);
		if(v != null) return v;
		// args of method
		v=lookupArgs(s);
		if(v != null) return v;
        // form extended class
		if (superKlass()!=null) v=superKlass().scope.lookupVariable(s);	
		if(v != null) return v;
        // from parent scope
		if(parent!=null)  v=parent.lookupVariable(s);
		if(v != null) return v;
		return null;
	}
	
	public Method   lookupMethod(String s) { 
		Method m = methods.get(s);
		if(m != null) return m;
		if (superKlass()!=null) m=superKlass().scope.lookupMethod(s);
		if(m != null) return m;
		if(parent!=null) m=parent.lookupMethod(s);
		if(m != null) return m;
		return null;
	}

	public Klass   lookupClass(String s) { 
		Klass c = classes.get(s);
		if(c != null) return c;
		if(parent!=null) c=parent.lookupClass(s);
		if(c != null) return c;
		return null;
	}
	
	public String toPrint(){
		return "Scope " +name;
	}
	
	public void print(String indent){
		System.out.println(indent + toPrint());
		indent=indent+"    ";
		for( Variable v: vars.values())		
			System.out.println(indent + v.toPrint() + ";");
		for( Scope sc: scopes) 
			sc.print(indent);
	}

}
