/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *ejemplo !expresion.
 * @author magyg
 */
public class Nodo_OpUnario extends Nodo_Expresion {
    private final String operador;
    private final Nodo_Expresion operando;

    public Nodo_OpUnario(String operador, Nodo_Expresion operando, int linea, int columna) {
        super(linea, columna);
        this.operador = operador;
        this.operando = operando;
    }

    public String getOperador() {
        return operador;
    }

    public Nodo_Expresion getOperando() {
        return operando;
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
        return prefijo + "Op:" + operador + "\n" +
               operando.imprimir(prefijo + "  └─ ");
    }
}
