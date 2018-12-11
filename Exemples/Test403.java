class Test403{
    public static void main(String[] a){
	System.out.println(this);  // this undef in static method
    }
}

class Operator {
    int i;
    Operator op;
    
    public int compute(){
    	i=this.doit(op);        // FAIL : number of arguments
    	i=this.doit(op, 3 ,4);  // FAIL : number of arguments
    	i=this.doit(3,4);       // FAIL : arg type
    	i=this.doit(4,op);      // FAIL : arg type
    	return this.doit(op,3);	// OK
    }
    
    public int doit(Operator op, int i){
    	return 42;
    }
}

