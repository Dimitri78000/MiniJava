package IR;
import symboltable.Variable;

/** <b>QAssignArrayTo :</b> <br>result[arg2] = arg1 */
public class QAssignArrayTo extends IRQuadruple{

	public QAssignArrayTo(Variable arg1, Variable arg2, Variable result) {
		super(null,arg1,arg2,result); 
	}
	
	public String toString() {
		return result + "[" + arg2 + "]" + " := " + arg1;
	}
}