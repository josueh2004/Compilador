package Directorio;

import InterfazIDE.Interfaz;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Directorio {

    private Interfaz interfaz;
    private static final String EXTENSION = ".pz";
    private boolean hayCambios = false;
    private File archivoActual;
    private final JFileChooser fileChooser = new JFileChooser();

    public void nuevoArchivo() {
    // System.out.println("Ejecutando nuevoArchivo()");
    if (hayCambios) {
        int opcion = JOptionPane.showConfirmDialog(interfaz,
                "¿Desea guardar los cambios del archivo actual antes de crear uno nuevo?",
                "Cambios no guardados",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.CANCEL_OPTION) {
            // System.out.println("Cancelado por el usuario");
            return;
        }

        if (opcion == JOptionPane.YES_OPTION) {
            // System.out.println("Guardando cambios...");
            guardarArchivo();
            if (hayCambios) {
                return; // Guardado fallo, no continuar
            }
        }

        archivoActual = null;
        interfaz.jTextCode.setText("");
        System.out.println("Después de setText: Editable = " + interfaz.jTextCode.isEditable());
        interfaz.jTextCode.setEditable(true); // Asegura que sea editable
        System.out.println("Después de setEditable(true): Editable = " + interfaz.jTextCode.isEditable());
        interfaz.jTextCode.requestFocusInWindow(); // Enfoca el componente
        hayCambios = false;
        interfaz.setTitle("[Structura-IDE]");
    }

    int contador = 1;
    File archivoPropuesto;
    do {
        archivoPropuesto = new File("NuevoArchivo" + contador + EXTENSION);
        contador++;
    } while (archivoPropuesto.exists());

    fileChooser.setSelectedFile(archivoPropuesto);
    int eleccion = fileChooser.showSaveDialog(interfaz);

    if (eleccion == JFileChooser.APPROVE_OPTION) {
        String nombre = fileChooser.getSelectedFile().getName();

        if (nombre.toLowerCase().endsWith(EXTENSION)) {
            nombre = nombre.substring(0, nombre.length() - EXTENSION.length());
        }

        if (!nombre.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
            JOptionPane.showMessageDialog(interfaz, "Nombre inválido. Use solo letras y números, comenzando con una letra.");
            return;
        }
        archivoActual = new File(fileChooser.getCurrentDirectory(), nombre + EXTENSION);
        try {
            if (archivoActual.createNewFile()) {
                interfaz.jTextCode.setText("");
                System.out.println("Después de setText: Editable = " + interfaz.jTextCode.isEditable());
                interfaz.jTextCode.setEditable(true); // Asegura editabilidad después de crear
                System.out.println("Después de setEditable(true): Editable = " + interfaz.jTextCode.isEditable());
                interfaz.jTextCode.requestFocusInWindow(); // Enfoca nuevamente
                hayCambios = false;
                interfaz.setTitle("IDE - " + archivoActual.getName());
                interfaz.Pestaña.setSelectedIndex(1);
            } else {
                JOptionPane.showMessageDialog(interfaz, "No se pudo crear el archivo.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(interfaz, "Error al crear el archivo: " + e.getMessage());
        }
    }
}

    public void setCambios(boolean cambios) {
        this.hayCambios = cambios;
    }

    public File getArchivoActual() {
        return archivoActual;
    }

    public void abrirArchivo() {
     //   System.out.println("Ejecutando abrirArchivo()");
        if (hayCambios) {
            int opcion = JOptionPane.showConfirmDialog(interfaz,
                    "¿Desea guardar los cambios antes de abrir otro archivo?",
                    "Cambios no guardados",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (opcion == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (opcion == JOptionPane.YES_OPTION) {
                guardarArchivo();
                if (hayCambios) {
                    return;
                }
            }
        }

        int seleccion = fileChooser.showOpenDialog(interfaz);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            if (!archivoSeleccionado.getName().toLowerCase().endsWith(EXTENSION)) {
                JOptionPane.showMessageDialog(interfaz, "Solo se pueden abrir archivos con extensión " + EXTENSION);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(archivoSeleccionado))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                interfaz.Pestaña.setSelectedIndex(1);
                interfaz.jTextCode.setText(contenido.toString());
                archivoActual = archivoSeleccionado;
                hayCambios = false;
                interfaz.setTitle("IDE - " + archivoActual.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(interfaz, "Error al abrir el archivo: " + ex.getMessage());
            }
        }
    }

    public void guardarArchivo() {
      //  System.out.println("Ejecutando guardarArchivo()");
        if (archivoActual != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoActual))) {
                writer.write(interfaz.jTextCode.getText());
                hayCambios = false;
                interfaz.setTitle("IDE - " + archivoActual.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(interfaz, "Error al guardar el archivo: " + ex.getMessage());
            }
        } else {
            guardarComo();
        }
    }

    public void guardarComo() {
      //  System.out.println("Ejecutando guardarComo()");
        if (interfaz.getTitle().equals("[Structura-IDE]") && interfaz.Pestaña.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(interfaz, "No hay archivo que guardar");
            return;
        }

        int opcion = fileChooser.showSaveDialog(interfaz);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            String nombre = fileChooser.getSelectedFile().getName();

            if (nombre.toLowerCase().endsWith(EXTENSION)) {
                nombre = nombre.substring(0, nombre.length() - EXTENSION.length());
            }

            if (!nombre.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
                JOptionPane.showMessageDialog(interfaz, "Nombre inválido. Use solo letras y números, empezando con letra.");
                return;
            }

            archivoActual = new File(fileChooser.getCurrentDirectory(), nombre + EXTENSION);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoActual))) {
                writer.write(interfaz.jTextCode.getText());
                hayCambios = false;
                interfaz.setTitle("IDE - " + archivoActual.getName());
                interfaz.Pestaña.setSelectedIndex(1);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(interfaz, "Error al guardar el archivo: " + ex.getMessage());
            }
        }
    }

    public void limpiar() {
        interfaz.setTitle("[Structura-IDE]");
        interfaz.jTextCode.setText("ESCRIBE EN EL IDE");
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }
}
