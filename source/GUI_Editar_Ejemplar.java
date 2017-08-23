import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI_Editar_Ejemplar extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_VERDE_OFF;
    private static final Color COLOR_BORDE=Constantes.COLOR_VERDE;
    
    private GUI_Frame parent;
    private JButton botonPadre, botonDescartarPadre, botonMadre, botonDescartarMadre, botonPropietario, botonDescartarPropietario, botonCriador, botonDescartarCriador;
    private JComboBox boxMes;
    private JPanel panelVacio, panelMiniatura;
    private JTextArea areaNotas;
    private JTextField fieldDia, fieldAnio, fieldRegistro, fieldTatuaje, fieldPadre, fieldMadre, fieldPropietario, fieldCriador, fieldFoto;
    
    private Datos baseDatos;
    private Ejemplar ejemplar;
    private Perro padre, madre;
    private Persona propietario, criador;
    private boolean seEdito;

// Constructores
    public GUI_Editar_Ejemplar(GUI_Frame p, Datos base, Ejemplar e) {
        super(p);
        parent=p;
        baseDatos=base;
        ejemplar=e;
        padre=ejemplar.getPadre();
        madre=ejemplar.getMadre();
        propietario=ejemplar.getPropietario();
        criador=ejemplar.getCriador();
        seEdito=false;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean seEdito() {
        return seEdito;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        
        String notas=areaNotas.getText();
        
        int dia, mes, anio;
        String field;
        
        field=fieldDia.getText();
        if ( Filtro.esEntero(field) )
            dia=Integer.parseInt(field);
            else
                dia=-1;
        
        mes=boxMes.getSelectedIndex();
        if (mes==0)
            mes=-1;
        
        field=fieldAnio.getText();
        if ( Filtro.esEntero(field) )
            anio=Integer.parseInt(field);
            else
                anio=-1;
        
        Fecha fecha=new Fecha(dia,mes,anio);
        
        String registro=fieldRegistro.getText();
        String tatuaje=fieldTatuaje.getText();
        
        ejemplar.setNotas(notas);
        ejemplar.setFecha(fecha);
        ejemplar.setPadre(padre);
        ejemplar.setMadre(madre);
        ejemplar.setPropietario(propietario);
        ejemplar.setCriador(criador);
        ejemplar.setRegistro(registro);
        ejemplar.setTatuaje(tatuaje);
        
        String nombre=ejemplar.getNombre();
        String rutaFoto=fieldFoto.getText();
        if ( ! rutaFoto.isEmpty() ) {
            boolean seGuardo=IO_Fotos.guardarFotoEjemplar(nombre, rutaFoto);
            if ( ! seGuardo )
                Mensaje.mostrarErrorFoto(this);
        } else
            if ( panelVacio.isVisible() )
                IO_Fotos.eliminarFotoEjemplar(nombre);
        
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
    }

    private void padre() {
        GUI_Busqueda_Macho dialogo=new GUI_Busqueda_Macho(this, baseDatos, ejemplar.getNombre() );
        padre=dialogo.obtenerElementoSeleccionado();
        if (padre != null) {
            fieldPadre.setText( padre.getNombre() );
            botonPadre.setEnabled(false);
            botonDescartarPadre.setEnabled(true);
        }
    }

    private void descartarPadre() {
        padre=null;
        botonPadre.setEnabled(true);
        botonDescartarPadre.setEnabled(false);
        fieldPadre.setText("Sin datos");
    }

    private void madre() {
        GUI_Busqueda_Hembra dialogo=new GUI_Busqueda_Hembra(this, baseDatos, ejemplar.getNombre() );
        madre=dialogo.obtenerElementoSeleccionado();
        if (madre != null) {
            fieldMadre.setText( madre.getNombre() );
            botonMadre.setEnabled(false);
            botonDescartarMadre.setEnabled(true);
        }
    }

    private void descartarMadre() {
        madre=null;
        botonMadre.setEnabled(true);
        botonDescartarMadre.setEnabled(false);
        fieldMadre.setText("Sin datos");
    }

    private void propietario() {
        GUI_Busqueda_Persona dialogo=new GUI_Busqueda_Persona(this, baseDatos);
        propietario=dialogo.obtenerElementoSeleccionado();
        if (propietario != null) {
            fieldPropietario.setText( propietario.getNombre() );
            botonPropietario.setEnabled(false);
            botonDescartarPropietario.setEnabled(true);
        }
    }

    private void descartarPropietario() {
        propietario=null;
        botonPropietario.setEnabled(true);
        botonDescartarPropietario.setEnabled(false);
        fieldPropietario.setText("Sin datos");
    }

    private void criador() {
        GUI_Busqueda_Persona dialogo=new GUI_Busqueda_Persona(this, baseDatos);
        criador=dialogo.obtenerElementoSeleccionado();
        if (criador != null) {
            fieldCriador.setText( criador.getNombre() );
            botonCriador.setEnabled(false);
            botonDescartarCriador.setEnabled(true);
        }
    }

    private void descartarCriador() {
        criador=null;
        botonCriador.setEnabled(true);
        botonDescartarCriador.setEnabled(false);
        fieldCriador.setText("Sin datos");
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Editar ejemplar - "+ejemplar.getNombre());
        setSize(550,510);
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
        JPanel panelIzquierda=armarPanelIzquierda();
        JPanel panelDerecha=armarPanelDerecha();
        
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
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelNombre);
            panel.add(panelFecha);
            panel.add(panelSexo);
            panel.add(panelPadre);
            panel.add(panelMadre);
            panel.add(panelPropietario);
            panel.add(panelCriador);
        
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
        JLabel labelFecha=new JLabelPersonalizado("Fecha de nacimiento");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(labelFecha);
        
        Fecha fecha=ejemplar.getFecha();
        int nro;
        
        fieldDia=new JTextFieldPersonalizado(3);
            fieldDia.setHorizontalAlignment(JTextField.CENTER);
        nro=fecha.getDia();
        if ( nro != -1 )
            if ( nro >= 0 && nro <= 9 )
                fieldDia.setText( "0"+nro );
                else
                    fieldDia.setText( Integer.toString(nro) );
        
        final String [ ] MESES=Constantes.MESES;
        boxMes=new JComboBoxPersonalizado(MESES, 121);
        nro=fecha.getMes();
        if (nro != -1)
            boxMes.setSelectedIndex(nro);
        
        fieldAnio=new JTextFieldPersonalizado(4);
            fieldAnio.setHorizontalAlignment(JTextField.CENTER);
        nro=fecha.getAnio();
        if ( nro != -1 )
            fieldAnio.setText( Integer.toString(nro) );
        
        JPanel panelFields=new JPanelTransparente();
            panelFields.add(fieldDia);
            panelFields.add(boxMes);
            panelFields.add(fieldAnio);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelFields, BorderLayout.CENTER);
        
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
        final Border BORDE=Constantes.BORDE_QUITA_ESPACIADO;
        
        JLabel label=new JLabelPersonalizado("Padre");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        fieldPadre=new JTextFieldPersonalizado(11);
            fieldPadre.setEditable(false);
        
        botonPadre=new JButtonChico("Agregar", 96);
            botonPadre.addActionListener( new OyentePadre() );
        
        botonDescartarPadre=new JButtonChico("Descartar", 96);
            botonDescartarPadre.addActionListener( new OyenteDescartarPadre() );
        
        if (padre != null) {
            fieldPadre.setText( padre.getNombre() );
            botonPadre.setEnabled(false);
        } else {
            fieldPadre.setText("Sin datos");
            botonDescartarPadre.setEnabled(false);
        }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(fieldPadre);
        JPanel panelBoton1=new JPanelTransparente();
            panelBoton1.add(botonPadre);
        JPanel panelVacio=new JPanelTransparente();
        JPanel panelBoton2=new JPanelTransparente();
            panelBoton2.add(botonDescartarPadre);
        
        JPanel panelVarios=new JPanelTransparente();
            panelVarios.setLayout( new GridLayout(2,2, -25, -5) );
            panelVarios.add(panelField);
            panelVarios.add(panelBoton1);
            panelVarios.add(panelVacio);
            panelVarios.add(panelBoton2);
        
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.setLayout( new BorderLayout(0, -10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelVarios, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelMadre() {
        final Border BORDE=Constantes.BORDE_QUITA_ESPACIADO;
        
        JLabel label=new JLabelPersonalizado("Madre");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        fieldMadre=new JTextFieldPersonalizado("Sin datos", 11);
            fieldMadre.setEditable(false);
        
        botonMadre=new JButtonChico("Agregar", 96);
            botonMadre.addActionListener( new OyenteMadre() );
        
        botonDescartarMadre=new JButtonChico("Descartar", 96);
            botonDescartarMadre.addActionListener( new OyenteDescartarMadre() );
        
        if (madre != null) {
            fieldMadre.setText( madre.getNombre() );
            botonMadre.setEnabled(false);
        } else {
            fieldMadre.setText("Sin datos");
            botonDescartarMadre.setEnabled(false);
        }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(fieldMadre);
        JPanel panelBoton1=new JPanelTransparente();
            panelBoton1.add(botonMadre);
        JPanel panelVacio=new JPanelTransparente();
        JPanel panelBoton2=new JPanelTransparente();
            panelBoton2.add(botonDescartarMadre);
        
        JPanel panelVarios=new JPanelTransparente();
            panelVarios.setLayout( new GridLayout(2,2, -25, -5) );
            panelVarios.add(panelField);
            panelVarios.add(panelBoton1);
            panelVarios.add(panelVacio);
            panelVarios.add(panelBoton2);
        
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.setLayout( new BorderLayout(0, -10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelVarios, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelPropietario() {
        final Border BORDE=Constantes.BORDE_QUITA_ESPACIADO;
        
        JLabel label=new JLabelPersonalizado("Propietario");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        fieldPropietario=new JTextFieldPersonalizado("Sin datos", 11);
            fieldPropietario.setEditable(false);
        
        botonPropietario=new JButtonChico("Agregar", 96);
            botonPropietario.addActionListener( new OyentePropietario() );
        
        botonDescartarPropietario=new JButtonChico("Descartar", 96);
            botonDescartarPropietario.addActionListener( new OyenteDescartarPropietario() );
        
        if (propietario != null) {
            fieldPropietario.setText( propietario.getNombre() );
            botonPropietario.setEnabled(false);
        } else {
            fieldPropietario.setText("Sin datos");
            botonDescartarPropietario.setEnabled(false);
        }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(fieldPropietario);
        JPanel panelBoton1=new JPanelTransparente();
            panelBoton1.add(botonPropietario);
        JPanel panelVacio=new JPanelTransparente();
        JPanel panelBoton2=new JPanelTransparente();
            panelBoton2.add(botonDescartarPropietario);
        
        JPanel panelVarios=new JPanelTransparente();
            panelVarios.setLayout( new GridLayout(2,2, -25, -5) );
            panelVarios.add(panelField);
            panelVarios.add(panelBoton1);
            panelVarios.add(panelVacio);
            panelVarios.add(panelBoton2);
        
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.setLayout( new BorderLayout(0, -10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelVarios, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelCriador() {
        final Border BORDE=Constantes.BORDE_QUITA_ESPACIADO;
        
        JLabel label=new JLabelPersonalizado("Criador");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        fieldCriador=new JTextFieldPersonalizado("Sin datos", 11);
            fieldCriador.setEditable(false);
        
        botonCriador=new JButtonChico("Agregar", 96);
            botonCriador.addActionListener( new OyenteCriador() );
        
        botonDescartarCriador=new JButtonChico("Descartar", 96);
            botonDescartarCriador.addActionListener( new OyenteDescartarCriador() );
        
        if (criador != null) {
            fieldCriador.setText( criador.getNombre() );
            botonCriador.setEnabled(false);
        } else {
            fieldCriador.setText("Sin datos");
            botonDescartarCriador.setEnabled(false);
        }
        
        JPanel panelField=new JPanelTransparente();
            panelField.add(fieldCriador);
        JPanel panelBoton1=new JPanelTransparente();
            panelBoton1.add(botonCriador);
        JPanel panelVacio=new JPanelTransparente();
        JPanel panelBoton2=new JPanelTransparente();
            panelBoton2.add(botonDescartarCriador);
        
        JPanel panelVarios=new JPanelTransparente();
            panelVarios.setLayout( new GridLayout(2,2, -25, -5) );
            panelVarios.add(panelField);
            panelVarios.add(panelBoton1);
            panelVarios.add(panelVacio);
            panelVarios.add(panelBoton2);
        
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.setLayout( new BorderLayout(0, -10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelVarios, BorderLayout.CENTER);
        
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

    private JPanel armarPanelRegistroTatuaje() {
        
        JLabel labelRegistro=new JLabelPersonalizado("Nº de registro");
        fieldRegistro=new JTextFieldPersonalizado(ejemplar.getRegistro(), 9);
        
        JPanel panelRegistro=new JPanelTransparente();
            panelRegistro.setLayout( new BorderLayout() );
            panelRegistro.add(labelRegistro, BorderLayout.NORTH);
            panelRegistro.add(fieldRegistro, BorderLayout.CENTER);
        
        JLabel labelTatuaje=new JLabelPersonalizado("Tatuaje");
        fieldTatuaje=new JTextFieldPersonalizado(ejemplar.getTatuaje(), 9);
        
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
        areaNotas=new JTextAreaPersonalizado(ejemplar.getNotas(), 7,20, true);
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
        
        panelVacio=armarPanelFotoVacio();
        panelMiniatura=armarPanelFotoMiniatura(imagen);
        
        if (imagen != null)
            // Hay foto
            panelVacio.setVisible(false);
            else
                panelMiniatura.setVisible(false);
        
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
    private class OyentePadre implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            padre();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDescartarPadre implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            descartarPadre();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteMadre implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            madre();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDescartarMadre implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            descartarMadre();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyentePropietario implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            propietario();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDescartarPropietario implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            descartarPropietario();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteCriador implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            criador();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDescartarCriador implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            descartarCriador();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}