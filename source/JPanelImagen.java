import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class JPanelImagen extends JPanel {

// Atributos
    private Image imagenFondo;

// Constructores
    public JPanelImagen(Image img) {
        imagenFondo=img;
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, null);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}