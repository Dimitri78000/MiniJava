package semantic;

import AST.*;

public class Ident extends ASTVisitorDefault {

    public Ident(ASTNode axiome) {
        System.out.println("=== Ident ===");
        axiome.accept(this); // => visite((Raxiome)axiome)
    }

    public void visit(Rfarg n) 		{
        System.out.println("Formalarg: "+n.id.s);
        defaultVisit(n);
    }


    /** Rvar : Rtype t; RidVar id; */
    public void visit(Rvar n) {
        System.out.println("local var: "+n.id.s);
        defaultVisit(n);
    }

    public void visit(Rfield n) 	{
        System.out.println("Class field: "+n.id.s);
        defaultVisit(n);
    }

    public void visit(RklassMain n) {
        System.out.println("Main: "+n.arg.s);
        defaultVisit(n);
    }

    public void visit(Rmethod n) 	{
        System.out.println("Method: "+n.id.s);
        defaultVisit(n);
    }





}
