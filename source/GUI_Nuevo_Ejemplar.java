import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class GUI_Nuevo_Ejemplar extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_VERDE_OFF;
    private static final Color COLOR_BORDE=Constantes.COLOR_VERDE;
    
    private Component parent;
    private JButton botonPadre, botonDescartarPadre, botonMadre, botonDescartarMadre, botonPropietario, botonDescartarPropietario, botonCriador, botonDescartarCriador;
    private JComboBox boxMes;
    private JRadioButton botonMasculino, botonFemenino;
    private JTextArea areaNotas;
    private JTextField fieldNombre, fieldDia, fieldAnio, fieldRegistro, fieldTatuaje, fieldPadre, fieldMadre, fieldPropietario, fieldCriador, fieldFoto;
    
    private Datos baseDatos;
    private Ejemplar ejemplar;
    private Perro padre, madre;
    private Persona propietario, criador;
    private int tipo;

// Constructores
    public GUI_Nuevo_Ejemplar(GUI_Frame p, Datos base) {
        super(p);
        parent=p;
        baseDatos=base;
        crearGUI();
    }

    public GUI_Nuevo_Ejemplar(GUI_Dialog p, Datos base, int t) {
        super(p);
        parent=p;
        baseDatos=base;
        tipo=t;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public Ejemplar getElemento() {
        // Devuelve el elemento agregado
        // Si la operación se canceló o hubo un error, devuelve null
        return ejemplar;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        
        String nombre=fieldNombre.getText();
        
        // Filtra el nombre
        if ( ! Filtro.nombreValido(nombre) )
            // Muestra un mensaje de error
            Mensaje.mostrarNombreInvalido(this);
            else {
                
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
                
                char sexo;
                if ( botonMasculino.isSelected() )
                    sexo=Constantes.SEXO_MACHO;
                    else
                        sexo=Constantes.SEXO_HEMBRA;
                
                String registro=fieldRegistro.getText();
                String tatuaje=fieldTatuaje.getText();
                
                ejemplar=new Ejemplar();
                ejemplar.setNombre(nombre);
                ejemplar.setNotas(notas);
                ejemplar.setFecha(fecha);
                ejemplar.setSexo(sexo);
                ejemplar.setPadre(padre);
                ejemplar.setMadre(madre);
                ejemplar.setPropietario(propietario);
                ejemplar.setCriador(criador);
                ejemplar.setRegistro(registro);
                ejemplar.setTatuaje(tatuaje);
                
                if ( ! baseDatos.getListaEjemplares().agregarElemento(ejemplar) ) {
                    // El elemento no se agregó porque ya existía uno con el mismo nombre
                    ejemplar=null;
                    Mensaje.mostrarNombreEnUso(this);
                } else {
                    // Aumenta el contador de modificaciones de la base de datos
                    baseDatos.aumentarContador();
                    
                    // Intenta almacenar la foto
                    String rutaFoto=fieldFoto.getText();
                    if ( ! rutaFoto.isEmpty() ) {
                        boolean seGuardo=IO_Fotos.guardarFotoEjemplar(nombre, rutaFoto);
                        if ( ! seGuardo )
                            Mensaje.mostrarErrorFoto(this);
                    }
                    
                    // Cierra el diálogo
                    cerrar();
                }
                
            }
        
    }

    private void examinar() {
        JFileChooser dialogo=new JFileChooserPersonalizado();
        
        int accion=dialogo.showDialog(this, "Seleccionar");
        if (accion==JFileChooser.APPROVE_OPTION) {
            String ruta=dialogo.getSelectedFile().getPath();
            fieldFoto.setText(ruta);
        }
    }

    private void padre() {
        GUI_Busqueda_Macho dialogo=new GUI_Busqueda_Macho(this, baseDatos);
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
        GUI_Busqueda_Hembra dialogo=new GUI_Busqueda_Hembra(this, baseDatos);
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
        setTitle("Nuevo ejemplar");
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
        fieldNombre=new JTextFieldPersonalizado(20);
        
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
        
        fieldDia=new JTextFieldPersonalizado(3);
            fieldDia.setHorizontalAlignment(JTextField.CENTER);
        
        final String [ ] MESES=Constantes.MESES;
        boxMes=new JComboBoxPersonalizado(MESES, 121);
        
        fieldAnio=new JTextFieldPersonalizado(4);
            fieldAnio.setHorizontalAlignment(JTextField.CENTER);
        
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
        final char MACHO=Constantes.SEXO_MACHO;
        final char HEMBRA=Constantes.SEXO_HEMBRA;
        
        JLabel labelSexo=new JLabelPersonalizado("Sexo: ");
        
        botonMasculino=new JRadioButtonPersonalizado("Masculino");
        botonFemenino=new JRadioButtonPersonalizado("Femenino");
        
        switch (tipo) {
            case MACHO : botonMasculino.setSelected(true);
                                        botonMasculino.setEnabled(false);
                                        botonFemenino.setEnabled(false);
                                        break;
            case HEMBRA : botonFemenino.setSelected(true);
                                          botonMasculino.setEnabled(false);
                                          botonFemenino.setEnabled(false);
                                          break;
            default : botonMasculino.setSelected(true);
        }
        
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
        
        fieldPadre=new JTextFieldPersonalizado("Sin datos", 11);
            fieldPadre.setEditable(false);
        
        botonPadre=new JButtonChico("Agregar", 96);
            botonPadre.addActionListener( new OyentePadre() );
        
        botonDescartarPadre=new JButtonChico("Descartar", 96);
            botonDescartarPadre.addActionListener( new OyenteDescartarPadre() );
            botonDescartarPadre.setEnabled(false);
        
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
            botonDescartarMadre.setEnabled(false);
        
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
            botonDescartarPropietario.setEnabled(false);
        
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
            botonDescartarCriador.setEnabled(false);
        
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
        fieldRegistro=new JTextFieldPersonalizado(9);
        
        JPanel panelRegistro=new JPanelTransparente();
            panelRegistro.setLayout( new BorderLayout() );
            panelRegistro.add(labelRegistro, BorderLayout.NORTH);
            panelRegistro.add(fieldRegistro, BorderLayout.CENTER);
        
        JLabel labelTatuaje=new JLabelPersonalizado("Tatuaje");
        fieldTatuaje=new JTextFieldPersonalizado(9);
        
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
        areaNotas=new JTextAreaPersonalizado(7,20, true);
        JScrollPane scrollNotas=new JScrollPanePersonalizado(areaNotas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(scrollNotas, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelFoto() {
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