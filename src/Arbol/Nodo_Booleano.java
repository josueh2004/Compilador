
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

public class Nodo_Booleano extends Nodo_Expresion{
    private final boolean valor;
    private final int linea;
    private final int columna;

    public Nodo_Booleano(boolean valor, int linea, int columna) {
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public boolean isValor() {
        return valor;
    }

    @Override
    public int getLinea() {
        return linea;
    }

    @Override
    public int getColumna() {
        return columna;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public List<Nodo_AST> getHijos() {
        return hijos;
    }

    
    public boolean getValor() {
        return valor;
    }
    
    

    public String imprimir(String prefijo){
        return prefijo + "Booleano( " + valor + ")\n";
    }
}
