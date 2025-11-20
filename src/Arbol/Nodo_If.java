package Arbol;

import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_If extends Nodo_Instruccion{
    private final Nodo_Expresion condicion;
    private final List<Nodo_Instruccion> bloqueThen;
    private final List<Nodo_Instruccion> bloqueElse; 

    public Nodo_If(Nodo_Expresion condicion, List<Nodo_Instruccion> bloqueThen, List<Nodo_Instruccion> bloqueElse) {
        this.condicion = condicion;
        this.bloqueThen = bloqueThen;
        this.bloqueElse = bloqueElse;
    }

    public Nodo_Expresion getCondicion() {
        return condicion;
    }

    public List<Nodo_Instruccion> getBloqueThen() {
        return bloqueThen;
    }

    public List<Nodo_Instruccion> getBloqueElse() {
        return bloqueElse;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public List<Nodo_AST> getHijos() {
        return hijos;
    }

    
    @Override
    public String imprimir(String prefijo) {
         String texto = prefijo + "If\n";
        
        // Imprimir la condición
        texto += condicion.imprimir(prefijo + "  ├─ Condicion: ");

        // Imprimir el bloque then
        texto += prefijo + "  ├─ Then:\n";
        for (Nodo_Instruccion instruccion : bloqueThen) {
            texto += instruccion.imprimir(prefijo + "  │    ");
        }

        // Imprimir el bloque else solo si existe
        if (bloqueElse != null && !bloqueElse.isEmpty()) {
            texto += prefijo + "  └─ Else:\n";
            for (Nodo_Instruccion instruccion : bloqueElse) {
                texto += instruccion.imprimir(prefijo + "       ");
            }
        }
        return texto;
    
    }
}
