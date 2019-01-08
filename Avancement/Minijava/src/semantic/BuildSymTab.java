package semantic;
import java.util.HashSet;
import symboltable.*;
import AST.*;

public class BuildSymTab extends ASTVisitorDefault {
    public boolean error;
    private SemanticTree semanticTree;
    private Scope currentScope;

    /** Construct the symbol Table and generate herited Attributes (Scope, Method, Klass ) in semantic Tree
     * @param semanticTree
     * <br>IN : AST
     * <br>OUT : generated SymbolTable and top Down Attributs
     */
    public BuildSymTab(SemanticTree semanticTree){
        this.error = false;
        this.semanticTree = semanticTree;
        this.currentScope = semanticTree.rootScope; // assume empty symbolTable

        semanticTree.axiome.accept(this);
        addObjectClass();
    }

    public void addObjectClass(){ // Add an Object class. not usefull ?
        Scope sc;
        sc = currentScope.newScope(new Klass("Object",null));
        sc.newScope(new Method("_system_out_println","void",true, new Variable("i","int")));
        sc.newScope(new Method("_system_exit","void",true,new Variable("i","int")));
    }

    private void setAttributs(ASTNode n){
        semanticTree.setScope(n, currentScope);
    }
    private void resetAttributs(ASTNode n){
        currentScope=semanticTree.getScope(n);
    }

    // overide defaultvisit to generate top Down Attributs
    @Override
    public  void defaultVisit(ASTNode n){
        setAttributs(n);
        for ( ASTNode f : n.fils) f.accept(this);
        resetAttributs(n); // just in case
    };

    //Error "allready defined" (only on variable...!)
    private void redefError(ASTNode where,String name) {
        System.err.println(where + " Multiply defined identifier " + name );
        error = true;
    }

    ////////////// Visit ////////////////////////
    /** RklassMain : RidKlass id; RidVar arg; Pinst s; */
    public void visit(RklassMain n) {
        setAttributs(n);
        n.id.accept(this);
        currentScope = currentScope.newScope(new Klass(n.id.s,"Object"));

        Method m=new Method("main", "void",true, new Variable(n.arg.s,"String[]"));
        currentScope = currentScope.newScope(m);
        n.arg.accept(this);
        n.s.accept(this);
        resetAttributs(n);
    }

    /** Rfield : Rtype t; RidVar id; */
    public void visit(Rfield n) {
        defaultVisit(n);
        if(currentScope.vars.get(n.id.s) != null) redefError(n,n.id.s);
        currentScope.addVariable(new Variable(n.id.s, n.t.s));
    }

    /** Rvar : Rtype t; RidVar id; */
    public void visit(Rvar n) {
        defaultVisit(n);
        if(currentScope.vars.get(n.id.s) != null) redefError(n,n.id.s);
        currentScope.addVariable(new Variable(n.id.s, n.t.s));
    }

    /** RIblock : LmethMember mml; */
    public void visit(RIblock n) {
        setAttributs(n);
        currentScope = currentScope.newScope();
        n.mml.accept(this);
        resetAttributs(n);
    }

    /** Rklass : RidKlass id; RidKlass pid; LklassMember kml;*/
    public void visit(Rklass n) {
        setAttributs(n);
        n.id.accept(this);
        n.pid.accept(this);
        currentScope = currentScope.newScope(new Klass(n.id.s, n.pid.s));
        n.kml.accept(this);
        resetAttributs(n);
    }

    /** Rmethod : Rtype t; RidMeth id; Lfarg fl; LmethMember mml; Pexpr e; */
    public void visit(Rmethod n) {
        setAttributs(n);
        n.t.accept(this);
        n.id.accept(this);

        // Construct args for new Method(...)
        int flsize=n.fl.fils.size();
        Variable[] args=new Variable[flsize+1];
        int i=0;
        args[i++]=new Variable("this",currentScope.currentKlass().name);
        for (ASTNode f : n.fl.fils) {
            Rfarg ff=(Rfarg)f;
            args[i++]=new Variable(ff.id.s,ff.t.s);
        }

        //Check for redef in method arguments
        HashSet<String> methodArgs = new HashSet<String>();
        for (ASTNode f : n.fl.fils) {
            Rfarg ff=(Rfarg)f;
            if(!methodArgs.add(ff.id.s)) redefError(n,ff.id.s);
        }

        Method m = new Method(n.id.s, n.t.s,false,args);
        currentScope = currentScope.newScope(m);
        n.fl.accept(this);
        n.mml.accept(this);
        n.e.accept(this);
        resetAttributs(n);
    }
}
