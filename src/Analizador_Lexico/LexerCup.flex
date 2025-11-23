package Analizador_Lexico;

import java_cup.runtime.Symbol;

%%
// -------------------------------
//   Configuración general
// -------------------------------
%public
%class LexerCup
%type Symbol
%cup
%line
%column

%{
    public StringBuilder errores = new StringBuilder();

    public Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    public Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

// -------------------------------
//   Expresiones regulares
// -------------------------------
LETRA = [a-zA-Z_]
DIGITO = [0-9]
ID = {LETRA}({LETRA}|{DIGITO})*

NUM_INT = {DIGITO}+
NUM_REAL = {DIGITO}+"."{DIGITO}+

ESPACIO = [ \t\r\n]+

STRING_LITERAL = \"([^\"\\]|\\.)*\"


// -------------------------------
//   PALABRAS RESERVADAS
// -------------------------------
%%
{ESPACIO}   { /* ignorar */ }

"main"      { return symbol(sym.MAIN, yytext()); }
"int"       { return symbol(sym.INT, yytext()); }
"float"     { return symbol(sym.FLOAT, yytext()); }
"if"        { return symbol(sym.IF, yytext()); }
"else"      { return symbol(sym.ELSE, yytext()); }
"print"     { return symbol(sym.PRINT, yytext()); }
"boolean"   { return symbol(sym.BOOLEAN, yytext()); }
"string"    { return symbol(sym.STRING, yytext()); }
"return"    { return symbol(sym.RETURN, yytext()); }
"void"      { return symbol(sym.VOID, yytext()); }
"while"     { return symbol(sym.WHILE, yytext()); }
"for"       { return symbol(sym.FOR, yytext()); }
"read"      { return symbol(sym.READ, yytext()); }
"true"      { return symbol(sym.TRUE, yytext()); }
"false"     { return symbol(sym.FALSE, yytext()); }


// -------------------------------
//   OPERADORES Y SÍMBOLOS
// -------------------------------
"="     { return symbol(sym.ASIGNACION); }
";"     { return symbol(sym.TERMINADOR); }
","     { return symbol(sym.COMMA); }

"("     { return symbol(sym.IN_PAREN); }
")"     { return symbol(sym.FIN_PAREN); }
"{"     { return symbol(sym.IN_LLAVE); }
"}"     { return symbol(sym.FIN_LLAVE); }
"["     { return symbol(sym.IN_CORCH); }
"]"     { return symbol(sym.FIN_CORCH); }

">"     { return symbol(sym.MAYOR); }
"<"     { return symbol(sym.MENOR); }

"=="    { return symbol(sym.COMPARACION); }
"!="    { return symbol(sym.COMPARACION); }   // tu gramática usa COMPARACION
"<="    { return symbol(sym.MENORIGUAL); }
">="    { return symbol(sym.MAYORIGUAL); }

"!"     { return symbol(sym.NEGACION); }
"&&"    { return symbol(sym.AND); }
"||"    { return symbol(sym.OR); }

"++"    { return symbol(sym.INCREMENTO); }
"--"    { return symbol(sym.DECREMENTO); }

"+"     { return symbol(sym.OPE_SUMA); }
"-"     { return symbol(sym.OPE_RESTA); }
"*"     { return symbol(sym.OPE_MULT); }
"/"     { return symbol(sym.OPE_DIV); }


// -------------------------------
//   LITERALES Y IDENTIFICADORES
// -------------------------------
{NUM_REAL}  { return symbol(sym.NUMERO_REAL, yytext()); }
{NUM_INT}   { return symbol(sym.NUMERO, yytext()); }
{ID}        { return symbol(sym.ID, yytext()); }

{STRING_LITERAL} {
    return symbol(sym.STRING_LITERAL, yytext());
}


// -------------------------------
//   ERRORES
// -------------------------------
\"[^\"\n\r]* {
    errores.append("Error léxico: cadena sin cerrar en línea "
        + (yyline + 1) + ", columna " + (yycolumn + 1) + "\n");
    return symbol(sym.ERROR, yytext());
}

\" {
    errores.append("Símbolo no reconocido '\"' en línea "
        + (yyline + 1) + ", columna " + (yycolumn + 1) + "\n");
    return symbol(sym.ERROR, yytext());
}

// Cualquier otro símbolo no válido
[^] {
    errores.append("Símbolo no reconocido '" + yytext() + "' en línea "
        + (yyline + 1) + ", columna " + (yycolumn + 1) + "\n");
    return symbol(sym.ERROR, yytext());
}


