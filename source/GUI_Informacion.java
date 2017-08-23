import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI_Informacion extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    
    private GUI_Frame parent;
    private JButton botonAplicar, botonRestaurar;
    private JTextField fieldTitulo;
    
    private Datos baseDatos;
    private String tituloOriginal;

// Constructores
    public GUI_Informacion(GUI_Frame p, Datos base) {
        super(p);
        parent=p;
        baseDatos=base;
        tituloOriginal=baseDatos.getTitulo();
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        
        String nuevoTitulo=fieldTitulo.getText();
        String titulo=tituloOriginal;
        
        final String DIRECTORIO=Constantes.DIRECTORIO_DATOS;
        final String EXTENSION=Constantes.EXTENSION_DATOS;
        String rutaOrigen=DIRECTORIO+"/"+titulo+"."+EXTENSION;
        String rutaDestino=DIRECTORIO+"/"+nuevoTitulo+"."+EXTENSION;
        
        if ( ! ( titulo.toUpperCase() ).equals( nuevoTitulo.toUpperCase() ) ) {
            
            if ( Filtro.nombreValido(nuevoTitulo) )
                // El nombre es válido para archivo
                if ( ! IO.existeArchivo(rutaDestino) )
                    // Renombra la base de datos
                    renombrar(nuevoTitulo, rutaOrigen, rutaDestino);
                    else {
                        // Si el título está en uso, pide confirmación
                        final int RESPUESTA_SI=Constantes.MENSAJE_RESPUESTA_SI;
                        int respuesta=Mensaje.mostrarSobreescribirBaseDatos(this);
                        
                        if (respuesta==RESPUESTA_SI)
                            // Renombra la base de datos
                            renombrar(nuevoTitulo, rutaOrigen, rutaDestino);
                            else
                                restaurar();
                    }
            else {
                // El nombre no es válido para archivo
                Mensaje.mostrarTituloInvalido(this);
                restaurar();
            }
            
        } else
            // Renombra directamente
            if ( IO.renombrar(rutaOrigen, rutaDestino) )
                Mensaje.mostrarRenombrarCorrectamente(this);
    }

    private void renombrar(String nuevoTitulo, String rutaOrigen, String rutaDestino) {
        boolean seRenombro=IO.renombrarArchivo(rutaOrigen, rutaDestino);
        if (seRenombro) {
            boolean esDefault=baseDatos.esBaseDefault();
            
            // Si renombra correctamente, cambia el título de la base de datos
            baseDatos.setTitulo(nuevoTitulo);
            
            // Si la base de datos era la predeterminada, actualiza el archivo default.cfg
            if (esDefault)
                baseDatos.establecerComoDefault();
            
            // Muestra un mensaje confirmando la acción
            Mensaje.mostrarRenombrarCorrectamente(this);
            
            tituloOriginal=nuevoTitulo;
            bloquearBotones();
            
        } else {
            //  Si no renombra, muestra un mensaje
            Mensaje.mostrarErrorRenombrar(this);
            restaurar();
        }
    }

    private void backup() {
        new GUI_Backup(this, baseDatos);
    }

    private void chequearField() {
        String texto=fieldTitulo.getText();
        if ( texto.equals(tituloOriginal) )
            bloquearBotones();
            else
                desbloquearBotones();
    }

    private void bloquearBotones() {
        botonAplicar.setEnabled(false);
        botonRestaurar.setEnabled(false);
    }

    private void desbloquearBotones() {
        botonAplicar.setEnabled(true);
        botonRestaurar.setEnabled(true);
    }

    private void restaurar() {
        fieldTitulo.setText(tituloOriginal);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        bloquearBotones();
        setTitle(tituloOriginal);
        setSize(480,280);
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelBanner=armarPanelBanner();
        JPanel panelInformacion=armarPanelInformacion();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setBorder(BORDE_PRINCIPAL);
            panelPrincipal.setLayout(new BorderLayout(0,5));
            panelPrincipal.add(panelBanner, BorderLayout.NORTH);
            panelPrincipal.add(panelInformacion, BorderLayout.CENTER);
            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelBanner() {
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_INFORMACION;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(BANNER) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelInformacion() {
        JPanel panelTitulo=armarPanelTitulo();
        JPanel panelEstadisticas=armarPanelEstadisticas();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0, 10) );
            panel.add(panelTitulo, BorderLayout.NORTH);
            panel.add(panelEstadisticas, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelTitulo() {
        JLabel label=new JLabelPersonalizado("Título");
        fieldTitulo=new JTextFieldPersonalizado(tituloOriginal, 13 );
            fieldTitulo.getDocument().addDocumentListener( new OyenteField() );
        botonRestaurar=new JButtonChico("Restaurar título original", 150);
            botonRestaurar.addActionListener( new OyenteRestaurar() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(label);
            panel.add(fieldTitulo);
            panel.add(botonRestaurar);
        
        return panel;
    }

    private JPanel armarPanelEstadisticas() {
        JLabel labelEstadisticas=new JLabel("Estadísticas");
        JLabel labelEjemplares=new JLabelPersonalizado("        Ejemplares: "+baseDatos.getListaEjemplares().cantElementos() );
        JLabel labelContactos=new JLabelPersonalizado("        Contactos: "+baseDatos.getListaContactos().cantElementos() );
        JLabel labelProgramadas=new JLabelPersonalizado("        Crías programadas: "+baseDatos.getListaCriasProgramadas().cantElementos() );
        JLabel labelNacidas=new JLabelPersonalizado("        Crías nacidas: "+baseDatos.getListaCriasNacidas().cantElementos() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(5,1) );
            panel.add(labelEstadisticas);
            panel.add(labelEjemplares);
            panel.add(labelContactos);
            panel.add(labelProgramadas);
            panel.add(labelNacidas);
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JButton botonCerrar=new JButtonChico("Cerrar");
            botonCerrar.addActionListener( new OyenteCerrar() );
        botonAplicar=new JButtonChico("Cambiar título",120);
            botonAplicar.addActionListener( new OyenteAceptar() );
        
        JPanel panelCentral=new JPanelTransparente();
            panelCentral.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelCentral.add(botonCerrar);
            panelCentral.add(botonAplicar);
        
        JButton botonBackup=new JButtonChico("Crear copia de seguridad", 170);
            botonBackup.addActionListener( new OyenteBackup() );
        
        JPanel panelBackup=new JPanelTransparente();
            panelBackup.add(botonBackup);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelCentral, BorderLayout.CENTER);
            panel.add(panelBackup, BorderLayout.EAST);
        
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
    private class OyenteBackup implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            backup();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteField implements DocumentListener {
        public void changedUpdate(DocumentEvent evento) {
        }
        
        public void insertUpdate(DocumentEvent evento) {
            chequearField();
        }
        
        public void removeUpdate(DocumentEvent evento) {
            chequearField();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteRestaurar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            restaurar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}