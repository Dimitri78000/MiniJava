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
BooleanLiteral  = "true" | "false"

%%
/* Keywords */
"boolean"       { return TOKEN(BOOLEAN); }
"class"         { return TOKEN(CLASS);   }
"else"          { return TOKEN(ELSE);    } 
"extends"       { return TOKEN(EXTENDS); }
"if"            { return TOKEN(IF);      }
"int"           { return TOKEN(INT);     }
"main"          { return TOKEN(MAIN);    }
"new"           { return TOKEN(NEW);     }
"out"           { return TOKEN(OUT); }
"println"       { return TOKEN(PRINTLN); }
"public"        { return TOKEN(PUBLIC);  }
"return"        { return TOKEN(RETURN);  }
"static"        { return TOKEN(STATIC);  }
"String"        { return TOKEN(STRING);  }
"System"        { return TOKEN(SYSTEM); }
"this"          { return TOKEN(THIS);    }
"void"          { return TOKEN(VOID);    }
"while"         { return TOKEN(WHILE);   }
/* operators */
"="             { return TOKEN(ASSIGN);  }
"+"             { return TOKEN(PLUS);    }
"-"             { return TOKEN(MINUS);   }
"*"             { return TOKEN(TIMES);   }
"!"             { return TOKEN(NOT);     }
"<"             { return TOKEN(LESS);    }
"&&"            { return TOKEN(AND);     }
/* additional symbols */
"."             { return TOKEN(DOT);     }
","             { return TOKEN(COMMA);   }
";"             { return TOKEN(SEP);     }
"{"             { return TOKEN(LC);      }
"}"             { return TOKEN(RC);      }
"("             { return TOKEN(LP);      }
")"             { return TOKEN(RP);      }
"["             { return TOKEN(LB);      }
"]"             { return TOKEN(RB);      }
/* literals */
{BooleanLiteral} { return TOKEN(LIT_BOOL,   Boolean.parseBoolean(yytext())); }
{IntegerLiteral} { return TOKEN(LIT_INT,    Integer.parseInt(yytext()));     }  
{Identifier}     { return TOKEN(IDENTIFIER, new String(yytext())) ;          }
/* Ignore */
{Ignore} {}
/* Ramasse Miette*/
[^]      { WARN("Unknown char '"+yytext()+"' "); return TOKEN(error);}