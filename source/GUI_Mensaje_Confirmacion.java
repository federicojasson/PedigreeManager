import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GUI_Mensaje_Confirmacion extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_DIALOGO;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    
    private Component parent;
    private JButton botonSi;
    
    private int respuesta;
    private String [ ] lineas;

// Constructores
    public GUI_Mensaje_Confirmacion(GUI_Frame p, String texto) {
        super(p);
        parent=p;
        lineas=texto.split("\n");
        crearGUI();
    }

    public GUI_Mensaje_Confirmacion(GUI_Dialog p, String texto) {
        super(p);
        parent=p;
        lineas=texto.split("\n");
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public int getRespuesta() {
        return respuesta;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Se necesita confirmación");
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
        JPanel panelTexto=armarPanelTexto();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new BorderLayout(10,0));
            panel.add(panelIcono, BorderLayout.WEST);
            panel.add(panelTexto, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelIcono() {
        final String ICONO_ALERTA=Constantes.ARCHIVO_IMAGEN_ICONO_ALERTA;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(ICONO_ALERTA) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new GridLayout());
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelTexto() {
        JLabel label;
        JPanel panel=new JPanelTransparente();
        int cantLineas=lineas.length;
            panel.setLayout(new GridLayout(cantLineas, 1));
        int i;
        for (i=0; i<cantLineas; i++) {
            label=new JLabelPersonalizado( lineas [ i ] );
            panel.add(label);
        }
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        botonSi=new JButtonChico("Sí", 50);
            botonSi.addActionListener( new OyenteSi() );
        JButton botonNo=new JButtonChico("No", 50);
            botonNo.addActionListener( new OyenteNo() );
        
        JPanel panel=new JPanelTransparente();
            panel.add(botonSi);
            panel.add(botonNo);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteSi implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            final int SI=Constantes.MENSAJE_RESPUESTA_SI;
            respuesta=SI;
            
            cerrar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteNo implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            final int NO=Constantes.MENSAJE_RESPUESTA_NO;
            respuesta=NO;
            
            cerrar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}