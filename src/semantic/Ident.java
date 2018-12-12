package semantic;

import AST.*;

public class Ident extends ASTVisitorDefault {

    public Ident(ASTNode axiome) {
		System.out.println("=== IDENT ===");
		axiome.accept(this); // => visite((Raxiome)axiome)
		System.out.println("");
	}
	private void print(String s)   { System.out.print(s); }
	private void println(String s) { print(s + "\n"); }
	
	public void visit(Rvar n) {
		println(n.id.s);
		defaultVisit(n);
	}
	
	public void visit(Rfield n) {
		println(n.id.s);
		defaultVisit(n);
	}
	
	public void visit(Rfarg n) {
		println(n.id.s);
		defaultVisit(n);
	}
	
    /** RklassMain : RidKlass id; RidVar arg; Pinst s; */
    public void visit(RklassMain n) {
        System.out.println("Formalarg: " +n.arg.s );
        defaultVisit(n);
    }
}