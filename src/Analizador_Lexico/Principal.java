package Analizador_Lexico;

import java.io.File;

public class Principal {

    public static void main(String[] args) throws Exception {
        String ruta1 = "C:/Users/Lidia/Downloads/Compilador/src/Analizador_Lexico/Lexer.flex";
        String ruta2 = "C:/Users/Lidia/Downloads/Compilador/src/Analizador_Lexico/LexerCup.flex";
        String rutaSalidaCup = "C:/Users/Lidia/Downloads/Compilador/src/Analizador_Lexico/";

        String[] rutaS = {
            "-parser", "Parser",
            "-destdir", rutaSalidaCup,
            "C:/Users/Lidia/Downloads/Compilador/src/Analizador_Lexico/Parser.cup"
        };

        generarLexer(ruta1);
        generarLexer(ruta2);
        generarCup(rutaS);
    }

    public static void generarLexer(String ruta) throws Exception {
        String[] argumentos = {ruta};
        jflex.Main.main(argumentos);
    }

    public static void generarCup(String[] rutaS) throws Exception {

        File parserFile = new File("C:/Users/Lidia/Downloads/Compilador/src/Analizador_Lexico/Parser.java");
        if (parserFile.exists()) {
            parserFile.delete();
            System.out.println("Parser.java eliminado antes de regenerar.");
        }
        java_cup.Main.main(rutaS);

    }
    public void muestra(){
       // string nimbre= "gg";
    }
}
