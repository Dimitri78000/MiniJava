package semantic;

import symboltable.*;

/** checking for class loop in symbol table
*   <br>assume no inner Classes => all classes in the root scope
*/
public class CheckLoop  {
    public boolean error;
    public String Debug;
    public Scope root;
    
    public CheckLoop(SemanticTree semanticTree){        
        this.error = false;
        this.Debug = "=== Hierarchie des Classes =====" +System.lineSeparator();
        this.root=semanticTree.rootScope;
        check();
    }
    
    public void check(){ 
        for (Klass kl : root.classes.values()) {
            java.util.HashSet<String> ancestors=new java.util.HashSet<String>();
            boolean noloop=true;
            Debug +=kl.name ;
            ancestors.add(kl.name);
            for (Klass k = kl; (k!=null) && (k.parentName !=null) && noloop ; k=root.classes.get(k.parentName) ){
                noloop=ancestors.add(k.parentName);
                Debug += " -> " + k.parentName;
            }
            Debug += System.lineSeparator();
            if (! noloop) {
                System.err.println("Loop for Class " +kl.name);
                error=true;
            }                           
        }
    }           
}
