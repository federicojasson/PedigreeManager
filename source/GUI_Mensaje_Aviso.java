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

public class GUI_Mensaje_Aviso extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_DIALOGO;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    
    private Component parent;
    
    private String [ ] lineas;

// Constructores
    public GUI_Mensaje_Aviso(GUI_Frame p, String texto) {
        super(p);
        parent=p;
        lineas=texto.split("\n");
        crearGUI();
    }

    public GUI_Mensaje_Aviso(GUI_Dialog p, String texto) {
        super(p);
        parent=p;
        lineas=texto.split("\n");
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Aviso");
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelContenido=armarPanelContenido();
        JPanel panelBoton=armarPanelBoton();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setBorder(BORDE_PRINCIPAL);
            panelPrincipal.setLayout(new BorderLayout(0,5));
            panelPrincipal.add(panelContenido, BorderLayout.CENTER);
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        
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
        final String ICONO_OK=Constantes.ARCHIVO_IMAGEN_ICONO_OK;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(ICONO_OK) ) );
        
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

    private JPanel armarPanelBoton() {
        JButton botonAceptar=new JButtonChico("Aceptar");
            botonAceptar.addActionListener(new OyenteCerrar());
        
        JPanel panel=new JPanelTransparente();
            panel.add(botonAceptar);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}