package Controlador;

import Directorio.NumeroLinea;
import Directorio.Directorio;
import InterfazIDE.Interfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import Analizador_Lexico.*; // Importa tu Lexer y Tokens
import java.io.StringReader;
import javax.swing.table.DefaultTableModel;
import Analizador_Semantico.AnalizadorSemantico;
import Analizador_Semantico.ErrorSemantico;
import Arbol.Nodo_Programa;

public class Acciones implements ActionListener {

    private final NumeroLinea NumeroLinea;
    private final Interfaz vista;
    private final Directorio dir;
    private final UndoManager undoManager = new UndoManager();

    public Acciones(Interfaz vista, Directorio dir) {
        this.vista = vista;
        this.dir = dir;
        //this.vista.Pestaña.setSelectedIndex(0);
        // Remover listeners previos para evitar duplicados
        eliminarListeners();

        this.vista.Pestaña.setSelectedIndex(0);
        NumeroLinea = new NumeroLinea(this.vista.jTextCode);
        this.vista.jScrollPane1.setRowHeaderView(this.NumeroLinea);
        colors();

        // Configurar los comandos y atajos del menú Archivo
        vista.jMenuNuevo.setActionCommand("Nuevo");
        vista.jMenuNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuNuevo.addActionListener(this);

        vista.jMenuAbrir.setActionCommand("Abrir");
        vista.jMenuAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuAbrir.addActionListener(this);

        vista.jMenuGuardar.setActionCommand("Guardar");
        vista.jMenuGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuGuardar.addActionListener(this);

        vista.jMenuGuardarC.setActionCommand("Guardar como");
        vista.jMenuGuardarC.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuGuardarC.addActionListener(this);

        vista.jMenuSalir.setActionCommand("Salir");
        vista.jMenuSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuSalir.addActionListener(this);

        // Configurar los comandos y atajos del menú Editar
        vista.jMenuDeshacer.setActionCommand("Deshacer");
        vista.jMenuDeshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuDeshacer.addActionListener(this);

        vista.jMenuRehacer.setActionCommand("Rehacer");
        vista.jMenuRehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuRehacer.addActionListener(this);

        vista.jMenuCopiar.setActionCommand("Copiar");
        vista.jMenuCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuCopiar.addActionListener(this);

        vista.jMenuPegar.setActionCommand("Pegar");
        vista.jMenuPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuPegar.addActionListener(this);

        vista.jMenuCortar.setActionCommand("Cortar");
        vista.jMenuCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuCortar.addActionListener(this);

        // Configurar el menú Compilar
        vista.jMenuItem12.setActionCommand("Compilar");
        vista.jMenuItem12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, InputEvent.CTRL_DOWN_MASK));
        vista.jMenuItem12.addActionListener(this);

        // Configurar el menú Ayuda
        vista.jMenu_Manual.setActionCommand("Manual");
        vista.jMenu_Manual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_DOWN_MASK));
        vista.jMenu_Manual.addActionListener(this);

        vista.jMenu_AcercaDe.setActionCommand("Acerca De");
        vista.jMenu_AcercaDe.addActionListener(this);

        // Opcional: Asignar mnemonics a los menús
        vista.mArchivo.setMnemonic(KeyEvent.VK_F); // Alt + F
        vista.mCompilar.setMnemonic(KeyEvent.VK_C); // Alt + C
        vista.mEditor.setMnemonic(KeyEvent.VK_E); // Alt + E
        vista.mAyuda.setMnemonic(KeyEvent.VK_A); // Alt + A
    }

    public void iniciar() {
        this.vista.setVisible(true);
        this.vista.setResizable(false);
        this.vista.setLocationRelativeTo(null);
        registraCambioDocumento();
        registroEdicion();
    }

    //Aqui es donde iria el metodo eliminarListener
    private void eliminarListeners() {
        for (ActionListener al : vista.jMenuNuevo.getActionListeners()) {
            vista.jMenuNuevo.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuAbrir.getActionListeners()) {
            vista.jMenuAbrir.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuGuardar.getActionListeners()) {
            vista.jMenuGuardar.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuGuardarC.getActionListeners()) {
            vista.jMenuGuardarC.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuSalir.getActionListeners()) {
            vista.jMenuSalir.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuDeshacer.getActionListeners()) {
            vista.jMenuDeshacer.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuRehacer.getActionListeners()) {
            vista.jMenuRehacer.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuCopiar.getActionListeners()) {
            vista.jMenuCopiar.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuCortar.getActionListeners()) {
            vista.jMenuCortar.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuPegar.getActionListeners()) {
            vista.jMenuPegar.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenuItem12.getActionListeners()) {
            vista.jMenuItem12.removeActionListener(al);
        }
        for (ActionListener al : vista.jMenu_Manual.getActionListeners()) {
            vista.jMenu_Manual.removeActionListener(al);
        }

    }

    // ===========================
    // Nuevo método: compilarCodigo
    // ===========================
    private void compilarCodigo() {
        String codigo = vista.jTextCode.getText();
        // limpiar salidas previas
        vista.jTcompile.setText("");
        vista.resultados_sintactico.setText("");
        vista.resultados_arbolSintactico.setText("");
        vista.resultado_Semantico.setText("");

        if (codigo == null || codigo.trim().isEmpty()) {
            vista.jTcompile.setText("No hay código para compilar.");
            vista.jTcompile.setForeground(Color.ORANGE);
            return;
        }

        // --- ANÁLISIS LÉXICO ---
        try {
            Lexer lexer = new Lexer(new StringReader(codigo));
            Tokens token;
            StringBuilder resultado = new StringBuilder();
            DefaultTableModel model = (DefaultTableModel) vista.tabla_lexico.getModel();
            model.setRowCount(0);

            boolean hayErroresLexicos = false;

            while ((token = lexer.yylex()) != null) {
                resultado.append(token)
                        .append(" : ")
                        .append(lexer.yytext())
                        .append("\n");
                Object[] fila = {
                    token,
                    lexer.yytext(),
                    lexer.getLinea() + 1,
                    lexer.getColumna() + 1,
                    obtenerGrupo(token)
                };
                model.addRow(fila);
                // detectar si hay errores léxicos
                if (token == Tokens.ERROR) {
                    hayErroresLexicos = true;
                }
            }

            // Verifica si hay errores léxicos
            if (!lexer.errores.isEmpty()) {
                resultado.append("\n--- Errores léxicos ---\n").append(lexer.errores.toString());
            }
            vista.jTcompile.setText("Análisis léxico completado.\n" + resultado.toString());

            // --- ANÁLISIS SINTÁCTICO ---
            if (!hayErroresLexicos) {
                StringReader parserReader = new StringReader(codigo);
                LexerCup lexerCup = new LexerCup(parserReader);
                Parser parser = new Parser(lexerCup);

                java_cup.runtime.Symbol resultSymbol = null;
                try {
                    // protegemos la llamada a parse para que NUNCA lance un error fatal al UI
                    resultSymbol = parser.parse();
                } catch (Exception ex) {
                    // ignoramos la excepción: parser.erroresSintacticos debería contener mensajes
                    // opcional: puedes loggear ex.printStackTrace();
                } catch (Error er) {
                    // también capturamos Error por si hay lanzamientos raros
                }

                // Mostrar errores sintácticos si los hubo
                if (parser.erroresSintacticos != null && parser.erroresSintacticos.length() > 0) {
                    StringBuilder erroresSint = new StringBuilder("Errores sintácticos:\n");
                    erroresSint.append(parser.erroresSintacticos.toString());
                    vista.resultados_sintactico.setText(erroresSint.toString());
                } else {
                    vista.resultados_sintactico.setText("Análisis sintáctico completado correctamente.");
                    if (resultSymbol != null && resultSymbol.value instanceof Arbol.Nodo_AST) {
                        Arbol.Nodo_AST nodoRaiz = (Arbol.Nodo_AST) resultSymbol.value;
                        vista.resultados_arbolSintactico.setText(nodoRaiz.imprimir(""));

                        // ANALISIS SEMÁNTICO
                        AnalizadorSemantico semantico = new AnalizadorSemantico();
                        semantico.analizar((Nodo_Programa) nodoRaiz);
                        if (semantico.getError().isEmpty()) {
                            vista.resultado_Semantico.setText("Análisis semántico completado sin errores.");
                        } else {
                            StringBuilder erroresSem = new StringBuilder("Errores semánticos:\n");
                            for (ErrorSemantico ex : semantico.getError()) {
                                erroresSem.append(" - ").append(ex.toString()).append("\n");
                            }
                            vista.resultado_Semantico.setText(erroresSem.toString());
                        }

                    } else {
                        vista.resultados_arbolSintactico.setText("No se pudo generar el árbol");
                    }
                }

            } else {
                vista.resultados_sintactico.setText("No se puede ejecutar análisis sintáctico debido a errores léxicos.");
            }

        } catch (IOException ex) {
            vista.jTcompile.setText("Error durante el análisis léxico: " + ex.getMessage());
            vista.jTcompile.setForeground(Color.RED);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        //   System.out.println("Acción ejecutada: " + comando);
        switch (comando) {
            case "Nuevo":
                dir.nuevoArchivo();
                break;
            case "Abrir":
                dir.abrirArchivo();
                break;
            case "Guardar":
                dir.guardarArchivo();
                break;
            case "Guardar como":
                dir.guardarComo();
                break;
            case "Salir":
                System.exit(0);
                break;
            case "Deshacer":
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
                break;
            case "Rehacer":
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
                break;
            case "Copiar":
                this.vista.jTextCode.copy();
                break;
            case "Cortar":
                this.vista.jTextCode.cut();
                break;
            case "Pegar":
                this.vista.jTextCode.paste();
                break;

            case "Compilar":
                // ahora solo llamamos al método seguro de compilación
                compilarCodigo();
                break;

            case "Manual":
                String rutaPDF = "ManualDeUsuario.pdf";
                abrirPDF(rutaPDF);
                break;

            default:
                break;
        }
    }
    
    public void realizarAnalisisSintactico() {
    String codigoFuente = vista.jTextCode.getText();
    
    // Limpiar las áreas de resultados antes de realizar el análisis
    vista.resultados_sintactico.setText("");
    vista.resultados_arbolSintactico.setText("");
    
    // Crear el analizador léxico y sintáctico
    StringReader reader = new StringReader(codigoFuente);
    LexerCup lexer = new LexerCup(reader);
    Parser parser = new Parser(lexer);
    
    try {
        // Realizar el análisis sintáctico
        parser.parse();
        
        // Verificar si hay errores sintácticos
        String erroresSintacticos = parser.erroresSintacticos.toString();
        
        if (!erroresSintacticos.isEmpty()) {
            vista.resultados_sintactico.setText(erroresSintacticos);
        } else {
            vista.resultados_sintactico.setText("Análisis sintáctico realizado exitosamente. No se encontraron errores sintácticos.");
        }
        
    } catch (Exception e) {
        vista.resultados_sintactico.setText("Error durante el análisis sintáctico: " + e.getMessage());
    }
}

    private String obtenerGrupo(Tokens token) {
        switch (token) {
            //palabras reservadas
            case BOOLEAN:
            case MAIN:
            case ELSE:
            case FLOAT:
            case FOR:
            case INT:
            case IF:
            case RETURN:
            case VOID:
            case WHILE:
            case PRINT:
            case READ:
            case STRING:
                return "Palabra Reservada";

            //literales
            case TRUE:
            case FALSE:
            case NUMERO:
            case STRING_LITERAL:
                return "Literal";

            //operadores
            case ASIGNACION:
            case MAYOR:
            case MENOR:
            case NEGACION:
            case COMPARACION:
            case MAYORIGUAL:
            case MENORIGUAL:
            case AND:
            case OR:
            case INCREMENTO:
            case DECREMENTO:
            case OPE_SUMA:
            case OPE_RESTA:
            case OPE_MULT:
            case OPE_DIV:
                return "Operador";

            //delimitadores
            case IN_PAREN:
            case FIN_PAREN:
            case IN_LLAVE:
            case FIN_LLAVE:
            case IN_CORCH:
            case FIN_CORCH:
            case TERMINADOR:
            case COMMA:
                return "Delimitador";

            //identificador
            case ID:
                return "Identificador";

            default:
                return "...";
        }
    }

    public void registraCambioDocumento() {
        vista.jTextCode.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                dir.setCambios(true);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                dir.setCambios(true);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                dir.setCambios(true);
            }
        });

    }

    public void registroEdicion() {
        // Registros de los cambios para poder deshacer o rehacer
        vista.jTextCode.getDocument().addUndoableEditListener(e -> {
            undoManager.addEdit(e.getEdit());
        });

    }

    //    controlador de ayuda
    public void abrirPDF(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                JOptionPane.showMessageDialog(null, "El archivo no existe: " + rutaArchivo);
                return;
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            } else {
                JOptionPane.showMessageDialog(null, "La apertura de archivos no está soportada en este sistema.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir el archivo.");
        }
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    private void colors() {

        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attrRojo = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 0));
        final AttributeSet attrNegro = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 0, 0));
        final AttributeSet attrAzul = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 191, 255));
        final AttributeSet attrAmarillo = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 215, 0));
        final AttributeSet attrVerde = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(50, 205, 50));
        final AttributeSet attrAqua = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 255, 255));

        DefaultStyledDocument doc = new DefaultStyledDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);
                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        String word = text.substring(wordL, wordR);
                        if ("main".equalsIgnoreCase(word)) {
                            setCharacterAttributes(wordL, wordR - wordL, attrRojo, false);

                        } else if (text.substring(wordL, wordR).matches("(\\W)*(int|string|boolean|float|if|else|while|read|return|true|false|for)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrAzul, false);

                        }/* else if (text.substring(wordL, wordR).matches("(\\W)*(si|sino|mientras|para|hacer)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrAmarillo, false);

                        } else if (text.substring(wordL, wordR).matches("(\\W)*(imprimir|leer|nuevo)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrVerde, false);

                        } else if (text.substring(wordL, wordR).matches("(\\W)*(retorno|true|false)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrAqua, false);

                        } */ else {
                            setCharacterAttributes(wordL, wordR - wordL, attrNegro, false);
                        }
                        wordL = wordR + 1;
                    }
                    wordR++;
                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offs);
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        String word = text.substring(wordL, wordR);
                        if ("main".equalsIgnoreCase(word)) {
                            setCharacterAttributes(wordL, wordR - wordL, attrRojo, false);

                        } else if (text.substring(wordL, wordR).matches("(\\W)*(int|string|boolean|float|if|else|while|read|return|true|false|for)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrAzul, false);

                        } /*else if (text.substring(wordL, wordR).matches("(\\W)*(si|sino|mientras|para|hacer)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrAmarillo, false);

                        } else if (text.substring(wordL, wordR).matches("(\\W)*(imprimir|leer|nuevo)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrVerde, false);

                        } else if (text.substring(wordL, wordR).matches("(\\W)*(retorno|true|false)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attrAqua, false);

                        }*/ else {
                            setCharacterAttributes(wordL, wordR - wordL, attrNegro, false);
                        }
                        wordL = wordR + 1;
                    }
                    wordR++;
                }
            }
        };

        String temp = vista.jTextCode.getText();
        vista.jTextCode.setStyledDocument(doc);
        vista.jTextCode.setText(temp);

        // Aplicar resaltado inicial al texto existente
        try {
            String text = doc.getText(0, doc.getLength());
            int wordL = 0;
            int wordR = 0;
            while (wordR <= text.length()) {
                if (wordR == text.length() || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                    String word = text.substring(wordL, wordR);
                    if ("main".equalsIgnoreCase(word)) {
                        doc.setCharacterAttributes(wordL, wordR - wordL, attrRojo, false);

                    } else if (text.substring(wordL, wordR).matches("(\\W)*(int|string|boolean|float|if|else|while|read|return|true|false|for)")) {
                        doc.setCharacterAttributes(wordL, wordR - wordL, attrAzul, false);

                    }/* else if (text.substring(wordL, wordR).matches("(\\W)*(si|sino|mientras|para|hacer)")) {
                        doc.setCharacterAttributes(wordL, wordR - wordL, attrAmarillo, false);

                    } else if (text.substring(wordL, wordR).matches("(\\W)*(imprimir|leer|nuevo)")) {
                        doc.setCharacterAttributes(wordL, wordR - wordL, attrVerde, false);

                    } else if (text.substring(wordL, wordR).matches("(\\W)*(retorno|true|false)")) {
                        doc.setCharacterAttributes(wordL, wordR - wordL, attrAqua, false);

                    }*/ else {
                        doc.setCharacterAttributes(wordL, wordR - wordL, attrNegro, false);
                    }
                    wordL = wordR + 1;
                }
                wordR++;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}

