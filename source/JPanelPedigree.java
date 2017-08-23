import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class JPanelPedigree extends JPanelTransparente {

// La numeración de paneles es creciente de arriba a abajo y de izquierda a derecha

// Atributos
    private Color FONDO_ACTIVADO=Constantes.COLOR_BLANCO;
    private Color FONDO_DESACTIVADO=Constantes.COLOR_GRIS_OFF;
    private Color LETRA_DESACTIVADO=Constantes.COLOR_GRIS;
    private Font FUENTE_ACTIVADO=Constantes.FUENTE_NORMAL_VERDANA;
    private Font FUENTE_DESACTIVADO=Constantes.FUENTE_ITALICA;
    private Border BORDE=Constantes.BORDE_DATO;
    
    private GUI_Frame parent;
    
    private Datos baseDatos;
    private JPanel [ ] paneles;
    private Perro [ ] perros;
    private Perro ejemplar;

// Constructores
    public JPanelPedigree(GUI_Frame pr, Datos base, Perro p) {
        parent=pr;
        baseDatos=base;
        paneles=new JPanel [ 30 ];
        perros=new Perro [ 30 ];
        ejemplar=p;
        inicializarPerros();
        crearGUI();
    }

    public JPanelPedigree(GUI_Frame p, Datos base, Cria cria) {
        parent=p;
        baseDatos=base;
        paneles=new JPanel [ 30 ];
        perros=new Perro [ 30 ];
        ejemplar=new Ejemplar();
            ejemplar.setPadre( cria.getPadre() );
            ejemplar.setMadre( cria.getMadre() );
        inicializarPerros();
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    private void inicializarPerros() {
        Perro padre, madre;
        padre=ejemplar.getPadre();
        madre=ejemplar.getMadre();
        
        perros [ 0 ] = padre;
        perros [ 1 ] = madre;
        
        if ( padre != null )
            armarRama(padre, 1);
        if ( madre != null )
            armarRama(madre, 2);
    }

    private void armarRama(Perro perro, int indicePerro) {
        // Asume que perro no es nulo
        // Recursivo
        if (indicePerro < 15) {
            
            Perro padre, madre;
            int indicePadre, indiceMadre;
            
            padre=perro.getPadre();
            madre=perro.getMadre();
            
            indicePadre=indicePerro*2+1;
            indiceMadre=indicePerro*2+2;
            
            perros [ indicePadre-1 ] = padre;
            perros [ indiceMadre-1 ] = madre;
            
            if ( padre != null )
                armarRama(padre, indicePadre);
            if ( madre != null )
                armarRama(madre, indiceMadre);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        setPreferredSize( new Dimension(704+2,352+2) );
        setBorder(BORDE);
        setLayout( new GridLayout() );
        
        JPanel panelPrincipal=armarPaneles();
        add(panelPrincipal);
    }

    private JPanel armarPanel(int nro) {
        // nro > 0
        nro--;
        
        Perro perro=perros [ nro ];
        
        JTextField field;
        
        if (perro != null) {
            field=new JTextFieldPedigree(parent, baseDatos, perro.getNombre() );
            field.setText( perro.getNombre() );
            field.setFont(FUENTE_ACTIVADO);
            field.setBackground(FONDO_ACTIVADO);
        } else {
            field=new JTextField();
            field.setText("Sin datos");
            field.setFocusable(false);
            field.setFont(FUENTE_DESACTIVADO);
            field.setForeground(LETRA_DESACTIVADO);
            field.setBackground(FONDO_DESACTIVADO);
        }
        
        field.setEditable(false);
        field.setBorder(BORDE);
        field.setHorizontalAlignment(JTextField.CENTER);
        
        paneles [ nro ] = new JPanelTransparente();
            paneles [ nro ] . setLayout( new GridLayout() );
            paneles [ nro ] . add(field);
        
        return paneles [ nro ];
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private JPanel armarPaneles() {
        JPanel panelIzquierda=armarPaneles1_6();
        JPanel panelDerecha=armarPaneles7_30();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles1_6() {
        JPanel panelArriba=armarPaneles1_4();
        JPanel panelAbajo=armarPaneles2_6();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPaneles1_4() {
        JPanel panelIzquierda=armarPanel(1);
        JPanel panelDerecha=armarPaneles3_4();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles3_4() {
        JPanel panelArriba=armarPanel(3);
        JPanel panelAbajo=armarPanel(4);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPaneles2_6() {
        JPanel panelIzquierda=armarPanel(2);
        JPanel panelDerecha=armarPaneles5_6();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles5_6() {
        JPanel panelArriba=armarPanel(5);
        JPanel panelAbajo=armarPanel(6);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPaneles7_30() {
        JPanel panelArriba=armarPaneles7_22();
        JPanel panelAbajo=armarPaneles11_30();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPaneles7_22() {
        JPanel panel7=armarPanel7();
        JPanel panel8=armarPanel8();
        JPanel panel9=armarPanel9();
        JPanel panel10=armarPanel10();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(4,1) );
            panel.add(panel7);
            panel.add(panel8);
            panel.add(panel9);
            panel.add(panel10);
        
        return panel;
    }

    private JPanel armarPanel7() {
        JPanel panelIzquierda=armarPanel(7);
        JPanel panelDerecha=armarPaneles15_16();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles15_16() {
        JPanel panelArriba=armarPanel(15);
        JPanel panelAbajo=armarPanel(16);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPanel8() {
        JPanel panelIzquierda=armarPanel(8);
        JPanel panelDerecha=armarPaneles17_18();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles17_18() {
        JPanel panelArriba=armarPanel(17);
        JPanel panelAbajo=armarPanel(18);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPanel9() {
        JPanel panelIzquierda=armarPanel(9);
        JPanel panelDerecha=armarPaneles19_20();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles19_20() {
        JPanel panelArriba=armarPanel(19);
        JPanel panelAbajo=armarPanel(20);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPanel10() {
        JPanel panelIzquierda=armarPanel(10);
        JPanel panelDerecha=armarPaneles21_22();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles21_22() {
        JPanel panelArriba=armarPanel(21);
        JPanel panelAbajo=armarPanel(22);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPaneles11_30() {
        JPanel panel11=armarPanel11();
        JPanel panel12=armarPanel12();
        JPanel panel13=armarPanel13();
        JPanel panel14=armarPanel14();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(4,1) );
            panel.add(panel11);
            panel.add(panel12);
            panel.add(panel13);
            panel.add(panel14);
        
        return panel;
    }

    private JPanel armarPanel11() {
        JPanel panelIzquierda=armarPanel(11);
        JPanel panelDerecha=armarPaneles23_24();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles23_24() {
        JPanel panelArriba=armarPanel(23);
        JPanel panelAbajo=armarPanel(24);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPanel12() {
        JPanel panelIzquierda=armarPanel(12);
        JPanel panelDerecha=armarPaneles25_26();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles25_26() {
        JPanel panelArriba=armarPanel(25);
        JPanel panelAbajo=armarPanel(26);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPanel13() {
        JPanel panelIzquierda=armarPanel(13);
        JPanel panelDerecha=armarPaneles27_28();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles27_28() {
        JPanel panelArriba=armarPanel(27);
        JPanel panelAbajo=armarPanel(28);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }

    private JPanel armarPanel14() {
        JPanel panelIzquierda=armarPanel(14);
        JPanel panelDerecha=armarPaneles29_30();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelIzquierda);
            panel.add(panelDerecha);
        
        return panel;
    }

    private JPanel armarPaneles29_30() {
        JPanel panelArriba=armarPanel(29);
        JPanel panelAbajo=armarPanel(30);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(2,1) );
            panel.add(panelArriba);
            panel.add(panelAbajo);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}