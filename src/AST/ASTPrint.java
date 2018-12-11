package AST;

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
/** "Extension" de la classe ASTNode pour differentes impressions d'arbre
 * Utilise ASTNode.*.toString() pour label des noeuds
 * Methode statique print(mode=0..11)
 * Main() pour test/demo
 * @author hennequi Mars 2017
 */
public class ASTPrint {

public static String print(ASTNode root,int mode){
	if (mode==0) return print0(root); // Prefixe (Lisp)
	if (mode==1) return print1(root); // Prefixe (fonction)
	if (mode==2) return print2(root); // Postfixe
	if (mode==3) return print3(root); // Infixe
	if (mode==4) return print4(root); // leaf_only
	if (mode==5) return print5(root); // Arbre vertical
	if (mode==6) return print6(root); // Arbre vertical + branches
	if (mode==7) return print7(root); // Arbre Horizontal par profondeur
	if (mode==8) return print8(root); // idem algo bis
	if (mode==9) return print9(root); // idem reverse Haut/bas
	if (mode==10) return print10(root); // Arbre Horizontal par hauteur
	if (mode==11) return print11(root); // idem revers Haut/Bas
	return "Mode Impression Inconnu";
}

private static String print0(ASTNode root) { // Prefixe, parenthese "lisp"
	if (root==null) return("");
	StringBuffer res =  new StringBuffer("");
	if (root.fils.size()==0) 
		res.append(root);
	else {
		res.append("(" + root);
		for (ASTNode n : root.fils)
			res.append(" "+print0(n));
		res.append(")");
	}
	return res.toString();
}

private static String print1(ASTNode root) { // Prefixe, parenthese "fonctionnelle"
	if (root==null) return("");
	StringBuffer res =  new StringBuffer("");
	if (root.fils.size()==0) 
		res.append(root);
	else {
		res.append(root + "(");
		boolean first=true;
		for (ASTNode n : root.fils){
			if (first) first = false;
			else       res.append(",");
			res.append(print1(n));
		}
		res.append(")");
	}
	return res.toString();
}

private static String print2(ASTNode root) { // Postfixe, parenthese "lisp"
	if (root==null) return("");
	StringBuffer res =  new StringBuffer("");
	if (root.fils.size()==0)
		res.append(root);
	else {
		res.append("(");
		for (ASTNode n : root.fils)
			res.append(" "+print2(n));
		res.append(" " + root + " )");
	}
	return res.toString();
}
	
private static String print3(ASTNode root) { // Infixe
	if (root==null) return("");
	StringBuffer res =  new StringBuffer("");
	if (root.fils.size()==0)
		res.append(root);	
	else {
		res.append("(");
		boolean first=true;
		for (ASTNode n : root.fils){
			res.append(print3(n));
			if (first) {first=false;  res.append(" " + root + " ");}
		}
		res.append(")");
	}
	return res.toString();
}

private static String print4(ASTNode root) { // leaf_only
	if (root==null) return("");
	StringBuffer res =  new StringBuffer("");
	if (root.fils.size()==0) 
		res.append(" " +root);
	for (ASTNode n : root.fils)
		res.append(print4(n));
	return res.toString();
}

// ---- Impressions Verticales
private static String print5(ASTNode root){ // vertical avec indentation"
	if (root==null) return("");
	return print5(root,"\n");
}

private static String print5(ASTNode root,String indent) {
	StringBuffer res =  new StringBuffer(indent);
	res.append(root);
	for (ASTNode n : root.fils)
		res.append(print5(n,indent + "   "));
	return res.toString();
}

private static String print6(ASTNode root){ // vertical avec indentation + branches
	if (root==null) return("");
	return print6(root,"\n",true);
}
private static String print6(ASTNode root,String indent, boolean last) {
	StringBuffer res =  new StringBuffer(indent);
	if (last) {res.append("\\--"); indent=indent+"   ";}
	else      {res.append("|--");  indent=indent+"|  ";}
	res.append(root);
	int nb=root.fils.size(); // detection last fils
	for (ASTNode n : root.fils)
		res.append(print6(n,indent,(nb--==1)));
// 	res.append(indent); // aeration verticale
	return res.toString();
}  

//---- Impressions Horizontales : 
// precalcul indentation, hauteur et profondeur des noeuds
// ensuite Parcours (+/- optimum) pour impression
static class HPrint {
	final int H_TAB=2;      // espacement entre noeud (en plus des labels) 
	final int H_INDENT=0;   // indentation initiale 	
    final int H_TRUNC=256;  // trunc line
    
