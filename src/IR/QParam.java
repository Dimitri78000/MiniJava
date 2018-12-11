package IR;
import symboltable.Variable;

/** <b>QParam : </b> <br> Param arg1 */
public class QParam extends IRQuadruple {

	public QParam(Variable arg1) {
		super(null,arg1,null,null);
	}
	
	public String toString() {
		return "param " + arg1;
	}
}