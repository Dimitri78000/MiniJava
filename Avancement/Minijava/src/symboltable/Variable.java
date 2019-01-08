package symboltable;

public class Variable{
	public String name;
	public String type;
	
	/* - Used in Symbol Table
	 * - Reused in Intermediate Representation with Additionnal Conventional Type
	 *            "label" : Function name, Label for jump, TypeName for New
	 *            "constant" : litteral value used in Mips
	 *            "temporary" : variable temporaires de la forme intermediare
	 */
	
	public Variable(String name, String type){
		this.name = name;
		this.type = type;
	}
			
	public String toPrint() { // used for printing SymbolTable
		return type + " " + name;
	}
	
	public String toString() { // used for printing IR
		return name;
	}

}
