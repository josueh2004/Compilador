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
public class Nodo_Print extends Nodo_Instruccion{
    private final Nodo_Expresion expresion;

    public Nodo_Print(Nodo_Expresion expresion) {
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
        // Imprime el nombre de la instrucción y luego, recursivamente, su contenido.
        return prefijo + "Print\n" +
               expresion.imprimir(prefijo + "  └─ ");
    }
}
