import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI_Declarar extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    private static final Color COLOR_BORDE=Constantes.COLOR_BEIGE;
    
    private GUI_Frame parent;
    private JComboBox boxMes, boxLetra;
    private JTextField fieldDia, fieldAnio, fieldMachos, fieldHembras;
    
    private Datos baseDatos;
    private int indice;
    private boolean seDeclaro;

// Constructores
    public GUI_Declarar(GUI_Frame p, Datos base, int i) {
        super(p);
        parent=p;
        baseDatos=base;
        indice=i;
        seDeclaro=false;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean seDeclaro() {
        return seDeclaro;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        
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
        int indiceLetra=boxLetra.getSelectedIndex();
        if ( indiceLetra > 0 )
            letra=(char)(indiceLetra+64);
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
        
        baseDatos.declararNacimiento(indice, letra, fecha, cantMachos, cantHembras);
        
        // Aumenta el contador de modificaciones de la base de datos
        baseDatos.aumentarContador();
        
        seDeclaro=true;
        
        // Cierra el diálogo
        cerrar();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Puede agregar datos");
        setSize(280,196);
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
        JPanel panelFecha=armarPanelFecha();
        JPanel panelLetraHijos=armarPanelLetraHijos();
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelFecha);
            panel.add(panelLetraHijos);
        
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

}