package AST;
/** RidVar : String s; */
public class RidVar extends ASTNode{
	public String s;

	public RidVar(String s) {  this.s=s;}
	public void accept(ASTVisitor v) { v.visit(this); }

	@Override
	public String toString(){ return s ; }
}
