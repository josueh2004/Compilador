package InterfazIDE;

import Controlador.Acciones;
import Directorio.Directorio;
import java.io.StringReader;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;





public class Interfaz extends javax.swing.JFrame {
 
    private Directorio directorio;
    
   
    public Interfaz() {
    initComponents();
    this.setTitle("[Structura-IDE]");
    directorio = new Directorio();
    directorio.setInterfaz(this);
    Acciones acciones = new Acciones(this, directorio);
    acciones.iniciar();
}
    
    
   
    
    
    public JTextPane getjTextCode() {
    return jTextCode;
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        tapa = new javax.swing.JPanel();
        Pestaña = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextCode = new javax.swing.JTextPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTcompile = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_lexico = new javax.swing.JTable();
        panel_sintactico = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        resultados_sintactico = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        resultados_arbolSintactico = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        resultado_Semantico = new javax.swing.JTextArea();
        jMenuBar2 = new javax.swing.JMenuBar();
        mArchivo = new javax.swing.JMenu();
        jMenuNuevo = new javax.swing.JMenuItem();
        jMenuAbrir = new javax.swing.JMenuItem();
        jMenuGuardar = new javax.swing.JMenuItem();
        jMenuGuardarC = new javax.swing.JMenuItem();
        jMenuSalir = new javax.swing.JMenuItem();
        mEditor = new javax.swing.JMenu();
        jMenuDeshacer = new javax.swing.JMenuItem();
        jMenuRehacer = new javax.swing.JMenuItem();
        jMenuCopiar = new javax.swing.JMenuItem();
        jMenuPegar = new javax.swing.JMenuItem();
        jMenuCortar = new javax.swing.JMenuItem();
        mCompilar = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        mAyuda = new javax.swing.JMenu();
        jMenu_Manual = new javax.swing.JMenuItem();
        jMenu_AcercaDe = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tapa.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(tapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 580, 50));

