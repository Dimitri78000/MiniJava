package AST;

/** AST Visitor with defaultVisit()= depth-first */
public class ASTVisitorDefault implements ASTVisitor {

	public void defaultVisit(ASTNode n){
		for ( ASTNode f : n.fils)  f.accept(this);
	};
//	public void visit(ASTNode n) {defaultVisit(n);}
	// Listes, extends ASTList<...>
	public void visit(Lexpr n) 		{defaultVisit(n);}
	public void visit(Lfarg n) 		{defaultVisit(n);}
	public void visit(Lklass n) 	{defaultVisit(n);}
	public void visit(LklassMember n) 	{defaultVisit(n);}
	public void visit(LmethMember n) 	{defaultVisit(n);}
	// Basiques, extends ASTNode
	public void visit(Raxiome n) 	{defaultVisit(n);}
	public void visit(Rfarg n) 		{defaultVisit(n);}
	public void visit(Rfield n) 	{defaultVisit(n);}
	public void visit(RidKlass n) 	{defaultVisit(n);}
	public void visit(RidMeth n) 	{defaultVisit(n);}
	public void visit(RidVar n) 	{defaultVisit(n);}
	public void visit(Rklass n) 	{defaultVisit(n);}
	public void visit(RklassMain n) {defaultVisit(n);}
	public void visit(Rmethod n) 	{defaultVisit(n);}
	public void visit(Rtype n) 		{defaultVisit(n);}
	public void visit(Rvar n) 		{defaultVisit(n);}
	// Expressions , extends PExpr
	public void visit(REarray n) 	{defaultVisit(n);}
	public void visit(REbool n) 	{defaultVisit(n);}
	public void visit(REcall n) 	{defaultVisit(n);}	
	public void visit(REint n) 		{defaultVisit(n);}
	public void visit(RElen n) 		{defaultVisit(n);}
	public void visit(REnew n) 		{defaultVisit(n);}
	public void visit(REnewArray n) {defaultVisit(n);}
	public void visit(REopBin n) 	{defaultVisit(n);}
	public void visit(REopUn n) 	{defaultVisit(n);}
	public void visit(REvar n) 		{defaultVisit(n);}
	// Instructions,, extends PIsnt
	public void visit(RIassign n) 	{defaultVisit(n);}
	public void visit(RIassignArray n)	{defaultVisit(n);}
	public void visit(RIblock n) 	{defaultVisit(n);}
	public void visit(RIif n) 		{defaultVisit(n);}
	public void visit(RIprint n) 	{defaultVisit(n);}
	public void visit(RIwhile n) 	{defaultVisit(n);}

	public void visit(AST n)		{defaultVisit(n);}
}
