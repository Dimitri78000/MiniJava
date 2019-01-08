package syntax;

import main.CompilException;
import main.DEBUG;
import AST.ASTNode;

public class Syntax {
    public ASTNode axiome;

    public Syntax(String file) {
	Object AST = new CupMain(file, DEBUG.TOKEN,DEBUG.PARSE).axiomeValue;
	if ( !(AST instanceof ASTNode) ) throw new CompilException("Syntax error");
	
	this.axiome=(ASTNode)AST;
	if (DEBUG.TREE) axiome.print(DEBUG.TREEMODE);
}

}
