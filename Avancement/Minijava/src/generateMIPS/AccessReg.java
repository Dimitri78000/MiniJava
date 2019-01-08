package generateMIPS;

public class AccessReg extends Access{
	String register; 

	public AccessReg(String register) {
		this.register = register;
	}
	String Store(String register) { 
		if ( ! register.equals(this.register))
			return "move " + this.register +", " + register;
		else return null;
	}
	String Load(String register) {
		if ( ! register.equals(this.register))
			return "move " + register +", " + this.register;
		else return null;		
	}
	String LoadSaved(String register) {
		if (this.register.equals("$a0")) 
			return "lw   " + register + ", 0($sp)";
		else if (this.register.equals("$a1")) 
			return "lw   " + register + ", 4($sp)";
		else if (this.register.equals("$a2")) 
			return "lw   " + register + ", 8($sp)";
		else if (this.register.equals("$a3")) 
			return "lw   " + register + ", 12($sp)";
		else // normal case 
			return Load(register);
	}
	String getRegister() { return register;}
}
