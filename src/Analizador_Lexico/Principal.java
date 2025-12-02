package Analizador_Lexico;

import java.io.File;

public class Principal {

    public static void main(String[] args) throws Exception {
        // RUTAS ACTUALIZADAS A TU CARPETA ACTUAL
        String ruta1 = "src/Analizador_Lexico/Lexer.flex";
        String ruta2 = "src/Analizador_Lexico/LexerCup.flex";
        String rutaSalidaCup = "src/Analizador_Lexico/";

        String[] rutaS = {
            "-parser", "Parser",
            "-destdir", rutaSalidaCup,
            "src/Analizador_Lexico/Parser.cup"
        };

        generarLexer(ruta1);
        generarLexer(ruta2);
        generarCup(rutaS);

        System.out.println("Lexer y Parser generados correctamente!");
    }

    public static void generarLexer(String ruta) throws Exception {
        String[] argumentos = {ruta};
        jflex.Main.main(argumentos);
    }

    public static void generarCup(String[] rutaS) throws Exception {
        // Ruta absoluta o relativa al directorio actual del proyecto
        File parserFile = new File("src/Analizador_Lexico/Parser.java");
        if (parserFile.exists()) {
            parserFile.delete();
            System.out.println("Parser.java eliminado antes de regenerar.");
        }
        java_cup.Main.main(rutaS);
    }
}
