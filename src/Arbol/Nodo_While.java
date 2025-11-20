package Arbol;

import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_While extends Nodo_Instruccion{
    private final Nodo_Expresion condicion;
    private final List<Nodo_Instruccion> instrucciones;

    public Nodo_While(Nodo_Expresion condicion, List<Nodo_Instruccion> bloque, int linea, int columna) {
        super(linea, columna);
        this.condicion = condicion;
        this.instrucciones = bloque;
    }

    public Nodo_Expresion getCondicion() {
        return condicion;
    }

    public List<Nodo_Instruccion> getInstrucciones() {
        return instrucciones;
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
    public List<Nodo_AST> getHijos() {
        return hijos;
    }
    
    
    @Override
    public String imprimir(String prefijo) {
         String texto = prefijo + "While\n";
        
        texto += condicion.imprimir(prefijo + "  ├─ Condicion: ");

        texto += prefijo + "  └─ Cuerpo:\n";
        for (Nodo_Instruccion instruccion : instrucciones) {
            texto += instruccion.imprimir(prefijo + "     ");
        }
        return texto;
    
    }
}
