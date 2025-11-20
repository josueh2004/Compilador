/*
contiene las instrucciones del bloque.
 */
package Arbol;

import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_Programa extends Nodo_AST {

    private final List<Nodo_Instruccion> instruccionesMain;
    private final List<Nodo_Metodo> metodos;

    public Nodo_Programa(List<Nodo_Instruccion> instruccionesMain, List<Nodo_Metodo> metodos) {
        super();
        this.instruccionesMain = instruccionesMain;
        this.metodos = metodos;
        
        // se agregan las instrucciones y métodos con hijos del nodo
        if (instruccionesMain != null) {
            for (Nodo_Instruccion instruccion : instruccionesMain) {
                agregarHijo(instruccion);
            }
        }

        if (metodos != null) {
            for (Nodo_Metodo metodo : metodos) {
                agregarHijo(metodo);
            }
        }
    
    }
    
    
     public Nodo_Programa(List<Nodo_Instruccion> instruccionesMain, List<Nodo_Metodo> metodos, int linea, int columna) {
        super(linea, columna);
        this.instruccionesMain = instruccionesMain;
        this.metodos = metodos;
    }

    public List<Nodo_Instruccion> getInstruccionesMain() {
        return instruccionesMain;
    }

    public List<Nodo_Metodo> getMetodos() {
        return metodos;
    }
     
     

    @Override
    public String imprimir(String prefijo) {
        String texto = prefijo + "Programa\n";

        texto += prefijo + "├─ MAIN:\n";
        for (Nodo_Instruccion instruccion : instruccionesMain) {
            texto += instruccion.imprimir(prefijo + "│  ");
        }

        texto += prefijo + "└─ METODOS:\n";
        for (Nodo_Metodo metodo : metodos) {
            texto += metodo.imprimir(prefijo + "   ");
        }
        return texto;
    }

}
