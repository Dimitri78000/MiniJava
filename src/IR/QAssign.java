package IR;
import symboltable.Variable;
import main.OPER;

/** <b>QAssign :</b> <br> result = arg1 op arg2  */
public class QAssign extends IRQuadruple { 

	public QAssign(OPER op, Variable arg1, Variable arg2, Variable result) {
		super(op,arg1,arg2,result); 
	}
	
	public String toString() {
		return result + " := " + arg1 + " " + op + " " + arg2;
	}
}