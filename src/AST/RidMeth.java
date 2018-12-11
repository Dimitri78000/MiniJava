package AST;
/** RidMeth : String s; */
public class RidMeth extends ASTNode{
	public String s;

	public RidMeth(String s) {  this.s=s;}
	public void accept(ASTVisitor v) { v.visit(this); }

	@Override
	public String toString(){ return super.toString() + s ; }
}
