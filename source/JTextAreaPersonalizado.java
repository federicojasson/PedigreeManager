import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class JTextAreaPersonalizado extends JTextArea {

// Atributos
    private JPopupMenuPortapapeles popup;

// Constructores
    public JTextAreaPersonalizado() {
        super();
        popup=new JPopupMenuPortapapeles(this);
        
        final Border BORDE=Constantes.BORDE_NORMAL;
        setBorder(BORDE);
        
        addMouseListener( new OyentePopup() );
    }

    public JTextAreaPersonalizado(int ancho, int alto, boolean wrap) {
        super(ancho,alto);
        setLineWrap(wrap);
        setWrapStyleWord(wrap);
        popup=new JPopupMenuPortapapeles(this);
        
        final Border BORDE=Constantes.BORDE_NORMAL;
        setBorder(BORDE);
        addMouseListener( new OyentePopup() );
    }

    public JTextAreaPersonalizado(String initialText, int ancho, int alto, boolean wrap) {
        this(ancho,alto, wrap);
        setText(initialText);
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        setOpaque(b);
        setFocusable(b);
    }

    public void setEditable(boolean b) {
        final Color COLOR_FONDO_ENABLED=Constantes.COLOR_BLANCO;
        final Color COLOR_FONDO_NOT_ENABLED=Constantes.COLOR_GRIS_OFF;
        super.setEditable(b);
        if (b)
            setBackground(COLOR_FONDO_ENABLED);
            else
                setBackground(COLOR_FONDO_NOT_ENABLED);
    }

    public void setText(String cadena) {
        super.setText(cadena);
        setCaretPosition(0);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void deseleccionarTexto() {
        int posicion=getCaretPosition();
        setSelectionStart(posicion);
        setSelectionEnd(posicion);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyentePopup extends MouseAdapter implements MouseListener {
        public void mouseReleased(MouseEvent evento) {
            requestFocus();
            if ( evento.isPopupTrigger() ) {
                int xPos=evento.getX();
                int yPos=evento.getY();
                popup.show(xPos, yPos);
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}