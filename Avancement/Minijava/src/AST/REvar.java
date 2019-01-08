package AST;
/** REvar : RidVar id; */
public class REvar extends Pexpr {
	public RidVar id;

	public REvar(RidVar id)  { 
		super(id); 
		this.id=id;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
