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
public class Nodo_Return extends Nodo_Instruccion {

    private final Nodo_Expresion expresion; // Puede ser null

    public Nodo_Return(Nodo_Expresion valor, int linea, int columna) {
        super(linea, columna);
        this.expresion = valor;
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
        String texto = prefijo + "Return\n";

        // Solo imprimimos el valor de retorno si existe
        if (expresion != null) {
            texto += expresion.imprimir(prefijo + "  └─ Valor: ");
        }

        return texto;
    }
}
