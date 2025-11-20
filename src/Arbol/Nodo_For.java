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
public class Nodo_For extends Nodo_Instruccion {

    private final Nodo_Instruccion inicializacion; // Puede ser null
    private final Nodo_Expresion condicion;
  //  private final Nodo_Expresion paso;             // Puede ser null
    private final Nodo_Instruccion paso;             // Puede ser null
    private final List<Nodo_Instruccion> cuerpo;

    public Nodo_For(Nodo_Instruccion inicializacion, Nodo_Expresion condicion,Nodo_Instruccion paso, List<Nodo_Instruccion> cuerpo, int linea, int columna) {
        super(linea, columna);
        this.inicializacion = inicializacion;
        this.condicion = condicion;
        this.paso = paso;
        this.cuerpo = cuerpo;
    }

    public Nodo_Instruccion getInicializacion() {
        return inicializacion;
    }

    public Nodo_Expresion getCondicion() {
        return condicion;
    }

    public Nodo_Instruccion getPaso() {
        return paso;
    }

    public List<Nodo_Instruccion> getCuerpo() {
        return cuerpo;
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
        String texto = prefijo + "For\n";

        // Imprimir cada parte solo si no es nula
        if (inicializacion != null) {
            texto += inicializacion.imprimir(prefijo + "  ├─ Inicializacion: ");
        } else {
            texto += prefijo + "  ├─ Inicializacion: (vacia)\n";
        }

        texto += condicion.imprimir(prefijo + "  ├─ Condicion: ");

        if (paso != null) {
            texto += paso.imprimir(prefijo + "  ├─ Paso: ");
        } else {
            texto += prefijo + "  ├─ Paso: (vacio)\n";
        }

        texto += prefijo + "  └─ Cuerpo:\n";
        for (Nodo_Instruccion instruccion : cuerpo) {
            texto += instruccion.imprimir(prefijo + "     ");
        }

        return texto;
    }
}
