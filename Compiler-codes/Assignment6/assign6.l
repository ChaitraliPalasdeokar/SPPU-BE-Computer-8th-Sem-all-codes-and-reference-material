%{
#include"stdio.h"
#include"y.tab.h"
%}
%%
[a-z][a-zA-Z0-9]*|[0-9]+ { strcpy(yylval.vname,yytext);printf("NAME token is %s \n",yytext); return NAME; }
.|\n { printf("token is %s \n",yytext);return yytext[0]; }
%%


