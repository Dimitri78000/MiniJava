package AST;
/** RIassignArray : RidVar id; Pexpr e1; Pexpr e2; */
public class RIassignArray extends Pinst {
	public RidVar id;     // variable tableau
	public Pexpr e1;      // index
	public Pexpr e2;      // valeur

	public RIassignArray(RidVar id, Pexpr e1, Pexpr e2) { 
		super(id,e1,e2); 
		this.id=id; this.e1=e1; this.e2=e2;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
