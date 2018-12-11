package AST;
import main.OPER;

/** REopUn : util.OPER op; Pexpr e; */
public class REopUn extends Pexpr {
	public OPER op;
	public Pexpr e;

	public REopUn(OPER op, Pexpr e) { 
		super(e); 
		this.op=op; this.e=e; 
	}
	public void accept(ASTVisitor v) { v.visit(this); }
	@Override
	public String toString(){ return super.toString()+" (" +op+")"; }
}

