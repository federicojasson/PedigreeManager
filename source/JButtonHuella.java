import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.*;

public class JButtonHuella extends JButton {

// Constructores
    public JButtonHuella() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_HUELLA_DISABLED;
        final String ICONO_PRESSED=Constantes.ARCHIVO_IMAGEN_ICONO_HUELLA_PRESSED;
        
        setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
        setPressedIcon ( new ImageIcon( getClass().getResource(ICONO_PRESSED) ) );
        
        setBorderPainted(false);
        setFocusable(false);
        setOpaque(false);
        
        setPreferredSize( new Dimension(64,64) );
        setMargin( new Insets(0,0,0,0) );
    }

}