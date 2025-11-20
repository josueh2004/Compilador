
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_LlamaMetodo extends Nodo_Expresion {

    private final Nodo_Identificador nombreMetodo;
    private final List<Nodo_Expresion> argumentos;

    public Nodo_LlamaMetodo(Nodo_Identificador nombreMetodo, List<Nodo_Expresion> argumentos, int linea, int columna) {
        super(linea, columna);
        this.nombreMetodo = nombreMetodo;
        this.argumentos = argumentos;
    }

    public Nodo_Identificador getNombreMetodo() {
        return nombreMetodo;
    }

    public List<Nodo_Expresion> getArgumentos() {
        return argumentos;
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
        String texto = prefijo + "LlamadaMetodo\n"
                + nombreMetodo.imprimir(prefijo + "  ├─ Nombre: ");

        if (!argumentos.isEmpty()) {
            texto += prefijo + "  └─ Argumentos:\n";
            for (Nodo_Expresion argumento : argumentos) {
                texto += argumento.imprimir(prefijo + "     ");
            }
        }
        return texto;
    }
}
