package IR;
import symboltable.Variable;

/** <b>QAssignArrayFrom :</b> <br> result = arg1[arg2] */
public class QAssignArrayFrom extends IRQuadruple {

	public QAssignArrayFrom(Variable arg1, Variable arg2, Variable result) {
		super(null,arg1,arg2,result); 
	}
	
	public String toString() {
		return result + " := " + arg1 + "[" + arg2 + "]";
	}
}