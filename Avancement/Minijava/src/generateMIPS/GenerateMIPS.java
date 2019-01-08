package generateMIPS;
public class GenerateMIPS {
	private final String RUNTIME="src/generateMIPS/runtime.mips";
	public GenerateMIPS(generateIR.Intermediate intermed, String outfile) {	
		System.out.println("=== Allocator ===");
		Allocator allocator =new Allocator(intermed);

		Output output = new Output(); // outfile I/O
		output.open(outfile);		
		System.out.println("=== Generate MIPS file : " +outfile + " ===");
		new IR2MIPS(intermed.program,allocator,output);

		System.out.println("=== Link runtime in " + outfile + "===");
		output.concat(RUNTIME);
		output.close();			
	}
}
	
