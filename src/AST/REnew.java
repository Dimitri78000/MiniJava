package AST;
/** REnew : RidKlass id; */
public class REnew extends Pexpr {
	public RidKlass id;
  
	public REnew(RidKlass id) { 
		super(id); 
		this.id=id;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
