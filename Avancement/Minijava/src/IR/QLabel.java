package IR;
import symboltable.Variable;

/** <b>QLabel : </b> <br>Label arg1 */
public class QLabel extends IRQuadruple {

	public QLabel(Variable arg1) {
		super(null,arg1,null,null);
	}
	
	public String toString() {
		return "label " + arg1;
	}
}