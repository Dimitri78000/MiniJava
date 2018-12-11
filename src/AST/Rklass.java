package AST;
/** Rklass : RidKlass id; RidKlass pid; LklassMember kml;*/		
public class Rklass extends ASTNode {
	public RidKlass id;   
	public RidKlass pid;  // "Object" if no extends, not really used
	public LklassMember kml;
	
	public Rklass(RidKlass id, RidKlass pid, LklassMember kml) {
		super(id,pid,kml); 
		this.id=id; 	this.pid=pid;	this.kml=kml;
		if (pid==null) this.pid = new RidKlass("Object");
	}
	public void accept(ASTVisitor v) { v.visit(this); }
 }
