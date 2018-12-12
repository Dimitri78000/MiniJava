package semantic;
import AST.*;

public class Block extends ASTVisitorDefault {
    public Block(ASTNode axiome) {
        System.out.println("=== Block ===");
        axiome.accept(this);
        System.out.println();
    }
   
/////////////////// Visit ////////////////////
    public void visit(RIblock n)  {
        System.out.print("{");
        defaultVisit(n);
        System.out.print("}");
    }
    public void visit(Rklass n)   {
        System.out.print(n.id.s +"{");
        defaultVisit(n);
        System.out.print("}");
    }
    public void visit(Rmethod n)   {
        System.out.print(n.id.s +"{");
        defaultVisit(n);
        System.out.print("}");
    }
    public void visit(RklassMain n) {
        System.out.print(n.id.s +"{");
        System.out.print("main{");
        defaultVisit(n);
        System.out.print("}}");
    }
}
