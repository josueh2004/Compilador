/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

/**
 *
 * @author magyg
 */
public class Nodo_StringLiteral extends Nodo_Expresion{
    private final  String valor;

    public Nodo_StringLiteral(String valor, int linea, int columna) {
        super(linea, columna);
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
    
    
    @Override
    public String imprimir(String prefijo){
        return prefijo + "String_Literal( " + valor + ")\n";
    }
}
