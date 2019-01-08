package main;

class Compil { /** Main */
 public static void main(String[] args){
     if (args.length == 0)         new Compil(DEBUG.INFILE);
     else for(String file : args)  new Compil(file);
 }
    
 Compil(String file) {
     // Analyse lexicale et syntaxique
     // => AST
     AST.ASTNode axiome;
     axiome=new syntax.Syntax(file).axiome;                  
     
     // Analyse Semantique
     // => Arbre Semantique = AST + Decos + Table de symboles
     semantic.SemanticTree semanticTree;
     semanticTree=new semantic.Semantic(axiome).semanticTree;              
     
     // Generation Forme Intermidiaire
     // => Intermediate = IRprog + Table des symboles + Vaiables IR
     generateIR.Intermediate intermed;
     intermed=new generateIR.GenerateIR(semanticTree).intermed;
     
     // "Optimisation"
     // => Allocator
     
     // Generation MIPS
     // => fichier xxx.mips
     String outfile = file.substring(0, file.lastIndexOf(".")) + ".mips";
     new generateMIPS.GenerateMIPS(intermed, outfile);
     
     // Running Mars (Mauvaise idée: pas multifichiers, dependance mars.jar,...)
     /*
       System.out.println("=== RUNNING MARS on " + outfile + "===") ;
       String [] marsargs={ outfile }; 
       new mars.MarsLaunch( marsargs); 
     */
 }
    
}
