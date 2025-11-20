package Analizador_Semantico;

import Arbol.*;
import java.util.*;

public class AnalizadorSemantico {

    private final TablaSimbolos tablaSimbolos = new TablaSimbolos();
    private final List<ErrorSemantico> Error = new ArrayList<>();

    // Método principal: analiza el programa completo
    public void analizar(Nodo_Programa programa) {
        if (programa == null) {
            return;
        }

        //todos los metodos que encuentre
        if (programa.getMetodos() != null) {
            for (Nodo_Metodo metodo : programa.getMetodos()) {
                // Solo se registra el método, no se analiza el cuerpo todavía.
                registrarMetodo(metodo);
            }
        }

        if (programa.getInstruccionesMain() != null) {
            tablaSimbolos.entrarAmbito();
            for (Nodo_Instruccion instr : programa.getInstruccionesMain()) {
                analizarNodo(instr);
            }
            tablaSimbolos.salirAmbito();
        }

        // analiza por metodo
        if (programa.getMetodos() != null) {
            for (Nodo_Metodo metodo : programa.getMetodos()) {
                // Llama al método completo de verificación y análisis del cuerpo
                verificarMetodo(metodo);
            }
        }

        //variables no usada
    }

    // Analiza cualquier nodo según su tipo 
    private void analizarNodo(Nodo_AST nodo) {
        if (nodo == null) {
            return;
        }

        if (nodo instanceof Nodo_DeclaracionVariable) {
            verificarDeclaracion((Nodo_DeclaracionVariable) nodo);
        } else if (nodo instanceof Nodo_DeclaracionArray) {
            verificarDeclaracionArray((Nodo_DeclaracionArray) nodo);
        } else if (nodo instanceof Nodo_Asignacion) {
            verificarAsignacion((Nodo_Asignacion) nodo);
        } else if (nodo instanceof Nodo_If) {
            verificarIf((Nodo_If) nodo);
        } else if (nodo instanceof Nodo_While) {
            verificarWhile((Nodo_While) nodo);
        } else if (nodo instanceof Nodo_For) {
            verificarFor((Nodo_For) nodo);
        } else if (nodo instanceof Nodo_ExpresionST) {
            // Maneja instrucciones como 'imprimir();' o 'a++;'
            Nodo_ExpresionST exprInstruccion = (Nodo_ExpresionST) nodo;

            // Verifica si la expresión es la llamada a método
            if (exprInstruccion.getExpresion() instanceof Nodo_LlamaMetodo) {
                verificarLlamadaMetodoComoInstruccion((Nodo_LlamaMetodo) exprInstruccion.getExpresion());
            }
            // Aquí podrías añadir un else-if para manejar Nodo_OpPostfija si tu 'a++;' lo necesita.

        } else if (nodo instanceof Nodo_LlamaMetodo) {

            verificarLlamadaMetodoComoInstruccion((Nodo_LlamaMetodo) nodo);
        } else if (nodo instanceof Nodo_Print) {
            verificarPrint((Nodo_Print) nodo);
        } else if (nodo instanceof Nodo_Read) {
            verificarRead((Nodo_Read) nodo);
        } else if (nodo instanceof Nodo_Return) {
            // agregar 
            Nodo_Return expRetorno = (Nodo_Return) nodo;

        }

      /*  if (!(nodo instanceof Nodo_If) && !(nodo instanceof Nodo_While) && !(nodo instanceof Nodo_For)
                && !(nodo instanceof Nodo_Metodo) && !(nodo instanceof Nodo_LlamaMetodo)
                && !(nodo instanceof Nodo_Print) && !(nodo instanceof Nodo_Read)) {
            for (Nodo_AST hijo : nodo.getHijos()) {
                analizarNodo(hijo);
            }
        }*/

    }

