import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;

public class JTextFieldPedigree extends JTextField {

// Atributos
    private JPopupMenuPedigree popup;

// Constructores
    public JTextFieldPedigree(GUI_Frame p, Datos base, String n) {
        super();
        popup=new JPopupMenuPedigree(p, base, n);
        addMouseListener( new OyentePopup() );
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void setText(String cadena) {
        super.setText(cadena);
        setCaretPosition(0);
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
                popup.show(JTextFieldPedigree.this, xPos, yPos);
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}