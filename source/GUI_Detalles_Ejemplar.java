import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI_Detalles_Ejemplar extends GUI_Frame {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_VERDE_OFF;
    private static final Color COLOR_BORDE=Constantes.COLOR_VERDE;
    
    private Component parent;
    private GUI_Imagen dialogoImagen;
    private JButton botonPadre, botonMadre, botonPropietario, botonCriador, botonPedigree, botonAmpliar;
    private JPanel panelLayout, panelPedigree, panelDatos, panelDerecha;
    
    private Datos baseDatos;
    private Ejemplar ejemplar;
    private boolean cerrar;

// Constructores
    public GUI_Detalles_Ejemplar(GUI_Frame p, Datos base, Ejemplar e) {
        super();
        parent=p;
        baseDatos=base;
        ejemplar=e;
        cerrar=false;
        crearGUI();
    }

    public GUI_Detalles_Ejemplar(JTextFieldPedigree p, Datos base, Ejemplar e) {
        super();
        parent=p;
        baseDatos=base;
        ejemplar=e;
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
                String nombre=ejemplar.getNombre();
                BufferedImage imagen=IO_Fotos.cargarFotoEjemplar(nombre);
                
                if (imagen != null) {
                    dialogoImagen=new GUI_Imagen(this, imagen, nombre);
                    botonAmpliar.setText("Cerrar foto");
                    cerrar=true;
                } else
                    Mensaje.mostrarFotoEliminada(this);
                
            }
    }

    private void padre() {
        Ejemplar padre=(Ejemplar)ejemplar.getPadre();
        if ( padre != null )
            new GUI_Detalles_Ejemplar(this, baseDatos, padre);
            else
                Mensaje.mostrarElementoEliminado(this);
    }

    private void madre() {
        Ejemplar madre=(Ejemplar)ejemplar.getMadre();
        if ( madre != null )
            new GUI_Detalles_Ejemplar(this, baseDatos, madre);
            else
                Mensaje.mostrarElementoEliminado(this);
    }

    private void propietario() {
        Persona propietario=ejemplar.getPropietario();
        if ( propietario != null )
            new GUI_Detalles_Persona(this, propietario);
            else
                Mensaje.mostrarElementoEliminado(this);
    }

    private void criador() {
        Persona criador=ejemplar.getCriador();
        if ( criador != null )
            new GUI_Detalles_Persona(this, criador);
            else
                Mensaje.mostrarElementoEliminado(this);
    }

    private void pedigree() {
        if ( panelPedigree.isShowing() ) {
            // Si es visible, lo oculta
            botonPedigree.setText("Mostrar Pedigree");
            
            panelLayout.remove(panelPedigree);
            
            int anchoActual=getWidth();
            
            setSize(550, getHeight() );
            
            if ( anchoActual < 1265 ) {
                panelDerecha=armarPanelDerecha();
                panelDatos.add(panelDerecha);
                panelDatos.validate();
            }
            
            Point localizacion=getLocation();
            setLocation( (int)localizacion.getX()+(anchoActual-550)/2 , (int)localizacion.getY() );
        } else {
            // Si no es visible, lo muestra
            botonPedigree.setText("Ocultar Pedigree");
            
            Point localizacion;
            
            int anchoResolucion=(int)Constantes.TOOLKIT.getScreenSize().getWidth();
            if ( anchoResolucion < 1265 ) {
                setSize(anchoResolucion, getHeight() );
                
                panelDatos.remove(panelDerecha);
                panelDatos.validate();
                
                localizacion=getLocation();
                setLocation( (int)localizacion.getX()-(anchoResolucion-550)/2, (int)localizacion.getY() );
            } else {
                setSize(1265, getHeight() );
                
                localizacion=getLocation();
                setLocation( (int)localizacion.getX()-357 , (int)localizacion.getY() );
            }
            
            panelLayout.add(panelPedigree, BorderLayout.EAST);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle( ejemplar.getNombre() );
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void armarPaneles() {
        panelDatos=armarPanelDatos();
        panelPedigree=armarPanelPedigree();
        
        panelLayout=new JPanel();
            panelLayout.setBackground(COLOR_FONDO);
            panelLayout.setBorder(BORDE_PRINCIPAL);
            panelLayout.setLayout( new BorderLayout() );
            panelLayout.add(panelDatos, BorderLayout.CENTER);
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_BORDE);
            panelPrincipal.setBorder(BORDE_PRINCIPAL);
            panelPrincipal.setLayout( new BorderLayout() );
            panelPrincipal.add(panelLayout);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelDatos() {
        JPanel panelIzquierda=armarPanelIzquierda();
        panelDerecha=armarPanelDerecha();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPanelIzquierda() {
        JPanel panelNombre=armarPanelNombre();
        JPanel panelFecha=armarPanelFecha();
        JPanel panelSexo=armarPanelSexo();
        JPanel panelPadre=armarPanelPadre();
        JPanel panelMadre=armarPanelMadre();
        JPanel panelPropietario=armarPanelPropietario();
        JPanel panelCriador=armarPanelCriador();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelDatos=new JPanelTransparente();
            panelDatos.add(panelNombre);
            panelDatos.add(panelFecha);
            panelDatos.add(panelSexo);
            panelDatos.add(panelPadre);
            panelDatos.add(panelMadre);
            panelDatos.add(panelPropietario);
            panelDatos.add(panelCriador);
        
        JPanel panelInferior=new JPanelTransparente();
            panelInferior.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelInferior.add(panelBotones);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelDatos, BorderLayout.CENTER);
            panel.add(panelInferior, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelNombre() {
        JLabel label=new JLabelPersonalizado("Nombre");
        JTextField fieldNombre=new JTextFieldPersonalizado(ejemplar.getNombre(), 20);
            fieldNombre.setEditable(false);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldNombre, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelFecha() {
        JLabel label=new JLabelPersonalizado("Fecha de nacimiento");
        
        JTextField field=new JTextFieldPersonalizado(ejemplar.getFecha().toString(), 20);
            field.setEditable(false);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelSexo() {
        
        JLabel labelSexo=new JLabelPersonalizado("Sexo: ");
        
        JRadioButton botonMasculino=new JRadioButtonPersonalizado("Masculino");
        JRadioButton botonFemenino=new JRadioButtonPersonalizado("Femenino");
        
        if ( ejemplar.esMacho() )
            botonMasculino.setSelected(true);
            else
                botonFemenino.setSelected(true);
        
        botonMasculino.setEnabled(false);
        botonFemenino.setEnabled(false);
        
        ButtonGroup grupoRadio=new ButtonGroup();
            grupoRadio.add(botonMasculino);
            grupoRadio.add(botonFemenino);
        
        JPanel panel=new JPanelTransparente();
            panel.add(labelSexo);
            panel.add(botonMasculino);
            panel.add(botonFemenino);
        
        return panel;
    }

    private JPanel armarPanelPadre() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        
        JLabel label=new JLabelPersonalizado("Padre");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        JTextField field=new JTextFieldPersonalizado(17);
            field.setEditable(false);
        JButton boton=new JButtonChico(22);
            boton.setHorizontalTextPosition(SwingConstants.LEFT);
            boton.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            boton.addActionListener( new OyentePadre() );
        
        Perro padre=ejemplar.getPadre();
        if ( padre != null )
            field.setText( padre.getNombre() );
            else {
                field.setText("Sin datos");
                boton.setEnabled(false);
            }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(field);
            panelField.add(boton);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelField, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelMadre() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        
        JLabel label=new JLabelPersonalizado("Madre");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        JTextField field=new JTextFieldPersonalizado(17);
            field.setEditable(false);
        JButton boton=new JButtonChico(22);
            boton.setHorizontalTextPosition(SwingConstants.LEFT);
            boton.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            boton.addActionListener( new OyenteMadre() );
        
        Perro madre=ejemplar.getMadre();
        if ( madre != null )
            field.setText( madre.getNombre() );
            else {
                field.setText("Sin datos");
                boton.setEnabled(false);
            }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(field);
            panelField.add(boton);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelField, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelPropietario() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        
        JLabel label=new JLabelPersonalizado("Propietario");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        JTextField field=new JTextFieldPersonalizado(17);
            field.setEditable(false);
        JButton boton=new JButtonChico(22);
            boton.setHorizontalTextPosition(SwingConstants.LEFT);
            boton.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            boton.addActionListener( new OyentePropietario() );
        
        Persona propietario=ejemplar.getPropietario();
        if ( propietario != null )
            field.setText( propietario.getNombre() );
            else {
                field.setText("Sin datos");
                boton.setEnabled(false);
            }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(field);
            panelField.add(boton);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelField, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelCriador() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        
        JLabel label=new JLabelPersonalizado("Criador");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        JTextField field=new JTextFieldPersonalizado(17);
            field.setEditable(false);
        JButton boton=new JButtonChico(22);
            boton.setHorizontalTextPosition(SwingConstants.LEFT);
            boton.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            boton.addActionListener( new OyenteCriador() );
        
        Persona criador=ejemplar.getCriador();
        if ( criador != null )
            field.setText( criador.getNombre() );
            else {
                field.setText("Sin datos");
                boton.setEnabled(false);
            }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(field);
            panelField.add(boton);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelField, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelRegistroTatuaje() {
        
        JLabel labelRegistro=new JLabelPersonalizado("Nº de registro");
        JTextField fieldRegistro=new JTextFieldPersonalizado(ejemplar.getRegistro(), 9);
            fieldRegistro.setEditable(false);
        
        JPanel panelRegistro=new JPanelTransparente();
            panelRegistro.setLayout( new BorderLayout() );
            panelRegistro.add(labelRegistro, BorderLayout.NORTH);
            panelRegistro.add(fieldRegistro, BorderLayout.CENTER);
        
        JLabel labelTatuaje=new JLabelPersonalizado("Tatuaje");
        JTextField fieldTatuaje=new JTextFieldPersonalizado(ejemplar.getTatuaje(), 9);
            fieldTatuaje.setEditable(false);
        
        JPanel panelTatuaje=new JPanelTransparente();
            panelTatuaje.setLayout( new BorderLayout() );
            panelTatuaje.add(labelTatuaje, BorderLayout.NORTH);
            panelTatuaje.add(fieldTatuaje, BorderLayout.CENTER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2,10,0) );
            panel.add(panelRegistro);
            panel.add(panelTatuaje);
        
        return panel;
    }

    private JPanel armarPanelNotas() {
        JLabel label=new JLabelPersonalizado("Notas");
        JTextArea areaNotas=new JTextAreaPersonalizado(ejemplar.getNotas(), 7,20, true);
            areaNotas.setEditable(false);
        JScrollPane scrollNotas=new JScrollPanePersonalizado(areaNotas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(scrollNotas, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelFoto() {
        String nombre=ejemplar.getNombre();
        
        BufferedImage imagen=IO_Fotos.cargarFotoMiniaturaEjemplar(nombre);
        
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
            
            if (alto <= 136)
                setSize(550,432);
                else
                    setSize(550, 296+alto);
            
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
            setSize(550,432);
            panel=new JPanelRecuadro("No hay foto para mostrar", 232,40);
        }
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JButton botonCerrar=new JButtonChico("Cerrar");
            botonCerrar.addActionListener( new OyenteCerrar() );
        
        botonPedigree=new JButtonChico("Mostrar Pedigree", 130);
            botonPedigree.addActionListener( new OyentePedigree() );
        
        JPanel panel=new JPanelTransparente();
            panel.add(botonCerrar);
            panel.add(botonPedigree);
        
        return panel;
    }

    private JPanel armarPanelDerecha() {
        JPanel panelRegistroTatuaje=armarPanelRegistroTatuaje();
        JPanel panelNotas=armarPanelNotas();
        JPanel panelFoto=armarPanelFoto();
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelRegistroTatuaje);
            panel.add(panelNotas);
            panel.add(panelFoto);
        
        return panel;
    }

    private JPanel armarPanelPedigree() {
        JLabel label=new JLabelPersonalizado("Pedigree");
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        JPanel panelP=new JPanelTransparente();
            panelP.add( new JPanelPedigree(this, baseDatos, ejemplar) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelP, BorderLayout.CENTER);
        
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
    private class OyentePadre implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            padre();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteMadre implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            madre();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyentePropietario implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            propietario();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteCriador implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            criador();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyentePedigree implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            pedigree();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}