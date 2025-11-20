package Arbol;

import java.util.HashMap;
import java.util.Stack;


public class TablaSimbolos {
     private Stack<HashMap<String, Simbolo>> ambitos;

    public TablaSimbolos() {
        this.ambitos = new Stack<>();
        abrirAmbito();
    }

    //se abre un ambito al encontrar un {
    public void abrirAmbito() {
        ambitos.push(new HashMap<>());
    }

//     cierra el ambito al encontrar }
    public void cerrarAmbito() {
        if (!ambitos.isEmpty()) {
            ambitos.pop();
        }
    }

    public void insertar(String nombre, String tipo) {
        HashMap<String, Simbolo> ambitoActual = ambitos.peek();
        if (ambitoActual.containsKey(nombre)) {
            System.err.println("Error Semántico: La variable '" + nombre + "' ya ha sido declarada en este ambito.");
        } else {
            ambitoActual.put(nombre, new Simbolo(nombre, tipo));
            System.out.println("Variable '" + nombre + "' de tipo '" + tipo + "' insertada correctamente.");
        }
    }

    // Busca un símbolo desde el ámbito actual hacia los exteriores
    public Simbolo buscar(String nombre) {
        for (int i = ambitos.size() - 1; i >= 0; i--) {
            if (ambitos.get(i).containsKey(nombre)) {
                return ambitos.get(i).get(nombre);
            }
        }
        System.err.println("Error Semántico: La variable '" + nombre + "' no ha sido declarada.");
        return null;
    }
}
