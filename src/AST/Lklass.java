package AST;
/** Lklass == ASTList<Rklass> */
public class Lklass extends ASTList<Rklass>{
 	public void accept(ASTVisitor v) { v.visit(this); }
}
