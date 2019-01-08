package IR;
import symboltable.Variable;

/** <b>QLength :</b> <br> result = length arg1 */
public class QLength extends IRQuadruple{

	public QLength(Variable arg1, Variable result) {
		super(null,arg1,null,result);
	}
	
	public String toString(){
		return result + " := length " + arg1;
	}
}