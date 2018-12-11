package AST;
/** RklassMain : RidKlass id; RidVar arg; Pinst s; */
public class RklassMain extends ASTNode{
	public RidKlass id;
	public RidVar arg;   //not usable because String[] unknown
	public Pinst  s;

	public RklassMain(RidKlass id, RidVar arg, Pinst s) {
		super(id,arg,s); 
		this.id=id; this.arg=arg; this.s=s;
	} 
	public void accept(ASTVisitor v) { v.visit(this); }
}
