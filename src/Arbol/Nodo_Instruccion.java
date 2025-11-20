/*
agrupa todos los nodos que representan una accion o una sentencia.
 */
package Arbol;

/**
 *
 * @author magyg
 */
public abstract class Nodo_Instruccion extends Nodo_AST {

    public Nodo_Instruccion() {
        super();
    }

    public Nodo_Instruccion(int linea, int columna) {
        super(linea, columna);
    }
    
}
