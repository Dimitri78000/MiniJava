package AST;
/** Rmethod : Rtype t; RidMeth id; Lfarg fl; LmethMember mml; Pexpr e; */
public class Rmethod extends PklassMember {
	public Rtype t;  // type retour
	public RidMeth id;
	public Lfarg fl;
	public LmethMember mml;
	public Pexpr e;    // valeur retour

	public Rmethod(Rtype t, RidMeth id, Lfarg fl, LmethMember mml, Pexpr e) {
	        super(t,id,fl,mml,e);
		this.t=t; this.id=id; this.fl=fl; this.mml=mml; this.e=e;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
