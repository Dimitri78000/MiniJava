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

    public void visit(Rmethod n){
        add(new QLabelMeth(newLabel(n.id.s)));
        defaultVisit(n);
        add(new QReturn (getVar(n.e)));
    }

    public void visit(RidVar n){
        setVar(n, lookupVar(n, n.s));
    }

    public void visit(REint n){
        setVar(n,newConst(n.i.toString()));
    }

    public void visit(REbool n){
        if(n.b){
            setVar(n,newConst("1"));
        }else {
            setVar(n, newConst("0"));
        }
    }

    public void visit(REcall n){
        defaultVisit(n);
        add(new QParam(getVar(n.e)));
        int nb_arg=1;
        for(ASTNode f: n.el.fils){
            add(new QParam((getVar(f))));
            nb_arg++;
        }
        setVar(n, newTemp(n));
        add(new QCall(newLabel(n.id.s), newConst(Integer.toString(nb_arg)), getVar(n)));
    }

    public void visit(REnew n){
        defaultVisit(n);
        setVar(n, newTemp(n));
        add(new QNew(newLabel(n.id.s), getVar(n)));
    }

    public void visit(REopBin n){
        defaultVisit(n);
        setVar(n, newTemp(n));
        add(new QAssign(n.op, getVar(n.e1), getVar(n.e2), getVar(n)));
    }

    public void visit(REopUn n){
        defaultVisit(n);
        setVar(n, newTemp(n));
        add(new QAssignUnary(n.op, getVar(n.e), getVar(n)));
    }

    public void visit(REvar n){
        defaultVisit(n);
        setVar(n, getVar(n.id));
    }

    /// "Instructions" =  RI*
    /** RIassign : RidVar id; Pexpr e; */
    public void visit(RIassign n) {
        defaultVisit(n);
        add(new QCopy(getVar(n.e), getVar(n.id)));
        setVar(n, getVar(n.id));
    }

    /** RIif : Pexpr e; Pinst s1; Pinst s2; */
    public void visit(RIif n) {
        Variable L1 = newLabel();
        Variable L2 = newLabel();
        n.e.accept(this);
        add(new QJumpCond(L1,getVar(n.e)));
        n.s1.accept(this);  //true
        add(new QJump(L2));
        add(new QLabel(L1));
        n.s2.accept(this); // false
        add(new QLabel(L2));
    }

    /** RIorint : Pexpr e; */
    public void visit(RIprint n) {
        defaultVisit(n);
        add(new QParam(getVar(n.e)));
        add(new QCall(newLabel ("_system_out_println"),
                newConst("1"),
                null));
    }

    /** RIwhile : Pexpr e; Pinst s; */
    public void visit(RIwhile n) {
        Variable L1 = newLabel();
        Variable L2 = newLabel();
        add(new QLabel(L1));
        n.e.accept(this);
        add(new QJumpCond(L2,getVar(n.e)));
        n.s.accept(this);
        add(new QJump(L1));
        add(new QLabel(L2));
    }



}
