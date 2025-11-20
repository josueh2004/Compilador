package Analizador_Lexico;

import static Analizador_Lexico.Tokens.*;


%%

//declaraciones y macros
%public
%class Lexer
%type Tokens
%line
%column

%{
    //guarda los mensajes de error
    public StringBuilder errores = new StringBuilder(); 
    public String lexeme;

    public int getLinea() {
        return this.yyline;
    }

    public int getColumna() {
        return this.yycolumn;
    }
%}

// expresiones regulares 
ID = [a-zA-Z_][0-9a-zA-Z_]*
//NUMERO = [0-9]+(\.[0-9]+)?
NUMERO_REAL = [0-9]+"."[0-9]+
NUMERO = [0-9]+
COMENT_BLOQUE = "/*" [^*]* "*"+ ([^*/][^*]* "*"+)* "/"
COMENT_LINEA = "//" [^\r\n]*
CADENA = \" [^\"\r\n]* \"
ESPACIO = [ \t\r\n]+
%%

//  reglas lexicas
 
<YYINITIAL> {
    /* palabras reservadas */
    "boolean"       { lexeme = yytext() ; return BOOLEAN; }
    "string"        {lexeme = yytext() ; return STRING; }
    "else"          {lexeme = yytext() ; return ELSE; }
    "float"         {lexeme = yytext() ; return FLOAT; }
    "for"           {lexeme = yytext() ; return FOR; }
    "int"           {lexeme = yytext() ; return INT; }
    "if"            {lexeme = yytext() ; return IF; }
    "return"        {lexeme = yytext() ; return RETURN; }
    "void"          {lexeme = yytext() ; return VOID; }
    "while"         {lexeme = yytext() ; return WHILE; }
    "main"          {lexeme = yytext() ; return MAIN; }
    "print"         {lexeme = yytext() ; return PRINT; }
    "read"          {lexeme = yytext() ; return READ; }
    "true"          {lexeme = yytext() ; return TRUE; }
    "false"         {lexeme = yytext() ; return FALSE; }

    /* operadores y delimitadores */
    "("             {lexeme = yytext() ; return IN_PAREN; }
    ")"             {lexeme = yytext() ; return FIN_PAREN; }
    "{"             {lexeme = yytext() ; return IN_LLAVE; }
    "}"             {lexeme = yytext() ; return FIN_LLAVE; }
    "["             {lexeme = yytext() ; return IN_CORCH; }
    "]"             {lexeme = yytext() ; return FIN_CORCH; }
    ";"             {lexeme = yytext() ; return TERMINADOR; }
    ","             {lexeme = yytext() ; return COMMA; }
    "="             {lexeme = yytext() ; return ASIGNACION; }
    ">"             {lexeme = yytext() ; return MAYOR; }
    "<"             {lexeme = yytext() ; return MENOR; }
    "!"             {lexeme = yytext() ; return NEGACION; }
    "=="            {lexeme = yytext() ; return COMPARACION; }
    "<="            {lexeme = yytext() ; return MAYORIGUAL; }
    ">="            {lexeme = yytext() ; return MENORIGUAL; }
    "&&"            {lexeme = yytext() ; return AND; }
    "||"            {lexeme = yytext() ; return OR; }
    "++"            {lexeme = yytext() ; return INCREMENTO; }
    "--"            {lexeme = yytext() ; return DECREMENTO; }
    "+"             {lexeme = yytext() ; return OPE_SUMA; }
    "-"             {lexeme = yytext() ; return OPE_RESTA; }
    "*"             {lexeme = yytext() ; return OPE_MULT; }
    "/"             {lexeme = yytext() ; return OPE_DIV; }

    /*expresiones regulares definidas como macros */
    {ID}            {lexeme = yytext() ; return ID; }
    {NUMERO_REAL}   {lexeme = yytext() ; return NUMERO_REAL; }
    {NUMERO}        {lexeme = yytext() ; return NUMERO; }
    {CADENA}        {lexeme = yytext() ; return STRING_LITERAL; }

    /* ignora comentarios y espacios */
    {COMENT_BLOQUE} { /*no hace nada*/ }
    {COMENT_LINEA}  { /* */ }
    {ESPACIO}        { /* */ }

   . {
    // mensaje de error LEXÍCO
   // System.err.println("Simbolo no reconocido '" + yytext() + "' en linea " + (yyline +1) + ", columna " + (yycolumn));
   // return ERROR; 
    lexeme = yytext();  // guarda el lexema
    errores.append("Símbolo no reconocido '")
           .append(yytext())
           .append("' en línea ")
           .append(yyline + 1)
           .append(", columna ")
           .append(yycolumn + 1)
           .append("\n");
    return ERROR;
}

}