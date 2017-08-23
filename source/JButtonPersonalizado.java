import javax.swing.JButton;

public abstract class JButtonPersonalizado extends JButton {

// Constructores
    public JButtonPersonalizado(String titulo) {
        super(titulo);
        setFocusable(false);
    }

    public JButtonPersonalizado() {
        super();
        setFocusable(false);
    }

}