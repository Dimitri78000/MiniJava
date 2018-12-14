package generateIR;

import AST.*;
import IR.*;
import symboltable.*;

public class GenerateIR extends ASTVisitorDefault { 
    public Intermediate intermed;   
    private semantic.SemanticTree semanticTree; // only for lookup in symbolTable
    private semantic.SemanticAttrbut<Variable> nodeVar;  // nom Variables dans IR       
    
    public GenerateIR(semantic.SemanticTree semanticTree){
        this.intermed=new Intermediate(semanticTree);
        this.semanticTree = semanticTree;
        this.nodeVar=new semantic.SemanticAttrbut<Variable>();
        
        semanticTree.axiome.accept(this); //=> visite((Raxiome)axiome) 
        
        if ( main.DEBUG.INTERMED ) {
            System.out.println("=== INTERMEDIATE REPRESENTATION ===");
            intermed.program.print(); 
        }
    }
    
// Helpers
    // Ajouter une instruction au programmeIR
    private void add(IRQuadruple irq)  { intermed.program.add(irq); }
    // Attribut synthetisee nodeVar = Variable IR Temp pour resultat des expression
    private Variable getVar(ASTNode n) { return nodeVar.getAttr(n); }
    private Variable setVar(ASTNode n, Variable var) { return nodeVar.setAttr(n, var);}
    // Variables IR : label tempo, label nom, Constante, Temp var
    private Variable newLabel()            { return intermed.newLabel(); }
    private Variable newLabel(String name) { return intermed.newLabel(name); }
    private Variable newConst(String name) { return intermed.newConst(name); }
    private Variable newTemp(ASTNode n)    { return intermed.newTemp(n); }   
    // Variable de l'AST depuis la table de symbole
    private Variable lookupVar(ASTNode n, String name){
        return semanticTree.getScope(n).lookupVariable(name); 
    }
 
/////////////////// Visit ////////////////////
    /** RklassMain : RidKlass id; RidVar arg; Pinst s; */
    public void visit(RklassMain n) {
       add(new QLabel(newLabel("main")));               
        defaultVisit(n);
        add(new QCall(  newLabel("_system_exit"),
                        newConst("0"),
                        null));
    }
}
