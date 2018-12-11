package symboltable;

public class Method {
	public String name;
	public String returnType;
	public boolean isStatic;
	public Variable[] args;
	public String uname;  // uniq name for polymorphisme ?!?
	public MethodScope scope;

	public Method(String name, 
				String returnType,
				boolean istatic,
				Variable ... fargs ){
	this.name = name;
	this.returnType = returnType;
	this.isStatic=istatic;
	this.args= fargs;
	this.uname="__"+name;
	for(Variable v : fargs){
		this.uname=uname+"_"+v.type;
	}	
	this.scope=null;
}	
}
