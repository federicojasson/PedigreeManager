import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class JLabelPersonalizado extends JLabel {

// Constructores
    public JLabelPersonalizado(String titulo) {
        super(titulo);
        
        final Font FUENTE=Constantes.FUENTE_NORMAL_VERDANA;
        
        setFont(FUENTE);
    }

    public JLabelPersonalizado(String titulo, Color color, int alignment) {
        super(titulo, alignment);
        
        final Font FUENTE=Constantes.FUENTE_NORMAL_VERDANA;
        
        setFont(FUENTE);
        setForeground(color);
    }

}