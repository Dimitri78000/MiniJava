package semantic;

import AST.*;

public class PrettyPrint extends ASTVisitorDefault {
    private int indent; // nb de TAB ; "argument" de visit

    public PrettyPrint(ASTNode axiome) {
		System.out.println("=== PRETTY PRINT ===");
		this.indent=0;
		axiome.accept(this); // => visite((Raxiome)axiome)
		System.out.println("");
	}
    
	private static boolean isNotLast(ASTNode f, ASTNode n) { 
		return f != n.fils.get(n.fils.size()-1);
	}
	private void print(String s)   { System.out.print(s); }
	private void println(String s) { print(s + "\n"); }
	private void indent() {
		for(int i=0;i<indent;i++) print("   ");
	}
/////////////////// Visit ////////////////////
/// Listes homogenes : ASTList<R>
	/** Lklass == ASTList<Rklass> */
	   
	/** Lexpr == ASTList<Pexpr> */
	public void visit(Lexpr n) {
		for (ASTNode f : n.fils) {
			f.accept(this);
			if (isNotLast(f,n))  print(", "); 
			}
	}
	
	/** Lfarg == ASTList<Rfarg> */
	public void visit(Lfarg n) {
		for (ASTNode f : n.fils) {
			f.accept(this);
			if (isNotLast(f,n))  print(", "); 
			}
	}
	
	/** LklassMember == ASTList<RklassMember> */
	public void visit(LklassMember n) {
		for (ASTNode f : n.fils) {
			indent();
			f.accept(this);
		}
	}

	/** LmethMember == ASTList<RmethMember> */
	public void visit(LmethMember n) {
		for (ASTNode f : n.fils) {
			indent();
			f.accept(this);
		}
	}
	
/// Bases
	/** Raxiome : RklassMain m; Lklass cl; */
 
	/** Rfarg : Rtype t; RidVar id; */
	public void visit(Rfarg n) {
		n.t.accept(this);
		print(" ");
		n.id.accept(this);
	}
	
	/** Rfield : Rtype t; RidVar id; */
	public void visit(Rfield n) {
		n.t.accept(this);
		print(" ");
		n.id.accept(this);
		println(";");
	}

	/** RidKlass : String s; */
	public void visit(RidKlass n) { print(n.s); }
	
	/** RidMeth : String s; */
	public void visit(RidMeth n)  { print(n.s); }
	
	/** RidVar : String s; */
	public void visit(RidVar n)   { print(n.s); }
	
	/** Rklass : RidKlass id; RidKlass pid; LklassMember kml;*/		
	public void visit(Rklass n) {
		println(""); indent(); print("class ");
		n.id.accept(this);
//		if( ! n.pid.s.equals("Object")) {
			print(" extends ");
			n.pid.accept(this);
//		}
		println(" { "); indent++;
		n.kml.accept(this);
		indent--; indent(); println("}");
	}
	
	/** RklassMain : RidKlass id; RidVar arg; Pinst s; */
	public void visit(RklassMain n) {
		indent(); print("class ");
		n.id.accept(this);
		println(" {");  indent++; indent();
		print("public static void main (String [] ");
		n.arg.accept(this);
		println(") {"); indent++; indent();
		n.s.accept(this);
		indent--; indent(); println("}");
		indent--; indent(); println("}");
	}

	/** Rmethod : Rtype t; RidMeth id; Lfarg fl; LmethMember mml; Pexpr e; */
	public void visit(Rmethod n) {
		println(""); indent(); print("public ");
		n.t.accept(this);
		print(" ");
		n.id.accept(this);
		print(" (");
		n.fl.accept(this);
		println(") { "); indent++;
		n.mml.accept(this);
		indent(); print("return ");
	    n.e.accept(this);
	    println(";");
	    indent--; indent(); println("}");
	}
	
	/** Rtype  : String s; */
	public void visit(Rtype n) { print(n.s);}

	/** Rvar : Rtype t; RidVar id; */
	public void visit(Rvar n) {
		n.t.accept(this);
		print(" ");
		n.id.accept(this);
		println(";");
	}

/// "Expressions" = RE*
	/** REarray : Pexpr e1; Pexpr e2; */
	public void visit(REarray n) {
		n.e1.accept(this);
		print("[");
		n.e2.accept(this);
		print("]");
	}

	/** REbool : Boolean b; */
	public void visit(REbool n) { print( n.b.toString()); }

	/** REcall : Pexpr e; RidMeth id; Lexpr el; */
	public void visit(REcall n) {
		n.e.accept(this);
		print(".");
		n.id.accept(this);
		print("(");
		n.el.accept(this);
		print(")");
	}

	/** REint : Integer i; */
	public void visit(REint n) { print(n.i.toString()); }

	/** RElen: Pexpr e; */
	public void visit(RElen n) {
		n.e.accept(this);
		print(".length");
	}

	/** REnew : RidKlass id; */
	public void visit(REnew n) {
		print("new ");
		n.id.accept(this);
		print("()");
	}

	/** REnewArray : Pexpr e; */
	public void visit(REnewArray n) {
		print("new int [");
		n.e.accept(this);
		print("]");
	}

	/** REopBin : Pexpr e1; util.OPER op; Pexpr e2; */
	public void visit(REopBin n) {
		print("(");
		n.e1.accept(this);
		print(" "+n.op.getname()+" ");
		n.e2.accept(this);
		print(")");
	}

	/** REopUn : util.OPER op; Pexpr e; */
	public void visit(REopUn n) {
		print(n.op.getname() + " ");
		n.e.accept(this);
	}
	/** REvar : RidVar id; */

/// "Instructions" =  RI* 
	/** RIassign : RidVar id; Pexpr e; */
	public void visit(RIassign n) {
		n.id.accept(this);
	    print(" = ");
	    n.e.accept(this);
	    println(";");
	}

	/** RIassignArray : RidVar id; Pexpr e1; Pexpr e2; */
	public void visit(RIassignArray n) {
		n.id.accept(this);
		print("[");
		n.e1.accept(this);
		print("] = ");
		n.e2.accept(this);
		println(";");
	}

	/** RIblock : LmethMember mml; */
	public void visit(RIblock n) {
		int size=n.mml.fils.size();
		if (size==0) println("{ }");
		else if (size==1) n.mml.fils.get(0).accept(this);
		else {
			println("{"); indent++;
			n.mml.accept(this);
			indent--; indent(); println("}");
		}
	}

	/** RIif : Pexpr e; Pinst s1; Pinst s2; */
	public void visit(RIif n) {
		print("if (");
	    n.e.accept(this);
	    print(") ");
	    n.s1.accept(this);
	    indent(); print("else ");
	    n.s2.accept(this);
	}

	/** RIorint : Pexpr e; */
	public void visit(RIprint n) {
		print("System.out.println(");
		n.e.accept(this);
		println(");");
	}

	/** RIwhile : Pexpr e; Pinst s; */
	public void visit(RIwhile n) {
		print("while (");
		n.e.accept(this);
		print(") ");
		n.s.accept(this);
	}

}
