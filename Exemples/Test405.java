class Test405{
    public static void main(String[] a){
    	System.out.println(new Operator().compute());
    }
}

class Op2 {
	public int start(){
		return 42;
	}
}

class Operator {
    boolean b;  //Unused
    int i;
    Operator op;

    public Operator get() {
    	Operator oper;
    	return op;
    } 
    
    public int compute(){
    	op = oper;	   		// FAIL : "oper" undef (+ type) 
    	i = i.compute(); 	// FAIL : undef Class (+...)
    	i = op.compute();	// OK
    	i = op.start();   	// FAIL : undef method
    	op = new i();   	// FAIL : class undef (+ type)
    	op = new op();	 	// FAIL : class undef (+ type)
    	i1=10 ;           	// FAIL : var undef
    	return new Op2().start();
    }
    
    // ...
    boolean b;   	// Redefinition
    boolean i;  	// Redefinition2
    public int next(boolean i) {
    	int i;		// Redefinition3
    	return 0;
    }
}
    

