package semantic;

import AST.*;

public class Block extends ASTVisitorDefault {
    public Block(ASTNode axiome) {
        System.out.println("=== Portee ===");
        axiome.accept(this); // => visite((Raxiome)axiome)
        System.out.println();
    }

    public void altVisit(ASTNode n){
        System.out.print("{");
        defaultVisit(n);
        System.out.print("}");
    }

    public void visit(RIblock n){altVisit(n);}

    public void visit(Rklass n){altVisit(n);}

    public void visit(Rmethod n){altVisit(n);}

    public void visit(RklassMain n)
    {
        System.out.print(n.id.s+"{");
        System.out.print("main");
        altVisit(n);
        System.out.print("}");
    }
}
