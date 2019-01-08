package IR;
import symboltable.Variable;

/** <b>QNew : </b> <br> result = new arg1 */
public class QNew extends IRQuadruple {

	public QNew(Variable arg1, Variable result) {
		super(null,arg1,null,result);
	}
	
	public String toString() {
		return result + " := new " + arg1;
	}
}