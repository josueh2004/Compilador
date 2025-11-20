package Analizador_Semantico;

import java.util.*;

public class TablaSimbolos {
    
    // La pila de mapas representa la pila de ámbitos (alcances).
    // Alcance de una variable sea global, local, funcion o bloque
    // El mapa en la cima (peek()) es el ámbito actual (más local).
    private final Stack<Map<String, Simbolo>> pilaTabla = new Stack<>();//se almacenan variables y arreglos
    
    // Los métodos son globales y se buscan solo por firma.
    private final Map<String, Simbolo> metodosPorFirma = new HashMap<>();
    public TablaSimbolos() {
       //al inicializarlo, se inicializa la tabla global.
        entrarAmbito(); 
    }
  
    public void entrarAmbito() {
        pilaTabla.push(new HashMap<>());
    }
   
    public void salirAmbito() {
        if (!pilaTabla.isEmpty()) {
            pilaTabla.pop();
        } 
       
    }
    //se cambia si el anterior produce un valor este metodo lo lo utilizo
    public void salirAmbitoss() {
    if (pilaTabla.size() <= 1) {
        throw new IllegalStateException("No se puede salir del ámbito global");
    }
    pilaTabla.pop();
}

     //se utilizan para las variables
    public void agregarSimbolo(String nombre, TipoDato tipo, boolean inicializado, Object valor) {
        if (pilaTabla.isEmpty()) {
            // Protección, aunque el constructor ya crea el ámbito global
            entrarAmbito(); 
        }
        Simbolo s = new Simbolo(nombre, tipo, inicializado, valor);
        pilaTabla.peek().put(nombre, s);
    }
    //seutiliza para insertar símbolos ya validados
    //solo para arreglo
    public boolean insertar(Simbolo s) { 
        String nombre = s.getNombre();
        
        // 1. Verificar si ya existe en el ámbito actual (para detectar re-declaraciones)
        if (existeSimboloEnAmbitoActual(nombre)) {
            return false;
        }
        
        // 2. Asegurar el ámbito y guardar
        if (pilaTabla.isEmpty()) {
            entrarAmbito();
        }
        pilaTabla.peek().put(nombre, s);
        return true;
    }
    
    //le quite dos parametros, boleanos
    public void agregarMetodo(String nombre, TipoDato tipoRetorno, List<TipoDato> tiposParametros,String firma) {
        if (pilaTabla.isEmpty()) {
            entrarAmbito();
        }
        // para almacenar método.
       /* Simbolo s = new Simbolo(nombre, tipoRetorno, tiposParametros);
        pilaTabla.peek().put(firma, s);*/
       metodosPorFirma.put(firma, new Simbolo(nombre, tipoRetorno, tiposParametros));
    }
    
    // Comprueba si una firma específica ya está registrada.
    public boolean existeFirmaMetodo(String firma) {
        return metodosPorFirma.containsKey(firma);
    }

    // permite obtiene un método basado en la firma calculada a partir de la llamada.
    public Simbolo obtenerMetodoPorFirma(String firma) {
        return metodosPorFirma.get(firma);
    }
    
    public boolean existeSimboloEnAmbitoActual(String nombre) {
        if (pilaTabla.isEmpty()) return false;
        return pilaTabla.peek().containsKey(nombre);
    }
    
    
     //verificar si una variable está declarada y si se puede acceder a ella.
     
    public Simbolo obtenerSimbolo(String nombre) {
        // Itera de la cima (más local) a la base (más global)
        for (int i = pilaTabla.size() - 1; i >= 0; i--) {
            Map<String, Simbolo> ambito = pilaTabla.get(i);
            if (ambito.containsKey(nombre)) {
                return ambito.get(nombre);
            }
        }
        return null; 
    }

    public boolean existeSimbolo(String nombre) {
        return obtenerSimbolo(nombre) != null;
    }

    public void inicializarSimbolo(String nombre) {
        Simbolo s = obtenerSimbolo(nombre);
        if (s != null){
            s.inicializar();
        }
    }

   
    public void marcarUsado(String nombre) {
        Simbolo s = obtenerSimbolo(nombre);
        if (s != null){
            s.usar();
        }
    }
    
  
   // todos los simbolos en todos los ámbitos.
    
    public Collection<Simbolo> obtenerTodos() {
        List<Simbolo> todos = new ArrayList<>();
        // recorre todo los ámbitos para recoger todos los símbolos
        for (Map<String, Simbolo> ambito : pilaTabla) {
            todos.addAll(ambito.values());
        }
        return todos;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TABLA DE SÍMBOLOS COMPLETA:\n");
        int nivel = 0;
        for (Map<String, Simbolo> ambito : pilaTabla) {
            sb.append("--- Ámbito (Nivel ").append(nivel++).append(") ---\n");
            for (Simbolo s : ambito.values()) {
                 sb.append("  ").append(s.getNombre())
                  .append(" -> Tipo: ").append(s.getTipo())
                  .append(", Inicializada: ").append(s.estaInicializado())
                  .append("\n");
            }
        }
        return sb.toString();
    }
}