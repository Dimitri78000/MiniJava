package main;

/**  Running Options for main() */
public class DEBUG {
	static public final String INFILE="input.txt"; // default minijava input file 
	static public final boolean TOKEN=false; // dump apres parsing de la chaine de Token     
	static public final boolean PARSE=false; // debug (lourd) du parsing     
	static public final boolean TREE= true;  // print AST as generic Nodes
	static public final int     TREEMODE=6; // mode (1..11) pour TREEPRINT cf class ASTPrint
	static public final boolean TREELOCATION=true; // add fsource location in TreePrint
	static public final boolean PRETTY=true; // pretty print of visitable ASTNode 
	static public final boolean SYMTAB=true; // dump symbol table
	static public final boolean KLASS=true;  // dump Class Hierarchy
	static public final boolean INTERMED=true; // dump Intermediate Code
	static public final boolean MIPSCOMMENT=true; // insert IR as MIPS comments
}
