package AST;
/** LmethMember == ASTList<RmethMember> */
public class LmethMember extends ASTList<PmethMember>{
	public void accept(ASTVisitor v) { v.visit(this); }
}
