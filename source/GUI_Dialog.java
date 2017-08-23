import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;

public abstract class GUI_Dialog extends JDialog {

// Constructores
    public GUI_Dialog(GUI_Frame parent) {
        super(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener( new OyenteCerrarVentana() );
    }

    public GUI_Dialog() {
        super();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener( new OyenteCerrarVentana() );
    }

    public GUI_Dialog(GUI_Dialog parent) {
        super(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener( new OyenteCerrarVentana() );
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        dispose();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    protected class OyenteCerrar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            cerrar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteCerrarVentana extends WindowAdapter implements WindowListener {
        public void windowClosing ( WindowEvent e ) {
            cerrar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}