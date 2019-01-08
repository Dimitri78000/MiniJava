package main;

/** Runtime Exception de Compilation */
@SuppressWarnings("serial")
public class CompilException extends RuntimeException {
	public CompilException(String message) {
		super(message);
	}
	
}
