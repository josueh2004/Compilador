
package Main;

import Controlador.Acciones;
import Directorio.Directorio;
import InterfazIDE.Interfaz;

public class Compilador_Py {
    
    public static void main(String[] args) {
        Interfaz vista = new Interfaz();
        Directorio dir = new Directorio();
        dir.setInterfaz(vista);
        Acciones acciones = new Acciones(vista, dir);
        acciones.iniciar();
    }
    
}
