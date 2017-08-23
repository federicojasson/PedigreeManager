import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;

public class JComboBoxPersonalizado extends JComboBox {

// Constructores
    public JComboBoxPersonalizado(String [ ] lista) {
        this(lista, Constantes.COMBO_ANCHO_DEFAULT);
    }

    public JComboBoxPersonalizado(String [ ] lista, int ancho) {
        super(lista);
        
        final Font FUENTE=Constantes.FUENTE_NORMAL_VERDANA;
        final int ALTO_DEFAULT=Constantes.COMBO_ALTO_DEFAULT;
        
        setFocusable(false);
        setFont(FUENTE);
        setPreferredSize( new Dimension (ancho, ALTO_DEFAULT) );
    }

}