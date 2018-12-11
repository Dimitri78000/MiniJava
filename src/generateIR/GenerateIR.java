package generateIR;

import AST.*;
import IR.*;
import symboltable.*;
import semantic.SemanticAttrbut;
import semantic.SemanticTree;

public class GenerateIR extends ASTVisitorDefault { 
    public Intermediate intermed;	
    private SemanticAttrbut<Variable> nodeVar;  // nom Variables dans IR	
    
    public GenerateIR(SemanticTree semanticTree){
	this.intermed=new Intermediate(semanticTree.rootScope);
	this.nodeVar=new  SemanticAttrbut<Variable>();
	semanticTree.axiome.accept(this); //=> visite((Raxiome)axiome) 
	if ( main.DEBUG.INTERMED ) {
	    System.out.println("=== INTERMEDIATE REPRESENTATION ===");
	    intermed.program.print(); 
	}
    }
    
    // helpers
    private Variable newLabel(String name) {return new Variable(name,"label");}
    private Variable newConst(String name) { return new Variable(name, "constant"); }
    private Variable getVar(ASTNode n) { return nodeVar.getAttr(n); }
    private Variable setVar(ASTNode n, Variable var) { return nodeVar.setAttr(n, var);}	
    private void add(IRQuadruple irq) { intermed.program.add(irq); }
    
    /////////////////// Visit ////////////////////
    public void visit(RklassMain n) {
        add(new QLabel(newLabel("main")));		
    	defaultVisit(n);
	add(new QCall(	newLabel("_system_exit"),
			newConst("0"),
			null));
    }
    public void visit(REint n) {
	setVar(n, newConst(n.i.toString()));
    }
    public void visit(RIprint n) {
    	defaultVisit(n);	   	
	add(new QParam(getVar(n.e)));
	add(new QCall(newLabel ("_system_out_println"),
		      newConst("1"),
		      null));
    }
}
