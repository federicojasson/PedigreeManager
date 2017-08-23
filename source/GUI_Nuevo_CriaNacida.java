import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI_Nuevo_CriaNacida extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_TURQUESA_OFF;
    private static final Color COLOR_BORDE=Constantes.COLOR_TURQUESA;
    
    private GUI_Frame parent;
    private JButton botonPadre, botonDescartarPadre, botonMadre, botonDescartarMadre;
    private JComboBox boxMes, boxLetra;
    private JTextArea areaNotas;
    private JTextField fieldPadre, fieldMadre, fieldDia, fieldAnio, fieldMachos, fieldHembras;
    
    private Datos baseDatos;
    private Perro padre, madre;
    private boolean seAgrego;

// Constructores
    public GUI_Nuevo_CriaNacida(GUI_Frame p, Datos base) {
        super(p);
        parent=p;
        baseDatos=base;
        seAgrego=false;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean seAgrego() {
        return seAgrego;
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
        
        char letra;
        int indice=boxLetra.getSelectedIndex();
        if ( indice > 0 )
            letra=(char)(indice+64);
            else
                letra='\0';
        
        int cantMachos, cantHembras;
        String texto;
        
        texto=fieldMachos.getText();
        if ( Filtro.esEntero(texto) )
            cantMachos=Integer.parseInt(texto);
            else
                cantMachos=-1;
        
        texto=fieldHembras.getText();
        if ( Filtro.esEntero(texto) )
            cantHembras=Integer.parseInt(texto);
            else
                cantHembras=-1;
        
        CriaNacida cria=new CriaNacida();
        cria.setPadre(padre);
        cria.setMadre(madre);
        cria.setNotas(notas);
        cria.setFecha(fecha);
        cria.setLetra(letra);
        cria.setCantMachos(cantMachos);
        cria.setCantHembras(cantHembras);
        
        baseDatos.getListaCriasNacidas().agregarElemento(cria);
        
        // Aumenta el contador de modificaciones de la base de datos
        baseDatos.aumentarContador();
        
        seAgrego=true;
        
        // Cierra el diálogo
        cerrar();
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
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Nueva cría nacida");
        setSize(280,480);
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
        JPanel panelPadre=armarPanelPadre();
        JPanel panelMadre=armarPanelMadre();
        JPanel panelFecha=armarPanelFecha();
        JPanel panelLetraHijos=armarPanelLetraHijos();
        JPanel panelNotas=armarPanelNotas();
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelPadre);
            panel.add(panelMadre);
            panel.add(panelFecha);
            panel.add(panelLetraHijos);
            panel.add(panelNotas);
        
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

    private JPanel armarPanelLetraHijos() {
        JLabel labelLetra=new JLabelPersonalizado("Letra");
        final String [ ] LETRAS=Constantes.ABECEDARIO();
        boxLetra=new JComboBoxPersonalizado(LETRAS, 66);
        
        JPanel panelLetra=new JPanelTransparente();
            panelLetra.setLayout( new BorderLayout() );
            panelLetra.add(labelLetra, BorderLayout.NORTH);
            panelLetra.add(boxLetra, BorderLayout.CENTER);
        
        JLabel labelMachos=new JLabelPersonalizado("Machos");
        fieldMachos=new JTextFieldPersonalizado(6);
            fieldMachos.setHorizontalAlignment(JTextField.CENTER);
        
        JPanel panelMachos=new JPanelTransparente();
            panelMachos.setLayout( new BorderLayout() );
            panelMachos.add(labelMachos, BorderLayout.NORTH);
            panelMachos.add(fieldMachos, BorderLayout.CENTER);
        
        JLabel labelHembras=new JLabelPersonalizado("Hembras");
        fieldHembras=new JTextFieldPersonalizado(6);
            fieldHembras.setHorizontalAlignment(JTextField.CENTER);
        
        JPanel panelHembras=new JPanelTransparente();
            panelHembras.setLayout( new BorderLayout() );
            panelHembras.add(labelHembras, BorderLayout.NORTH);
            panelHembras.add(fieldHembras, BorderLayout.CENTER);
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelLetra);
            panel.add(panelMachos);
            panel.add(panelHembras);
        
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

}