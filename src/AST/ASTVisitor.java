package AST;

/** AST visitor */  
public interface ASTVisitor {
	// Listes, extends ASTList<...>
	public void visit(Lexpr n);
	public void visit(Lfarg n);
	public void visit(Lklass n);
	public void visit(LklassMember n);
	public void visit(LmethMember n);
	// Basiques, extends ASTNode
	public void visit(Raxiome n);
	public void visit(Rfarg n);
	public void visit(Rfield n);
	public void visit(RidKlass n);
	public void visit(RidMeth n);
	public void visit(RidVar n);
	public void visit(Rklass n);
	public void visit(RklassMain n);
	public void visit(Rmethod n);
	public void visit(Rtype n);
	public void visit(Rvar n);
	// Expressions , extends PExpr
	public void visit(REarray n);
	public void visit(REbool n);
	public void visit(REcall n);
	public void visit(REint n);
	public void visit(RElen n);
	public void visit(REnew n);
	public void visit(REnewArray n);
	public void visit(REopBin n);
	public void visit(REopUn n);
	public void visit(REvar n);
	// Instructions,, extends PIsnt
	public void visit(RIassign n);
	public void visit(RIassignArray n);
	public void visit(RIblock n);
	public void visit(RIif n);
	public void visit(RIprint n);
	public void visit(RIwhile n);

	// may be for testing
	public void visit(AST n);
}
