
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;


public class Nodo_OpPostfija extends Nodo_Expresion {
    private final Nodo_Expresion operando;
    private final String operador;

   
    public Nodo_OpPostfija(Nodo_Expresion operando, String operador, int linea, int columna) {
        super(linea, columna);
        this.operando = operando;
        this.operador = operador;
    }

    

    public Nodo_Expresion getOperando() {
        return operando;
    }

    public String getOperador() {
        return operador;
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
    
    @Override
    public String imprimir(String prefijo) {
        // Muestra el operando primero y luego el operador para indicar que es postfijo
        return prefijo + "Op:Postfija(" + operador + ")\n" +
               operando.imprimir(prefijo + "  └─ ");
    }
    
}
