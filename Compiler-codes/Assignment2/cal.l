
%{
#include<stdio.h>
#include "y.tab.h"
//extern int yylval;
%}

%%

[0-9]+ { yylval=atoi(yytext); return NUMBER; }

[\n] return 0;
. return yytext[0];

%%

int yywrap()
{
return 1;
}


/*

Output-

(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler$ lex cal.l
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler$ yacc -d cal.y
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler$ gcc y.tab.c lex.yy.c -ll -ly -lm       
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler$ ./a.out
2+3

Result=5

Entered arithmetic expression is Valid

(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler$ 

*/
