package AST;
import main.OPER;

/** REopBin : Pexpr e1; util.OPER op; Pexpr e2; */
public class REopBin extends Pexpr {
	public Pexpr e1;
	public OPER op;
	public Pexpr e2;
 
	public REopBin(Pexpr e1, OPER c, Pexpr e2) { 
		super(e1,e2);
		this.e1=e1; this.op=c; this.e2=e2; 
	}
	public void accept(ASTVisitor v) { v.visit(this); }
	@Override
	public String toString(){ return super.toString()+"(" +op.getname()+")"; }
}
