package semantic;

public class Semantic {
    public SemanticTree semanticTree;
    
    public Semantic(AST.ASTNode axiome) {
	boolean error=false;
        this.semanticTree = new SemanticTree(axiome);
        
        // pretty print 
        if (main.DEBUG.PRETTY) new PrettyPrint(axiome);
        
        // construct symbolTable,
        //...
        
        if (main.DEBUG.SYMTAB) {
            System.out.println("=== SYMBOL TABLE ===");
            semanticTree.rootScope.print("");
        }
        
        // Loop checking in Classes
        CheckLoop cl=new CheckLoop(semanticTree);
        if (main.DEBUG.KLASS) System.out.println(cl.Debug);
        error=error || cl.error;
	
        // Type checking
        // ...
        
        // decide for fatal errot
        if ( error )
            throw new main.CompilException("Semantic Error(s)");
    }
}
