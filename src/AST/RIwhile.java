package AST;
/** RIwhile : Pexpr e; Pinst s; */
public class RIwhile extends Pinst {
	public Pexpr e;
	public Pinst s;

	public RIwhile(Pexpr e, Pinst s) { 
		super(e,s); 
		this.e=e; this.s=s;
	} 
	public void accept(ASTVisitor v) { v.visit(this); }
}

