package IR;
import symboltable.Variable;

/** <b>QCall :</b> <br> result = call arg1 [numParams=arg2] */
public class QCall extends IRQuadruple {
	public QCall(Variable arg1, Variable arg2, Variable result) {
		super(null,arg1,arg2,result);
	}
	
	public String toString() {
		if(result != null)
			return result + " := call " + arg1 + "<" + arg2+">";
		else 	//void  ?
			return "call " + arg1 + "<" + arg2 +">";
	}
}