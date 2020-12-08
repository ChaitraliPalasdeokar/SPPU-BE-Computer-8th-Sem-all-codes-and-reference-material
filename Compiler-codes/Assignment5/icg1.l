ALPHA [A-Za-z]
DIGIT [0-9]
%{
 #include"y.tab.h"
%}

%%
{ALPHA}({ALPHA}|{DIGIT})* return ID;
{DIGIT}+ {yylval=atoi(yytext);return NUM;}
[\n\t] yyterminate();
. return yytext[0];
%%
int yywrap()
{
return 1;
}

/*

(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment5$ ./a.out
enter the expression:a=b+c*d
t0=c * d 
t1=b + t0 
a=t1
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment5$ 

*/
