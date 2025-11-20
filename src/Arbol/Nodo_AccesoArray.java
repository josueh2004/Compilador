
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;


public class Nodo_AccesoArray extends Nodo_Expresion {

    private final Nodo_Identificador nombreArray;
    private final List<Nodo_Expresion> indices;
   

    public Nodo_AccesoArray(Nodo_Identificador nombreArray, List<Nodo_Expresion> indices, int linea, int columna) {
        super(linea,columna);
        this.nombreArray = nombreArray;
        this.indices = indices;
        this.linea = linea;
        this.columna = columna;
    }
    
    
    
    public Nodo_AccesoArray(Nodo_Identificador nombreArray, List<Nodo_Expresion> indices) {
        this.nombreArray = nombreArray;
        this.indices = indices;
    }

    public Nodo_Identificador getNombreArray() {
        return nombreArray;
    }

    public List<Nodo_Expresion> getIndices() {
        return indices;
    }

    public TipoDato getTipo() {
        return tipo;
    }

    public List<Nodo_AST> getHijos() {
        return hijos;
    }

    @Override
    public int getLinea() {
        return linea;
    }

    @Override
    public int getColumna() {
        return columna;
    }
    

    @Override
    public String imprimir(String prefijo) {
        String texto = prefijo + "AccesoArray\n"
                + nombreArray.imprimir(prefijo + "  ├─ Nombre: ");

        texto += prefijo + "  └─ Indices:\n";
        for (Nodo_Expresion indice : indices) {
            texto += indice.imprimir(prefijo + "     ");
        }
        return texto;
    }
}
