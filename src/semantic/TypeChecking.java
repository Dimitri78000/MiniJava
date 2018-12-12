package semantic;

import main.TYPE;
import AST.*;
import symboltable.*;

public class TypeChecking extends ASTVisitorDefault {
    /* 
     Calcule l'attribut synthétisé Type :
       - requis pour tout les noeuds Pexp (RE*)
       - pratique pour RidVar et RidClass
       - pas possible pour RidMethod : 
     Verifie les contraintes de Typage de minijava 
       - a peu pres dans tout les noeuds 
    */
    
    public boolean error;
    private SemanticTree semanticTree;
    
    public TypeChecking(SemanticTree semanticTree) {
	this.error = false;
	this.semanticTree=semanticTree;
	semanticTree.axiome.accept(this);
    }
	
    ////// Helpers
    // set and get Type Attribut
    private String getType(ASTNode n) { return semanticTree.getType(n); }
    private String setType(ASTNode n, String type) { return semanticTree.setType(n,type);}
    // get Scope Attribut
    private Scope getScope(ASTNode n) { return semanticTree.getScope(n); }

    // Primitive Type names in Minijava
    private final String BOOL = main.TYPE.BOOL.getname();
    private final String INT = main.TYPE.INT.getname();
    private final String INT_ARRAY = main.TYPE.INT_ARRAY.getname();

    private final String VOID = main.TYPE.UNDEF.getname();
    
    // Compare type : returns true if t2 is subtype of t1
    private boolean compareType(String t1, String t2) {
	if (t2==null) return false;
	if (t2.equals(t1)) return true;
	
	// NB : assume no loop in classes !!
	Klass c2=semanticTree.rootScope.lookupClass(t2);
	if (c2!=null) // recusrse pour heritage
	    return compareType(t1,c2.parentName);
	return false;
    }

    // repport error
    private void erreur(ASTNode where,String msg){
	System.err.println(where + " " + msg ); 
	error=true;
    }
    // Validation : "t2 subtype of t1"
    private void checkType(String t1, String t2, String msg, ASTNode where) {
	if ( ! compareType(t1,t2))		
	    erreur(where,"Wrong Type : " + t2 + "->" + t1 + ";  " + msg);
    }
    // Validation : " Type(node) subtype of t1"
    private void checkType(String t1, ASTNode n, String msg, ASTNode where) {
	checkType(t1,getType(n),msg,where);
    }

    /////////////////// Visit ////////////////////
    /** RidKlass : String s; */
    public void visit(RidKlass n) {
	setType(n,n.s);	   // Type = ClassName 
    }
    
    /** RidVar : String s; */
    public void visit(RidVar n) {
	Variable v = getScope(n).lookupVariable(n.s);
	if (v==null)	setType(n,VOID);	
	else		setType(n,v.type);
    }
    
    /** Rmethod : Rtype t; RidMeth id; Lfarg fl; LmethMember mml; Pexpr e; */
    public void visit(Rmethod n) {	
	defaultVisit(n);
	checkType(n.t.s, n.e, "returned Value", n);
    }
    
    /// "Expressions" = Pexpr = RE*
    /** REbool : Boolean b; */
    public void visit(REbool n)   {
	setType(n, BOOL);
    }
    
    /** REcall : Pexpr e; RidMeth id; Lexpr el; */
    // Compliqué!!
    public void visit(REcall n) {   
	n.e.accept(this);
	Klass cl = getScope(n).lookupClass(getType(n.e));
	if (cl==null) {
	    erreur(n,"Attempt to call a non-method (unknown class)");
	    setType(n, VOID);
	    return;
	}
	
	n.id.accept(this);
	String methName = n.id.s;
	Method m = cl.scope.lookupMethod(methName);
	if (m==null){	  
	    erreur(n, "Attempt to call a non-method (unknown method)");
	    setType(n, VOID);
	    return;
	}
		
	if (m.args.length!=n.el.fils.size()+1){
	    erreur(n,"Call of method " + methName + " does not match the number of args");
	    setType(n, VOID);
	    return;
	}
		
	int i=1;
	for ( ASTNode f : n.el.fils) {
	    f.accept(this);
	    checkType(m.args[i].type,getType(f),
		      "Call of method does not match the signature :" +m.uname ,n);
	    i++;
	}
	setType(n, m.returnType);
    }
    
    /** REint : Integer i; */
    public void visit(REint n) {
	setType(n, INT);
    }
    
    /** REnew : RidKlass id; */
    public void visit(REnew n) {
	defaultVisit(n);
	setType(n, getType(n.id));
    }
    
    /** REopBin : Pexpr e1; util.OPER op; Pexpr e2; */
    public void visit(REopBin n)  {
	defaultVisit(n);
	String msg = "Invalid Type for operator " + n.op;
	switch (n.op) {
	case PLUS:
	case MINUS:
	case TIMES:
	    checkType(INT,n.e1,msg+" (letf)",n);
	    checkType(INT,n.e2,msg+" (right)",n);
	    setType(n, INT);
			break;
	case AND:
	    checkType(BOOL,n.e1,msg+" (letf)",n);
	    checkType(BOOL,n.e2,msg+" (right)",n);
	    setType(n, BOOL);
	    break;
	case LESS:
	    checkType(INT,n.e1,msg+" (letf)",n);
	    checkType(INT,n.e2,msg+" (right)",n);
	    setType(n, BOOL);
	    break;
	default:
	    setType(n, VOID);
	    break;
	}
    }
    
    /** REopUn : util.OPER op; Pexpr e; */
    public void visit(REopUn n) {
	defaultVisit(n);
	String msg = "Invalid Type for operator " + n.op;
	switch(n.op){
	case NOT:
	    checkType(BOOL,n.e, msg,n);
	    setType(n,BOOL);
	    break;
	default: // never
	    setType(n,VOID);
	    break;
	}
    }
    
    /** REvar : RidVar id; */
    public void visit(REvar n) {
	defaultVisit(n);
	setType(n, getType(n.id));
    }
    
    /// "Instructions" == Pinst =  RI* 
    /** RIassign : RidVar id; Pexpr e; */
    public void visit(RIassign n) {
	defaultVisit(n);
	checkType(getType(n.id),n.e,"Type mismatch in assignment",n);
    }
    
    /** RIif : Pexpr e; Pinst s1; Pinst s2; */
    public void visit(RIif n) {
	defaultVisit(n);
	checkType(BOOL,n.e,"non boolean as condition of if",n);	
    }
    
    /** RIorint : Pexpr e; */
    public void visit(RIprint n) {
	defaultVisit(n);
	checkType(INT,n.e,"non integer for printing",n);	    	
    }
    
    /** RIwhile : Pexpr e; Pinst s; */
    public void visit(RIwhile n) {
	defaultVisit(n);
	checkType(BOOL,n.e,"non boolean as condition of while",n);	    	
    }
}

