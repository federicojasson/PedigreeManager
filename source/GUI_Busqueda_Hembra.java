import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI_Busqueda_Hembra extends GUI_Dialog {

// Atributos
    private static final Color COLOR_FONDO=Constantes.COLOR_VERDE_OFF;
    private static final int CANT_ELEMENTOS=Constantes.BUSQUEDA_CANTIDAD_ELEMENTOS;
    
    private GUI_Dialog parent;
    private JButton botonAceptar, botonSiguiente, botonAnterior;
    private JList listaGUI;
    private JPanel panelLista;
    private JTextField fieldSaltear;
    
    private Datos baseDatos;
    private ListaSer lista;
    private Ejemplar ejemplar;
    private String nombre;
    private String [ ] datos;
    private int indice, salteo;
    private boolean llegoAlLimite;

// Constructores
    public GUI_Busqueda_Hembra(GUI_Dialog p, Datos base) {
        super(p);
        parent=p;
        baseDatos=base;
        lista=Busqueda.buscarEjemplaresHembras( baseDatos.getListaEjemplares() );
        indice=0;
        crearGUI();
    }

    public GUI_Busqueda_Hembra(GUI_Dialog p, Datos base, String n) {
        super(p);
        parent=p;
        baseDatos=base;
        lista=Busqueda.buscarEjemplaresHembras( baseDatos.getListaEjemplares() );
        nombre=n;
        indice=0;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public Ejemplar obtenerElementoSeleccionado() {
        return ejemplar;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void aceptar() {
        // Asume que hay alguna fila seleccionada
        String nombreSeleccionado=(String)listaGUI.getSelectedValue();
        if ( nombreSeleccionado.equals(nombre) )
            // Evita que un perro sea padre de sí mismo
            Mensaje.mostrarErrorNombreIgual(this);
            else {
                int filaSeleccionada=listaGUI.getSelectedIndex();
                ejemplar=(Ejemplar)lista.getElemento(indice+filaSeleccionada);
                
                cerrar();
            }
    }

    private void nuevo() {
        final int TIPO=Constantes.SEXO_HEMBRA;
        GUI_Nuevo_Ejemplar dialogo=new GUI_Nuevo_Ejemplar(this, baseDatos, TIPO);
        ejemplar=dialogo.getElemento();
        
        if (ejemplar != null)
            cerrar();
    }

    private void anterior() {
        String saltoCadena=fieldSaltear.getText();
        if ( Filtro.esEntero(saltoCadena) )
            salteo=Integer.parseInt(saltoCadena);
            else
                salteo=0;
        
        botonSiguiente.setEnabled(true);
        
        indice=indice-CANT_ELEMENTOS*(salteo+1);
        int cantElementos=lista.cantElementos();
        while (indice < 0)
            indice=indice+CANT_ELEMENTOS;
        
        actualizarLista();
        if ( indice==0 )
            botonAnterior.setEnabled(false);
    }

    private void siguiente() {
        String saltoCadena=fieldSaltear.getText();
        if ( Filtro.esEntero(saltoCadena) )
            salteo=Integer.parseInt(saltoCadena);
            else
                salteo=0;
        
        botonAnterior.setEnabled(true);
        
        indice=indice+CANT_ELEMENTOS*(salteo+1);
        int cantElementos=lista.cantElementos();
        while (indice > cantElementos)
            indice=indice-CANT_ELEMENTOS;
        
        if ( actualizarLista() )
            botonSiguiente.setEnabled(false);
    }

    private void desbloquearBotones() {
        botonAceptar.setEnabled(true);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Buscar ejemplar hembra");
        setSize(302,306);
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelNavegacion=armarPanelNavegacion();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setLayout( new BorderLayout() );
            panelPrincipal.add(panelNavegacion, BorderLayout.CENTER);
            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelNavegacion() {
        JPanel panelVarios=armarPanelVarios();
        panelLista=armarPanelLista();
        JPanel panelFlechas=armarPanelFlechas();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelVarios, BorderLayout.NORTH);
            panel.add(panelLista, BorderLayout.CENTER);
            panel.add(panelFlechas, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelVarios() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_AGREGAR;
        
        JButton botonNuevo=new JButtonChico("Nuevo", 90);
            botonNuevo.setHorizontalTextPosition(SwingConstants.LEFT);
            botonNuevo.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            botonNuevo.addActionListener( new OyenteNuevo() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panel.add(botonNuevo);
        
        return panel;
    }

    private JPanel armarPanelLista() {
        final Border BORDE=Constantes.BORDE_LISTA;
        final Color COLOR=Constantes.COLOR_GRIS_OFF;
        
        llegoAlLimite=armarLista();
        
        JPanel panel=new JPanel();
            panel.setBorder(BORDE);
            panel.setBackground(COLOR);
            panel.setLayout( new BorderLayout() );
            panel.add(listaGUI);
        
        return panel;
    }

    private JPanel armarPanelFlechas() {
        final String ICONO_ANTERIOR=Constantes.ARCHIVO_IMAGEN_ICONO_NAVEGACION_ANTERIOR;
        final String ICONO_SIGUIENTE=Constantes.ARCHIVO_IMAGEN_ICONO_NAVEGACION_SIGUIENTE;
        
        botonAnterior=new JButtonChico("Anterior", 95);
            botonAnterior.setIcon( new ImageIcon( getClass().getResource(ICONO_ANTERIOR) ) );
            botonAnterior.addActionListener( new OyenteAnterior() );
            botonAnterior.setEnabled(false);
        botonSiguiente=new JButtonChico("Siguiente", 95);
            botonSiguiente.setHorizontalTextPosition(SwingConstants.LEFT);
            botonSiguiente.setIcon( new ImageIcon( getClass().getResource(ICONO_SIGUIENTE) ) );
            botonSiguiente.addActionListener( new OyenteSiguiente() );
            if ( llegoAlLimite )
                botonSiguiente.setEnabled(false);
        
        JLabel labelSaltear=new JLabelPersonalizado("Saltear:");
        fieldSaltear=new JTextFieldPersonalizado("0", 2);
        
        JPanel panel=new JPanelTransparente();
            panel.add(botonAnterior);
            panel.add(labelSaltear);
            panel.add(fieldSaltear);
            panel.add(botonSiguiente);
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        botonAceptar=new JButtonChico("Aceptar");
            botonAceptar.addActionListener( new OyenteAceptar() );
            botonAceptar.setEnabled(false);
        JButton botonCancelar=new JButtonChico("Cancelar");
            botonCancelar.addActionListener( new OyenteCerrar() );
        
        JPanel panel=new JPanelTransparente();
            panel.add(botonAceptar);
            panel.add(botonCancelar);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private boolean armarLista() {
        // Retorna true si es la última instancia de elementos
        boolean ultimaInstancia;
        int i, elementosRestantes;
        
        elementosRestantes=lista.cantElementos() - indice;
        
        if (elementosRestantes > CANT_ELEMENTOS) {
            ultimaInstancia=false;
            datos=new String [ CANT_ELEMENTOS ];
            
            for (i=0; i<CANT_ELEMENTOS; i++)
                datos [ i ] = lista.getElemento(i+indice).getNombre();
            
        } else {
            ultimaInstancia=true;
            datos=new String [ elementosRestantes ];
            
            for (i=0; i<elementosRestantes; i++)
                datos [ i ] = lista.getElemento(i+indice).getNombre();
            
        }
        
        // Crea la nueva lista
        listaGUI=new JListPersonalizado(datos);
            listaGUI.setSelectionBackground(COLOR_FONDO);
            listaGUI.addMouseListener( new OyenteSeleccion() );
        
        return ultimaInstancia;
    }

    private boolean actualizarLista() {
        // Retorna true si es la última instancia de elementos
        
        // Elimina la lista del panel
        panelLista.remove(listaGUI);
        
        // Actualiza los datos
        boolean ultimaInstancia;
        int i, elementosRestantes;
        
        elementosRestantes=lista.cantElementos()-indice;
        
        if (elementosRestantes > CANT_ELEMENTOS) {
            ultimaInstancia=false;
            datos=new String [ CANT_ELEMENTOS ];
            
            for (i=0; i<CANT_ELEMENTOS; i++)
                datos [ i ] = lista.getElemento(i+indice).getNombre();
            
        } else {
            ultimaInstancia=true;
            datos=new String [ elementosRestantes ];
            
            for (i=0; i<elementosRestantes; i++)
                datos [ i ] = lista.getElemento(i+indice).getNombre();
            
        }
        
        // Crea la nueva lista
        listaGUI=new JListPersonalizado(datos);
            listaGUI.setSelectionBackground(COLOR_FONDO);
            listaGUI.addMouseListener( new OyenteSeleccion() );
        
        // Agrega la nueva lista
        panelLista.add(listaGUI);
        panelLista.validate();
        panelLista.repaint();
        
        return ultimaInstancia;
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
    private class OyenteNuevo implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            nuevo();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteAnterior implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            anterior();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteSiguiente implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            siguiente();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteSeleccion extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            desbloquearBotones();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}