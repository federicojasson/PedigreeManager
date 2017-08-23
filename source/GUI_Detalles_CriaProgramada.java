import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI_Detalles_CriaProgramada extends GUI_Frame {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Color COLOR_FONDO=Constantes.COLOR_VERDE_CLARO;
    private static final Color COLOR_BORDE=Constantes.COLOR_VERDE_OSCURO;
    
    private GUI_Frame parent;
    private JButton botonPadre, botonMadre, botonPedigree;
    private JPanel panelLayout, panelPedigree;
    
    private Datos baseDatos;
    private CriaProgramada cria;

// Constructores
    public GUI_Detalles_CriaProgramada(GUI_Frame p, Datos base, CriaProgramada c) {
        super();
        parent=p;
        baseDatos=base;
        cria=c;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        dispose();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void padre() {
        new GUI_Detalles_Ejemplar(this, baseDatos, (Ejemplar)cria.getPadre() );
    }

    private void madre() {
        new GUI_Detalles_Ejemplar(this, baseDatos, (Ejemplar)cria.getMadre() );
    }

    private void pedigree() {
        if ( panelPedigree.isShowing() ) {
            // Si es visible, lo oculta
            botonPedigree.setText("Mostrar Pedigree");
            
            panelLayout.remove(panelPedigree);
            
            setSize(280,342);
            Point localizacion=getLocation();
            setLocation( (int)localizacion.getX()+357 , (int)localizacion.getY() );
        } else {
            // Si no es visible, lo muestra
            botonPedigree.setText("Ocultar Pedigree");
            
            setSize(995,432);
            Point localizacion=getLocation();
            setLocation( (int)localizacion.getX()-357 , (int)localizacion.getY() );
            
            panelLayout.add(panelPedigree, BorderLayout.EAST);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Detalles cría programada");
        setSize(280,342);
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelDatos=armarPanelInfo();
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

    private JPanel armarPanelInfo() {
        JPanel panelDatos=armarPanelDatos();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelInferior=new JPanelTransparente();
            panelInferior.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelInferior.add(panelBotones);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelDatos, BorderLayout.CENTER);
            panel.add(panelInferior, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelDatos() {
        JPanel panelPadre=armarPanelPadre();
        JPanel panelMadre=armarPanelMadre();
        JPanel panelNotas=armarPanelNotas();
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelPadre);
            panel.add(panelMadre);
            panel.add(panelNotas);
        
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
        
        Perro padre=cria.getPadre();
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
        
        Perro madre=cria.getMadre();
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

    private JPanel armarPanelNotas() {
        JLabel label=new JLabelPersonalizado("Notas");
        String texto=cria.getNotas();
        JTextArea areaNotas=new JTextAreaPersonalizado(texto, 7,20, true);
            areaNotas.setEditable(false);
        JScrollPane scrollNotas=new JScrollPanePersonalizado(areaNotas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(scrollNotas, BorderLayout.CENTER);
        
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

    private JPanel armarPanelPedigree() {
        JLabel label=new JLabelPersonalizado("Pedigree");
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        JPanel panelP=new JPanelTransparente();
            panelP.add( new JPanelPedigree(this, baseDatos, cria) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(0,-10) );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelP, BorderLayout.CENTER);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

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
    private class OyentePedigree implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            pedigree();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}