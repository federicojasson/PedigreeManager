import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI_Detalles_Persona extends GUI_Frame {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_VIOLETA_OFF;
    private static final Color COLOR_BORDE=Constantes.COLOR_VIOLETA;
    
    private GUI_Frame parent;
    private GUI_Imagen dialogoImagen;
    private JButton botonAmpliar;
    
    private Persona contacto;
    private boolean cerrar;

// Constructores
    public GUI_Detalles_Persona(GUI_Frame p, Persona c) {
        super();
        parent=p;
        contacto=c;
        cerrar=false;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        dispose();
        if (dialogoImagen != null)
            // Si hay una instancia de GUI_Imagen, la cierra
            dialogoImagen.cerrar();
    }

    public void alCerrarImagen() {
        // Invocado por cerrar() de GUI_Imagen
        // Realiza una acción al cerrar una GUI_Imagen
        botonAmpliar.setText("Ampliar");
        cerrar=false;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void ampliar() {
        if ( cerrar )
            dialogoImagen.cerrar();
            else {
                String nombre=contacto.getNombre();
                BufferedImage imagen=IO_Fotos.cargarFotoContacto(nombre);
                
                if (imagen != null) {
                    dialogoImagen=new GUI_Imagen(this, imagen, nombre);
                    botonAmpliar.setText("Cerrar foto");
                    cerrar=true;
                } else
                    Mensaje.mostrarFotoEliminada(this);
                
            }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle( contacto.getNombre() );
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelDatos=armarPanelDatos();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panel=new JPanel();
            panel.setBackground(COLOR_FONDO);
            panel.setBorder(BORDE_PRINCIPAL);
            panel.setLayout( new BorderLayout() );
            panel.add(panelDatos, BorderLayout.CENTER);
            panel.add(panelBotones, BorderLayout.SOUTH);
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_BORDE);
            panelPrincipal.setBorder(BORDE_PRINCIPAL);
            panelPrincipal.setLayout( new BorderLayout() );
            panelPrincipal.add(panel);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelDatos() {
        JPanel panelNombre=armarPanelNombre();
        JPanel panelDomicilio=armarPanelDomicilio();
        JPanel panelTelefono=armarPanelTelefono();
        JPanel panelNotas=armarPanelNotas();
        JPanel panelFoto=armarPanelFoto();
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelNombre);
            panel.add(panelDomicilio);
            panel.add(panelTelefono);
            panel.add(panelNotas);
            panel.add(panelFoto);
        
        return panel;
    }

    private JPanel armarPanelNombre() {
        JLabel label=new JLabelPersonalizado("Nombre");
        JTextField fieldNombre=new JTextFieldPersonalizado(contacto.getNombre(), 20);
            fieldNombre.setEditable(false);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldNombre, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelDomicilio() {
        JLabel label=new JLabelPersonalizado("Domicilio");
        JTextField fieldDomicilio=new JTextFieldPersonalizado(contacto.getDomicilio(), 20);
            fieldDomicilio.setEditable(false);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldDomicilio, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelTelefono() {
        JLabel label=new JLabelPersonalizado("Teléfono");
        JTextField fieldTelefono=new JTextFieldPersonalizado(contacto.getTelefono(), 20);
            fieldTelefono.setEditable(false);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldTelefono, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelNotas() {
        JLabel label=new JLabelPersonalizado("Notas");
        JTextArea areaNotas=new JTextAreaPersonalizado(contacto.getNotas(), 7,20, true);
            areaNotas.setEditable(false);
        JScrollPane scrollNotas=new JScrollPanePersonalizado(areaNotas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(scrollNotas, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelFoto() {
        String nombre=contacto.getNombre();
        
        BufferedImage imagen=IO_Fotos.cargarFotoMiniaturaContacto(nombre);
        
        JPanel panelMiniatura=armarPanelFotoMiniatura(imagen);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelMiniatura);
        
        return panel;
    }

    private JPanel armarPanelFotoMiniatura(BufferedImage imagen) {
        
        JPanel panel=null;
        
        if (imagen != null) {
            // Hay una imagen
            
            JLabel label=new JLabel( new ImageIcon(imagen) );
            int ancho=imagen.getWidth();
            int alto=imagen.getHeight();
            
            setSize(280, 416+alto);
            
            JPanel panelRecuadro=new JPanelRecuadro(ancho,alto);
                panelRecuadro.setLayout( new BorderLayout() );
                panelRecuadro.add(label);
            
            JPanel panelImagen=new JPanelTransparente();
                panelImagen.add(panelRecuadro);
            
            botonAmpliar=new JButtonChico("Ampliar", 100);
                botonAmpliar.addActionListener( new OyenteAmpliar() );
            
            JPanel panelBoton=new JPanelTransparente();
                panelBoton.add(botonAmpliar);
            
            panel=new JPanelTransparente();
                panel.setLayout( new BorderLayout() );
                panel.add(panelImagen, BorderLayout.CENTER);
                panel.add(panelBoton, BorderLayout.SOUTH);
            
        } else {
            // No hay imagen
            setSize(280,408);
            panel=new JPanelRecuadro("No hay foto para mostrar", 232,40);
        }
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JButton botonCerrar=new JButtonChico("Cerrar");
            botonCerrar.addActionListener( new OyenteCerrar() );
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.add(botonCerrar);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(panelBotones);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteAmpliar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            ampliar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}