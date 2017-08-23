import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class JPanelRecuadro extends JPanel {

// Constructores
    public JPanelRecuadro(String title, int ancho, int alto) {
        super();
        
        final Color COLOR_FONDO=Constantes.COLOR_GRIS_OFF;
        final Color COLOR_LETRA=Constantes.COLOR_GRIS_DEFAULT;
        
        JLabel label=new JLabelPersonalizado(title, COLOR_LETRA, JLabel.CENTER);
        
        setPreferredSize( new Dimension(ancho, alto) );
        setBackground(COLOR_FONDO);
        setLayout( new BorderLayout() );
        add(label);
        
        final Border BORDE=Constantes.BORDE_LADOS_HUNDIDOS;
        setBorder(BORDE);
    }

    public JPanelRecuadro(int ancho, int alto) {
        super();
        
        final int DIFERENCIA=Constantes.DIFERENCIA_BORDE_RECUADRO;
        
        setOpaque(false);
        setPreferredSize( new Dimension(ancho+DIFERENCIA, alto+DIFERENCIA) );
        
        final Border BORDE=Constantes.BORDE_RECUADRO;
        setBorder(BORDE);
    }

}