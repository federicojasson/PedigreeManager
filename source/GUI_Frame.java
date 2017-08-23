import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public abstract class GUI_Frame extends JFrame {

// Constructores
    public GUI_Frame() {
        super();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener( new OyenteCerrarVentana() );
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public abstract void cerrar();
//----------------------------------------------------------------------------------------------------------------------------------------------
    public void alCerrarImagen() {
        // Debe ser redefinida para funcionar
        // Realiza una acción al cerrar una GUI_Imagen
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