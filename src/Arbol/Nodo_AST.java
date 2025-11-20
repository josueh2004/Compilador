//clase base que solo asegura que todas las que hereden de ella tengan el 
// metodo imprimir
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.ArrayList;
import java.util.List;


public abstract class Nodo_AST {
    protected TipoDato tipo = TipoDato.DESCONOCIDO;
    protected List<Nodo_AST> hijos = new ArrayList<>();
    protected int linea;   // línea del código fuente
    protected int columna; // columna del código fuente
    

    public Nodo_AST() {
        this.linea = -1;
        this.columna = -1;
    }

    public Nodo_AST(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
       
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public void setTipo(TipoDato tipo) {
        this.tipo = tipo;
    }

    public List<Nodo_AST> getHijos() {
        return hijos;
    }

    public void agregarHijo(Nodo_AST hijo) {
        if (hijo != null) {
            hijos.add(hijo);
        }
    }


    public abstract String imprimir(String prefijo);
}
