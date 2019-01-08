package AST;
import main.TYPE;
/** Rtype  : String s; NB: constructeur additionel Rtype(main.TYPE t) */
public class Rtype extends ASTNode {
	public String s;

	public Rtype(String s)	{ this.s=s; }      	// referenced type
	public Rtype(TYPE t) 	{ this.s=t.getname(); } // primitive types
	
	public void accept(ASTVisitor v) { v.visit(this); }

	@Override
	public String toString(){ return super.toString() + s ; }
}
