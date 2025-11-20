/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_ExpresionST extends Nodo_Instruccion{
    private final Nodo_Expresion expresion;

    public Nodo_ExpresionST(Nodo_Expresion expresion) {
        this.expresion = expresion;
    }

    public Nodo_Expresion getExpresion() {
        return expresion;
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
        // Imprime que es una 'Expresion-Instruccion' y luego la expresión contenida.
        return prefijo + "ExpresionStatement\n" +
               expresion.imprimir(prefijo + "  └─ ");
    }
}
