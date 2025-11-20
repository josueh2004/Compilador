/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;


public class Nodo_OpBinario extends Nodo_Expresion {

    private final Nodo_Expresion izquierda;
    private final Nodo_Expresion derecha;
    private final String operador;

    public Nodo_OpBinario(Nodo_Expresion izquierda, String operador, Nodo_Expresion derecha, int linea, int columna) {
         super(linea, columna);
        this.izquierda = izquierda;
        this.operador = operador;
        this.derecha = derecha;
        agregarHijo(izquierda);
        agregarHijo(derecha);
        //agregan como hijos para permitir recorrido recursivo
    }

    public Nodo_Expresion getIzquierda() {
        return izquierda;
    }

    public String getOperador() {
        return operador;
    }

    public Nodo_Expresion getDerecha() {
        return derecha;
    }

    public boolean esOperadorLogico() {
        return operador.equals("&&") || operador.equals("||") || operador.equals("==") || operador.equals("!=");
    }

    @Override
    public String imprimir(String prefijo) {
        return prefijo + "Operador:" + operador + "\n"
                + izquierda.imprimir(prefijo + "  ├─ ")
                + derecha.imprimir(prefijo + "  └─ ");
    }
}
