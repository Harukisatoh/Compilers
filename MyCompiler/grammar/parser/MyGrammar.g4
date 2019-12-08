/******************************************************
 * A multi-line Javadoc-like comment about my grammar *
 ******************************************************/
grammar MyGrammar;
@header {
package org.myorganization.mycompiler;
}

// The initial nonterminal symbol
myGrammar : program;

/* 
 Parser rules
 */
program             : body
                    ;

body                : (function)+
                    ;

function            : type NAME parameters block
                    ;

type                : TYPEINT           #typeInt
                    | TYPEFLOAT         #typeFloat
                    | TYPEBOOLEAN       #typeBoolean
                    | TYPESTRING        #typeString
                    ;

parameters          : OPNPAR params_decl CLSPAR
                    ;

params_decl         : (type NAME)? (COMMA type NAME)*
                    ;

block               : OPNBR (statement)* CLSBR
                    ;

statement           : var_declaration EOL
                    | var_attrib EOL
                    | in EOL
                    | out EOL
                    | if_statement
                    | for_statement
                    | while_statement
                    | dowhile_statement EOL
                    ;

in                  : READLN OPNPAR CLSPAR
                    ;

out                 : WRITELN OPNPAR STRING CLSPAR
                    | WRITE OPNPAR STRING CLSPAR
                    ;

var_declaration     : type NAME             #varDeclRule
                    | var_decl_and_attrib   #varDeclAndAttribRule
                    ;

var_decl_and_attrib : type NAME ATR expr
                    ;

var_attrib          : NAME ATR expr
                    | NAME ADD ADD
                    | NAME SUB SUB
                    ;

expr                : l=term ADD r=expr         #exprAdd
                    | l=term SUB r=expr         #exprSub
                    | t=term                  #exprTerm
                    ;

term                : l=fact MUL r=term         #termMul
                    | l=fact DIV r=term         #termDiv
                    | l=fact MOD r=term         #termMod
                    | f=fact                  #termFact
                    ;

fact                : NAME                  #factName
                    | (INT | FLOAT)         #factNumber
                    | OPNPAR expr CLSPAR    #factExpr
                    | in                    #factIn
                    ;

if_statement        : IF OPNPAR cond CLSPAR block else_statement?
                    ;

else_statement      : ELSE (if_statement | block)
                    ;

cond                : cond OR cond_and
                    | cond_and       
                    ;

cond_and            : cond_and AND cond_term
                    | cond_term
                    ;

cond_term           : expr relop expr                                          
                    | OPNPAR cond CLSPAR
                    | expr
                    ;

relop               : MOR
                    | LESS 
                    | MOR_EQ 
                    | LESS_EQ 
                    | EQ
                    | NEQ
                    ;

for_statement       : FOR OPNPAR for_decl EOL cond EOL var_attrib CLSPAR block
                    ;

for_decl            : var_decl_and_attrib
                    | var_attrib
                    ;

while_statement     : while_cond block
                    ;

while_cond          : WHILE OPNPAR cond CLSPAR
                    ;

dowhile_statement   : DO block while_cond
                    ;

// Lexer rules
TYPEINT: 'int';
TYPEFLOAT: 'float';
TYPEBOOLEAN: 'boolean';
TYPESTRING: 'string';
OPNPAR: '(';
CLSPAR: ')';
OPNBR: '{';
CLSBR: '}';
COMMA: ',';
EOL: ';';
ATR: '=';
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
MOD: '%';
OR: '||';
AND: '&&';
MOR: '>';
LESS: '<';
MOR_EQ: '>=';
LESS_EQ: '<=';
EQ: '==';
NEQ: '!=';
IF: 'if';
ELSE: 'else';
READLN: 'readln';
WRITELN: 'writeln';
WRITE: 'write';
FOR: 'for';
WHILE: 'while';
DO: 'do';
//STRING: '"' ~('\r' | '\n' | '"')* '"' ;
STRING: '"' (~'"'|'"')* '"';
NAME: [_a-zA-Z][_a-zA-Z0-9]*;
INT: [¬]?[0-9]+;                
FLOAT: [¬]?[0-9]+'.'[0-9]+;     
/*
    The symbol ¬ says that the number is negative, this is necessary because
    the parser doesn't know how to properly handle subtraction expressions
    like (2-5), without that the AST erroneously derives to a positive number
    followed by a negative one.
*/

// Whitespace management
WS: [ \t\r\n]+ -> skip;

// Comment management
COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;