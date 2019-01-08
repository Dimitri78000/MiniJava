package generateIR;
import java.util.ArrayList;
import symboltable.*;

public class Intermediate {
        public IR.IRProg program;      //
        public Scope rootScope;     // Table des symboles AST
        public ArrayList<Variable> constList; // liste des constantes
        public ArrayList<Variable> labelList; // liste des Label
        public ArrayList<Variable> tempList;  // liste des variablesIR

        private semantic.SemanticTree semanticTree; // only for lookup symbol
    private int labelNum;  // numerotation des labels temporaires
    private int varNum;   // numerotation des variables temporaires

        public Intermediate(semantic.SemanticTree semanticTree) {
                this.semanticTree = semanticTree;
                this.labelNum=0;
                this.varNum=0;
                
                this.program  = new IR.IRProg();
                this.rootScope= semanticTree.rootScope;
                this.constList= new ArrayList<Variable>();
                this.labelList= new ArrayList<Variable>();
                this.tempList = new ArrayList<Variable>();
        }
        
        /* Variables IR = Constante/immediate , Labels , Temporaire */
     public Variable newLabel() { //label tempo
                return newLabel("L"+labelNum++);
        }
    public Variable newLabel(String name) {  //label method
        Variable v = new Variable(name, "label");
        labelList.add(v);
        return v;
    }
    public Variable newConst(String name) { 
        Variable v=new Variable(name, "constant");
        constList.add(v);
        return v;
    }
    public Variable newTemp(AST.ASTNode n) { 
        String methname= semanticTree.getScope(n).currentMethod().name;
        Variable v=new Variable("t"+varNum++, methname);
        tempList.add(v);
        return v;
    }
      
}

