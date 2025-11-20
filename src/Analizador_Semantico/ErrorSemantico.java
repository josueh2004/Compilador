package Analizador_Semantico;

public class ErrorSemantico {

    private final String mensaje;
    private final int linea;
    private final int columna;

    public ErrorSemantico(String mensaje, int linea) {
        this.mensaje = mensaje;
        this.linea = linea;
        this.columna = -1;          // <- valor por defecto si no hay columna
    }

    public ErrorSemantico(String mensaje, int linea, int columna) {
        this.mensaje = mensaje;
        this.linea = linea;
        this.columna = columna;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        if (columna >= 0) {
            return "Línea " + linea + ", Columna " + columna + ": " + mensaje;
        } else {
            return "Línea " + linea + ": " + mensaje;
        }
    }
}
