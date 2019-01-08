package IR;
import symboltable.Variable;

/** <b>QReturn :</b> <br> Return arg1 */
public class QReturn extends IRQuadruple {

	public QReturn(Variable arg1) {
		super(null,arg1,null,null);
	}
	
	public String toString() {
		return "return " + arg1;
	}
}