/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Analizador_Semantico;

/**
 *
 * @author Lidia
 */
// se crea la clase tipos, ´para realizar el manejo de los tipos da datos a manejar
public enum TipoDato {
    INT,
    STRING,
    BOOLEAN,
    FLOAT,
    DESCONOCIDO,
    VOID;

    public static TipoDato desdeString(String tipo) {
        switch (tipo.toLowerCase()) {
            case "int":
                return INT;
            case "float":
                return FLOAT;
            case "string":
                return STRING;
            case "boolean":
                return BOOLEAN;
            case "void":
                return VOID;
            default:
                return DESCONOCIDO;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
