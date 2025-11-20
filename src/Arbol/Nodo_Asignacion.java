
package Arbol;

/**
 *
 * @author magyg
 */
public class Nodo_Asignacion extends Nodo_Instruccion {

    private final Nodo_Expresion destino; //izquierda
    private final Nodo_Expresion valor;   //derecha

    

    public Nodo_Asignacion(Nodo_Expresion destino, Nodo_Expresion valor, int linea, int columna) {
        super(linea, columna);
        this.destino = destino;
        this.valor = valor;
        agregarHijo(destino);
        agregarHijo(valor);
    }

    public Nodo_Expresion getDestino() {
        return destino;
    }

    public Nodo_Expresion getValor() {
        return valor;
    }

    @Override
    public String imprimir(String prefijo) {
        return prefijo + "Asignacion\n"
                + destino.imprimir(prefijo + "  ├─ Destino: ")
                + valor.imprimir(prefijo + "  └─ Valor: ");
    }

}
