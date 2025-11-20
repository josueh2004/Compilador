package Directorio;

import java.awt.*;
import java.beans.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * This class will display line numbers for a related text component. The text
 * component must use the same line height for each line. TextLineNumber
 * supports wrapped lines and will highlight the line number of the current line
 * in the text component.
 *
 * This class was designed to be used as a component added to the row header of
 * a JScrollPane.
 */
public class NumeroLinea extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {

    public final static float IZQUIERDA = 0.0f;
    public final static float CENTRO = 0.5f;
    public final static float DERECHA = 1.0f;

    private final static Border EXTERIOR = new MatteBorder(0, 0, 0, 2, Color.GRAY);

    private final static int ALTURA = Integer.MAX_VALUE - 1000000;

    // Componente de texto con el que este componente TextTextLineNumber está sincronizado
    private JTextComponent componente;

    // Propiedades que se pueden cambiar
    private boolean actualizarFuente;
    private int borde;
    private Color lineaPrimerPlano;
    private float alineacionDigitos;
    private int digMinDeVizualizacion;

    // Mantiene la información del historial para reducir la cantidad de veces que se ejecuta el componente.
    // necesita ser repintado
    private int ultimosDigitos;
    private int ultimaAltura;
    private int ultimaLinea;

    private HashMap<String, FontMetrics> fuentes;

    /**
     * Cree un componente de número de línea para un componente de texto. Este
     * mínimo el ancho de visualización se basará en 3 dígitos.
     *
     * @param component the related text component
     */
    public NumeroLinea(JTextComponent component) {
        this(component, 3);
    }

    /**
     * Cree un componente de número de línea para un componente de texto.
     *
     * @param component the related text component
     * @param minimumDisplayDigits el número de dígitos utilizados para calcular
     * el ancho mínimo del componente
     */
    public NumeroLinea(JTextComponent component, int minimumDisplayDigits) {
        this.componente = component;

        setFont(component.getFont());

        setBorderGap(5);
        setCurrentLineForeground(Color.RED);
        setDigitAlignment(DERECHA);
        setMinimumDisplayDigits(minimumDisplayDigits);

        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
        component.addPropertyChangeListener("font", this);
    }

    /**
     * Obtiene la propiedad de fuente de actualización
     *
     * @return la propiedad de fuente de actualización
     */
    public boolean getUpdateFont() {
        return actualizarFuente;
    }

    /**
     * Establezca la propiedad de fuente de actualización. Indica si esta fuente debe ser
     * actualizado automáticamente cuando la fuente del componente de texto relacionado es
     * cambió.
     *
     * @param updateFont cuando sea verdadero, actualice la fuente y vuelva a pintar los números de línea,
     * de lo contrario, simplemente vuelva a pintar los números de línea.
     */
    public void setUpdateFont(boolean updateFont) {
        this.actualizarFuente = updateFont;
    }

    /**
    * Obtiene la brecha fronteriza
     *
     * @return el espacio del borde en píxeles
     */
    public int getBorderGap() {
        return borde;
    }

    /**
    * El espacio en el borde se utiliza para calcular los recuadros izquierdo y derecho del
     * borde. El valor predeterminado es 5.
     *
     * @param borderGap el espacio en píxeles
     */
    public void setBorderGap(int borderGap) {
        this.borde = borderGap;
        Border inner = new EmptyBorder(0, borderGap, 0, borderGap);
        setBorder(new CompoundBorder(EXTERIOR, inner));
        ultimosDigitos = 0;
        setPreferredWidth();
    }

    /**
     * Obtiene el color de representación de la línea actual
     *
     * @return el color utilizado para representar el número de línea actual
     */
    public Color getCurrentLineForeground() {
        return lineaPrimerPlano == null ? getForeground() : lineaPrimerPlano;
    }

