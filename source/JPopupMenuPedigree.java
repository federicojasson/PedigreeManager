import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

public class JPopupMenuPedigree extends JPopupMenu {

// Atributos
    private GUI_Frame source;
    
    private Datos baseDatos;
    private String nombre;

// Constructores
    public JPopupMenuPedigree(GUI_Frame s, Datos base, String n) {
        // Asume que el parámetro no es nulo
        source=s;
        baseDatos=base;
        nombre=n;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void detalles() {
        Ejemplar ejemplar=(Ejemplar)baseDatos.getListaEjemplares().buscarElemento(nombre);
        
        if (ejemplar != null)        
            new GUI_Detalles_Ejemplar(source, baseDatos, ejemplar);
            else
                Mensaje.mostrarElementoEliminado(source);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        JMenuItem menuDetalles=armarMenuDetalles();
        
        add(menuDetalles);
    }

    private JMenuItem armarMenuDetalles() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        
        JMenuItem menu=new JMenuItemPersonalizado("Detalles");
            menu.setIcon( new ImageIcon(  getClass().getResource(ICONO) ) );
            menu.addActionListener( new OyenteDetalles() );
        
        return menu;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDetalles implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            detalles();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}