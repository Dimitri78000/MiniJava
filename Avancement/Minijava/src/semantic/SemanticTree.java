package semantic;

import AST.ASTNode;
import symboltable.*;

/** <b>Arbre Semantique</b> == AST + Attributs de Noeuds + Table de Symboles
 * <p> <b>Attributs Descendants (heriteś) :</b>
 * <br> <b>Scope</b> : reference dans la table de symboles
 * <p> <b>Attributs Montants : /b>
 * <br> <b>Type</b> : Data Type
 * @author Pascal Hennequin (Jan 2018)
 */
public class SemanticTree {
	final public ASTNode axiome; // AST
	public Scope rootScope;      // Racine de la table de symbol
	// top-dowm attributes
	private SemanticAttrbut<Scope>  nodeScope;  // Scope courrant du noeud
	// bottom-up attribures
	private SemanticAttrbut<String> nodeType;   // Type de Données du noeud

	public SemanticTree(ASTNode axiome) {
		this.axiome=axiome;
		this.rootScope = new Scope(null,"root");
		this.nodeScope = new SemanticAttrbut<Scope>();
		this.nodeType = new SemanticAttrbut<String>();
	}
	
	public Scope getScope(ASTNode n){ return nodeScope.getAttr(n); }
	public Scope setScope(ASTNode n, Scope s){ return nodeScope.setAttr(n,s);}

	public String getType(ASTNode n){ return nodeType.getAttr(n); }
	public String setType(ASTNode n, String s){ return nodeType.setAttr(n,s);}
	
}