  /**
     * El color utilizado para representar los dígitos de la línea actual. El valor predeterminado es Coolor.RED.
     *
     * @param currentLineForeground el color utilizado para representar la línea actual
     */
    public void setCurrentLineForeground(Color currentLineForeground) {
        this.lineaPrimerPlano = currentLineForeground;
    }

   /**
     * Obtiene la alineación de los dígitos
     *
     * @return la alineación de los dígitos pintados
     */
    public float getDigitAlignment() {
        return alineacionDigitos;
    }

   /**
     * Especifique la alineación horizontal de los dígitos dentro del componente.
     *Los valores comunes serían:
     * <ul>
     * <li>NúmeroLíneaTexto.IZQUIERDA
     * <li>NúmeroLíneaTexto.CENTRO
     * <li>TextLineNumber.DERECHA (predeterminado)
     *</ul>
     *
     * @param currentLineForeground el color utilizado para representar la línea actual
     */
    public void setDigitAlignment(float digitAlignment) {
        this.alineacionDigitos
                = digitAlignment > 1.0f ? 1.0f : digitAlignment < 0.0f ? -1.0f : digitAlignment;
    }

   /**
     * Obtiene los dígitos mínimos de visualización
     *
     * @return los dígitos mínimos de visualización
     */
    public int getMinimumDisplayDigits() {
        return digMinDeVizualizacion;
    }

 /**
     * Especifique el número mínimo de dígitos utilizados para calcular el preferido
     * ancho del componente. El valor predeterminado es 3.
     *
     * @param minimoDisplayDigits el número de dígitos utilizados en el ancho preferido
     * cálculo
     */
    public void setMinimumDisplayDigits(int minimumDisplayDigits) {
        this.digMinDeVizualizacion = minimumDisplayDigits;
        setPreferredWidth();
    }

   /**
     * Calcule el ancho necesario para mostrar el número máximo de líneas
     */
    private void setPreferredWidth() {
        Element root = componente.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digits = Math.max(String.valueOf(lines).length(), digMinDeVizualizacion);

        //  Update sizes when number of digits in the line number changes
        if (ultimosDigitos != digits) {
            ultimosDigitos = digits;
            FontMetrics fontMetrics = getFontMetrics(getFont());
            int width = fontMetrics.charWidth('0') * digits;
            Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;

            Dimension d = getPreferredSize();
            d.setSize(preferredWidth, ALTURA);
            setPreferredSize(d);
            setSize(d);
        }
    }

