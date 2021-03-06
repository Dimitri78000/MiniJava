package generateMIPS;
import IR.*;
import symboltable.Variable;

public class IR2MIPS {
	private Allocator allocator;
	private Output output;
	
	public IR2MIPS(IRProg irprog, Allocator allocator, Output output) {
		this.allocator = allocator;
		this.output=output;
		outNoTab(".text");
		for(IRQuadruple q: irprog.prog)
			accept(q);
	}

	private void accept(IRQuadruple q) {
		if (main.DEBUG.MIPSCOMMENT) 
			outNoTab("# "+q.toString());
		if      (q instanceof QLabelMeth) visit((QLabelMeth)q);
		else if (q instanceof QLabel)     visit((QLabel)q);
		else if (q instanceof QJump)      visit((QJump)q);
		else if (q instanceof QJumpCond)  visit((QJumpCond)q);
		else if (q instanceof QReturn)    visit((QReturn)q);
		else if (q instanceof QParam)     visit((QParam)q);
		else if (q instanceof QCall)      visit((QCall)q);
		else if (q instanceof QNew)       visit((QNew)q);
		else if (q instanceof QCopy)      visit((QCopy)q);
		else if (q instanceof QAssign)    visit((QAssign)q);
		else if (q instanceof QAssignUnary) visit((QAssignUnary)q);
		else if (q instanceof QNewArray)        visit((QNewArray)q);
		else if (q instanceof QAssignArrayFrom) visit((QAssignArrayFrom) q);
		else if (q instanceof QAssignArrayTo)   visit((QAssignArrayTo)q);
		else if (q instanceof QLength)          visit((QLength)q);
		else visit(q);
	}
	
	private void visit(IRQuadruple q) { // default
		throw new main.CompilException("IR2MIPS : unmanaged IR :" + q);
	}

	//// printing helpers : println in outfile
	private void out(String s) { if (s!=null) output.write("\t" + s + "\n"); }
	private void outNoTab(String s) { if (s!=null) output.write( s + "\n"); }

    //// register helpers :Load and Store with respect to Variable Accss
	private void regLoad(String reg, Variable v) { out(allocator.access(v).Load(reg));}
	private void regLoadSaved(String reg, Variable v) {out(allocator.access(v).LoadSaved(reg));}
	private void regStore(String reg, Variable v) {out(allocator.access(v).Store(reg));}
	
	// varargs for stack ; push/pop n register
	private void push(String ... regs) {
		int size=regs.length;
		out("addi $sp, $sp, -" + 4*size);
		for (int i=0; i<size; i++)
			out("sw   " + regs[i] + ", " + 4*(size-i-1) + "($sp)");
	}
	private void pop(String ... regs) {
		int size=regs.length;
		for (int i=0; i<size; i++)
			out("lw   " + regs[i] + ", " + 4*(size-i-1) + "($sp)");
		out("addi $sp, $sp, " + 4*size);
	}
	
	/////// helpers : save/restore register (should be in allocator)
	//  Callee-saved (QLabelMeth,QReturn) : $ra (+ $s0-7) 
	private void calleeIn() { 
		out("sw   $ra ,  -4($fp)");
	//	for(int i=0; i < 8; i++)
	//		out("sw   $s"+ i +" ,  -" + (4*i+8) +"($fp)");
	}
	private void calleeOut() {
		out("lw   $ra ,  -4($fp)");
	//	for(int i=0; i < 8; i++)
	//		out("lw   $s"+ i +" ,  -" + (4*i+8) +"($fp)");
	}
	// Caller save/restore (QCall) : $a0-3 (+ $t0-9) 
	private void callerSave() {
		// push("$t9", "$t8","$t7","$t6","$t5","$t4","$t3", "$t2", "$t1", "$t0");	
		push("$fp", "$a3", "$a2","$a1","$a0");
	}
	private void callerRestore() {
		pop("$fp", "$a3", "$a2","$a1","$a0");
		// pop("$t9", "$t8","$t7","$t6","$t5","$t4","$t3", "$t2", "$t1", "$t0");	
	}

	//////////////// VISIT ///////////////
	/** <b>QJump :</b> <br> Jump arg1 */
	/** <b>QJumpCond : </b> <br> Jump arg1 IfNot arg2 */
	/** <b>QCopy :</b> <br>result = arg1 */
	/** <b>QNew : </b> <br> result = new arg1 */	
	/** <b>QAssign :</b> <br> result = arg1 op arg2  */
	/** <b>QAssignUnary :</b> <br> result = op arg1 */
	/** <b>QReturn :</b> <br> Return arg1 */
	/** <b>QLabelMeth : </b> <br>Label arg1 */

	/** <b>QJump :</b> <br> Jump arg1 */
	private void visit(QJump q) {
		out("j    " + q.arg1.name);
	}

