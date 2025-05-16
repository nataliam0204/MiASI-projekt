grammar MathExpr;

// Główna reguła — cały plik może zawierać wiele równań
prog: statement (';' statement)* ;

statement
    : expr (relop expr)?                 # ExpressionStmt
    ;

expr
    : expr '^' expr                      # PowExpr
    | expr op=(MUL | DIV | COLON) expr  # MulDivExpr
    | expr op=(ADD | SUB) expr          # AddSubExpr
    | expr ELLIPSIS_H expr              # EllipsisHExpr
    | expr ELLIPSIS_V expr              # EllipsisVExpr
    | expr ELLIPSIS_D expr              # EllipsisDExpr
    | '|' expr '|'                      # AbsExpr
    | func '(' expr ')'                 # FuncExpr
    | '(' expr ')'                      # ParenExpr
    | '[' row (',' row)* ']'            # MatrixExpr
    | ELLIPSIS_H                        # EllipsisHorizontalExpr
    | ELLIPSIS_V                        # EllipsisVerticalExpr
    | ELLIPSIS_D                        # EllipsisDiagonalExpr
    | NUMBER                            # NumberExpr
    | VARIABLE                          # VarExpr
    ;

row: expr+ ;

// Funkcje matematyczne
func: 'sqrt' | 'log' | 'ln' | 'sin' | 'cos' | 'tan';

// Operatory relacji
relop: '=' | '!=' | '<' | '<=' | '>' | '>=';


ELLIPSIS_H: '...';
ELLIPSIS_V: ':..';
ELLIPSIS_D: '\\..';
// Tokeny operatorów — konieczne, by działało getType() w Java
MUL   : '*' ;
DIV   : '/' ;
COLON : ':' ;   // dodano nowy token dla operatora dzielenia ukośnikiem
ADD   : '+' ;
SUB   : '-' ;

// Inne tokeny
NUMBER: [0-9]+ ('.' [0-9]+)? | '.' [0-9]+ ;
VARIABLE: [a-zA-Z]+ ;
WS: [ \t\r\n]+ -> skip ;