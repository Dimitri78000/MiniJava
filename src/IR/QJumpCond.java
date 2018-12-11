package IR;
import symboltable.Variable;

/** <b>QJumpCond : </b> <br> Jump arg1 IfNot arg2 */
public class QJumpCond extends IRQuadruple {

	public QJumpCond(Variable arg1, Variable arg2) {
		super(null,arg1,arg2,null);
	}
	
	public String toString() {
		return "iffalse " + arg2 + " goto " + arg1;
	}
}