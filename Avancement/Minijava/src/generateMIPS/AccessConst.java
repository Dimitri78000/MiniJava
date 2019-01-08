package generateMIPS;
public class AccessConst extends Access {
	String immediate;
	public AccessConst(String immediate) {
		this.immediate = immediate;
	}
	String Store(String register) { 
		throw new main.CompilException( "genMIPS : store in immediate !?!?");
	}
	String Load(String register) {
		return "li   " + register + ", " + immediate ;
	}
	String LoadSaved(String register) { return Load(register);}
	String getRegister() { return null;}
}
