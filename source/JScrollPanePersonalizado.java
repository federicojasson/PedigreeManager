import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class JScrollPanePersonalizado extends JScrollPane {

// Constructores
    public JScrollPanePersonalizado(Component view, int vsbPolicy, int hsbPolicy)  {
        super(view,vsbPolicy,hsbPolicy);
        
        final Border BORDE=Constantes.BORDE_DATO;
        setBorder(BORDE);
    }


}