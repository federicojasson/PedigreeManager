import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI_Opciones extends GUI_Dialog {

// Atributos
    private static final Border BORDE_PRINCIPAL=Constantes.BORDE_NORMAL;
    private static final Border BORDE_SANGRIA=Constantes.BORDE_SANGRIA;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    
    private GUI_Frame parent;
    private JCheckBox checkDefault, checkAutosave, checkAutosaveInterval;
    private JComboBox boxLista;
    private JTextField fieldInterval;
    
    private Opciones opciones;

// Constructores
    public GUI_Opciones(GUI_Frame p, Datos baseDatos) {
        super(p);
        parent=p;
        opciones=baseDatos.getOpciones();
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        actualizarOpcionDefault();
        actualizarOpcionAutosave();
        actualizarOpcionAutosaveInterval();
        cerrar();
    }

    private void actualizarOpcionDefault() {
        if ( ! checkDefault.isSelected() )
            opciones.setBaseDefault( new String() );
            else {
                String titulo=(String)boxLista.getSelectedItem();
                opciones.setBaseDefault( titulo );
            }
    }

    private void actualizarOpcionAutosave() {
        boolean autosave=checkAutosave.isSelected();
        opciones.setAutosaveOnClose(autosave);
    }

    private void actualizarOpcionAutosaveInterval() {
        final int VALOR_DEFAULT=Constantes.OPCION_AUTOSAVE_INTERVAL_DEFAULT;
        final int VALOR_NONE=Constantes.OPCION_AUTOSAVE_INTERVAL_NONE;
        
        if ( ! checkAutosaveInterval.isSelected() )
            opciones.setAutosaveInterval(VALOR_NONE);
            else {
                String cadena=fieldInterval.getText();
                int nro=VALOR_DEFAULT;
                
                if ( Filtro.esEntero(cadena) )
                    try {
                        nro=Integer.parseInt(cadena);
                    } catch (NumberFormatException exc) {
                        // El número supera el máximo valor permitido
                    }
                
                opciones.setAutosaveInterval(nro);
            }
    }

    private void establecerOpcionesDefault() {
        final boolean AUTOSAVE_ON_CLOSE=Constantes.OPCION_AUTOSAVE_ON_CLOSE_DEFAULT;
        final int INTERVALO=Constantes.OPCION_AUTOSAVE_INTERVAL_DEFAULT;
        
        // Base de datos predeterminada
        checkDefault.setSelected(false);
        bloquearDefaultElementos();
        // Autosave on close
        checkAutosave.setSelected(AUTOSAVE_ON_CLOSE);
        // Autosave interval
        checkAutosaveInterval.setSelected(true);
        desbloquearAutosaveIntervalElementos();
        fieldInterval.setText( Integer.toString(INTERVALO) );
    }

    private void bloquearElementos() {
        checkDefault.setEnabled(false);
        boxLista.setEnabled(false);
    }

    private void bloquearAutosaveIntervalElementos() {
        fieldInterval.setEnabled(false);
    }

    private void desbloquearAutosaveIntervalElementos() {
        fieldInterval.setEnabled(true);
    }

    private void bloquearDefaultElementos() {
        boxLista.setEnabled(false);
    }

    private void desbloquearDefaultElementos() {
        boxLista.setEnabled(true);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Opciones");
        setSize(480,250);
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelBanner=armarPanelBanner();
        JPanel panelOpciones=armarPanelOpciones();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setBorder(BORDE_PRINCIPAL);
            panelPrincipal.setLayout(new BorderLayout());
            panelPrincipal.add(panelBanner, BorderLayout.NORTH);
            panelPrincipal.add(panelOpciones, BorderLayout.CENTER);
            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelBanner() {
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_OPCIONES;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(BANNER) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelOpciones() {
        JPanel panelDefault=armarPanelDefault();
        JPanel panelAutosave=armarPanelAutosave();
        JPanel panelAutosaveInterval=armarPanelAutosaveInterval();
        
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE_SANGRIA);
            panel.setLayout(new GridLayout(3,1));
            panel.add(panelDefault);
            panel.add(panelAutosave);
            panel.add(panelAutosaveInterval);
        
        return panel;
    }

    private JPanel armarPanelDefault() {
        checkDefault=new JCheckBoxPersonalizado("Utilizar");
            checkDefault.addActionListener( new OyenteDefault() );
        
        String [ ] lista=Datos.obtenerBasesDisponibles();
        if (lista != null) {
            // Hay bases disponibles
            boxLista=new JComboBoxPersonalizado(lista, 146);
            String baseDefault=opciones.baseDefault();
            if ( ! baseDefault.isEmpty() && Datos.existeBaseDatos(baseDefault) ) {
                // Hay una base default y existe
                checkDefault.setSelected(true);
                boxLista.setSelectedItem(baseDefault);
            } else
                bloquearDefaultElementos();
        } else {
            // No hay bases disponibles
            lista = new String [ 1 ];
            lista [ 0  ]= " -- ";
            boxLista=new JComboBoxPersonalizado(lista, 146);
            bloquearElementos();
        }
        
        JLabel label=new JLabelPersonalizado("como base de datos predeterminada.");
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(checkDefault);
            panel.add(boxLista);
            panel.add(label);
        
        return panel;
    }

    private JPanel armarPanelAutosave() {
        checkAutosave=new JCheckBoxPersonalizado("Guardar los datos automáticamente al salir.");
        if ( opciones.autosaveOnClose() )
            checkAutosave.setSelected(true);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(checkAutosave);
        
        return panel;
    }

    private JPanel armarPanelAutosaveInterval() {
        final int DESACTIVADO=Constantes.OPCION_AUTOSAVE_INTERVAL_NONE;
        
        checkAutosaveInterval=new JCheckBoxPersonalizado("Guardar los datos automáticamente cada");
            checkAutosaveInterval.addActionListener( new OyenteAutosaveInterval() );
        
        fieldInterval=new JTextFieldPersonalizado(2);
            fieldInterval.setHorizontalAlignment(JTextField.CENTER);
        int intervalo=opciones.autosaveInterval();
        if ( intervalo != DESACTIVADO ) {
            checkAutosaveInterval.setSelected(true);
            fieldInterval.setText( Integer.toString(intervalo) );
        } else {
            fieldInterval.setText(" -- ");
            fieldInterval.setEnabled(false);
        }
        
        JLabel label=new JLabelPersonalizado("modificaciones.");        
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(checkAutosaveInterval);
            panel.add(fieldInterval);
            panel.add(label);
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JButton botonAceptar=new JButtonChico("Aceptar");
            botonAceptar.addActionListener( new OyenteAceptar() );
        JButton botonCancelar=new JButtonChico("Cancelar");
            botonCancelar.addActionListener( new OyenteCerrar() );
        
        JPanel panelCentral=new JPanelTransparente();
            panelCentral.add(botonAceptar);
            panelCentral.add(botonCancelar);
        
        JButton botonDefault=new JButtonChico("Opciones predeterminadas", 176);
            botonDefault.addActionListener( new OyenteEstablecerDefault() );
        
        JPanel panelDefault=new JPanelTransparente();
            panelDefault.add(botonDefault);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelCentral, BorderLayout.CENTER);
            panel.add(panelDefault, BorderLayout.EAST);
        
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
    private class OyenteAutosaveInterval implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            if ( checkAutosaveInterval.isSelected() )
                desbloquearAutosaveIntervalElementos();
                else
                    bloquearAutosaveIntervalElementos();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDefault implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            if ( checkDefault.isSelected() )
                desbloquearDefaultElementos();
                else
                    bloquearDefaultElementos();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEstablecerDefault implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            establecerOpcionesDefault();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}