import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI_Imagen extends GUI_Dialog {

// Atributos
    private static final int DIF_ANCHO=Constantes.IMAGEN_DIFERENCIA_ANCHO;
    private static final int DIF_ALTO=Constantes.IMAGEN_DIFERENCIA_ALTO;
    
    private GUI_Frame parent;
    private JLabel labelFoto;
    private JPanel panelFoto;
    
    private BufferedImage imagen, imagenOriginal;
    private int ancho, alto;

// Constructores
    public GUI_Imagen(GUI_Frame p, BufferedImage img, String titulo) {
        super();
        parent=p;
        imagenOriginal=img;
        setTitle(titulo);
        
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        dispose();
        parent.alCerrarImagen();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void redimensionarVentana() {
        // Establece la dimensión inicial teniendo en cuenta la resolución de la pantalla
        setSize(ancho+DIF_ANCHO, alto+DIF_ALTO);
    }

    private void actualizarImagen() {
        panelFoto.remove(labelFoto);
        
        labelFoto=new JLabel( new ImageIcon(imagen) );
        
        panelFoto.add(labelFoto);
        panelFoto.validate();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        ancho=imagenOriginal.getWidth();
        alto=imagenOriginal.getHeight();
        
        final Toolkit TOOLKIT=Constantes.TOOLKIT;
        
        Dimension resolucion=TOOLKIT.getScreenSize();
        int anchoResolucion=(int)resolucion.getWidth()-20-DIF_ANCHO;
        int altoResolucion=(int)resolucion.getHeight()-50-DIF_ALTO;
        
        final int FOTO_SIZE=Constantes.FOTO_MINIATURA_SIZE;
        
        // Inicializa la variable 'imagen' con las dimensiones originales, si superan la resolución, se la redimensiona
        if (ancho > alto) {
            setMinimumSize ( new Dimension ( FOTO_SIZE+DIF_ANCHO , FOTO_SIZE*alto/ancho+DIF_ALTO ) );
            if (ancho <= anchoResolucion)
                imagen=IO_Fotos.redimensionar(imagenOriginal, ancho);
                else {
                    imagen=IO_Fotos.redimensionar(imagenOriginal, anchoResolucion);
                    if (imagen.getHeight() > altoResolucion)
                        imagen=IO_Fotos.redimensionar(imagenOriginal, altoResolucion, Constantes.FOTO_REDIMENSIONAR_ALTO);
                }
        }
            else {
            setMinimumSize ( new Dimension ( FOTO_SIZE*ancho/alto+DIF_ANCHO , FOTO_SIZE+DIF_ALTO ) );
            if (alto <= altoResolucion)
                imagen=IO_Fotos.redimensionar(imagenOriginal, alto);
                else {
                    imagen=IO_Fotos.redimensionar(imagenOriginal, altoResolucion);
                    if (imagen.getWidth() > anchoResolucion)
                        imagen=IO_Fotos.redimensionar(imagenOriginal, anchoResolucion, Constantes.FOTO_REDIMENSIONAR_ANCHO);
                }
        }
        
        ancho=imagen.getWidth();
        alto=imagen.getHeight();
        
        redimensionarVentana();
        
        labelFoto=new JLabel( new ImageIcon(imagen) );
        
        final Color COLOR_FONDO=Constantes.COLOR_NEGRO;
        panelFoto=new JPanel();
            panelFoto.setBackground(COLOR_FONDO);
            panelFoto.setLayout( new BorderLayout() );
            panelFoto.add(labelFoto);
        getContentPane().add(panelFoto);
        
        addComponentListener( new OyenteRedimensionar() );
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteRedimensionar extends ComponentAdapter implements ComponentListener {
        
        // Atributos
            private int an, alt;
        
        public void componentResized(ComponentEvent evento) {
            
            super.componentResized(evento);
            
            // Recibe las nuevas dimensiones del frame
            an=GUI_Imagen.this.getWidth()-DIF_ANCHO;
            alt=GUI_Imagen.this.getHeight()-DIF_ALTO;
            
            // Redimensiona la imagen
            if ( an != ancho )
                imagen=IO_Fotos.redimensionar(imagenOriginal, an, Constantes.FOTO_REDIMENSIONAR_ANCHO);
                else
                    if ( alt != alto)
                        imagen=IO_Fotos.redimensionar(imagenOriginal, alt, Constantes.FOTO_REDIMENSIONAR_ALTO);
            
            // Actualiza la imagen y las variables 'ancho' y 'alto'
            actualizarImagen();
            ancho=imagen.getWidth();
            alto=imagen.getHeight();
            
            // Redimensiona la ventana
            redimensionarVentana();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}