    // Verifica declaración de variables 
    private void verificarDeclaracion(Nodo_DeclaracionVariable decl) {
        String nombre = decl.getNombre();
        TipoDato tipo = decl.getTipo();

        if (tablaSimbolos.existeSimbolo(nombre)) {
            Error.add(new ErrorSemantico("La variable '" + nombre + "' ya fue declarada.", decl.getLinea()));
        } else {
            // Inicializamos como no inicializado por defecto
            boolean inicializado = false;
            Object valor = null;

            // tiene valor inicial sen evaluarlo
            if (decl.getValorInicial() != null) {
                Nodo_Expresion exp = decl.getValorInicial();
                TipoDato tipoValor = obtenerTipoExpresion(exp);

                if (!sonCompatibles(tipo, tipoValor)) {
                    Error.add(new ErrorSemantico("Incompatibilidad de tipos: no se puede asignar un " + tipoValor + " a " + tipo, decl.getLinea()));
                } else {
                    // Si es compatible, marcar como inicializado
                    inicializado = true;
                    if (exp instanceof Nodo_Numero) {
                        valor = ((Nodo_Numero) exp).getValor();
                    } else if (exp instanceof Nodo_Float) {
                        valor = ((Nodo_Float) exp).getValor();
                    } else if (exp instanceof Nodo_Booleano) {
                        valor = ((Nodo_Booleano) exp).getValor();
                    } else if ((exp instanceof Nodo_StringLiteral)) {
                        valor = ((Nodo_StringLiteral) exp).getValor();
                    }
                }
            }
            // se guada la variab
            tablaSimbolos.agregarSimbolo(nombre, tipo, inicializado, valor);
        }
    }

    // Verifica asignaciones //nombre = 'hll'
    private void verificarAsignacion(Nodo_Asignacion asign) {
        Nodo_Expresion destino = asign.getDestino();
        TipoDato tipoDestino = TipoDato.DESCONOCIDO;
        String nombreDestino = null;
        int lineaError = asign.getLinea();

        // Determinar TIPO del destino izq
        if (destino instanceof Nodo_Identificador) {
            Nodo_Identificador idDestino = (Nodo_Identificador) destino;
            nombreDestino = idDestino.getNombre();

            Simbolo simboloDestino = tablaSimbolos.obtenerSimbolo(nombreDestino);

            /* if (simboloDestino == null) {
                Error.add(new ErrorSemantico("Variable '" + nombreDestino + "' no declarada.", idDestino.getLinea()));
                return;
            }*/
            if (simboloDestino == null) {
                Error.add(new ErrorSemantico("Variable '" + nombreDestino + "' no declarada.", idDestino.getLinea()));
                return;
            }

            // Verificación de si se intenta asignar a un método
            if (simboloDestino.esMetodo()) {
                Error.add(new ErrorSemantico("No se puede asignar un valor a un método.", idDestino.getLinea()));
                return;
            }

            // Verificación de si se intenta asignar a un nombre de array sin índices
            if (simboloDestino.esArray()) {
                Error.add(new ErrorSemantico("Asignación inválida: se esperaba un elemento de array (" + nombreDestino + "[i]), no el nombre del array.", idDestino.getLinea()));
                return;
            }
            tipoDestino = simboloDestino.getTipo();
            lineaError = idDestino.getLinea();

        } else if (destino instanceof Nodo_AccesoArray) {
            Nodo_AccesoArray accesoArray = (Nodo_AccesoArray) destino;
            //llama al metodo para realizar la verificacion de las dimenciones o indice del arreglo
            Simbolo simboloArray = verificarAccesoArray(accesoArray);

            if (simboloArray == null) {
                return; // se lanza el error dentro de verificarAccesoArray
            }

            // Obtenemos el tipo base del elemento ( INT para un int array[10])
            tipoDestino = simboloArray.getTipo();

            // Obtenemos el nombre del array (variable base) para el marcado de inicialización
            nombreDestino = simboloArray.getNombre();
            lineaError = accesoArray.getLinea();

        } else {
            Error.add(new ErrorSemantico("El destino de la asignación debe ser una variable o un elemento de arreglo.", asign.getLinea()));
            return;
        }

        if (tipoDestino == TipoDato.DESCONOCIDO) {
            return;
        }

        //TIPO del valor derecho
        TipoDato tipoValor = obtenerTipoExpresion(asign.getValor());

        if (!sonCompatibles(tipoDestino, tipoValor)) {
            Error.add(new ErrorSemantico(
                    "Incompatibilidad de tipos: no se puede asignar un '" + tipoValor + "' a un '" + tipoDestino + "'.",
                    lineaError));
            return;
        }

        // Marcar como inicializado
        if (nombreDestino != null) {
            tablaSimbolos.inicializarSimbolo(nombreDestino);
        }
    }

