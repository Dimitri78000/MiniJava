package AST;

import java.util.List;
import java.util.ArrayList;
import main.DEBUG;
import java_cup.runtime.ComplexSymbolFactory.Location;

/** Arbre de Syntaxe Abstraite 
 * <p> Classe abstraite mere :
 * <li>  <b>String name</b> : nom de la sousclasse (autogénéré)
 * <li> <b>ArrayList&lt;Node&gt; fils</b> : les fils
 * <li> <b>ASTNode( ASTNode ... fils)</b> : Constructeur varargs
 * <li> <b>String toString()</b> : overide in subclasses
 * <li> Gestion possible des positions dans fichier source avec methode <b>addPos(Location left, Location right)</b>
 * @author Pascal Hennequin (Mars 2017)
 */
//revoir contrainte de ASTPrint..
// revoir si besoin visitable pour ASTNode...
public abstract class ASTNode {	
	public abstract void accept(ASTVisitor v);    

	private String name; // constructed as classname for subclasses
	public List<ASTNode> fils;

	/** Constructeur avec VarArgs
	 * @param fils liste quelconque de fils
	 */	
	public ASTNode( ASTNode ... fils ){
		this.name=getClass().getSimpleName();
		this.fils = new ArrayList<ASTNode>();
		for (ASTNode f : fils)	this.fils.add(f);
	}
		
	// Impression AST, cf ASTPrint
	public void print (int mode){
		System.out.println("=== TREE PRINT ===");
		System.out.println(ASTPrint.print(this,mode));;
	}
		
	public String toString() { // used by ASTPrint
		if (DEBUG.TREELOCATION) return toStrLoc();
		else return name;
	}
	
	// Gestion  Locations des symbols dans le source.
	private Location start=null;
	private Location stop=null;

	public void addPos(Location left, Location right){ // to be used in CUP spec.
		this.start=left;  this.stop=right;
	}
	
	private static String locStr(Location loc){
		return loc.getLine()+"/"+loc.getColumn()+"/"+loc.getOffset();
		// default (loc.toString()) == getUnit()+":"+getLine()+"/"+getColumn()+"("+offset+")";
	}
	
	public String toStrLoc() {
		String res = name + "[";
		if (start!=null) res += locStr(start);
		if (stop!=null) res += "-" +locStr(stop);
		return res + "]";
	}
}
