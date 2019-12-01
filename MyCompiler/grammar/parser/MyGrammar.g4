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
 Nonterminal symbols
 */
program: body
       ;
body: (function)+
    ;
function: type NAME parameters block
        ;
type: TYPEINT
    | TYPEFLOAT
    | TYPEBOOLEAN
    | TYPESTRING
    ;
parameters: OPNPAR parameters_declaration CLSPAR
          ;
parameters_declaration: (type NAME)? (COMMA type NAME)*
                      ;
block: OPNBR (statement)+ CLSBR
     ;
statement: var_declaration EOL
         | var_assignment EOL
         | in EOL
         | out EOL
         ;
in: READLN OPNPAR CLSPAR
  ;
out: WRITELN OPNPAR STRING CLSPAR
   | WRITE OPNPAR STRING CLSPAR
   ;
var_declaration: type NAME
               ;
var_assignment: NAME ATR expr
              ;
expr              : term ADD expr 
                  | term SUB expr
		  | term
                  ;
term              : fact MUL term 
                  | fact DIV term 
                  | fact MOD term
		  | fact
                  ;
fact              : NAME 
                  | number 
                  | OPNPAR expr CLSPAR
                  | in
                  ;
number: INT
      | FLOAT
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
READLN: 'readln';
WRITELN: 'writeln';
WRITE: 'write';
STRING: '"' ~('\r' | '\n' | '"')* '"' ;
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