	public boolean reverse; //inversion haut-bas de l'impression

	static class Position { int hpos=0; int profondeur=0; int hauteur=0;}
	private Map <ASTNode,Position> positions;
	private int hpos(ASTNode n){ return positions.get(n).hpos; }
	private int hauteur(ASTNode n){ return positions.get(n).hauteur; }
	private int profondeur(ASTNode n){ return positions.get(n).profondeur; }

    private int indent;         //variable "globale" pour recursion recursePosition()
	private StringBuffer [] sb; //impression temporaire d'un niveau sur 4 lignes

	HPrint(ASTNode root, boolean reverse) {
		this(root);
		this.reverse=reverse;
	}

	HPrint(ASTNode root) {
		this.reverse=false;
		this.indent=H_INDENT;
		positions = new HashMap<ASTNode,Position>(); 
		recursePosition(root,0);  // initialisation de la map positions	
		sb = new StringBuffer[4]; //initialisation buffer pour impression
		for(int i=0; i<4; i++) {  // troncation des lignes a etudier !
			sb[i] = new StringBuffer(H_TRUNC);	 
			for (int j=0; j<H_TRUNC; j++)
				sb[i].append(' ');
		}
	}

	private Position recursePosition(ASTNode root, int level){ // calcul de la map position
		Position res = new Position();			 
		res.profondeur=level;
		if (root.fils.size()==0) { // feuille
			int labelSize=root.toString().length();
			res.hauteur=0;
			res.hpos = indent+labelSize/2;
		indent = indent + H_TAB + labelSize;
		} else { // hpos = moyenne (fils) ,hauteur = 1+ max(fils)
			int moy=0;
			int h=0;
			for (ASTNode n : root.fils){
				Position tmp= recursePosition(n, level+1);
				moy = moy + tmp.hpos;
				h = Math.max(h,tmp.hauteur);
			}
			res.hauteur=h+1;
			res.hpos=moy/root.fils.size();
		}
		positions.put(root, res);
		return res;
	}
	
	private void pushPrint(StringBuffer res) {  // "concatene" SB dans res et reset SB avec memoire sb3
		// TBD: pour le dernier niveau de l'arbre, pushPrint met 3 lignes blanches inutiles 
		// ... un peu compliqué a eliminer simplement.. par exemple ici :
		// (si sb[3] contient que blanc faire uniquement res.insert(sb0)
		// autre choix faire un pushPrintLast , (pas utilisable avec print7b) ...
		 for(int i=0; i<4; i++)
			 if (reverse) res.insert(0,sb[i] + "\n"); 
			 else res.append(sb[i] + "\n");	
         // assert sb[].length==256 (trunc ok?)
		 for(int i=0; i<3; i++) // reset SB with sb[3]
			 sb[i].replace(0,H_TRUNC,sb[3].toString());	 
	}

	
	// Truncation
	private void putcar(char c, int line,int col){
		if (col < H_TRUNC)
			sb[line].setCharAt(col, c);
	}
	private void putstr (String str, int line, int start){
		if (start < H_TRUNC) {
			int stop=start + str.length();
			if (stop >=H_TRUNC) stop = H_TRUNC-1;
			sb[line].replace(start, stop, str);
		}
	}
	
	private void printNode(ASTNode root) { // impression ASCII pour un seul noeud
		 // sb0, "node"            N1         N1            or (memory)  |
	     // sb1, "veritical1"      |          |                         ...
	     // sb2, "horizontal"   -------   or  | (start==stop)           ...
	     // sb3, "vertical2"    |  |  |       |
		 int hp = hpos(root);
		 int first = hp-root.toString().length()/2;
		 if (first <0) first=0; // special case : leftmost label is not leftmost node
		                        // = noeud monofils avec label pere plus grand
		                        // TBD : trouver meilleur correctif
		 putstr(root.toString(),0,first);
		 for (int j=1; j<4; j++)  
			 putcar(' ',j,hp); // forget memory
		 if (root.fils.size()>0) {
			 putcar('|',1,hp);
			 int start=hpos(root.fils.get(0));
			 int stop = hpos(root.fils.get(root.fils.size()-1));
			 for(int i=start; i<=stop; i++)
				 putcar('-',2,i);
			 if (start==stop) 
				 putcar('|',2,start);
			 for(ASTNode n: root.fils)
				 putcar('|',3,hpos(n));
		 }
	 }	