        Pestaña.setEnabled(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Pestaña.addTab("tab2", jPanel4);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setViewportView(jTextCode);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 240));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTcompile.setEditable(false);
        jTcompile.setColumns(20);
        jTcompile.setRows(5);
        jScrollPane2.setViewportView(jTcompile);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 170));

        tabla_lexico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Token", "Lexema", "Linea", "Columna", "Grupo", "Alcance", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabla_lexico);
        if (tabla_lexico.getColumnModel().getColumnCount() > 0) {
            tabla_lexico.getColumnModel().getColumn(0).setResizable(false);
            tabla_lexico.getColumnModel().getColumn(1).setResizable(false);
            tabla_lexico.getColumnModel().getColumn(2).setResizable(false);
            tabla_lexico.getColumnModel().getColumn(3).setResizable(false);
            tabla_lexico.getColumnModel().getColumn(4).setResizable(false);
            tabla_lexico.getColumnModel().getColumn(5).setResizable(false);
            tabla_lexico.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 690, 170));

        jTabbedPane1.addTab("Analisis Lexico", jPanel2);

        panel_sintactico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        resultados_sintactico.setEditable(false);
        resultados_sintactico.setColumns(20);
        resultados_sintactico.setRows(5);
        jScrollPane4.setViewportView(resultados_sintactico);

        panel_sintactico.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 170));

        resultados_arbolSintactico.setEditable(false);
        resultados_arbolSintactico.setColumns(20);
        resultados_arbolSintactico.setRows(5);
        jScrollPane5.setViewportView(resultados_arbolSintactico);

        panel_sintactico.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 490, 170));

        jTabbedPane1.addTab("Analisis Sintactico", panel_sintactico);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        resultado_Semantico.setEditable(false);
        resultado_Semantico.setColumns(20);
        resultado_Semantico.setRows(5);
        jScrollPane6.setViewportView(resultado_Semantico);

        jPanel5.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 170));

        jTabbedPane1.addTab("Anlisis Semantico", jPanel5);

        jPanel3.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 1010, 200));

        Pestaña.addTab("tab2", jPanel3);

        jPanel1.add(Pestaña, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 1010, 460));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 510));

        jMenuBar2.setAlignmentX(50.0F);
        jMenuBar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        mArchivo.setText("Archivo");

        jMenuNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nuevo.png"))); // NOI18N
        jMenuNuevo.setText("Nuevo     ");
        jMenuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuNuevoActionPerformed(evt);
            }
        });
        mArchivo.add(jMenuNuevo);

        jMenuAbrir.setText("Abrir    ");
        mArchivo.add(jMenuAbrir);

        jMenuGuardar.setText("Guardar    ");
        mArchivo.add(jMenuGuardar);

        jMenuGuardarC.setText("Guardar como    ");
        mArchivo.add(jMenuGuardarC);

        jMenuSalir.setText("Salir    Ctrl + Q");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        mArchivo.add(jMenuSalir);

        jMenuBar2.add(mArchivo);

        mEditor.setText("Editar");

        jMenuDeshacer.setText("Deshacer");
        mEditor.add(jMenuDeshacer);

        jMenuRehacer.setText("Rehacer");
        jMenuRehacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRehacerActionPerformed(evt);
            }
        });
        mEditor.add(jMenuRehacer);

        jMenuCopiar.setText("Copiar");
        mEditor.add(jMenuCopiar);

        jMenuPegar.setText("Pegar");
        mEditor.add(jMenuPegar);

        jMenuCortar.setText("Cortar");
        mEditor.add(jMenuCortar);

        jMenuBar2.add(mEditor);

        mCompilar.setText("Compilar");
        mCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCompilarActionPerformed(evt);
            }
        });

        jMenuItem12.setText("Compilar   ");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        mCompilar.add(jMenuItem12);

        jMenuBar2.add(mCompilar);

        mAyuda.setText("Ayuda");

        jMenu_Manual.setText("Manual de Usuario");
        mAyuda.add(jMenu_Manual);

        jMenu_AcercaDe.setText("Acerca De");
        jMenu_AcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu_AcercaDeActionPerformed(evt);
            }
        });
        mAyuda.add(jMenu_AcercaDe);

        jMenuBar2.add(mAyuda);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuNuevoActionPerformed

    private void jMenuRehacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRehacerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuRehacerActionPerformed

    private void jMenu_AcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_AcercaDeActionPerformed
        Acerca a = new Acerca();
        a.setVisible(true);
        
        
    }//GEN-LAST:event_jMenu_AcercaDeActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
//        System.exit(WIDTH);
    }//GEN-LAST:event_jMenuSalirActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
       
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void mCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCompilarActionPerformed
        

    }//GEN-LAST:event_mCompilarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
   
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Interfaz().setVisible(true);
            }
        });
    }

    public void limpiarCode() {
        jTextCode.setText("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTabbedPane Pestaña;
    public javax.swing.JMenuItem jMenuAbrir;
    private javax.swing.JMenuBar jMenuBar2;
    public javax.swing.JMenuItem jMenuCopiar;
    public javax.swing.JMenuItem jMenuCortar;
    public javax.swing.JMenuItem jMenuDeshacer;
    public javax.swing.JMenuItem jMenuGuardar;
    public javax.swing.JMenuItem jMenuGuardarC;
    public javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem3;
    public javax.swing.JMenuItem jMenuNuevo;
    public javax.swing.JMenuItem jMenuPegar;
    public javax.swing.JMenuItem jMenuRehacer;
    public javax.swing.JMenuItem jMenuSalir;
    public javax.swing.JMenuItem jMenu_AcercaDe;
    public javax.swing.JMenuItem jMenu_Manual;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTextArea jTcompile;
    public javax.swing.JTextPane jTextCode;
    public javax.swing.JMenu mArchivo;
    public javax.swing.JMenu mAyuda;
    public javax.swing.JMenu mCompilar;
    public javax.swing.JMenu mEditor;
    public javax.swing.JPanel panel_sintactico;
    public javax.swing.JTextArea resultado_Semantico;
    public javax.swing.JTextArea resultados_arbolSintactico;
    public javax.swing.JTextArea resultados_sintactico;
    public javax.swing.JTable tabla_lexico;
    public javax.swing.JPanel tapa;
    // End of variables declaration//GEN-END:variables
}
