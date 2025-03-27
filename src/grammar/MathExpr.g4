grammar MathExpr;

// Główna reguła - cały plik może zawierać wiele równań
prog: statement (';' statement)* ;

statement
    : expr (relop expr)?                 # ExpressionStmt
    ;

// Wyrażenia matematyczne
expr
    : expr op=('+'|'-') expr             # AddSubExpr
    | expr op=('*'|'/') expr             # MulDivExpr
    | expr '^' expr                      # PowExpr
    | '|' expr '|'                       # AbsExpr
    | func '(' expr ')'                  # FuncExpr
    | '(' expr ')'                       # ParenExpr
    | NUMBER                             # NumberExpr
    | VARIABLE                           # VarExpr
    ;

// Funkcje matematyczne
func: 'sqrt' | 'log' | 'ln' | 'sin' | 'cos' | 'tan';

// Relacje logiczne
relop: '=' | '!=' | '<' | '<=' | '>' | '>=';

// Tokeny
NUMBER: [0-9]+ ('.' [0-9]+)? | '.' [0-9]+;
VARIABLE: [a-zA-Z]+;
WS: [ \t\r\n]+ -> skip;


/*
grammar MathExpr;

// Główna reguła - wszystkie dozwolone wyrażenia
expr
    : expr op=('+'|'-') expr    # AddSubExpr
    | expr op=('*'|'/') expr    # MulDivExpr
    | expr '^' expr             # PowExpr
    | func '(' expr ')'         # FuncExpr
    | '(' expr ')'              # ParenExpr
    | NUMBER                    # NumberExpr
    | VARIABLE                  # VarExpr
    ;

// Dozwolone funkcje matematyczne
func: 'sqrt' | 'log' | 'sin' | 'cos';

// Tokeny (słowa kluczowe)
NUMBER: [0-9]+ ('.' [0-9]+)?;
VARIABLE: [a-zA-Z]+;
WS: [ \t\r\n]+ -> skip;
*/