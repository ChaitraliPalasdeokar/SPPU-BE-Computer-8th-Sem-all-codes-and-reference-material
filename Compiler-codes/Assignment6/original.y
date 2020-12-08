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
	int val;

}
%left '+' '-'
%left '*' '/'
%token <vname> NAME
%type <vname> expression
%%
input : line '\n' input
		| '\n' input
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
	| NAME '*' NAME
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
