import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class JListPersonalizado extends JList {

// Constructores
    public JListPersonalizado(Object [ ] lista) {
        super(lista);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        final Font FUENTE=Constantes.FUENTE_NORMAL_VERDANA;
        setFont(FUENTE);
    }

}