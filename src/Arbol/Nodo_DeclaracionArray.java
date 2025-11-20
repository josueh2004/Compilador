/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;



public class Nodo_DeclaracionArray extends Nodo_Instruccion{
    //private final String tipo;
    private final TipoDato tipos;
    private final String nombre;
    private final List<Nodo_Expresion> dimensiones;

    public Nodo_DeclaracionArray(TipoDato tipo, String nombre, List<Nodo_Expresion> dimensiones, int linea, int columna) {
       super(linea, columna);
        this.tipos = tipo;
        this.nombre = nombre;
        this.dimensiones = dimensiones;
    }

    public TipoDato getTipos() {
        return tipos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Nodo_Expresion> getDimensiones() {
        return dimensiones;
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
        String texto = prefijo + "DeclaracionArray: (Tipo: " + tipos + " Nombre: " + nombre + ")\n";
        texto += prefijo + " └─ Dimensiones:\n";
        for (Nodo_Expresion dim : dimensiones) {
            texto += dim.imprimir( prefijo + "   ");
        }
        return texto;
    }
    
    
    
}