   /**
     * Dibuja los números de línea.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Determinar el ancho del espacio disponible para dibujar el número de línea
        FontMetrics fontMetrics = componente.getFontMetrics(componente.getFont());
        Insets insets = getInsets();
        int availableWidth = getSize().width - insets.left - insets.right;

       // Determina las filas a dibujar dentro de los límites recortados.
        Rectangle clip = g.getClipBounds();
        int rowStartOffset = componente.viewToModel(new Point(0, clip.y));
        int endOffset = componente.viewToModel(new Point(0, clip.y + clip.height));

        while (rowStartOffset <= endOffset) {
            try {
                if (isCurrentLine(rowStartOffset)) {
                    g.setColor(getCurrentLineForeground());
                } else {
                    g.setColor(getForeground());
                }

                // Obtener el número de línea como una cadena y luego determinar el
                // Desplazamientos "X" e "Y" para dibujar la cadena.
                String lineNumber = getTextLineNumber(rowStartOffset);
                int stringWidth = fontMetrics.stringWidth(lineNumber);
                int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);

                // Pasar a la siguiente fila
                rowStartOffset = Utilities.getRowEnd(componente, rowStartOffset) + 1;
            } catch (Exception e) {
                break;
            }
        }
    }

/*
	 * Necesitamos saber si el cursor está actualmente posicionado en la línea que
	 * están a punto de pintar para que se pueda resaltar el número de línea.
     */
    private boolean isCurrentLine(int rowStartOffset) {
        int caretPosition = componente.getCaretPosition();
        Element root = componente.getDocument().getDefaultRootElement();

        if (root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition)) {
            return true;
        } else {
            return false;
        }
    }

   /*
	 * Obtenga el número de línea que se dibujará. Se devolverá la cadena vacía.
	 * cuando se ha ajustado una línea de texto.
     */
    protected String getTextLineNumber(int rowStartOffset) {
        Element root = componente.getDocument().getDefaultRootElement();
        int index = root.getElementIndex(rowStartOffset);
        Element line = root.getElement(index);

        if (line.getStartOffset() == rowStartOffset) {
            return String.valueOf(index + 1);
        } else {
            return "";
        }
    }

    /*
	 * Determine el desplazamiento X para alinear correctamente el número de línea cuando se dibuje
     */
    private int getOffsetX(int availableWidth, int stringWidth) {
        return (int) ((availableWidth - stringWidth) * alineacionDigitos);
    }

    /*
	 * Determinar el desplazamiento Y para la fila actual
     */
    private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics)
            throws BadLocationException {
      //Obtiene el rectángulo delimitador de la fila
        Rectangle r = componente.modelToView(rowStartOffset);
        int lineHeight = fontMetrics.getHeight();
        int y = r.y + r.height;
        int descent = 0;

       // El texto debe colocarse encima de la parte inferior del límite.
        // rectángulo basado en el descenso de las fuentes contenidas en la fila.
        // se está utilizando la fuente predeterminada
        if (r.height == lineHeight) {
            descent = fontMetrics.getDescent();

           // Necesitamos verificar todos los atributos para cambios de fuente
        } else {
            if (fuentes == null) {
                fuentes = new HashMap<String, FontMetrics>();
            }

            Element root = componente.getDocument().getDefaultRootElement();
            int index = root.getElementIndex(rowStartOffset);
            Element line = root.getElement(index);

            for (int i = 0; i < line.getElementCount(); i++) {
                Element child = line.getElement(i);
                AttributeSet as = child.getAttributes();
                String fontFamily = (String) as.getAttribute(StyleConstants.FontFamily);
                Integer fontSize = (Integer) as.getAttribute(StyleConstants.FontSize);
                String key = fontFamily + fontSize;

                FontMetrics fm = fuentes.get(key);

                if (fm == null) {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = componente.getFontMetrics(font);
                    fuentes.put(key, fm);
                }

                descent = Math.max(descent, fm.getDescent());
            }
        }

        return y - descent;
    }


    @Override
    public void caretUpdate(CaretEvent e) {
       //Obtiene la línea en la que está colocado el cursor

        int caretPosition = componente.getCaretPosition();
        Element root = componente.getDocument().getDefaultRootElement();
        int currentLine = root.getElementIndex(caretPosition);

       // Es necesario volver a pintar para poder resaltar el número de línea correcto
        if (ultimaLinea != currentLine) {
            repaint();
            ultimaLinea = currentLine;
        }
    }
//
// Implementar la interfaz DocumentListener
//
    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    /*
	 * Un cambio en el documento puede afectar la cantidad de líneas de texto mostradas.
	 * Por lo tanto los números de líneas también cambiarán.
     */
    private void documentChanged() {
       // La vista del componente no se ha actualizado en ese momento
        // se activa el DocumentEvent
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    int endPos = componente.getDocument().getLength();
                    Rectangle rect = componente.modelToView(endPos);

                    if (rect != null && rect.y != ultimaAltura) {
                        setPreferredWidth();
                        repaint();
                        ultimaAltura = rect.y;
                    }
                } catch (BadLocationException ex) {
                     }
            }
        });
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof Font) {
            if (actualizarFuente) {
                Font newFont = (Font) evt.getNewValue();
                setFont(newFont);
                ultimosDigitos = 0;
                setPreferredWidth();
            } else {
                repaint();
            }
        }
    }
}
