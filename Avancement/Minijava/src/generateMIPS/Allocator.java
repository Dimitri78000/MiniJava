package generateMIPS;
import java.util.HashMap;
import symboltable.*;

public class Allocator{
	private int globalSize ;                     // size for global variables
	private HashMap<String, Integer> sizeOf;     // sizeOf classes, used in New
	private HashMap<String, Integer> frameSize;  // frameSize for methods, 
	private HashMap<Variable,Access> access;     // How to Load/Store with MIPS
	private generateIR.Intermediate intermed;

	public Integer globalSize() { return globalSize; }
	public Integer sizeOfKlass(String klassName) { return sizeOf.get(klassName); }
	public Integer frameSize(String methodName) { return frameSize.get(methodName); }
	public Access  access(Variable v) { return access.get(v); }
	
	// Constructor = compute Allocation
	public Allocator(generateIR.Intermediate intermed) {
		this.globalSize=0;
		this.sizeOf = new HashMap<String,Integer>();
		this.frameSize= new HashMap<String,Integer>();
		this.access = new HashMap<Variable,Access>();
		this.intermed=intermed;
		klassAlloc();
		methodAlloc();
		IRAlloc();
	}
		
	/////// Instances de Classe
	// A extends B extends ... Object (extends null)
	// new A == [champs Object] ... [champs B][champs A]
	private Integer klassSize(Klass cl){
		if (cl == null) return 0;
		else return 4*cl.scope.vars.size()+klassSize(cl.scope.superKlass());
	}
    private void klassAlloc() {
		sizeOf.put(null, 0);
		for (Klass cl : intermed.rootScope.classes.values()){
			int off=klassSize(cl);
			sizeOf.put(cl.name, off);
			for( Variable v : cl.scope.vars.values()) {
				// Fields Access = Registre $a0(this) +  off
				// minijava : fields only on this
				off -= 4;
				access.put(v, new AccessOff("$a0", off));
			}
		}
   }
    
    private void methodAlloc() {
    	for ( Klass cl : intermed.rootScope.classes.values()) 
    		for ( Method m : cl.scope.methods.values())
    			methodAlloc(m);
    }

   //Method Frame      +0  FP -4                         SP   
   // Argn Argn-1 ... Arg4 | $ra $s0-$s7 locals IRlocals | ..
    private void methodAlloc(Method m) {
    	final int ARGSMORE=4;
    	int frSize=0;
    	// fixed frame : save/restore $ra, $s0-$s7
    	frSize += 4 + 4 * 8;
        // args in $ai
		for ( int i = 0 ;((i < m.args.length) && (i<ARGSMORE)); i++)
    		access.put(m.args[i], new AccessReg("$a" + i));
		// more args: stack before FP
		for ( int i = ARGSMORE; i < m.args.length; i++)
    		access.put(m.args[i], new AccessOff("$fp" , 4*(i-ARGSMORE) ));
    	// local vars
 		for ( Variable v : m.scope.vars.values())  {
 			access.put(v, new AccessOff("$fp",-4-frSize));
 			frSize+=4;
 		}
 		// local variables in blocks not managed
 	
		frameSize.put(m.name, frSize);
    }
    
    //// IR Variables : temporaire, constant=immediate
    private void IRAlloc() {
    	for (Variable v : intermed.constList)
    		access.put(v, new AccessConst(v.name));
    	for (Variable v : intermed.tempList) {
    		String methName=v.type;
    		if (methName.equals("main")) {
    			access.put(v, new AccessOff("$gp", globalSize));
    			globalSize+=4;
    		} else {
    			int frSize=frameSize.get(methName);
    			access.put(v, new AccessOff("$fp",-4-frSize));
    			frSize+=4;
    			frameSize.put(methName, frSize);
    		}
    	}
    }		
/*
	///// Basic static register allocation
    private int regIndex=0; 
	private String allocateReg() { return regName(regIndex++); }
	private String regName(int i) {
		if (i<10) return "$t" + i ;
		if (i<18) return "$s" + (i - 10);
		else 
			throw new main.CompilException("Allocator : Out of register");
	}
*/
}
