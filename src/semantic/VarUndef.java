package semantic;

import AST.*;
import symboltable.*;

public class VarUndef extends ASTVisitorDefault {
    public boolean error;
    private SemanticTree semanticTree;
    
    public VarUndef(SemanticTree semanticTree){
        this.error = false;
        this.semanticTree=semanticTree;
        semanticTree.axiome.accept(this);
    }
        
    //Helper function to report Redefinition Errors
    private void undefError(ASTNode where, String name){
        System.err.println(where + " Use of undefined identifier " + name);
        error = true;
    }

    private Scope getScope(ASTNode n) { return semanticTree.getScope(n); }
    private String getType(ASTNode n) { return semanticTree.getType(n); }

/////////////////// Visit ////////////////////
    /** RidKlass : String s; */
    public void visit(RidKlass n) {
        if (getScope(n).lookupClass(n.s)==null)
            undefError(n,"Class " + n.s);
    }
        
    /** RidVar : String s; */
    public void visit(RidVar n) {
        if(getScope(n).lookupVariable(n.s) == null)
            undefError(n,"Var " + n.s);
    }
        
    /** REcall : Pexpr e; RidMeth id; Lexpr el; */
    // asume type checking before
    public void visit(REcall n) {
        defaultVisit(n);
        String klassName=getType(n.e);
        Klass kl=getScope(n).lookupClass(klassName);
        if (kl==null)
            undefError(n,"Class(dup!) "+klassName); // duplicated message (always ?)
        else 
            if ( kl.scope.lookupMethod(n.id.s) == null )
                undefError(n,"Method " + klassName + "." + n.id.s);
    }
}
