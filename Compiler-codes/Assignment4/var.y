%{
#include<stdio.h>
int yylex(void);
void yyerror(char *);
int flag=0;
%}

%token COMMA ID BUILTIN SC RP LP

%%
var:BUILTIN EXP SC {flag=1;}
;
EXP:EXP COMMA ID| ID |ID LP BUILTIN ID RP {flag =1;}
;
%%


int main()
{ 

yyparse(); 
if(flag==1)
{
printf("valid Declaration\n");
}
else
{ printf("Invalid Declaration\n");
}
return 0;
}


void yyerror(char *s) 
{
printf(" ");
}






