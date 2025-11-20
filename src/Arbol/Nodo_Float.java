/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *
 * @author Lidia
 */
public class Nodo_Float extends Nodo_Expresion{
    private final  float valor;
  
    
    public Nodo_Float(float valor, int linea, int columna) {  
        super(linea, columna);
        this.valor = valor;      
        this.tipo = TipoDato.FLOAT;
    }

    public float getValor() {
        return valor;
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

    public List<Nodo_AST> getHijos() {
        return hijos;
    }


    
    
    
    
    
    @Override
    public String imprimir(String prefijo) {
          return prefijo + "Real (" + valor + ")\n";
    }
}
