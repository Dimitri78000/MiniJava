package IR;
import main.OPER;
import symboltable.Variable;

/** <b>QAssignUnary :</b> <br> result = op arg1 */
public class QAssignUnary extends IRQuadruple {
	public QAssignUnary(OPER op, Variable arg1, Variable result){
		super(op,arg1,null,result);
	}
	
	public String toString(){
		return result + " := " + op + " " + arg1;
	}
}