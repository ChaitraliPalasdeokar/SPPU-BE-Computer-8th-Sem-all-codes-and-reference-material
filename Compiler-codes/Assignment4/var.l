%{
#include<stdio.h>
void yyerror(char *);
#include "y.tab.h"
%}

%%
void|int|float|char return BUILTIN;
, return COMMA;
; return SC;
"\n" return 0;
")" return RP;
"(" return LP;
[a-zA-Z][a-zA-Z0-9]* return ID;
%%

int yywrap(void) {
return 1;
}


/*

Output-

(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment4$ lex var.l
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment4$ yacc -d var.y
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment4$ gcc y.tab.c lex.yy.c -ll -ly -lm
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment4$ ./a.out
int a;
 valid Declaration

*/
