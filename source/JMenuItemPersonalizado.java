import java.awt.Font;
import javax.swing.JMenuItem;

public class JMenuItemPersonalizado extends JMenuItem {

// Constructores
    public JMenuItemPersonalizado(String title) {
        super();
        setText(title);
        final Font FUENTE=Constantes.FUENTE_NORMAL;
        setFont(FUENTE);
    }

}