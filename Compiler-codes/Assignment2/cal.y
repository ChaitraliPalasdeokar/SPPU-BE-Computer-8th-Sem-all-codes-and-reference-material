%{
#include<stdio.h>
int flag=0;
%}

%token NUMBER
%left '+' '-'
%left '*' '/' '%'
%left '(' ')'

%%

ArithmeticExpression: E{

         printf("\nResult=%d\n",$$);
         return 0;
        };

E:E'+'E {$$=$1+$3;}
 |E'-'E {$$=$1-$3;}
 |E'*'E {$$=$1*$3;}
 |E'/'E {$$=$1/$3;}
 |E'%'E {$$=$1%$3;}
 |'('E')' {$$=$2;}
 | NUMBER {$$=$1;}
;

%%
int main()
{
  yyparse();
  if(flag==0)
   printf("\nEntered arithmetic expression is Valid\n\n");
  return 0;
}

int yyerror()
{
   printf("\nEntered arithmetic expression is Invalid\n\n");
   flag=1;
   return 0;
}


/*
user@user-HP-EliteBook-8460p:~$ cd Desktop
user@user-HP-EliteBook-8460p:~/Desktop$ lex cal.l
user@user-HP-EliteBook-8460p:~/Desktop$ yacc -d cal.y
user@user-HP-EliteBook-8460p:~/Desktop$ cc lex.yy.c y.tab.c
user@user-HP-EliteBook-8460p:~/Desktop$ ./a.out
4+5

Result=9

Entered arithmetic expression is Valid
user@user-HP-EliteBook-8460p:~/Desktop$ ./a.out
4+

Entered arithmetic expression is Invalid
user@user-HP-EliteBook-8460p:~/Desktop$ ./a.out
+5

Entered arithmetic expression is Invalid
*/
