package AST;
/** Lfarg == ASTList<Rfarg> */
public class Lfarg extends ASTList<Rfarg>{
	public void accept(ASTVisitor v) { v.visit(this); }
}
