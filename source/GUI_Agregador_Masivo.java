import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI_Agregador_Masivo extends GUI_Dialog {

// Atributos
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    private static final int MACHO=Constantes.TIPO_EJEMPLAR_MACHO;
    private static final int HEMBRA=Constantes.TIPO_EJEMPLAR_HEMBRA;
    private static final int CONTACTO=Constantes.TIPO_CONTACTO;
    
    private GUI_Frame parent;
    private JCheckBox checkGuardar;
    private JRadioButton botonMachos, botonHembras, botonContactos;
    private JTextArea textArea;
    
    private Datos baseDatos;
    private int tipo;
    private boolean guardarAutomaticamente, seAgregaronEjemplares, seAgregaronContactos;

// Constructores
    public GUI_Agregador_Masivo(GUI_Frame p, Datos base) {
        super(p);
        parent=p;
        baseDatos=base;
        tipo=MACHO;
        seAgregaronEjemplares=false;
        seAgregaronContactos=false;
        crearGUI();
    }

    public GUI_Agregador_Masivo(GUI_Frame p, Datos base, int t) {
        super(p);
        parent=p;
        baseDatos=base;
        tipo=t;
        seAgregaronEjemplares=false;
        seAgregaronContactos=false;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean seAgregaronEjemplares() {
        return seAgregaronEjemplares;
    }

    public boolean seAgregaronContactos() {
        return seAgregaronContactos;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void reiniciarPlanilla() {
        // Prepara la planilla para que pueda ser reutilizada
        textArea.setText(null);
    }

    private void agregar() {
        // Verifica si hay que guardar al finalizar
        if( checkGuardar.isSelected() )
            guardarAutomaticamente=true;
            else
                guardarAutomaticamente=false;
        
        // Obtiene los nombres
        String texto=textArea.getText();
        String [ ] nombres=texto.split("\n");
        
        if ( botonMachos.isSelected() )
            agregarMachos(nombres);
            else
                if( botonHembras.isSelected() )
                    agregarHembras(nombres);
                    else
                        agregarContactos(nombres);
        
        if (guardarAutomaticamente) {
            boolean seGuardo=baseDatos.guardarDatos();
            if (seGuardo)
                Mensaje.mostrarGuardarCorrectamente(this);
                else
                    Mensaje.mostrarError101(this);
        } else
            Mensaje.mostrarAgregarMasivoCorrectamente(this);
        
        reiniciarPlanilla();
    }

    private void agregarMachos(String [ ] nombres) {
        final char SEXO=Constantes.SEXO_MACHO;
        
        ListaSer lista=baseDatos.getListaEjemplares();
        
        int i;
        String nombre;
        Ejemplar ejemplar;
        int cantElementos=nombres.length;
        for (i=0; i<cantElementos; i++) {
            nombre=nombres [ i ];
            
            // Si es válido se agrega
            if ( Filtro.nombreValido(nombre) ) {
                ejemplar=new Ejemplar();
                ejemplar.setNombre(nombre);
                ejemplar.setNotas( new String() );
                ejemplar.setFecha( new Fecha(-1,-1,-1) );
                ejemplar.setSexo(SEXO);
                ejemplar.setRegistro( new String() );
                ejemplar.setTatuaje( new String() );
                if ( lista.agregarElemento(ejemplar) )
                    seAgregaronEjemplares=true;
            }
        }
    }

    private void agregarHembras(String [ ] nombres) {
        final char SEXO=Constantes.SEXO_HEMBRA;
        
        ListaSer lista=baseDatos.getListaEjemplares();
        
        int i;
        String nombre;
        Ejemplar ejemplar;
        int cantElementos=nombres.length;
        for (i=0; i<cantElementos; i++) {
            nombre=nombres [ i ];
            
            // Si es válido se agrega
            if ( Filtro.nombreValido(nombre) ) {
                ejemplar=new Ejemplar();
                ejemplar.setNombre(nombre);
                ejemplar.setNotas( new String() );
                ejemplar.setFecha( new Fecha(-1,-1,-1) );
                ejemplar.setSexo(SEXO);
                ejemplar.setRegistro( new String() );
                ejemplar.setTatuaje( new String() );
                if ( lista.agregarElemento(ejemplar) )
                    seAgregaronEjemplares=true;
            }
        }
    }

    private void agregarContactos(String [ ] nombres) {
        ListaSer lista=baseDatos.getListaContactos();
        
        int i;
        String nombre;
        Persona contacto;
        int cantElementos=nombres.length;
        for(i=0; i<cantElementos; i++) {
            nombre=nombres [ i ];
            
            // Si es válido se agrega
            if ( Filtro.nombreValido(nombre) ) {
                contacto=new Persona();
                contacto.setNombre(nombre);
                contacto.setNotas( new String() );
                contacto.setDomicilio( new String() );
                contacto.setTelefono( new String() );
                if ( lista.agregarElemento(contacto) )
                    seAgregaronContactos=true;
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Agregador masivo de elementos");
        setSize(608,624);
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelSuperior=armarPanelSuperior();
        JPanel panelCentral=armarPanelCentral();
        JPanel panelInferior=armarPanelInferior();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setLayout( new BorderLayout() );
            panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
            panelPrincipal.add(panelCentral, BorderLayout.CENTER);
            panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelSuperior() {
        JPanel panelBanner=armarPanelBanner();
        JPanel panelOpciones=armarPanelOpciones();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelBanner, BorderLayout.NORTH);
            panel.add(panelOpciones, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelBanner() {
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_MASIVO;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(BANNER) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelOpciones() {
        final Border BORDE_EXTERNO=Constantes.BORDE_NORMAL;
        final Border BORDE_INTERNO=Constantes.BORDE_TITULADO("Opciones");
        
        JLabel label1=new JLabelPersonalizado("Utilice esta herramienta para agregar elementos en forma rápida y poco detallada.");
        JLabel label2=new JLabelPersonalizado("De ser necesario, los elementos agregados podrán ser editados en el Navegador de Elementos.");
        JLabel label3=new JLabelPersonalizado("Seleccione qué tipo de dato desea agregar:");
        
        JPanel panelSuperior=new JPanelTransparente();
            panelSuperior.setLayout( new GridLayout(3,1) );
            panelSuperior.add(label1);
            panelSuperior.add(label2);
            panelSuperior.add(label3);
        
        botonMachos=new JRadioButtonPersonalizado("Machos");
        botonHembras=new JRadioButtonPersonalizado("Hembras");
        botonContactos=new JRadioButtonPersonalizado("Contactos");
        
        ButtonGroup grupo=new ButtonGroup();
            grupo.add(botonMachos);
            grupo.add(botonHembras);
            grupo.add(botonContactos);
        
        if ( tipo==CONTACTO )
            botonContactos.setSelected(true);
            else
                if ( tipo==HEMBRA )
                    botonHembras.setSelected(true);
                    else
                        botonMachos.setSelected(true);
        
        JPanel panelCentral=new JPanelTransparente();
            panelCentral.add(botonMachos);
            panelCentral.add(botonHembras);
            panelCentral.add(botonContactos);
        
        JLabel label4=new JLabelPersonalizado("Cada línea corresponde al nombre de un nuevo elemento. Los elementos con nombres inválidos");
        JLabel label5=new JLabelPersonalizado("(que contengan | \\ / : * ? \" < > o superen los "+Constantes.NOMBRE_LONGITUD_MAXIMA+" caracteres) no serán agregados");
        JLabel label6=new JLabelPersonalizado("así como tampoco aquellos cuyo nombre ya esté en uso.");
        
        JPanel panelInferior=new JPanelTransparente();
            panelInferior.setLayout( new GridLayout(3,1) );
            panelInferior.add(label4);
            panelInferior.add(label5);
            panelInferior.add(label6);
        
        JPanel panel=new JPanelTransparente();
            panel.setBorder( BorderFactory.createCompoundBorder(BORDE_EXTERNO , BORDE_INTERNO) );
            panel.setLayout( new BorderLayout() );
            panel.add(panelSuperior, BorderLayout.NORTH);
            panel.add(panelCentral, BorderLayout.CENTER);
            panel.add(panelInferior, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelCentral() {
        final Border BORDE=Constantes.BORDE_VENTANA;
        final Color COLOR_FONDO=Constantes.COLOR_GRIS_OFF;
        
        textArea=new JTextAreaPersonalizado();
        
        JScrollPane scrollPane=new JScrollPanePersonalizado(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        checkGuardar=new JCheckBoxPersonalizado("Guardar automáticamente al agregar los elementos");
            checkGuardar.setSelected(true);
        
        JPanel panel=new JPanel();
            panel.setBorder(BORDE);
            panel.setBackground(COLOR_FONDO);
            panel.setLayout( new BorderLayout() );
            panel.add(scrollPane, BorderLayout.CENTER);
            panel.add(checkGuardar, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelInferior() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_AGREGAR;
        
        JButton botonLimpiar=new JButtonChico("Limpiar planilla", 120);
            botonLimpiar.addActionListener( new OyenteLimpiar() );
        JButton botonAgregar=new JButtonChico("Agregar",100);
            botonAgregar.setHorizontalTextPosition(SwingConstants.LEFT);
            botonAgregar.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            botonAgregar.addActionListener( new OyenteAgregar() );
        JButton botonCerrar=new JButtonChico("Cerrar");
            botonCerrar.addActionListener( new OyenteCerrar() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panel.add(botonLimpiar);
            panel.add(botonAgregar);
            panel.add(botonCerrar);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteLimpiar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            reiniciarPlanilla();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteAgregar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            agregar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}