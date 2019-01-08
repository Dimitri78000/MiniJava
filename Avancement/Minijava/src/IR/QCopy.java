package IR;
import symboltable.Variable;

/** <b>QCopy :</b> <br>result = arg1 */
public class QCopy extends IRQuadruple {

	public QCopy(Variable arg1, Variable result) {
		super(null,arg1,null,result);
	}
	
	public String toString() {
		return result + " := " + arg1;
	}
}