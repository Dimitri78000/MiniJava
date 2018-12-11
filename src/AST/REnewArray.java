package AST;
/** REnewArray : Pexpr e; */
public class REnewArray extends Pexpr {
	public Pexpr e;

	public REnewArray(Pexpr e) { 
		super(e); 
		this.e=e; 
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
