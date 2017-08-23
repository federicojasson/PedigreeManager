import java.awt.Color;
import java.awt.Font;
import javax.swing.JRadioButton;

public class JRadioButtonPersonalizado extends JRadioButton {

// Constructores
    public JRadioButtonPersonalizado(String titulo) {
        super(titulo);
        
        final Font FUENTE=Constantes.FUENTE_NORMAL_VERDANA;
        
        setFocusable(false);
        setFont(FUENTE);
        setOpaque(false);
    }

    public JRadioButtonPersonalizado(String titulo, Color color) {
        this(titulo);
        setForeground(color);
    }

}