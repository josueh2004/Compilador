package Analizador_Lexico;

import java_cup.runtime.Symbol;

%%

// Configuración del analizador léxico
%public
%class LexerCup
%type Symbol
%cup
%line
%column

%{
    // Guarda los errores léxicos detectados
    public StringBuilder errores = new StringBuilder();

    // Método auxiliar para crear símbolos sin valor asociado
    private Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    // Método auxiliar para crear símbolos con valor asociado
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

// Expresiones regulares
ID = [a-zA-Z_][0-9a-zA-Z_]*
/*NUMERO = [0-9]+(\.[0-9]+)?*/
NUMERO_REAL = [0-9]+"."[0-9]+
NUMERO = [0-9]+
COMENT_BLOQUE = "/*" [^*]* "*"+ ([^*/][^*]* "*"+)* "/"
COMENT_LINEA = "//" [^\r\n]*
CADENA = \" [^\"\r\n]* \"
ESPACIO = [ \t\r\n]+

%%

// Palabras reservadas
"boolean"       { return symbol(sym.BOOLEAN, yytext()); }
"string"        { return symbol(sym.STRING, yytext()); }
"else"          { return symbol(sym.ELSE, yytext()); }
"float"         { return symbol(sym.FLOAT, yytext()); }
"for"           { return symbol(sym.FOR, yytext()); }
"int"           { return symbol(sym.INT, yytext()); }
"if"            { return symbol(sym.IF, yytext()); }
"return"        { return symbol(sym.RETURN, yytext()); }
"void"          { return symbol(sym.VOID, yytext()); }
"while"         { return symbol(sym.WHILE, yytext()); }
"main"          { return symbol(sym.MAIN, yytext()); }
"print"         { return symbol(sym.PRINT, yytext()); }
"read"          { return symbol(sym.READ, yytext()); }
//"true"          { return symbol(sym.TRUE, yytext()); }
//"false"         { return symbol(sym.FALSE, yytext()); }
// Literales booleanos (devuelven valores tipo Boolean)
"true"          { return new Symbol(sym.TRUE, yyline, yycolumn, Boolean.TRUE); }
"false"         { return new Symbol(sym.FALSE, yyline, yycolumn, Boolean.FALSE); }


// Operadores y delimitadores
"("             { return symbol(sym.IN_PAREN); }
")"             { return symbol(sym.FIN_PAREN); }
"{"             { return symbol(sym.IN_LLAVE); }
"}"             { return symbol(sym.FIN_LLAVE); }
"["             { return symbol(sym.IN_CORCH); }
"]"             { return symbol(sym.FIN_CORCH); }
";"             { return symbol(sym.TERMINADOR); }
","             { return symbol(sym.COMMA); }
"="             { return symbol(sym.ASIGNACION); }
">"             { return symbol(sym.MAYOR); }
"<"             { return symbol(sym.MENOR); }
"!"             { return symbol(sym.NEGACION); }
"=="            { return symbol(sym.COMPARACION); }
"<="            { return symbol(sym.MENORIGUAL); }
">="            { return symbol(sym.MAYORIGUAL); }
"&&"            { return symbol(sym.AND); }
"||"            { return symbol(sym.OR); }
"++"            { return symbol(sym.INCREMENTO); }
"--"            { return symbol(sym.DECREMENTO); }
"+"             { return symbol(sym.OPE_SUMA); }
"-"             { return symbol(sym.OPE_RESTA); }
"*"             { return symbol(sym.OPE_MULT); }
"/"             { return symbol(sym.OPE_DIV); }

// Identificadores, números y cadenas

{ID}            { return new Symbol(sym.ID, yyline, yycolumn, yytext()); }
{NUMERO}        { return new Symbol(sym.NUMERO, yyline, yycolumn, Integer.parseInt(yytext())); }
{NUMERO_REAL}   { return new Symbol(sym.NUMERO_REAL, yyline, yycolumn, Float.parseFloat(yytext())); }
{CADENA}        { return new Symbol(sym.STRING_LITERAL, yyline, yycolumn, yytext()); }


// Ignorar comentarios y espacios
{COMENT_BLOQUE} { /* Ignorar comentarios */ }
{COMENT_LINEA}  { /* Ignorar comentarios */ }
{ESPACIO}       { /* Ignorar espacios */ }

// Errores léxicos
. {
    errores.append("Error: símbolo no reconocido '" + yytext() + 
                   "' en línea " + (yyline + 1) + 
                   ", columna " + (yycolumn + 1) + "\n");
    return symbol(sym.ERROR, yytext());
}



