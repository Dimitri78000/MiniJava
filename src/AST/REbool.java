package AST;
/** REbool : Boolean b; */
public class REbool extends Pexpr {
	public Boolean b;

	public REbool(Boolean b) { this.b=b; }
	public void accept(ASTVisitor v) { v.visit(this); }

	@Override
	public String toString(){ return super.toString() + b; }
}
