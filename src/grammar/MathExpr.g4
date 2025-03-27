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
