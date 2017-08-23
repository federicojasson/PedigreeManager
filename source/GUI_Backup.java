import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI_Backup extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_DIALOGO;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    
    private Component parent;
    private JCheckBox checkGuardar;
    private JTextField fieldNombre;
    
    private Datos baseDatos;
    private String tituloBase;

// Constructores
    public GUI_Backup(GUI_Frame p, Datos base) {
        super(p);
        parent=p;
        baseDatos=base;
        crearGUI();
    }

    public GUI_Backup(GUI_Frame p, String base) {
        super(p);
        parent=p;
        tituloBase=base;
        crearGUI();
    }

    public GUI_Backup(GUI_Dialog p, Datos base) {
        super(p);
        parent=p;
        baseDatos=base;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        final String DIRECTORIO=Constantes.DIRECTORIO_BACKUPS;
        
        String titulo=fieldNombre.getText();
        
        if ( ! Filtro.nombreValido(titulo) )
            // El nombre no es válido para archivo
            Mensaje.mostrarNombreInvalido(this);
            else
                
                if ( ! IO.existeArchivo(DIRECTORIO) ) {
                    // Si el directorio no existe, lo genera y crea la copia de seguridad
                    IO.crearDirectorios(DIRECTORIO);
                    crearBackup(titulo);
                } else
                    if ( Datos.existeBackup(titulo) ) {
                        // Ya existe un archivo con el mismo nombre
                        // Pide confirmación
                        final int RESPUESTA_SI=Constantes.MENSAJE_RESPUESTA_SI;
                        int respuesta=Mensaje.mostrarSobreescribirBaseDatos(this);
                        if (respuesta==RESPUESTA_SI)
                        crearBackup(titulo);
                    } else
                        crearBackup(titulo);
    }

    private void crearBackup(String nombre) {
        // Se ha confirmado completamente la acción
        boolean seCreo;
        if (baseDatos==null)
            seCreo=Datos.crearBackup(tituloBase, nombre);
            else
                seCreo=baseDatos.crearBackup(nombre, checkGuardar.isSelected() );
        
        if ( seCreo ) {
            Mensaje.mostrarBackup(this);
            cerrar();
        } else
            Mensaje.mostrarError101(this);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Crear copia de seguridad");
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelContenido=armarPanelContenido();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setBorder(BORDE_PRINCIPAL);
            panelPrincipal.setLayout(new BorderLayout(0,5));
            panelPrincipal.add(panelContenido, BorderLayout.CENTER);
            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelContenido() {
        JPanel panelIcono=armarPanelIcono();
        JPanel panelInput=armarPanelInput();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new BorderLayout(10,0));
            panel.add(panelIcono, BorderLayout.WEST);
            panel.add(panelInput, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelIcono() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_BACKUP;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(ICONO) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new GridLayout());
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelInput() {
        JLabel labelIntro=new JLabelPersonalizado("Inserte un nombre para la copia de la base de datos");
        
        JLabel label=new JLabelPersonalizado("Título: ");
        fieldNombre=new JTextFieldPersonalizado(20);
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(label);
            panelField.add(fieldNombre);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(labelIntro);
            panel.add(panelField);
            if (baseDatos != null) {
                
                checkGuardar=new JCheckBoxPersonalizado("Guardar datos antes de crear la copia");
                    checkGuardar.setSelected(true);
                
                panel.setLayout( new GridLayout(3,1) );
                panel.add(checkGuardar);
            }
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JButton botonAceptar=new JButtonChico("Aceptar");
            botonAceptar.addActionListener(new OyenteAceptar());
        JButton botonCancelar=new JButtonChico("Cancelar");
            botonCancelar.addActionListener( new OyenteCerrar() );
        
        JPanel panel=new JPanelTransparente();
            panel.add(botonAceptar);
            panel.add(botonCancelar);
        
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
    
//----------------------------------------------------------------------------------------------------------------------------------------------

}