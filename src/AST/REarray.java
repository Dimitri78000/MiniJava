package AST;
/** REarray : Pexpr e1; Pexpr e2; */
public class REarray extends Pexpr {
	public Pexpr e1; // tableau
	public Pexpr e2; // index
  
	public REarray(Pexpr e1, Pexpr e2) { 
		super(e1,e2); 
		this.e1=e1; this.e2=e2;
	}
 	public void accept(ASTVisitor v) { v.visit(this); }
}
