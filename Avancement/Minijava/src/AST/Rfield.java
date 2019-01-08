package AST;
/** Rfield : Rtype t; RidVar id; */
public class  Rfield extends PklassMember{
	public Rtype t;
	public RidVar id;
  
	public Rfield(Rtype t, RidVar id) { 
		super(t,id); 
		this.t=t; this.id=id;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
