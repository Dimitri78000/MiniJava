package generateIR;
import symboltable.Variable; // reutilise pour les variables IR

public class Intermediate {
    public IR.IRProg program;            // programme intermédiaire
    public symboltable.Scope rootScope;  // table de symbole
    public java.util.ArrayList<Variable> varList; // variables IR
    
    public Intermediate(symboltable.Scope rootScope) {
        this.rootScope = rootScope;
        this.program=new IR.IRProg();
        this.varList= new java.util.ArrayList<Variable>();
    }

}
