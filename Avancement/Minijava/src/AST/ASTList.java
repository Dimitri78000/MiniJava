package AST;

/** ASTNode avec fils homogenes :
 * <li> Construction itérative avec constructeur par defaut et methode AddFils()
 * <li> Abstract => must implements accept() for visitor
 */
public abstract class ASTList<R extends ASTNode> extends ASTNode{
	
	public void addFils(R n) { fils.add(n); }

	// not used (only if right recursive production)
	public void addFilsFirst(R n) { fils.add(0, n); }
	
	// no helpers for iterate, use foreach !
	
    // varargs constructor , not used , not safe
	@SafeVarargs
	public ASTList(R... fils) { super(fils); }
}
