package AST;
/** LklassMember == ASTList<RklassMember> */
public class LklassMember extends ASTList<PklassMember>{
	public void accept(ASTVisitor v) { v.visit(this); }
}
