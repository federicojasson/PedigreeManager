import java.awt.Font;
import javax.swing.JMenu;

public class JMenuPersonalizado extends JMenu {

// Constructores
    public JMenuPersonalizado(String title) {
        super();
        setText(title);
        final Font FUENTE=Constantes.FUENTE_NORMAL;
        setFont(FUENTE);
    }

}