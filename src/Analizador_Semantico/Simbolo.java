package Analizador_Semantico;
//esta clase me permite guardar los atributos  de las 

import java.util.Collections;
import java.util.List;

public class Simbolo {

    private final String nombre;
    private final TipoDato tipo;
    private boolean inicializado;
    private boolean usado;
    private Object valor;
    
    //para metodos
    private final boolean esMetodo;
    private final List<TipoDato> tiposParametros;
    
    // para  Arrays 
    private final boolean esArray;
    private final List<Integer> dimensiones;
        
    //contructor de variables
    public Simbolo(String nombre, TipoDato tipo, boolean inicializado, Object valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.inicializado = inicializado;
        this.valor = valor;
       // Valores por defecto para variables
        this.esMetodo = false;
        this.esArray = false; 
        this.dimensiones = Collections.emptyList(); // las dimensiones se declaran vacías        
        this.tiposParametros = null;
    }
    
    //constructor para los metodos
  public Simbolo(String nombre, TipoDato tipoRetorno, List<TipoDato> tiposParametros) {
        this.nombre = nombre;
        this.tipo = tipoRetorno; // El tipo es el tipo de retorno
        this.tiposParametros = tiposParametros;
        
        // un método 
        this.inicializado = true; 
        this.usado = false;
        this.valor = null;
        this.esMetodo = true;
        this.esArray = false; 
        this.dimensiones = Collections.emptyList();
    }
  
  // contructor para el arreglo
    public Simbolo(String nombre, TipoDato tipoElemento, boolean inicializado, boolean esArray, List<Integer> dimensiones) {
        this.nombre = nombre;
        this.tipo = tipoElemento; // El tipo es el tipo de los elementos del array (ej: INT)
        this.inicializado = inicializado;
        this.esArray = esArray;   // 🟢 ¡ES ARRAY!
        this.dimensiones = dimensiones; // 🟢 GUARDAR DIMENSIONES
        
        // Valores por defecto para arrays
        this.valor = null;
        this.esMetodo = false;
        this.tiposParametros = null;
    }
  

    public String getNombre() {
        return nombre;
    }

    public TipoDato getTipo() {
        return tipo;
    }
    
    public boolean esMetodo() {
        return esMetodo;
    }
    
    public boolean esArray() {
        return esArray;
    }
    
    public List<Integer> getDimensiones() {
        return dimensiones;
    }

  //  verificación de llamadas a métodos
    public List<TipoDato> getTiposParametros() {
        if (!esMetodo) {
            // Lanza una excepción o retorna lista vacía si se llama en una variable
            throw new UnsupportedOperationException("Solo los símbolos de tipo método tienen una lista de parámetros.");
        }
        // Devuelve una lista vacía 
        return tiposParametros != null ? tiposParametros : Collections.emptyList();
    }
    
    public boolean estaInicializado() {
        return inicializado;
    }
    
     public void inicializar() {
        this.inicializado = true;
    }
    public boolean estaUsado() {
        return usado;
    }
    
     public void usar() {
        this.usado = true;
    }
  
   

    @Override
    public String toString() {
        return "Simbolo{"
                + "nombre='" + nombre + '\''
                + ", tipo='" + tipo + '\''
                + ", inicializado=" + inicializado
                + ", usado=" + usado
                + '}';
    }

}
