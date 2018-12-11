package AST;
/** RIif : Pexpr e; Pinst s1; Pinst s2; */
public class RIif extends Pinst {
	public Pexpr e;   // Test
	public Pinst s1; // if true
	public Pinst s2; // if flase
 
	public RIif(Pexpr e, Pinst s1, Pinst s2) {
		super(e,s1,s2); 
		this.e=e; this.s1=s1; this.s2=s2;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
