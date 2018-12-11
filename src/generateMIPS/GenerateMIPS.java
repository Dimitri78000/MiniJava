package generateMIPS;
import IR.*;
import java.io.*;

public class GenerateMIPS {
    private final String runtime="src/generateMIPS/runtime.mips";
    private BufferedWriter bw;
    private IRProg prog;
    
    public GenerateMIPS(IRProg prog, String output) {
	this.prog = prog;
	try {	
	    bw = new BufferedWriter(new FileWriter(output));
	    System.out.println("=== GENERATE MIPS file : " +output + " ===");
	    generateMIPS();  
	    System.out.println("=== LINK RUNTIME in " + output + "===");
	    link();	    
	    bw.close();
	    } catch (IOException e) { e.printStackTrace(); }
    }

    // printing helpers
    private void out0(String s) {
	try {  if (s!=null) bw.write(s + "\n"); }
	catch (IOException e) {e.printStackTrace();}
    }
    private void out(String s) { if (s!=null) out0("\t"+s); }

    // Edition de lien = concatenation runtime.mips
    private void link(){
	try {
	BufferedReader br = new BufferedReader(new FileReader(runtime));
	String temp;
	while((temp = br.readLine()) != null) out0(temp);
	br.close();
	} catch (IOException e) { e.printStackTrace(); }
    }

    // Generation
    public void generateMIPS() {
	out(".text");
	for (int i = 0; i < prog.size(); i++) {
	    IRQuadruple q = prog.get(i);
	    if (main.DEBUG.MIPSCOMMENT) out("# " + q.toString());
	    
	    if      (q instanceof QLabel)  handleLabel(q);	
	    else if (q instanceof QCall)   functionCall(q, i);
	}
    }
    
    // helperss for stack
    private void push(String ... regs) {
	int size=regs.length;
	out("addi $sp, $sp, -" + 4*size);
	for (int i=0; i<size; i++)
	    out("sw " + regs[i] + ", " + 4*(size-i-1) + "($sp)");
    }
    private void pop(String ... regs) {
	int size=regs.length;
	for (int i=0; i<size; i++)
	    out("lw " + regs[i] + ", " + 4*(size-i-1) + "($sp)");
	out("addi $sp, $sp, " + 4*size);
    }
    
    //-----------------------------------------------------------//
    private void handleLabel(IRQuadruple q) {	//Label y
	out0(q.arg1.name + ":");
    }
    
    private void functionCall(IRQuadruple q, int index) {//x := call y <NUMPARAMS=z>
	int paramCount = Integer.parseInt(q.arg2.name);
	String function= q.arg1.name;
	
	if(function.equals("_system_exit")) {
	    out("jal " + function);
	    return;
	}
	if(function.equals("_system_out_println")) {
	    push("$v0" , "$a0" );
	    QParam param = (QParam) prog.get(index-paramCount);
	    out("li $a0,  " + param.arg1);
	    out("jal " + function);
	    pop("$v0" , "$a0");
	    return;
	}
	//
    }
}
