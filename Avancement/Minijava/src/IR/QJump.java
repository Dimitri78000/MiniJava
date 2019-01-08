package IR;
import symboltable.Variable;

/** <b>QJump :</b> <br> Jump arg1 */
public class QJump extends IRQuadruple {
	
	public QJump(Variable arg1){
		super(null,arg1,null,null);
	}
	
	public String toString() {
		return "goto " + arg1;
	}
}