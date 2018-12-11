package AST;
/** Raxiome : RklassMain km; Lklass kl; */
public class Raxiome extends ASTNode {
	public RklassMain km;
	public Lklass kl;

	public Raxiome(RklassMain km, Lklass kl) { 
		super(km,kl); 
		this.km=km; this.kl=kl; 
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
