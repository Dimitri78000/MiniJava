package AST;
/** Sous classe minimale de Node pour AST "non typée" : String label*/
public class AST extends ASTNode{
	public String label;
	
	public AST(String label, ASTNode... fils){
		super(fils);
		this.label=label;
	}
	public void accept(ASTVisitor v) {}
	
	public String toString() {
		return label;
	}
}
