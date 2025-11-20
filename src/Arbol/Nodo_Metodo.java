/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arbol;

import Analizador_Semantico.TipoDato;
import java.util.List;

/**
 *
 * @author magyg
 */
public class Nodo_Metodo extends Nodo_AST{
    private final TipoDato tipoRetorno;
    private final String nombre;
    private final List<Nodo_Parametro> parametros;
    private final List<Nodo_Instruccion> cuerpo;

    public Nodo_Metodo(TipoDato tipoRetorno, String nombre, List<Nodo_Parametro> parametros, List<Nodo_Instruccion> cuerpo) {
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombre;
        this.parametros = parametros;
        this.cuerpo = cuerpo;
    }

    public TipoDato getTipoRetorno() {
        return tipoRetorno;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Nodo_Parametro> getParametros() {
        return parametros;
    }

    public List<Nodo_Instruccion> getCuerpo() {
        return cuerpo;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public List<Nodo_AST> getHijos() {
        return hijos;
    }
    
    
    
    @Override
    public String imprimir(String prefijo) {
        String texto = prefijo + "Metodo (Nombre: " + nombre + ", Retorno: " + tipoRetorno + ")\n";
        
        // Imprimir parámetros si existen
        if (!parametros.isEmpty()) {
            texto += prefijo + "  ├─ Parametros:\n";
            for (Nodo_Parametro parametro : parametros) {
                texto += parametro.imprimir(prefijo + "  │  ");
            }
        }

        // Imprimir cuerpo del método
        texto += prefijo + "  └─ Cuerpo:\n";
        for (Nodo_Instruccion instruccion : cuerpo) {
            texto += instruccion.imprimir(prefijo + "     ");
        }

        return texto;
    }
}
