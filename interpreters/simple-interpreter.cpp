#include <iostream>
#include <string>
using namespace std;

struct variable {
  char name;
  int value;
};

//DYNAMIC VECTOR TO STORE THE SYMBOL TABLE
//variable symbolTable [5];

//PROTOTYPE
int p();
int x();
int r();
int e();
void match(char c);
int numMatch();
int varMatch();
int isDigit(char c);
int isVar(char c);
int calculate();
void createVar();

string input;

// --- LEXER
int count = 0;
char nextToken(){
  if (count == input.length())
    return EOF;
  return input[count++];
}

// --- PARSER
char lookahead;
int answer;
int lastvalue;
char lastop;
char lastvar;

int p() {
  if(isDigit(lookahead) == 1) {
    numMatch();
    answer = lastvalue;
    r();
  } else if(isVar(lookahead) == 1) {
    varMatch();
    x();
  } else {
    printf("Unexpected character!\n");
  }
  return 0;
}

int x() {
  if(lookahead == '=') {
    //=E
    createVar();
    e();
  } else {
    //R
    //calcular answer se pá
    r();
  }
  return 0;
}

int r() {
  if (lookahead == '-'){
     //-E
     match('-');
     lastop = '-';
     e();
  } else if (lookahead == '+'){
    //+E
    match('+');
    lastop = '+';
    e();
  } else if (lookahead == EOF) {
    //END OF FILE
  } else {
    printf("Unexpected character!\n");
  }
  return 0;
}

int e() {
  if(isDigit(lookahead) == 1) {
    calculate();
    numMatch();
    r();
  } else if(isVar(lookahead) == 1) {
    calculate();
    varMatch();
    r();
  } else {
    printf("Unexpected character!\n");
  }
  return 0;
}

void match(char c) {
  if (lookahead == c) {
    lookahead = nextToken();
  } else {
    printf("Deu errinho!\n");
  }
}

int numMatch() {
  switch (lookahead) {
    case '0': match('0'); lastvalue = 0; break;
    case '1': match('1'); lastvalue = 1; break;
    case '2': match('2'); lastvalue = 2; break;
    case '3': match('3'); lastvalue = 3; break;
    case '4': match('4'); lastvalue = 4; break;
    case '5': match('5'); lastvalue = 5; break;
    case '6': match('6'); lastvalue = 6; break;
    case '7': match('7'); lastvalue = 7; break;
    case '8': match('8'); lastvalue = 8; break;
    case '9': match('9'); lastvalue = 9; break;
    default: printf("Number expected.");
  }
  return 0;
}

int varMatch() {
  switch (lookahead) {
    case 'q': match('q'); lastvar = 'q'; break;
    case 'w': match('w'); lastvar = 'w'; break;
    case 'e': match('e'); lastvar = 'e'; break;
    case 'r': match('r'); lastvar = 'r'; break;
    case 't': match('t'); lastvar = 't'; break;
    case 'y': match('y'); lastvar = 'y'; break;
    case 'u': match('u'); lastvar = 'u'; break;
    case 'i': match('i'); lastvar = 'i'; break;
    case 'o': match('o'); lastvar = 'o'; break;
    case 'p': match('p'); lastvar = 'p'; break;
    case 'a': match('a'); lastvar = 'a'; break;
    case 's': match('s'); lastvar = 's'; break;
    case 'd': match('d'); lastvar = 'd'; break;
    case 'f': match('f'); lastvar = 'f'; break;
    case 'g': match('g'); lastvar = 'g'; break;
    case 'h': match('h'); lastvar = 'h'; break;
    case 'j': match('j'); lastvar = 'j'; break;
    case 'k': match('k'); lastvar = 'k'; break;
    case 'l': match('l'); lastvar = 'l'; break;
    case 'z': match('z'); lastvar = 'z'; break;
    case 'x': match('x'); lastvar = 'x'; break;
    case 'c': match('c'); lastvar = 'c'; break;
    case 'v': match('v'); lastvar = 'v'; break;
    case 'b': match('b'); lastvar = 'b'; break;
    case 'n': match('n'); lastvar = 'n'; break;
    case 'm': match('m'); lastvar = 'm'; break;
    default: printf("Var expected.");
  }
  return 0;
}

int isDigit(char c) {
  switch (c) {
    case '0': return 1; break;
    case '1': return 1; break;
    case '2': return 1; break;
    case '3': return 1; break;
    case '4': return 1; break;
    case '5': return 1; break;
    case '6': return 1; break;
    case '7': return 1; break;
    case '8': return 1; break;
    case '9': return 1; break;
    default: return 0;
  }
}

int isVar(char c) {
  switch (c) {
    case 'q': return 1; break;
    case 'w': return 1; break;
    case 'e': return 1; break;
    case 'r': return 1; break;
    case 't': return 1; break;
    case 'y': return 1; break;
    case 'u': return 1; break;
    case 'i': return 1; break;
    case 'o': return 1; break;
    case 'p': return 1; break;
    case 'a': return 1; break;
    case 's': return 1; break;
    case 'd': return 1; break;
    case 'f': return 1; break;
    case 'g': return 1; break;
    case 'h': return 1; break;
    case 'j': return 1; break;
    case 'k': return 1; break;
    case 'l': return 1; break;
    case 'z': return 1; break;
    case 'x': return 1; break;
    case 'c': return 1; break;
    case 'v': return 1; break;
    case 'b': return 1; break;
    case 'n': return 1; break;
    case 'm': return 1; break;
    default: return 0;
  }
}

int calculate() {
  int aux = lookahead - '0'; //Convert char to int
  if(lastop == '+') {
    answer = answer + aux;
  } else {
    answer = answer - aux;
  }
}

void createVar() {

}

// --- ENTRYPOINT
int main(){
  cout << "$ ";
  getline(cin, input);
  while (!input.empty()){
    lookahead = nextToken();
    p();
    printf("%d\n$", answer);
    getline(cin, input);
    count = 0;
  }
}
