package IR;
import symboltable.Variable;
import main.OPER;

/** Classe Mere des Instructions Intermediaires
 * <p> <b>IRQuadruple : </b> <br>op ( arg1, arg2) -> result  
 */
public abstract class IRQuadruple { 
	public OPER op;
	public Variable arg1;
	public Variable arg2;
	public Variable result;
	
	protected IRQuadruple(OPER op, Variable arg1, Variable arg2, Variable result) {
		this.op = op; 
		this.arg1 = arg1; 
		this.arg2 = arg2; 
		this.result = result; 
	}

	public abstract String toString(); // Impression Debug		
}