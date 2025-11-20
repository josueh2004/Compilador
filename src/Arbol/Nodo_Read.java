/*
Guarda el identificador de la variable en la que se almacenara el valor leido desde la entrada.
 */
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_Read extends Nodo_Instruccion{

    private final Nodo_Identificador destino;

    public Nodo_Read(Nodo_Identificador destino) {
        this.destino = destino;
    }

    public Nodo_Identificador getDestino() {
        return destino;
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
        return prefijo + "Read\n"
                + destino.imprimir(prefijo + "  └─ Destino: ");
    }
}
