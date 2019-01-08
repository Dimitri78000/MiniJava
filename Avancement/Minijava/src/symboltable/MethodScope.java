package symboltable;

import java.util.Map;
import java.util.HashMap;

public class MethodScope extends Scope  {
	public Method method;
	private Map<String, Variable> args; // params are additionnal local var

	public MethodScope(Scope parent, Method m) {
		super(parent,m.name);
		this.method=m;
		method.scope=this;
		args = new HashMap<String, Variable>();
		for(int i = 0; i < m.args.length; i++)
			args.put(m.args[i].name, m.args[i]);
	}
	
	public Variable lookupArgs(String name) {
		return args.get(name);
	}
	
	public String toPrint(){
		String temp = "(" + method.returnType + ") " + method.name + "(";
		for(int j = 0; j < method.args.length; j++) {
			temp = temp + method.args[j].toPrint();
			if (j!=method.args.length-1) temp = temp +", ";
			else temp = temp + ")";
		}
		return temp;	
	}
	
}
