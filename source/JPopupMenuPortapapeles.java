import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

public class JPopupMenuPortapapeles extends JPopupMenu {

// Atributos
    private JMenuItem menuCortar, menuCopiar, menuPegar, menuSeleccionarTodo;
    private JTextComponent source;

// Constructores
    public JPopupMenuPortapapeles(JTextComponent s) {
        // Asume que el parámetro no es nulo
        source=s;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void show(int x, int y) {
        super.show(source, x, y);
        
        final Clipboard PORTAPAPELES=Constantes.PORTAPAPELES;
        
        String textoSeleccionado=source.getSelectedText();
        
        // Si no hay texto seleccionado o source no es editable, bloquea el botón cortar
        if ( textoSeleccionado == null || ! source.isEditable() )
            menuCortar.setEnabled(false);
            else
            menuCortar.setEnabled(true);
        
        // Si no hay texto seleccionado, bloquea el botón copiar
        if ( textoSeleccionado == null )
            menuCopiar.setEnabled(false);
            else
            menuCopiar.setEnabled(true);
        
        // Si no hay texto en el portapapeles o source no es editable, bloquea el botón pegar
        try {
            Transferable content=PORTAPAPELES.getContents(null);
            if ( source.isEditable() && content.isDataFlavorSupported(DataFlavor.stringFlavor) )
                menuPegar.setEnabled(true);
                else
                    menuPegar.setEnabled(false);
            
        } catch (IllegalStateException exc) {
            // Hubo un problema con el portapapeles
            menuPegar.setEnabled(false);
        }
        
        // Si no hay texto, o ya está completamente seleccionado, bloquea el botón seleccionar todo
        String texto=source.getText();
        if ( texto.isEmpty() )
            menuSeleccionarTodo.setEnabled(false);
            else
                if ( textoSeleccionado != null && textoSeleccionado.equals(texto) )
                    menuSeleccionarTodo.setEnabled(false);
                    else
                        menuSeleccionarTodo.setEnabled(true);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cortar() {
        source.cut();
    }

    public void copiar() {
        source.copy();
    }

    public void pegar() {
        source.paste();
    }

    public void seleccionarTodo() {
        source.selectAll();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        menuCortar=armarMenuCortar();
        menuCopiar=armarMenuCopiar();
        menuPegar=armarMenuPegar();
        menuSeleccionarTodo=armarMenuSeleccionarTodo();
        
        add(menuCortar);
        add(menuCopiar);
        add(menuPegar);
        addSeparator();
        add(menuSeleccionarTodo);
    }

    private JMenuItem armarMenuCortar() {
        JMenuItem menu=new JMenuItemPersonalizado("Cortar");
            menu.setAccelerator( KeyStroke.getKeyStroke("ctrl X") );
            menu.addActionListener( new OyenteCortar() );
        
        return menu;
    }

    private JMenuItem armarMenuCopiar() {
        JMenuItem menu=new JMenuItemPersonalizado("Copiar");
            menu.setAccelerator( KeyStroke.getKeyStroke("ctrl C") );
            menu.addActionListener( new OyenteCopiar() );
        
        return menu;
    }

    private JMenuItem armarMenuPegar() {
        JMenuItem menu=new JMenuItemPersonalizado("Pegar");
            menu.setAccelerator( KeyStroke.getKeyStroke("ctrl V") );
            menu.addActionListener( new OyentePegar() );
        
        return menu;
    }

    private JMenuItem armarMenuSeleccionarTodo() {
        JMenuItem menu=new JMenuItemPersonalizado("Seleccionar todo");
            menu.setAccelerator( KeyStroke.getKeyStroke("ctrl A") );
            menu.addActionListener( new OyenteSeleccionarTodo() );
        
        return menu;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteCortar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            cortar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteCopiar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            copiar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyentePegar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            pegar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteSeleccionarTodo implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            seleccionarTodo();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}