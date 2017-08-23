import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI_Editar_Persona extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_VIOLETA_OFF;
    private static final Color COLOR_BORDE=Constantes.COLOR_VIOLETA;
    
    private GUI_Frame parent;
    private JPanel panelVacio, panelMiniatura;
    private JTextArea areaNotas;
    private JTextField fieldDomicilio, fieldTelefono, fieldFoto;
    
    private Datos baseDatos;
    private Persona contacto;
    private boolean seEdito;

// Constructores
    public GUI_Editar_Persona(GUI_Frame p, Datos base, Persona c) {
        super(p);
        parent=p;
        baseDatos=base;
        seEdito=false;
        contacto=c;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean seEdito() {
        return seEdito;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        String domicilio=fieldDomicilio.getText();
        String telefono=fieldTelefono.getText();
        String notas=areaNotas.getText();
        
        contacto.setDomicilio(domicilio);
        contacto.setTelefono(telefono);
        contacto.setNotas(notas);
        
        String nombre=contacto.getNombre();
        String rutaFoto=fieldFoto.getText();
        if ( ! rutaFoto.isEmpty() ) {
            boolean seGuardo=IO_Fotos.guardarFotoContacto(nombre, rutaFoto);
            if ( ! seGuardo )
                Mensaje.mostrarErrorFoto(this);
        } else
            if ( panelVacio.isVisible() )
                IO_Fotos.eliminarFotoContacto(nombre);
        
        // Aumenta el contador de modificaciones de la base de datos
        baseDatos.aumentarContador();
        
        seEdito=true;
        
        // Cierra el diálogo
        cerrar();
    }

    private void examinar() {
        JFileChooser dialogo=new JFileChooserPersonalizado();
        
        int accion=dialogo.showDialog(this, "Seleccionar");
        if (accion==JFileChooser.APPROVE_OPTION) {
            String ruta=dialogo.getSelectedFile().getPath();
            fieldFoto.setText(ruta);
        }
    }

    private void descartarFoto() {
        panelMiniatura.setVisible(false);
        panelVacio.setVisible(true);
        setSize(280,411);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Editar contacto - "+contacto.getNombre());
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
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
        fieldDomicilio=new JTextFieldPersonalizado(contacto.getDomicilio(), 20);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldDomicilio, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelTelefono() {
        JLabel label=new JLabelPersonalizado("Teléfono");
        fieldTelefono=new JTextFieldPersonalizado(contacto.getTelefono(), 20);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldTelefono, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelNotas() {
        JLabel label=new JLabelPersonalizado("Notas");
        areaNotas=new JTextAreaPersonalizado(contacto.getNotas(), 7,20, true);
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
        
        panelVacio=armarPanelFotoVacio();
        panelMiniatura=armarPanelFotoMiniatura(imagen);
        
        if (imagen != null)
            // Hay foto
            panelVacio.setVisible(false);
            else {
                panelMiniatura.setVisible(false);
                setSize(280,411);
            }
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelVacio, BorderLayout.CENTER);
            panel.add(panelMiniatura, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelFotoVacio() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_FOLDER;
        
        JLabel label=new JLabelPersonalizado("Foto");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        fieldFoto=new JTextFieldPersonalizado(10);
        JButton boton=new JButtonChico("Examinar",104);
            boton.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            boton.addActionListener( new OyenteExaminar() );
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(fieldFoto);
            panelField.add(boton);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelField, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelFotoMiniatura(BufferedImage imagen) {
        
        JPanel panel=new JPanelTransparente();
        
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
            
            JButton botonDescartar=new JButtonChico("Descartar", 100);
                botonDescartar.addActionListener( new OyenteDescartar() );
            
            JPanel panelBoton=new JPanelTransparente();
                panelBoton.add(botonDescartar);
            
            panel.setLayout( new BorderLayout() );
            panel.add(panelImagen, BorderLayout.CENTER);
            panel.add(panelBoton, BorderLayout.SOUTH);
        }
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JButton botonAceptar=new JButtonChico("Aceptar");
            botonAceptar.addActionListener( new OyenteAceptar() );
        JButton botonCancelar=new JButtonChico("Cancelar");
            botonCancelar.addActionListener( new OyenteCerrar() );
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.add(botonAceptar);
            panelBotones.add(botonCancelar);
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelBotones);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteAceptar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            aceptar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteExaminar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            examinar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDescartar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            descartarFoto();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}