    // Verifica estructuras de control
    private void verificarIf(Nodo_If si) {

        TipoDato tipoCond = obtenerTipoExpresion(si.getCondicion());

        if (tipoCond != TipoDato.BOOLEAN) {
            Error.add(new ErrorSemantico("La condición del 'if' no se cumple.", si.getLinea()));
        }

        // bloque then
        tablaSimbolos.entrarAmbito();
        if (si.getBloqueThen() != null) {
            for (Nodo_Instruccion instr : si.getBloqueThen()) {
                analizarNodo(instr);
            }
        }
        tablaSimbolos.salirAmbito();

        // bloque ELSE
        if (si.getBloqueElse() != null) {
            tablaSimbolos.entrarAmbito();
            for (Nodo_Instruccion instr : si.getBloqueElse()) {
                analizarNodo(instr);
            }
            tablaSimbolos.salirAmbito();
        }
    }

    // Verifica la semántica del nodo WHILE
    private void verificarWhile(Nodo_While nodo) {
        TipoDato tipoCondicion = obtenerTipoExpresion(nodo.getCondicion());

        if (tipoCondicion != TipoDato.BOOLEAN) {     //La condición del 'while' debe ser booleana.
            Error.add(new ErrorSemantico("La condición del 'while' no se cumple.", nodo.getLinea()));
        }

        // instrucciones del cuerpo del while
        tablaSimbolos.entrarAmbito();
        if (nodo.getInstrucciones() != null) {
            for (Nodo_Instruccion instruccion : nodo.getInstrucciones()) {
                analizarNodo(instruccion);
            }
        }
        tablaSimbolos.salirAmbito();
    }

    private void verificarFor(Nodo_For nodo) {
        tablaSimbolos.entrarAmbito();
        // Inicialización
        if (nodo.getInicializacion() != null) {
            analizarNodo(nodo.getInicializacion());
        }

        // Condición solo verificar el tipo, el código
        if (nodo.getCondicion() != null) {
            TipoDato tipoCond = obtenerTipoExpresion(nodo.getCondicion());
            
            if (tipoCond != TipoDato.BOOLEAN) {     //La condición del 'for' debe ser de tipo boolean
                Error.add(new ErrorSemantico("La condición del 'for' no se cumple.", nodo.getLinea()));
            }
        }

        //  Paso
        if (nodo.getPaso() != null) {
           // obtenerTipoExpresion(nodo.getPaso());
            analizarNodo(nodo.getPaso());
        }

        // Cuerpo
        if (nodo.getCuerpo() != null) {
            for (Nodo_Instruccion instruccion : nodo.getCuerpo()) {
                analizarNodo(instruccion);
            }
        }

        tablaSimbolos.salirAmbito();
    }

// método para la verificación de límites de array en tiempo de compilación,
    //obtener el valor de índice o dimensión de un arreglo
    private Object evaluarConstante(Nodo_Expresion expr) {
        if (expr == null) {
            return null;
        }

        // lo importante para las dimensiones/índices
        if (expr instanceof Nodo_Numero) {
            return ((Nodo_Numero) expr).getValor();
        }
        if (expr instanceof Nodo_Float) {
            return ((Nodo_Float) expr).getValor();
        }

        // Literales de otros tipos (aunque no deberían usarse en índices)
        /* if (expr instanceof Nodo_Booleano) {
        return ((Nodo_Booleano) expr).getValor();
    }
    if (expr instanceof Nodo_StringLiteral) {
        return ((Nodo_StringLiteral) expr).getValor();
    }*/
        return null;
    }

