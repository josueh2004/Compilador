/*
es para representar el tipo y el nombre de un solo parametro en un metodo
 */
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_Parametro extends Nodo_AST{
    private final TipoDato tipo;
    private final String nombre;
    

    public Nodo_Parametro(String nombre, TipoDato tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public TipoDato getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
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

    
    
    @Override
    public String imprimir(String prefijo) {
        return prefijo + "Parametro (Tipo: " + tipo + " Nombre: " + nombre + ")\n";
    }
    
    
}
