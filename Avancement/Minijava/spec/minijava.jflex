package syntax;
%% 
%include Jflex.include
%include JflexCup.include

White      = [ \t\f] | \R
EOLComment = "//" .*
C89Comment = "/*" [^*]* ("*" ([^*/] [^*]*)?)* "*/"
Ignore     = {White} | {EOLComment} | {C89Comment}
IntegerLiteral  = 0 | [1-9] [0-9]*
Identifier      = [:jletter:] [:jletterdigit:]*
%%
/* Keywords */
"System"        { return TOKEN(SYSTEM); }
"out"           { return TOKEN(OUT); }
"println"       { return TOKEN(PRINTLN); }
"class"         { return TOKEN(CLASS);   }
"main"          { return TOKEN(MAIN);    }
"public"        { return TOKEN(PUBLIC);  }
"static"        { return TOKEN(STATIC);  }
"String"        { return TOKEN(STRING);  }
"void"          { return TOKEN(VOID);    }
/* additional symbols */
"."             { return TOKEN(DOT);     }
";"             { return TOKEN(SEP);     }
"{"             { return TOKEN(LC);      }
"}"             { return TOKEN(RC);      }
"("             { return TOKEN(LP);      }
")"             { return TOKEN(RP);      }
"["             { return TOKEN(LB);      }
"]"             { return TOKEN(RB);      }
/* literals */
{IntegerLiteral} { return TOKEN(LIT_INT,    Integer.parseInt(yytext())); }   
{Identifier}     { return TOKEN(IDENTIFIER, new String(yytext())) ;      }
/* Ignore */
{Ignore}         {}
/* Ramasse Miette*/
[^]              { WARN("Unknown char '"+yytext()+"' "); return TOKEN(error);}