    private void verificarDeclaracionArray(Nodo_DeclaracionArray decl) {
        String nombre = decl.getNombre();

        if (tablaSimbolos.existeSimbolo(nombre)) {
            Error.add(new ErrorSemantico("Variable o Array '" + nombre + "' ya declarado.", decl.getLinea()));
            return;

        }

        // 1 Obtener y validar TODAS las dimensiones
        List<Integer> dimensionesConstantes = new ArrayList<>();
        for (Nodo_Expresion tamañoExp : decl.getDimensiones()) {
            TipoDato tipoTam = obtenerTipoExpresion(tamañoExp);

            if (tipoTam != TipoDato.INT) {
                Error.add(new ErrorSemantico("El tamaño del array debe ser entero (INT), no " + tipoTam + ".", decl.getLinea()));
                return;
            }

            // Asumimos que evaluarConstante resuelve el valor del índice si es un literal.
            Object valorConstante = evaluarConstante(tamañoExp);

            if (!(valorConstante instanceof Integer)) {
                Error.add(new ErrorSemantico("El tamaño del array debe ser una constante entera positiva en la declaración.", decl.getLinea()));
                return;
            }
            int tamaño = (Integer) valorConstante;

            if (tamaño <= 0) {
                Error.add(new ErrorSemantico("El tamaño del array debe ser un valor positivo (> 0).", decl.getLinea()));
                return;
            }
            dimensionesConstantes.add(tamaño);
        }

        // crear y registrar el símbolo con todas sus dimensiones utilizamos el constructor del arreglo
        Simbolo nuevoSimbolo = new Simbolo(nombre, decl.getTipos(), true, true, dimensionesConstantes // Contiene [2, 2]
        );

        tablaSimbolos.insertar(nuevoSimbolo);
    }

    private Simbolo verificarAccesoArray(Nodo_AccesoArray accesoArray) {
        String nombreArray = accesoArray.getNombreArray().getNombre();
        Simbolo simboloArray = tablaSimbolos.obtenerSimbolo(nombreArray);

        if (simboloArray == null) {
            Error.add(new ErrorSemantico("Array '" + nombreArray + "' no declarado.", accesoArray.getLinea()));
            return null;
        }
        if (!simboloArray.esArray()) {
            Error.add(new ErrorSemantico("'" + nombreArray + "' no es un array.", accesoArray.getLinea()));
            return null;
        }

        List<Integer> dimensionesDeclaradas = simboloArray.getDimensiones(); // [2, 2]
        List<Nodo_Expresion> indices = accesoArray.getIndices();           // [i, j]

        // * verifica el número de dimensiones
        if (indices.size() != dimensionesDeclaradas.size()) {
            Error.add(new ErrorSemantico(
                    "Número de dimensiones incorrecto para el array '" + nombreArray + "'. Se esperaban "
                    + dimensionesDeclaradas.size() + " índices, se encontraron " + indices.size() + ".",
                    accesoArray.getLinea()));
            return null;
        }

        // * verificar CADA índice el Tipo y el Límite
        for (int i = 0; i < indices.size(); i++) {
            Nodo_Expresion indiceNodo = indices.get(i);
            int tamañoMaximo = dimensionesDeclaradas.get(i); // Obtenemos el tamaño de la dimensión n

            // *.* Verificar Tipo 
            TipoDato tipoIndice = obtenerTipoExpresion(indiceNodo);
            if (tipoIndice != TipoDato.INT) {
                Error.add(new ErrorSemantico(
                        "El índice [" + (i + 1) + "] del array debe ser de tipo entero (INT), no " + tipoIndice + ".",
                        indiceNodo.getLinea()));
                continue;
            }

            // *.b. Verificar el límite en compilación
            Object valorIndice = evaluarConstante(indiceNodo);

            if (valorIndice instanceof Integer) {
                int indice = (Integer) valorIndice;

                // Ejemplo de error: arreglo[2][0] donde el tamaño máximo es [2]
                if (indice < 0 || indice >= tamañoMaximo) {
                    Error.add(new ErrorSemantico(
                            "Índice fuera de límites en dimensión " + (i + 1) + ": " + indice
                            + " está fuera del rango válido [0, " + (tamañoMaximo - 1)
                            + "] para el array '" + nombreArray + "'.", indiceNodo.getLinea()));
                }
            }
        }
        return simboloArray;
    }

