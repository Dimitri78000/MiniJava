package AST;
/** RElen: Pexpr e; */
public class RElen extends Pexpr {
	public Pexpr e;  
   
	public RElen(Pexpr e) {
		super(e); 
		this.e=e; 
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
