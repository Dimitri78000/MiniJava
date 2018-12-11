package syntax;
import java_cup.runtime.*;

/** Analyse Syntaxique JFLEX + CUP , avec main()*/
public class  CupMain {
    Object axiomeValue; // valeur de retour de parse si analyse OK
                        // == valeur du symbol START de type defini dans la spec

    public static void main(String[] args) throws Exception { 
	if (args.length == 0) 	analyse("stdin");
	for(String file : args) { analyse(file); }
    }

    private static void analyse(String file){
	Object axiome=new CupMain(file, false, false).axiomeValue;
	System.err.println("Valeur Axiome = "+axiome);
    }

/** Constructeur
 * @param file  input filename, "-" or "stdin" for standard input
 * @param debugToken  if true, dump des tokens lues par le parseur
 * @param parserDebug if true, trace detaillee d'execution du parser
 */
    public CupMain( String file, boolean debugToken, boolean debugParse) {
	this.axiomeValue = null;
	ComplexSymbolFactory csf = new ComplexSymbolFactory();
	Scanner lexer = new Yylex(setReader(file), csf);
	ScannerBuffer scanBuf = new ScannerBuffer(lexer); 
	lr_parser parser = new parser(scanBuf,csf);

	try {  
	    Symbol result = debugParse? parser.debug_parse() : parser.parse() ;
	    if ( ( result != null) && (result.sym == 0) ) {
		this.axiomeValue=result.value;
		System.err.println("=== PARSING OK");
	    }
	    else
		System.err.println("=== PARSING ENDS with wrong symbol : " + result);
	} 
	catch(Exception e){
	    if ("Can't recover from previous error(s)".equals(e.getMessage()))
		// exception from lr_parser.report_fatal_error() !!!
		System.err.println("=== PARSING ENDS with unrecovered syntax error");
	    else
		e.printStackTrace();
	}
	finally {
	    if (debugToken) System.err.println("=== TOKEN DEBUG === :\n" + scanBuf.getBuffered());
	}
    }
    java.io.Reader setReader(String filename) {
	java.io.InputStream in;
	try {
	    if ( filename.equals("-") || filename.equals("stdin")) in=System.in;
	    else  in=new java.io.FileInputStream(filename);
	}
	catch(java.io.IOException e) {
	    System.err.println("error with file " + filename + ", using stdin");
	    in=System.in;
	}
	return  new java.io.InputStreamReader(in);
    }
}
