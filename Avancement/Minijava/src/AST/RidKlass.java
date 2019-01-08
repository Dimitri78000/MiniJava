package AST;
/** RidKlass : String s; */
public class RidKlass extends ASTNode{
	public String s;

	public RidKlass(String s) {  this.s=s;}
	public void accept(ASTVisitor v) { v.visit(this); }

	@Override
	public String toString(){ return super.toString()+s ; }
}
