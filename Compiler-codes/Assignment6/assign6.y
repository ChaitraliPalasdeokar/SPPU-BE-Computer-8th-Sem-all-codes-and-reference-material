%{
	#include<stdio.h>
	#include<ctype.h>
	#include<string.h>
	FILE *fpOut;
	int yylex();
	int yyerror();
	int fcloseall();
%}
%union
{

	char vname[10];

}
%left '+' '-'
%left '*' '/'
%token <vname> NAME
%type <vname> expression
%%
input : line '\n' input
		| '\n' input//t1=c*d
		| ;
line : NAME '=' expression { fprintf(fpOut,"MOV %s, AX\n",$1); }
;
expression: NAME '+' NAME
	{
	fprintf(fpOut,"MOV AX, %s\n",$1);
	fprintf(fpOut,"ADD AX, %s\n",$3);
	}
	| NAME '-' NAME
	{
	fprintf(fpOut,"MOV AX, %s\n",$1);
	fprintf(fpOut,"SUB AX, %s\n",$3);
	}
	| NAME '*' NAME//c*d
	{
	fprintf(fpOut,"MOV AX, %s\n",$1);
	fprintf(fpOut,"MUL AX, %s\n",$3);
	}
	| NAME '/' NAME
	{
	fprintf(fpOut,"MOV AX, %s\n",$1);
	fprintf(fpOut,"DIV AX, %s\n",$3);
	}| NAME
	{
	fprintf(fpOut,"MOV AX, %s\n",$1);
	strcpy($$, $1);
	}
;
%%
FILE *yyin;
int main()
{
	FILE *fpInput;
	fpInput = fopen("input1.c","r");
	if(fpInput=='\0')
	{
	printf("file read error");
	//exit(0);
	}
	fpOut = fopen("output.c","w");
	if(fpOut=='\0')
	{
	printf("file creation error");
	//exit(0);
	}
	yyin = fpInput;
	yyparse();
	fcloseall();
}
int yyerror(char *msg)
{
	printf("%s",msg);
}

/* OUTPUT-
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment6$ lex assign6.l
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment6$ yacc -d assign6.y
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment6$ gcc y.tab.c lex.yy.c -ll
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment6$ ./a.out
NAME token is t1 
token is = 
NAME token is c 
token is * 
NAME token is d 
token is 
 
NAME token is t2 
token is = 
NAME token is b 
token is + 
NAME token is t1 
token is 
 
NAME token is t3 
token is = 
NAME token is e 
token is / 
NAME token is f 
token is 
 
NAME token is t4 
token is = 
NAME token is t2 
token is + 
NAME token is t3 
token is 
 
NAME token is a 
token is = 
NAME token is t4 
token is 
 
(base) dell@dell-Inspiron-15-3567:~/BEALL2/Compiler/Compiler/Assignment6$ 
*/

