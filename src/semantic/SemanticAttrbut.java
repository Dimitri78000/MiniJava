package semantic;

import java.util.Map;
import java.util.HashMap;
import AST.ASTNode;

/** Attribut de Noeuds de l'AST . 
 * <br>Utilise un HashMap pour eviter de modifier l'AST existant.
 * <p> Usefull  to :
 * <li>manage return value with (void) visitor (bottom-up Attributs)
 * <li>avoid paramaters in visitor (top-down Attributs)
 * <li>reuse Attributs between several visits (avoiding unconsistancy)
 */
public class SemanticAttrbut<R>{
	Map<ASTNode,R> attribut;
	
	public SemanticAttrbut () { this.attribut = new HashMap<ASTNode,R>(); }
	
	public R getAttr(ASTNode n) { return attribut.get(n); }
	public R setAttr(ASTNode n, R attr) { return attribut.put(n, attr); }

}
