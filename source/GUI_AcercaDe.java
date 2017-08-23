import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GUI_AcercaDe extends GUI_Dialog {

// Atributos
    private static final Color COLOR_LETRA=Constantes.COLOR_AZUL_MARINO;
    private static final Font FUENTE1=new Font("Vani", Font.PLAIN, 24);
    private static final Font FUENTE2=new Font("Times New Roman", Font.ITALIC, 24);
    private static final Font FUENTE3=new Font("Verdanai", Font.PLAIN, 16);
    
    private GUI_Frame parent;

// Constructores
    public GUI_AcercaDe(GUI_Frame p) {
        super(p);
        parent=p;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Acerca de Pedigree Manager");
        setSize(468,510);
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelAnio=armarPanelAnio();
        JPanel panelCredito=armarPanelCredito();
        JPanel panelProgramado=armarPanelProgramado();
        
        final Border BORDE=Constantes.BORDE_SUPERIOR;
        
        JPanel panelLabels=new JPanelTransparente();
            panelLabels.setBorder(BORDE);
            panelLabels.setPreferredSize( new Dimension(400,430) );
            panelLabels.setLayout( new BorderLayout(0,20) );
            panelLabels.add(panelAnio, BorderLayout.NORTH);
            panelLabels.add(panelCredito, BorderLayout.CENTER);
            panelLabels.add(panelProgramado, BorderLayout.SOUTH);
        
        final String IMAGEN_FONDO=Constantes.ARCHIVO_IMAGEN_FONDO_ACERCA_DE;
        
        Image imagenFondo=null;
        try{
            imagenFondo=ImageIO.read( getClass().getResource(IMAGEN_FONDO) );
        } catch(Exception exc) {
            // Hubo un problema al cargar la imagen
        }
        
        JPanel panelPrincipal=new JPanelImagen(imagenFondo);
            panelPrincipal.add(panelLabels);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelAnio() {
        JLabel label=new JLabel("2010");
            label.setForeground(COLOR_LETRA);
            label.setFont(FUENTE1);
        
        JPanel panel=new JPanelTransparente();
            panel.add(label);
        
        return panel;
    }

    private JPanel armarPanelCredito() {
        JPanel panelImplementado=armarPanelImplementado();
        JPanel panelNombre=armarPanelNombre();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,20) );
            panel.add(panelImplementado, BorderLayout.CENTER);
            panel.add(panelNombre, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelNombre() {
        JLabel label1=new JLabel("Federico R. Jasson");
            label1.setFont(FUENTE1);
        JLabel label2=new JLabel("Contacto: federicojasson@gmail.com");
            label2.setFont(FUENTE1);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label1, BorderLayout.NORTH);
            panel.add(label2, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelImplementado() {
        JLabel label=new JLabel("Diseñado e implementado por:");
            label.setForeground(COLOR_LETRA);
            label.setFont(FUENTE2);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(label);
        
        return panel;
    }

    private JPanel armarPanelProgramado() {
        JLabel label=new JLabel("Programado en Java, IDE BlueJ 2.5.2");
            label.setForeground(COLOR_LETRA);
            label.setFont(FUENTE3);
        
        JPanel panel=new JPanelTransparente();
            panel.add(label);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}