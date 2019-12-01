/******************************************************
 * A multi-line Javadoc-like comment about my grammar *
 ******************************************************/
grammar MyGrammar;
@header {
package org.myorganization.mycompiler;
}

// Monoline comment about a parser rule
myGrammar : program;

/* 
 A multi-line Java-like comment
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
block: OPNBR (command)+ CLSBR
     ;
command: in EOL
       | out EOL
       ;
in: READLN
  ;
out: WRITELN OPNPAR STRING CLSPAR{ System.out.println($STRING.text); }
   | WRITE OPNPAR STRING CLSPAR { System.out.print($STRING.text); }
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
READLN: 'readln';
WRITELN: 'writeln';
WRITE: 'write';
STRING: '"' ~('\r' | '\n' | '"')* '"' ;
NAME: [_a-zA-Z][_a-zA-Z0-9]*;

// Whitespace management
WS: [ \t\r\n]+ -> skip;