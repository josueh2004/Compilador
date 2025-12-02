package Main;

import Controlador.Acciones;
import Directorio.Directorio;
import InterfazIDE.Interfaz;
import java.io.File;

public class Compilador_Py {
    
 
    
    public static void main(String[] args) {
        // Verificar que los archivos generados existan
        File parserFile = new File("src/Analizador_Lexico/Parser.java");
        File lexerCupFile = new File("src/Analizador_Lexico/LexerCup.java");
        
        if (!parserFile.exists() || !lexerCupFile.exists()) {
            System.err.println("ERROR: Los archivos del analizador léxico y sintáctico no han sido generados.");
            System.err.println("Por favor, ejecute primero la clase Principal para generar los archivos necesarios.");
            System.err.println("Archivos requeridos: Parser.java y LexerCup.java");
            return; // Termina la ejecución si los archivos no existen
        }
        
        Interfaz vista = new Interfaz();
        Directorio dir = new Directorio();
        dir.setInterfaz(vista);
        Acciones acciones = new Acciones(vista, dir);
        acciones.iniciar();
    }}
