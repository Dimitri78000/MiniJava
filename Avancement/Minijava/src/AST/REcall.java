package AST;
/** REcall : Pexpr e; RidMeth id; Lexpr el; */
public class REcall extends Pexpr {
	public Pexpr e;   
	public RidMeth id;
	public Lexpr el;   
  
	public REcall(Pexpr e, RidMeth id, Lexpr el) {
		super(e,id,el); 
		this.e=e; this.id=id; this.el=el;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}
