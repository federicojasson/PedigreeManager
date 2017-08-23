import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class JTextFieldPersonalizado extends JTextField {

// Atributos    
    private JPopupMenuPortapapeles popup;

// Constructores
    public JTextFieldPersonalizado() {
        this(Constantes.FIELD_LONGITUD_DEFAULT);
    }

    public JTextFieldPersonalizado(int longitud) {
        super(longitud);
        popup=new JPopupMenuPortapapeles(this);
        
        final Border BORDE=Constantes.BORDE_FIELD;
        setBorder(BORDE);
        addMouseListener( new OyentePopup() );
    }

    public JTextFieldPersonalizado(String initialText, int longitud) {
        this(longitud);
        setText(initialText);
    }

    public JTextFieldPersonalizado(String initialText) {
        this();
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