package AST;
/** RIblock : LmethMember mml; */
public class RIblock extends Pinst {
	public LmethMember mml;

	public RIblock(LmethMember mml) { 
		super(mml); 
		this.mml=mml;
	}
	public void accept(ASTVisitor v) { v.visit(this); }
}