    private void verificarLlamadaMetodoComoInstruccion(Nodo_LlamaMetodo llamada) {
        // Ejecutamos la verificación de tipos y aridad
        obtenerTipoExpresion(llamada);
    }

    private void verificarPrint(Nodo_Print nodo) {
        if (nodo.getExpresion() == null) {
            return;
        }

        TipoDato tipoExpresion = obtenerTipoExpresion(nodo.getExpresion());

        if (tipoExpresion == TipoDato.DESCONOCIDO) {
            Error.add(new ErrorSemantico(
                    "La expresión a imprimir no tiene un tipo de dato válido.", nodo.getLinea()));
        }
    }

    private void verificarRead(Nodo_Read nodo) {
        Nodo_Identificador id = nodo.getDestino();
        String nombreVariable = id.getNombre();

        Simbolo simbolo = tablaSimbolos.obtenerSimbolo(nombreVariable);

        if (simbolo == null) {
            Error.add(new ErrorSemantico(
                    "La variable '" + nombreVariable + "' donde se intentan leer datos no está declarada.", id.getLinea()));
            return;
        }

        if (simbolo.esMetodo()) {
            Error.add(new ErrorSemantico(
                    "No se puede leer un valor en el nombre del método '" + nombreVariable + "'.", id.getLinea()));
            return;
        }

        simbolo.inicializar();
    }

    
    private void registrarMetodo(Nodo_Metodo metodo) {
        String nombreMetodo = metodo.getNombre();

        // * obtenemos los tipos de parámetros
        List<TipoDato> tiposParametros = new ArrayList<>();
        for (Nodo_Parametro param : metodo.getParametros()) {
            tiposParametros.add(param.getTipo());
        }

        // *Generar el metodo con sus parametros
        String firma = generarFirmaMetodo(nombreMetodo, tiposParametros); // Usar la función utilitaria
       
        // + se realiza la verificacón de existencia del metodo
        if (tablaSimbolos.existeFirmaMetodo(firma)) {
            Error.add(new ErrorSemantico(
                    "El método '" + firma + "' ya ha sido declarada.", metodo.getLinea()));
            return;
        }

        // + Agregar el método con sus parametros unicos, que los diferencia
        tablaSimbolos.agregarMetodo(nombreMetodo, metodo.getTipoRetorno(), tiposParametros,firma);
    }

    // Verifica métodos //Verificar si no se repite el metodo con el de la´parte inferior
    private void verificarMetodo(Nodo_Metodo metodo) {
        TipoDato tipoRetornoEsperado = metodo.getTipoRetorno();

        tablaSimbolos.entrarAmbito();

        // Registra los parámetros como variables locales
        for (Nodo_Parametro param : metodo.getParametros()) {
            // Los parámetros están inicializados y declarados
            tablaSimbolos.agregarSimbolo(param.getNombre(), param.getTipo(), true, null);
        }

        //Analiza el cuerpo y verifica retornos
        boolean encontroReturn = false;
        for (Nodo_Instruccion instr : metodo.getCuerpo()) {
            if (instr instanceof Nodo_Return) {
                verificarRetorno((Nodo_Return) instr, tipoRetornoEsperado);
                encontroReturn = true;
            } else {
                analizarNodo(instr);
            }
        }

        // se encarga de la verificacion dde la existencia de retornos para métodos no vacios
        if (tipoRetornoEsperado != TipoDato.VOID && !encontroReturn) {
            Error.add(new ErrorSemantico(
                    "El método '" + metodo.getNombre() + "' debe retornar un valor de tipo " + tipoRetornoEsperado,
                    metodo.getLinea()));
        }

        tablaSimbolos.salirAmbito();
    }

