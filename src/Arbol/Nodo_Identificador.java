package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_Identificador extends Nodo_Expresion {

    private final String nombre;

    public Nodo_Identificador(String nombre, int linea,int columna) {
       super(linea, columna);
        this.nombre = nombre;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public List<Nodo_AST> getHijos() {
        return hijos;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
    
    
   

    public String getNombre() {
        return nombre;
    }

    @Override
    public String imprimir(String prefijo) {
        return prefijo + "Identificador( " + nombre + ")\n";
    }
}
