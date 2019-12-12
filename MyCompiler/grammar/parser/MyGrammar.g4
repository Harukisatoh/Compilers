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
program             :  body
                    ;

body                : main (function_decl)*
                    ;

main                : TYPEINT MAIN OPNPAR CLSPAR block
                    ;

function_decl       : type NAME parameters block
                    ;

function_call       : NAME OPNPAR call_params? CLSPAR
                    ;

call_params         : (expr) (COMMA expr)*
                    ;

type                : TYPEINT
                    | TYPEFLOAT
                    | TYPEBOOLEAN
                    | TYPESTRING
                    ;

parameters          : OPNPAR params_decl CLSPAR
                    ;

params_decl         : (var_declaration)? (COMMA var_declaration)*
                    ;

block               : OPNBR (statement)* CLSBR
                    ;

statement           : var_declaration EOL
                    | var_decl_and_attrib EOL
                    | var_attrib EOL
                    | in EOL
                    | out EOL
                    | if_statement
                    | for_statement
                    | while_statement
                    | dowhile_statement EOL
                    | function_call EOL
                    ;

in                  : READLN OPNPAR CLSPAR
                    ;

out                 : WRITELN OPNPAR out_params CLSPAR #writeln 
                    | WRITE OPNPAR out_params CLSPAR   #write
                    ;

out_params          : out_params_terminals (COMMA out_params_terminals)*
                    ;

out_params_terminals    : STRING                                        #out_params_string
                        | expr                                          #out_params_expr
                        ;

var_declaration     : type NAME
                    ;

var_decl_and_attrib : var_declaration ATR expr
                    ;

var_attrib          : NAME ATR expr             #var_attrib_expr
                    | NAME ADD ADD              #var_attrib_plus
                    | NAME SUB SUB              #var_attrib_sub
                    ;

expr                : l=term ADD r=expr         #expr_add
                    | l=term SUB r=expr         #expr_sub
                    | t=term                    #expr_term
                    ;

term                : l=fact MUL r=term         #term_mul
                    | l=fact DIV r=term         #term_div
                    | l=fact MOD r=term         #term_mod
                    | f=fact                  #term_fact
                    ;

fact                : NAME                  #fact_name
                    | (INT | FLOAT)         #fact_number
                    | OPNPAR expr CLSPAR    #fact_expr
                    | in                    #fact_in
                    ;

if_statement        : IF OPNPAR cond CLSPAR block else_statement?
                    ;

else_statement      : ELSE (if_statement | block)
                    ;

cond                : l=cond OR r=cond_and              #cond_or
                    | cond_and                      #cond_cont
                    ;

cond_and            : l=cond_and AND r=cond_term    #cond_a
                    | cond_term                     #cond_t
                    ;

cond_term           : l=expr relop r=expr       #cond_term_relop                                          
                    | OPNPAR cond CLSPAR        #cond_term_par
                    | expr                      #cond_term_expr
                    ;

relop               : MOR
                    | LESS 
                    | MOR_EQ 
                    | LESS_EQ 
                    | EQ
                    | NEQ
                    ;

for_statement       : FOR OPNPAR for_decl EOL cond EOL for_attrib CLSPAR block
                    ;

for_decl            : var_decl_and_attrib (COMMA var_decl_and_attrib)*
                    ;

for_attrib          : var_attrib (COMMA var_attrib)*
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
MAIN: 'main';
IF: 'if';
ELSE: 'else';
READLN: 'readln';
WRITELN: 'writeln';
WRITE: 'write';
FOR: 'for';
WHILE: 'while';
DO: 'do';
//STRING: '"' ~('\r' | '\n' | '"')* '"' ;
STRING: '"' ~('"')* '"';
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