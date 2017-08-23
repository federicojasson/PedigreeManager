import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

public class JButtonChico extends JButtonPersonalizado {

// Constructores
    public JButtonChico(String titulo) {
        this(titulo, Constantes.BOTON_CHICO_ANCHO_DEFAULT);
    }

    public JButtonChico(String titulo, int ancho) {
        super(titulo);
        
        final int ALTO_DEFAULT=Constantes.BOTON_CHICO_ALTO_DEFAULT;
        final Font FUENTE=Constantes.FUENTE_CHICA;
        final Color COLOR_LETRA=Constantes.COLOR_GRIS_OSCURO;
        
        setPreferredSize( new Dimension( ancho, ALTO_DEFAULT ) );
        setFont(FUENTE);
        setForeground(COLOR_LETRA);
    }

    public JButtonChico(int ancho) {
        super();
        
        final int ALTO_DEFAULT=Constantes.BOTON_CHICO_ALTO_DEFAULT;
        
        setPreferredSize( new Dimension(ancho, ALTO_DEFAULT) );
        setMargin( new Insets(0,0,0,0) );
    }

}