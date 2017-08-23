import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI_Inicial extends GUI_Frame {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Border BORDE_SANGRIA=Constantes.BORDE_SANGRIA;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    
    private JButton botonEliminar, botonBackup;
    private JCheckBox checkDefault;
    private JComboBox boxLista;
    private JPanel panelLista;
    private JRadioButton botonNuevo, botonCargar;
    private JTextField fieldNuevo;

// Constructores
    public GUI_Inicial() {
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        System.exit(0);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        
        String titulo;
        
        if ( botonNuevo.isSelected() ) {
            // Intenta crear una nueva base de datos
            titulo=fieldNuevo.getText();
            // Chequea que el título sea válido
            if ( ! Filtro.nombreValido(titulo) )
                // No es un título válido
                Mensaje.mostrarNombreInvalido(this);
                else
                    // El título es válido
                    // Chequea que no exista
                    if ( Datos.existeBaseDatos(titulo) ) {
                        final int RESPUESTA_SI=Constantes.MENSAJE_RESPUESTA_SI;
                        int opcion=Mensaje.mostrarSobreescribirBaseDatos(this);
                        if (opcion==RESPUESTA_SI)
                            cargarNuevaBaseDatos(titulo);
                    } else
                        cargarNuevaBaseDatos(titulo);
        } else {
            // Carga, si existe, la base de datos seleccionada
            titulo=(String)boxLista.getSelectedItem();
            cargarBaseDatos(titulo);
        }
    }

    private void cargarNuevaBaseDatos(String titulo) {
        // Crea una nueva base de datos e inicializa el programa si no hay problemas
        Datos baseDatos=new Datos();
        boolean seCargo=baseDatos.cargarNuevosDatos(titulo);
        if ( ! seCargo )
            Mensaje.mostrarError101(this);
            else
                iniciarPrograma(baseDatos);
    }

    private void cargarBaseDatos(String titulo) {
        // Carga una base de datos existente e inicializa el programa si no hay problemas
        Datos baseDatos=new Datos();
        boolean seCargo=baseDatos.cargarDatos(titulo);
        if ( ! seCargo )
            Mensaje.mostrarError101(this);
            else
                iniciarPrograma(baseDatos);
    }

    private void iniciarPrograma(Datos baseDatos) {
        // Si está tildado, establece la base como predeterminada
        if ( checkDefault.isSelected() )
            baseDatos.establecerComoDefault();
        dispose();
        new GUI_Principal(baseDatos);
    }

    private void eliminar() {
        // Asume que hay un elemento de la lista seleccionado
        String titulo=(String)boxLista.getSelectedItem();
        
        // Pide confirmación
        final int RESPUESTA_SI=Constantes.MENSAJE_RESPUESTA_SI;
        int respuesta=Mensaje.mostrarEliminarBaseDatos(this);
        if (respuesta==RESPUESTA_SI) {
            boolean seElimino=Datos.eliminarBaseDatos(titulo);
            if ( ! seElimino )
                Mensaje.mostrarError101(this);
                else
                    actualizarLista();
        }
    }

    private void backup() {
        // Asume que hay un elemento de la lista seleccionado
        String titulo=(String)boxLista.getSelectedItem();
        new GUI_Backup(this, titulo);
    }

    private void seleccionarNuevo() {
        botonNuevo.setSelected(true);
        fieldNuevo.setEnabled(true);
        boxLista.setEnabled(false);
        botonEliminar.setEnabled(false);
        botonBackup.setEnabled(false);
    }

    private void seleccionarCargar() {
        botonCargar.setSelected(true);
        fieldNuevo.setEnabled(false);
        boxLista.setEnabled(true);
        botonEliminar.setEnabled(true);
        botonBackup.setEnabled(true);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Pedigree Manager - Seleccionar base de datos");
        setSize(510,288);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelBanner=armarPanelBanner();
        JPanel panelSeleccion=armarPanelSeleccion();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setBorder(BORDE_PRINCIPAL);
            panelPrincipal.setLayout(new BorderLayout());
            panelPrincipal.add(panelBanner, BorderLayout.NORTH);
            panelPrincipal.add(panelSeleccion, BorderLayout.CENTER);
            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelBanner() {
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_DATOS;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(BANNER) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelSeleccion() {
        JPanel panelNuevo=armarPanelNuevo();
        JPanel panelCargar=armarPanelCargar();
        JPanel panelDefault=armarPanelDefault();
        
        ButtonGroup grupo=new ButtonGroup();
        grupo.add(botonNuevo);
        grupo.add(botonCargar);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new BorderLayout());
            panel.add(panelNuevo, BorderLayout.NORTH);
            panel.add(panelCargar, BorderLayout.CENTER);
            panel.add(panelDefault, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelNuevo() {
        botonNuevo=new JRadioButtonPersonalizado("Nueva base de datos");
            botonNuevo.addActionListener(new OyenteSeleccion());
        
        JLabel label=new JLabelPersonalizado("Título:");
        fieldNuevo=new JTextFieldPersonalizado(15);
        JPanel panelField=new JPanelTransparente();
            panelField.setBorder(BORDE_SANGRIA);
            panelField.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelField.add(label);
            panelField.add(fieldNuevo);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new GridLayout(2,1));
            panel.add(botonNuevo);
            panel.add(panelField);
        
        return panel;
    }

    private JPanel armarPanelCargar() {
        botonCargar=new JRadioButtonPersonalizado("Cargar base de datos");
            botonCargar.addActionListener(new OyenteSeleccion());
        
        JLabel label=new JLabelPersonalizado("Título:");
        
        final String ICONO_ELIMINAR=Constantes.ARCHIVO_IMAGEN_ICONO_ELIMINAR;
        botonEliminar=new JButtonChico("Eliminar", 100);
            botonEliminar.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEliminar.setIcon( new ImageIcon( getClass().getResource(ICONO_ELIMINAR) ) );
            botonEliminar.addActionListener( new OyenteEliminar() );
        
        botonBackup=new JButtonChico("Copia de seguridad", 130);
            botonBackup.addActionListener( new OyenteBackup() );
        
        String [ ] lista=Datos.obtenerBasesDisponibles();
        if (lista!=null) {
            boxLista=new JComboBoxPersonalizado(lista, 186);
            seleccionarCargar();
        } else {
            lista = new String [ 1 ];
            lista [ 0 ] = " -- ";
            boxLista=new JComboBoxPersonalizado(lista, 186);
            botonCargar.setEnabled(false);
            seleccionarNuevo();
        }
        
        panelLista=new JPanelTransparente();
            panelLista.setBorder(BORDE_SANGRIA);
            panelLista.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelLista.add(label);
            panelLista.add(boxLista);
            panelLista.add(botonEliminar);
            panelLista.add(botonBackup);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new GridLayout(2,1));
            panel.add(botonCargar);
            panel.add(panelLista);
        
        return panel;
    }

    private JPanel armarPanelDefault() {
        checkDefault=new JCheckBoxPersonalizado("Utilizar como predeterminada");
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(checkDefault);
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JButton botonAceptar=new JButtonChico("Ingresar");
            botonAceptar.addActionListener(new OyenteAceptar());
        JButton botonSalir=new JButtonChico("Salir");
            botonSalir.addActionListener(new OyenteCerrar());
        
        JPanel panel=new JPanelTransparente();
            panel.add(botonAceptar);
            panel.add(botonSalir);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void actualizarLista() {
        panelLista.remove(boxLista);
        
        String [ ] lista=Datos.obtenerBasesDisponibles();
        if (lista!=null) {
            boxLista=new JComboBoxPersonalizado(lista, 146);
            seleccionarCargar();
        } else {
            lista = new String [ 1 ];
            lista [ 0 ] = " -- ";
            boxLista=new JComboBoxPersonalizado(lista, 146);
            botonCargar.setEnabled(false);
            seleccionarNuevo();
        }
        
        panelLista.add(boxLista,1);
        panelLista.validate();
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
    private class OyenteSeleccion implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            if (botonNuevo.isSelected())
                seleccionarNuevo();
                else
                    seleccionarCargar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEliminar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            eliminar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteBackup implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            backup();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}