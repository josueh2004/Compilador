/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

import Analizador_Semantico.TipoDato;

/**
 *
 * @author magyg
 */
public class Nodo_Numero extends Nodo_Expresion{
    private final int valor;

    

    public Nodo_Numero(int valor, int linea, int columna) {
        super(linea, columna);
        this.valor = valor;
        this.tipo = TipoDato.INT;
       
    }

    public int getValor() {
        return valor;
    }
    
    

    public String imprimir(String prefijo){
        return prefijo + "Numero( " + valor + ")\n";
    }
    
}
