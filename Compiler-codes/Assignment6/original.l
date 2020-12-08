%{
#include"stdio.h"
#include"y.tab.h"
%}
%%
[a-z][a-zA-Z0-9]*|[0-9]+ { strcpy(yylval.vname,yytext);printf("text is %s \n",yytext); return NAME; }
[ |\t] {printf("text is %s \n",yytext);}
.|\n { return yytext[0]; }
%%


