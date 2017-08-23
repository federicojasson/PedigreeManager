import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JFileChooserPersonalizado extends JFileChooser {

// Atributos
    private static final int LONG_LADO=Constantes.FOTO_MINIATURA_SIZE;
    private static final int DIFERENCIA=Constantes.DIFERENCIA_BORDE_RECUADRO;
    private static final Border BORDE=Constantes.BORDE_RECUADRO;
    
    private JLabel labelImagen;
    private JPanel panelImagen;
    
    private BufferedImage imagen;

// Constructores
    public JFileChooserPersonalizado() {
        super();
        setDialogTitle("Buscar foto");
        
        final FileNameExtensionFilter FILTRO=Constantes.FILTRO_EXTENSIONES_IMAGENES;
        setFileFilter(FILTRO);
        
        addPropertyChangeListener( new OyentePreview() );
        
        JPanel panelPreview=armarPanelPreview();
        setAccessory(panelPreview);
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    private JPanel armarPanelPreview() {
        JLabel label=new JLabelPersonalizado("Vista previa");
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.add(label);
        
        labelImagen=new JLabel();
        
        panelImagen=new JPanelTransparente();
            panelImagen.setPreferredSize( new Dimension(LONG_LADO+20, LONG_LADO+20) );
            panelImagen.add(labelImagen);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelImagen, BorderLayout.CENTER);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void imprimirPreview(File archivo) {
        
        imagen=null;
        
        panelImagen.remove(labelImagen);
        
        if (archivo != null) {
            String ruta=archivo.getPath();
            imagen=IO_Fotos.cargarFoto(ruta);
            imagen=IO_Fotos.redimensionar(imagen, LONG_LADO);
            labelImagen=new JLabel( new ImageIcon(imagen) );
                labelImagen.setBorder(BORDE);
                labelImagen.setPreferredSize( new Dimension( imagen.getWidth()+DIFERENCIA, imagen.getHeight()+DIFERENCIA ) );
        } else
            labelImagen=new JLabel();
        
        panelImagen.add(labelImagen);
        panelImagen.validate();
        panelImagen.repaint();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    
// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyentePreview implements PropertyChangeListener {
        
        // Atributos
            private boolean actualizar;
            private File archivoSeleccionado;
            private String accion;
        
        public void propertyChange(PropertyChangeEvent evento) {
            
            actualizar=false;
            archivoSeleccionado=null;
            accion=evento.getPropertyName();
            
            // El preview se actualizará sólo cuando el evento se relacione con un directorio o archivo
            if ( DIRECTORY_CHANGED_PROPERTY.equals(accion) )
                // Si se selecciona un directorio, se imprime una imagen vacía
                actualizar=true;
                else
                    if ( SELECTED_FILE_CHANGED_PROPERTY.equals(accion) ) {
                        // Si se selecciona un archivo, intenta imprimir una imagen en miniatura
                        actualizar=true;
                        archivoSeleccionado=(File)evento.getNewValue();
                    }
            
            if (actualizar)
                try {
                    // Imprime la imagen en miniatura
                    imprimirPreview(archivoSeleccionado);
                } catch (Exception exc) {
                    // Si ocurre algún problema, imprime una imagen vacía
                    imprimirPreview(null);
                }
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}