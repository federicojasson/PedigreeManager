import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

public class JTablePersonalizado extends JTable {

// Constructores
    public JTablePersonalizado(Object [ ] [ ] datos, String [ ] columnas) {
        super();
        TableModel modelo=new ModeloTabla(datos, columnas);
        setModel(modelo);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setAutoCreateRowSorter(true);
        getTableHeader().setReorderingAllowed(false);
        
        addMouseListener( new OyenteMouse() );
    }

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteMouse extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent evento) {
            super.mouseClicked(evento);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}