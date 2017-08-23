import java.awt.Dimension;
import java.awt.Font;

public class JButtonNormal extends JButtonPersonalizado {

// Constructores
    public JButtonNormal(String titulo) {
        this(titulo, Constantes.BOTON_NORMAL_ANCHO_DEFAULT);
    }

    public JButtonNormal(String titulo, int ancho) {
        super(titulo);
        
        final int ALTO_DEFAULT=Constantes.BOTON_NORMAL_ALTO_DEFAULT;
        final Font FUENTE=Constantes.FUENTE_NEGRITA;
        
        setPreferredSize( new Dimension( ancho, ALTO_DEFAULT ) );
        setFont(FUENTE);
    }

}