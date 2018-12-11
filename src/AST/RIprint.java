package AST;
/** RIprint : Pexpr e; */
public class RIprint extends Pinst {
	public Pexpr e;

	public RIprint(Pexpr e) { 
		super(e); 
		this.e=e;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
