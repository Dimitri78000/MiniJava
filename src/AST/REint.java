package AST;
/** REint : Integer i; */
public class REint extends Pexpr {
	public Integer i;
 
	public REint(Integer i) { this.i=i; }
	public void accept(ASTVisitor v) { v.visit(this); }
	@Override
	public String toString(){ return super.toString()+i; }
}
