package IR;
import symboltable.Variable;

/** <b>QNewArray : </b> <br> result = new arg1 [Size=arg2] */
public class QNewArray extends IRQuadruple{

	public QNewArray(Variable arg1, Variable arg2, Variable result){
		super(null,arg1,arg2,result);
	}
	
	public String toString(){
		return result + " := new " + arg1 + "<" + arg2 + ">";
	}
}