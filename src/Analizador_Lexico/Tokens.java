package Analizador_Lexico;

public enum Tokens {
    // palabras reservadas
    BOOLEAN,
    STRING,
    ELSE,
    FLOAT,
    FOR,
    INT,
    IF,
    RETURN,
    VOID,
    WHILE,
    MAIN, 
    PRINT,
    READ,
    
    // literales
    TRUE,
    FALSE,
    NUMERO,
    STRING_LITERAL,
    NUMERO_REAL,
    
    // operadores
    ASIGNACION,
    MAYOR,
    MENOR,
    NEGACION,
    COMPARACION,
    MAYORIGUAL,
    MENORIGUAL,
    AND,
    OR,
    INCREMENTO,
    DECREMENTO,
    OPE_SUMA,
    OPE_RESTA,
    OPE_MULT,
    OPE_DIV,
    
    //delimitadores
    IN_PAREN,
    FIN_PAREN,
    IN_LLAVE,
    FIN_LLAVE,
    IN_CORCH,
    FIN_CORCH,
    TERMINADOR,
    COMMA,
   

    //identificador
    ID,
    
//   error
    ERROR 
}