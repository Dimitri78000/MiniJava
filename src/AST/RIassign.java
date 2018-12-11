package AST;
/** RIassign : RidVar id; Pexpr e; */
public class RIassign extends Pinst {
	public RidVar id; // variable
	public Pexpr e;   // valeur

	public RIassign(RidVar id, Pexpr e) {
		super(id,e); 
		this.id=id; this.e=e;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