    private void verificarRetorno(Nodo_Return nodoReturn, TipoDato tipoEsperado) {
        if (nodoReturn.getExpresion() == null) {
            // Retorno con valor
            if (tipoEsperado != TipoDato.VOID) {
                Error.add(new ErrorSemantico(
                        "El método debe retornar un valor de tipo " + tipoEsperado + ", no está permitido 'return;'.",
                        nodoReturn.getLinea()));
            }
        } else {
            // Retorno sin valor
            if (tipoEsperado == TipoDato.VOID) {
                Error.add(new ErrorSemantico(
                        "El método de tipo VOID no puede retornar un valor.", nodoReturn.getLinea()));
                return;
            }

            TipoDato tipoDevuelto = obtenerTipoExpresion(nodoReturn.getExpresion());

            if (!sonCompatibles(tipoEsperado, tipoDevuelto)) {
                Error.add(new ErrorSemantico(
                        "Tipo de retorno incompatible. Se esperaba '" + tipoEsperado + "' se devuelve '" + tipoDevuelto + "'.",
                        nodoReturn.getLinea()));
            }
        }
    }

    // Determina el tipo de una expresión 
    private TipoDato obtenerTipoExpresion(Nodo_Expresion expr) {
        if (expr == null) {
            return TipoDato.DESCONOCIDO;
        }

        // Literales
        if (expr instanceof Nodo_Numero) {
            return TipoDato.INT;
        }
        if (expr instanceof Nodo_Float) {
            return TipoDato.FLOAT;
        }
        if (expr instanceof Nodo_Booleano) {
            return TipoDato.BOOLEAN;
        }
        if (expr instanceof Nodo_StringLiteral) {
            return TipoDato.STRING;
        }

        // Identificador (variable)
        if (expr instanceof Nodo_Identificador) {
            Nodo_Identificador id = (Nodo_Identificador) expr;
            Simbolo simbol = tablaSimbolos.obtenerSimbolo(id.getNombre());

            if (simbol == null) {
                Error.add(new ErrorSemantico(
                        "La variable '" + id.getNombre() + "' no declarada.",
                        id.getLinea()));
                return TipoDato.DESCONOCIDO;
            }

            if (!simbol.estaInicializado()) {
                Error.add(new ErrorSemantico(
                        "La variable '" + id.getNombre() + "' sin inicializarse.",
                        id.getLinea()));
            }

            return simbol.getTipo();
        }

        if (expr instanceof Nodo_AccesoArray) {
            Nodo_AccesoArray accArr = (Nodo_AccesoArray) expr;
            String nombreArray = accArr.getNombreArray().getNombre();
            Simbolo simboloArray = tablaSimbolos.obtenerSimbolo(nombreArray);

            if (simboloArray == null) {
                Error.add(new ErrorSemantico("Array '" + nombreArray + "' no declarado.", accArr.getLinea()));
                return TipoDato.DESCONOCIDO;
            }

            // Verificar que CADA índice sea de tipo INT
            boolean indicesValidos = true;
            for (Nodo_Expresion indice : accArr.getIndices()) {
                TipoDato tipoIndice = obtenerTipoExpresion(indice);
                if (tipoIndice != TipoDato.INT) {
                    Error.add(new ErrorSemantico(
                            "El índice del array debe ser de tipo entero (INT), no " + tipoIndice + ".",
                            accArr.getLinea()));
                    indicesValidos = false;
                }
            }

            if (!indicesValidos) {
                return TipoDato.DESCONOCIDO;
            }

            return simboloArray.getTipo();
        }

        if (expr instanceof Nodo_OpBinario) {
            Nodo_OpBinario op = (Nodo_OpBinario) expr;
            TipoDato izq = obtenerTipoExpresion(op.getIzquierda());
            TipoDato der = obtenerTipoExpresion(op.getDerecha());

            switch (op.getOperador()) {
                case "+":
                case "-":
                case "*":
                case "/":
                    // Reglas numéricas
                    if (izq == TipoDato.FLOAT || der == TipoDato.FLOAT) {
                        return TipoDato.FLOAT;
                    }
                    if (izq == TipoDato.INT && der == TipoDato.INT) {
                        return TipoDato.INT;
                    }
                    // Concatenación de cadenas)
                    if (izq == TipoDato.STRING || der == TipoDato.STRING) {
                        return TipoDato.STRING;
                    }
                    Error.add(new ErrorSemantico(
                            "Operación aritmética inválida entre " + izq + " y " + der, expr.getLinea()));
                    return TipoDato.DESCONOCIDO;

                case "==":
                case "!=":
                    if (izq == der
                            || ((izq == TipoDato.INT || izq == TipoDato.FLOAT)
                            && (der == TipoDato.INT || der == TipoDato.FLOAT))) {
                        return TipoDato.BOOLEAN;
                    }
                    Error.add(new ErrorSemantico(
                            "Comparación inválida entre tipos " + izq + " y " + der,
                            expr.getLinea()
                    ));
                    return TipoDato.DESCONOCIDO;
                case ">":
                case "<":
                case ">=":
                case "<=":
                    // Comparaciones resultado booleano
                    if ((izq == TipoDato.INT || izq == TipoDato.FLOAT)
                            && (der == TipoDato.INT || der == TipoDato.FLOAT)) {
                        return TipoDato.BOOLEAN;
                    }
                    Error.add(new ErrorSemantico(
                            "Comparación relacional inválida entre tipo " + izq + " y " + der, expr.getLinea()));
                    return TipoDato.DESCONOCIDO;

                case "&&":
                case "||":
                    if (izq == TipoDato.BOOLEAN && der == TipoDato.BOOLEAN) {
                        return TipoDato.BOOLEAN;
                    }
                    Error.add(new ErrorSemantico(
                            "Operación lógica inválida entre " + izq + " y " + der,
                            expr.getLinea()
                    ));
                    return TipoDato.DESCONOCIDO;

                default:
                    Error.add(new ErrorSemantico(
                            "Operador desconocido: '" + op.getOperador() + "'.",
                            expr.getLinea()
                    ));
                    return TipoDato.DESCONOCIDO;
            }
        }
        //operaciones unarias
        if (expr instanceof Nodo_OpUnario) {
            Nodo_OpUnario op = (Nodo_OpUnario) expr;
            TipoDato tipoExp = obtenerTipoExpresion(op.getOperando());
            String operador = op.getOperador();

            if (tipoExp == TipoDato.DESCONOCIDO) {
                return TipoDato.DESCONOCIDO;
            }

            if (operador.equals("!")) {
                if (tipoExp == TipoDato.BOOLEAN) {
                    return TipoDato.BOOLEAN;
                }
                Error.add(new ErrorSemantico(
                        "El operador '!' solo puede aplicarse a valores booleanos.",
                        expr.getLinea()
                ));
                return TipoDato.DESCONOCIDO;
            }

            if (operador.equals("-")) {
                if (tipoExp == TipoDato.INT || tipoExp == TipoDato.FLOAT) {
                    return tipoExp;
                }
                Error.add(new ErrorSemantico(
                        "El operador '-' solo puede aplicarse a valores numéricos.",
                        expr.getLinea()
                ));
                return TipoDato.DESCONOCIDO;
            }

            Error.add(new ErrorSemantico(
                    "Operador unario '" + operador + "' no válido para tipo " + tipoExp,
                    expr.getLinea()
            ));
            return TipoDato.DESCONOCIDO;
        }

        if (expr instanceof Nodo_LlamaMetodo) {
            Nodo_LlamaMetodo llamada = (Nodo_LlamaMetodo) expr;
            String nombreMetodo = llamada.getNombreMetodo().getNombre();

            // 1. Obtener los tipos de los argumentos pasados
            List<TipoDato> tiposArgumentos = new ArrayList<>();
            for (Nodo_Expresion arg : llamada.getArgumentos()) {
                TipoDato tipoArg = obtenerTipoExpresion(arg);
                if (tipoArg == TipoDato.DESCONOCIDO) {
                    return TipoDato.DESCONOCIDO; // Manejo de error
                }
                tiposArgumentos.add(tipoArg);
            }

            // * Generar el metodo unico, de la llamada
            String Llamada = generarFirmaMetodo(nombreMetodo, tiposArgumentos);
            // *. Buscar el símbolo por la firma completa 
            Simbolo simboloMetodo = tablaSimbolos.obtenerMetodoPorFirma(Llamada);
            if (simboloMetodo == null) {
            Error.add(new ErrorSemantico(
            "La función o método '" + nombreMetodo + "' no ha sido declarado.", llamada.getLinea()));
                return TipoDato.DESCONOCIDO;
            }

            List<TipoDato> tiposParametrosEsperados = simboloMetodo.getTiposParametros();
            List<Nodo_Expresion> argumentos = llamada.getArgumentos();

            // n  argumentos
            if (argumentos.size() != tiposParametrosEsperados.size()) {
                Error.add(new ErrorSemantico(
                        "Llamada a método '" + nombreMetodo + "': se esperaban "
                        + tiposParametrosEsperados.size() + " argumentos, pero se encontraron " + argumentos.size() + ".", llamada.getLinea()));
            } else {
                // compatibilidad de tipos
                for (int i = 0; i < argumentos.size(); i++) {
                    TipoDato tipoArgumento = obtenerTipoExpresion(argumentos.get(i)); // Tipo proporcionado (tipo2)
                    TipoDato tipoParametro = tiposParametrosEsperados.get(i); // Tipo esperado (tipo1)

                    if (!sonCompatibles(tipoParametro, tipoArgumento)) {
                        Error.add(new ErrorSemantico(
                                "Llamada a '" + nombreMetodo + "': argumento " + (i + 1)
                                + " incompatible. Se esperaba '" + tipoParametro + "' se recibió '" + tipoArgumento + "'.",
                                argumentos.get(i).getLinea()));
                    }
                }
            }

            // tipo de retorno del método
            return simboloMetodo.getTipo();
        }

        return TipoDato.DESCONOCIDO;
    }

