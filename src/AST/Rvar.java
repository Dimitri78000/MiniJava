package AST;
/** Rvar : Rtype t; RidVar id; */
public class Rvar extends PmethMember{
	public Rtype t;
	public RidVar id;
  
	public Rvar(Rtype t, RidVar id) { 
		super(t,id); 
		this.t=t; this.id=id;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
