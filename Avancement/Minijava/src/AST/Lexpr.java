package AST;
/** Lexpr == ASTList<Pexpr> */
public class Lexpr extends ASTList<Pexpr>{
	public void accept(ASTVisitor v) { v.visit(this); }
}
