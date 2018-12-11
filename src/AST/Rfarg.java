package AST;
/** Rfarg : Rtype t; RidVar id; */
public class Rfarg extends ASTNode{
	public Rtype t;
	public RidVar id;
 
	public Rfarg(Rtype t, RidVar id) { 
		super(t,id); 
		this.t=t; this.id=id;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
