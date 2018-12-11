package IR;
import symboltable.Variable;

/** <b>QLabelMeth : </b> <br>Label arg1 */
// Label for method (not main)
// used to generate callee start code in generation
public class QLabelMeth extends QLabel {

	public QLabelMeth(Variable arg1) { super(arg1); }
}