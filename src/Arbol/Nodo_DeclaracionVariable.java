
package Arbol;

import Analizador_Semantico.TipoDato;

/**
 *
 * @author magyg
 */
public class Nodo_DeclaracionVariable extends Nodo_Instruccion {
    private final TipoDato tipos;
    private final String nombre;
    private final Nodo_Expresion valorInicial;
  
      public Nodo_DeclaracionVariable(TipoDato tipos, String nombre, Nodo_Expresion valorInicial,int linea, int columna) {
        super(linea, columna);
        this.tipos = tipos;
        this.nombre = nombre;
        this.valorInicial = valorInicial;
          if (valorInicial != null) {
            agregarHijo(valorInicial);  // Se agrega al árbol para recorrido semántico
        }
        
        
    }


    @Override
    public TipoDato getTipo() {
        return tipos;
    }

    public String getNombre() {
        return nombre;
    }

    public Nodo_Expresion getValorInicial() {
        return valorInicial;
    }

  

  
    
    
    @Override
    public String imprimir(String prefijo) {
        String texto = prefijo + "DeclaracionVariable (Tipo: " + tipos + ", Nombre: " + nombre + ")\n";
        // Solo imprimimos la parte del valor si existe.
        if (valorInicial != null) {
            texto += valorInicial.imprimir(prefijo + "  └─ ValorInicial: ");
        }

        return texto;
    }
    
      
}