    // compatibilidad de tipos   
    private boolean sonCompatibles(TipoDato tipo1, TipoDato tipo2) {

        if (tipo1 == null || tipo2 == null || tipo1 == TipoDato.DESCONOCIDO || tipo2 == TipoDato.DESCONOCIDO) {
            return false;
        }

        // tipo1 = tipo1.toUpperCase();
        // tipo2 = tipo2.toUpperCase();
        if (tipo1.equals(tipo2)) {
            return true;
        }

        // compatibilidad numérica
        if (tipo1 == TipoDato.FLOAT && tipo2 == TipoDato.INT) //      ||
        //   (tipo1 == TipoDato.INT && tipo2 == TipoDato.FLOAT))
        {
            return true;
        }

        if ((tipo1 == TipoDato.BOOLEAN && tipo2 == TipoDato.FLOAT)) {
            return false;
        }

        return false;
    }

    // Accesores 
    public List<ErrorSemantico> getError() {
        return Error;
    }

    //crea una cadena,contiene nombre y los tipos de parametros sello
    private String generarFirmaMetodo(String nombre, List<TipoDato> tiposParametros) {
        StringBuilder firma = new StringBuilder(nombre).append("(");
        for (int i = 0; i < tiposParametros.size(); i++) {
            firma.append(tiposParametros.get(i).toString().toLowerCase()); // Asegura minúsculas
            if (i < tiposParametros.size() - 1) {
                firma.append(",");
            }
        }
        firma.append(")");
        return firma.toString();
    }

    public void imprimirErrores() {
        if (Error.isEmpty()) {
            System.out.println("Análisis semántico completado sin errores.");
        } else {
            System.out.println("Errores semánticos detectados:");
            for (ErrorSemantico e : Error) {
                System.out.println("  - " + e);
            }
        }
    }
}
