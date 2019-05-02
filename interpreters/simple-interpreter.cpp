#include <iostream>
using namespace std;

struct variable {
  char name;
  int value;
};

//DYNAMIC VECTOR TO STORE THE SYMBOL TABLE

//PROTOTYPE
int p();
int x();
int r();
int e();
void match(char c);
int numMatch();
int varMatch();
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
  if(isdigit(lookahead) != 0) {
    numMatch();
    answer = lastvalue;
    r();
  } else if(isalpha(lookahead) != 0) {
    varMatch();
    x();
  } else {
    cout << "Unexpected character!" << "\n";
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
    cout << "Unexpected character!" << "\n";
  }
  return 0;
}

int e() {
  if(isdigit(lookahead) != 0) {
    calculate();
    numMatch();
    r();
  } else if(isalpha(lookahead) != 0) {
    calculate();
    varMatch();
    r();
  } else {
    cout << "Unexpected character!" << "\n";
  }
  return 0;
}

void match(char c) {
  if (lookahead == c) {
    lookahead = nextToken();
  } else {
    cout << "Deu errinho!" << "\n";
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
    default: cout << "Number expected!" << "\n";
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
    default: cout << "Var expected!" << "\n";
  }
  return 0;
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
  //Work In Progress
}

// --- ENTRYPOINT
int main(){
  cout << "$ ";
  getline(cin, input);
  while (!input.empty()){
    lookahead = nextToken();
    p();
    cout << answer << "\n\n";
    cout << "$ ";
    getline(cin, input);
    count = 0;
  }
}