	private String printProfondeurBis(ASTNode root){  // imoression verticale par profondeur (v2)
		 // parcours optimal (largeur iteratif avec queue), non utilisable pout vertical/hauteur	 
		 Queue<ASTNode> q = new LinkedList<ASTNode>();
		 StringBuffer res = new StringBuffer("\n");
		 q.add(root);
		 while(!q.isEmpty()){
			 for(int Nodes = q.size(); Nodes>0; Nodes --){
				 ASTNode node = q.remove();
				 printNode(node);
				 for (ASTNode n : node.fils) q.add(n);
			 }
			 pushPrint(res);
		 }
		 return res.toString();
	}	
	
	private String printProfondeur(ASTNode root){ // imoression verticale par profondeur
		 // parcours non optimal avec map "positions", reutilisable pour vertical/hauteur	 
		 StringBuffer res = new StringBuffer("\n");
		 for (int level=0; level<=hauteur(root); level++) {
			 for (Map.Entry<ASTNode, Position>entry : positions.entrySet()) {
				 ASTNode n=entry.getKey();
				 if (profondeur(n)==level) printNode(n);
			 }
			 pushPrint(res); 
		 }
		 return res.toString();
	}  
	
	String printHauteur(ASTNode root){         // impression horizontale. par hauteur
		StringBuffer res = new StringBuffer("\n");
		 for (int level=hauteur(root); level>=0; level--) {
			 for (Map.Entry<ASTNode, Position>entry : positions.entrySet()) {
				 ASTNode n=entry.getKey();
				 if (hauteur(n)==level) printNode(n);
			 }
			 pushPrint(res);
		 }
		return res.toString();
	}
	
} // End HPrint

private static String print7(ASTNode root){// impression horizontale Profondeur
	if (root==null) return("");
	return (new HPrint(root)).printProfondeur(root);
}
private static String print8(ASTNode root){// impression horizontale Profondeur (algo bis)
	if (root==null) return("");
	return (new HPrint(root)).printProfondeurBis(root);
}
private static String print9(ASTNode root){// print7 reverse haut bas
	if (root==null) return("");
	return (new HPrint(root,true)).printProfondeur(root);
}
private static String print10(ASTNode root){// impression horizontale. Hauteur
	if (root==null) return("");
	return (new HPrint(root,true)).printHauteur(root);
}
private static String print11(ASTNode root){// print9 reverse haut bas
	if (root==null) return("");
	return (new HPrint(root)).printHauteur(root);
}

// Main for testing 
private static void Test(ASTNode arbre) {
	System.out.println("print0, Prefixe (Lisp)        : "+print(arbre,0));
	System.out.println("print1, Prefixe (fonction)    : "+print(arbre,1));
	System.out.println("print2, Postfixe              : "+print(arbre,2));
	System.out.println("print3, Infixe                : "+print(arbre,3));
	System.out.println("print4, Leaf-only             : "+print(arbre,4));
	System.out.println("print5, Vertical              : "+print(arbre,5));
	System.out.println("print6, Vertical + branches   : "+print(arbre,6));
	System.out.println("print7, Horizontal profondeur : "+print(arbre,7));
	System.out.println("print8, print7 algov2         : "+print(arbre,8));
	System.out.println("print9,  print7 + reverse H/B : "+print(arbre,9));
	System.out.println("print10, Horizontal hauteur   : "+print(arbre,10));
	System.out.println("print11, print10 + reverse H/B: "+print(arbre,11));
}

private static AST N(String name, ASTNode ... fils) {
	 return new AST(name,fils);
}

public static void main(String[] args) {
		AST arbre = N("*",
				N("+", N("2"),N ("3")) ,
				N("*", N("4"),N("5"))  );
		Test(arbre);
		arbre=N("R*",N("R+",N("4"),N("+"), N("R*",N("222"),N("*"),N("3"))),N("*"),N("R*",N("2"),N("*"),N("1")));
		Test(arbre);
	}
}
