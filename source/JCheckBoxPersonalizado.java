import java.awt.Color;
import java.awt.Font;
import javax.swing.JCheckBox;

public class JCheckBoxPersonalizado extends JCheckBox {

// Constructores
    public JCheckBoxPersonalizado(String titulo) {
        super(titulo);
        
        final Font FUENTE=Constantes.FUENTE_NORMAL_VERDANA;
        
        setFocusable(false);
        setFont(FUENTE);
        setOpaque(false);
    }

    public JCheckBoxPersonalizado(String titulo, Color color) {
        this(titulo);
        setForeground(color);
    }

}