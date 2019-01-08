package generateMIPS;

public class AccessOff extends Access{
	String register;  // "$fp" for frame, "$a0" for this , $gp, $sp ...
	Integer offset;
	public AccessOff(String register, Integer offset) {
		this.register = register;
		this.offset = offset;
	}
	String Load(String register) {
		return 	"lw   " + register + ", " + offset + "(" + this.register + ")";
	}
	String Store(String register) { 
		return 	"sw   " + register + ", " + offset + "(" + this.register + ")";
	}
	String LoadSaved(String register) {
		if (this.register.equals("$a0")) 
			return "lw   " + register + ", 0($sp)\n\t" +
				   "lw   " + register + ", " + offset + "(" + register  + ")" ;
		else return Load(register);
	}
	String getRegister() { return null;}
}
