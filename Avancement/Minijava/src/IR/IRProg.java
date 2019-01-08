package IR;
import java.util.List;
import java.util.ArrayList;

/** Intermediate Program = List < IRQuadruple > */
public class IRProg {
	public List<IRQuadruple> prog;

	public IRProg(){
		prog= new ArrayList<IRQuadruple>(); 
	}
	
	public int size(){ return prog.size(); }
	public IRQuadruple get(int i){ return prog.get(i); }
	public void add(IRQuadruple q){ prog.add(q); }
	public void append(IRProg IRprog){
		for(IRQuadruple q:IRprog.prog) this.prog.add(q);
	}
	public void print() {
		for (IRQuadruple q : prog)
			if ( q instanceof QLabel)	System.out.println(q.arg1 + ":");
			else 						System.out.println("\t"+q);
	}

}