	/** <b>QJumpCond : </b> <br> Jump arg1 IfNot arg2 */
	private void visit(QJumpCond q) {
		regLoad("$v0", q.arg2);
		out("beq  $v0, $zero, " + q.arg1.name) ;
	}

	/** <b>QCopy :</b> <br>result = arg1 */
	private void visit(QCopy q) {
		regLoad("$v0", q.arg1);
		regStore("$v0", q.result);
	}

	/** <b>QNew : </b> <br> result = new arg1 */
	private void visit(QNew q) {
		Integer size=allocator.sizeOfKlass(q.arg1.name);
		if (size==null)  // not checked in VarUndef !
			throw new main.CompilException("GenerationMips.QNew :  unknown size for class " + q.arg1.name);
		push("$a0");
		out("li   $a0, " + size);
		out("jal  _new_object");
		regStore("$v0", q.result);
		pop("$a0");
	}

	/** <b>QAssign :</b> <br> result = arg1 op arg2  */
	private void visit(QAssign q) {
		regLoad("$v0", q.arg1);
		regLoad("$v1", q.arg2);

		switch(q.op) {
			case PLUS:
				out("add  $v0, $v0, $v1");
				break;
			case MINUS:
				out("sub  $v0, $v0, $v1");
				break;
			case TIMES:
				out("mult $v0, $v1");
				out("mflo $v0") ;
				break;
			case AND:
				out("and  $v0, $v0, $v1");
				break;
			case LESS:
				out("slt  $v0, $v0, $v1");
				break;
			default: // ERROR !!!
				break;
		}
		regStore("$v0", q.result);
	}

	/** <b>QAssignUnary :</b> <br> result = op arg1 */
	private void visit(QAssignUnary q) {
		regLoad("$v0", q.arg1);
		switch(q.op) {
			case NOT:
				out("seq  $v0, $zero, $v0");
				break;
			default: // ERROR
				break;
		}
		regStore("$v0", q.result);
	}

	/** <b>QReturn :</b> <br> Return arg1 */
	private void visit(QReturn q) {
		calleeOut();
		regLoad("$v0", q.arg1);
		out("jr $ra");
	}

	/** <b>QLabelMeth : </b> <br>Label arg1 */
	private void visit(QLabelMeth q) {
		outNoTab(q.arg1.name + ":");
		calleeIn();
	}

	/** <b>QLabel : </b> <br>Label arg1 */
	private void visit(QLabel q) {
		outNoTab(q.arg1.name + ":");
	}

	/* QParam/QCall */
	// helpers recording params
	private Variable[] params = new Variable[42];
	private int indexParams=0;
	private int checkArgs(QCall q) {
  /* check Qparam/QCall consistancy, return nbArgs
     must be called 1 time in each Qcall */
		int nbArgs = Integer.parseInt(q.arg2.name);
		if (nbArgs!=indexParams)
			throw new main.CompilException("IR2MIPS : Params error");
		indexParams=0;
		return nbArgs;
	}
	private Variable getArg(int i) {
		if (indexParams!=0) throw new main.CompilException("IR2MIPS : checkArgs() missing");
		return params[i];
	}

	//** <b>QParam : </b> <br> Param arg1 */
	private void visit(QParam q) {
		params[indexParams++]=q.arg1;
	}

	/** <b>QCall :</b> <br> result = call arg1 [numParams=arg2] */
	private void visit(QCall q) {
		if (q.result ==null) { specialCall(q); return; }
		String function= q.arg1.name;
		int frameSize=allocator.frameSize(function);
		int nbArg=checkArgs(q);
		if (nbArg >4)
			throw new main.CompilException("IR2MIPS : too many args in method "+function);
		callerSave();
		// Store up to 4 args in register
		for (int i = 0; (i < 4) && (i < nbArg) ; i++) {
			// pb : f(a,b) { f(b,a) } ecrasement
			// load saved values for $ai in offest($sp)
			regLoadSaved("$a" + i, getArg(i));
		}
		out("move $fp, $sp"); // set $fp (and save $sp)
		out("addi $sp, $sp, -" + frameSize );
		out("jal  " + function);
		out("move $sp, $fp"); // restore $sp
		callerRestore();
		regStore("$v0", q.result);
	}

	//Special Qcall (void methods)
	private void specialCall(QCall q) {
		String function= q.arg1.name;
		int nbArg=checkArgs(q);
		if (nbArg >4)
			throw new main.CompilException("IR2MIPS : too many args in method "+function);
		if(function.equals("_system_exit")) {
			out("jal  " + function);
			return;
		}
		if(function.equals("_system_out_println")) {
			push("$a0");
			regLoad("$a0", getArg(0));
			out("jal  " + function);
			pop("$a0");
			return;
		}
		if(function.equals("main"))
			throw new main.CompilException("IR2MIPS : recurse main forbidden");
		throw new main.CompilException("IR2MIPS : undef void Method " + function);
	}
}
