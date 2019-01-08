package generateMIPS;
public abstract class Access {
	abstract String Store(String register);      // store register to Variable
	abstract String Load(String register);       // Load Variable in register
	abstract String LoadSaved(String register); // use saved a0, a1 a2 a3 in stack for setting args
	abstract String getRegister(); // try to optimiqe tempreg if variable already in a register
}
