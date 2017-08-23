import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

public class GUI_Navegador extends GUI_Frame {

// Atributos
    private static final Color COLOR_FONDO=Constantes.COLOR_AZUL_APAGADO;
    private static final String [ ] COLUMNAS_EJEMPLAR=Constantes.TABLA_COLUMNAS_EJEMPLAR;
    private static final String [ ] COLUMNAS_PERSONA=Constantes.TABLA_COLUMNAS_PERSONA;
    
    private GUI_Frame parent;
    private JButton botonDetalles, botonEditar, botonEliminar;
    private JTabbedPane panelSolapas;
    private JTable tablaPersona, tablaEjemplar;
    
    private Datos baseDatos;
    private ListaSer listaPersona, listaEjemplar;

// Constructores
    public GUI_Navegador(GUI_Frame p, Datos base) {
        super();
        parent=p;
        baseDatos=base;
        listaPersona=baseDatos.getListaContactos();
        listaEjemplar=baseDatos.getListaEjemplares();
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        dispose();
        new GUI_Principal(baseDatos);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void bloquearBotones() {
        botonDetalles.setEnabled(false);
        botonEditar.setEnabled(false);
        botonEliminar.setEnabled(false);
    }

    private void desbloquearBotones() {
        botonDetalles.setEnabled(true);
        botonEditar.setEnabled(true);
        botonEliminar.setEnabled(true);
    }

    private void guardar() {
        boolean seGuardo=baseDatos.guardarDatos();
        if (seGuardo)
            Mensaje.mostrarGuardarCorrectamente(this);
            else
                Mensaje.mostrarError101(this);
    }

    private void masivo() {
        final int TIPO_CONTACTO=Constantes.TIPO_CONTACTO;
        
        GUI_Agregador_Masivo dialogo;
        if ( panelSolapas.getSelectedIndex() == 0 )
            dialogo=new GUI_Agregador_Masivo(this, baseDatos);
            else
                dialogo=new GUI_Agregador_Masivo(this, baseDatos, TIPO_CONTACTO);
        boolean seAgregaronEjemplares=dialogo.seAgregaronEjemplares();
        boolean seAgregaronContactos=dialogo.seAgregaronContactos();
        if ( seAgregaronContactos || seAgregaronEjemplares )
            if ( seAgregaronEjemplares && seAgregaronContactos )
                actualizarTablas();
                else
                    if ( seAgregaronEjemplares )
                        actualizarTablaEjemplar();
                        else
                            actualizarTablaPersona();
    }

    private void seleccion() {
        if ( panelSolapas.getSelectedIndex() == 0 )
            // Ejemplares
            if ( tablaEjemplar.getSelectedRow() == -1 )
                bloquearBotones();
                else
                    desbloquearBotones();
        else
            // Contactos
            if ( tablaPersona.getSelectedRow() == -1 )
                bloquearBotones();
                else
                    desbloquearBotones();
    }

    private void dobleClickEjemplar() {
        int seleccion=tablaEjemplar.getSelectedRow();
        if ( seleccion != -1 ) {
            String nombre=(String)tablaEjemplar.getValueAt(seleccion, 0);
            Ser elemento=listaEjemplar.buscarElemento(nombre);
            new GUI_Detalles_Ejemplar(this, baseDatos, (Ejemplar)elemento);
        }
    }

    private void dobleClickPersona() {
        int seleccion=tablaPersona.getSelectedRow();
        if ( seleccion != -1 ) {
            String nombre=(String)tablaPersona.getValueAt(seleccion, 0);
            Ser elemento=listaPersona.buscarElemento(nombre);
            new GUI_Detalles_Persona(this, (Persona)elemento);
        }
    }

    private void detalles() {
        // Asume que hay un elemento seleccionado
        
        Ser elemento;
        String nombre;
        int indice;
        
        if ( panelSolapas.getSelectedIndex() == 0 ) {
            // Ejemplares
            indice=tablaEjemplar.getSelectedRow();
            nombre=(String)tablaEjemplar.getValueAt(indice,0);
            elemento=listaEjemplar.buscarElemento(nombre);
            new GUI_Detalles_Ejemplar(this, baseDatos, (Ejemplar)elemento );
        } else {
            // Contactos
            indice=tablaPersona.getSelectedRow();
            nombre=(String)tablaPersona.getValueAt(indice,0);
            elemento=listaPersona.buscarElemento(nombre);
            new GUI_Detalles_Persona(this, (Persona)elemento );
        }
    }

    private void editar() {
        // Asume que hay un elemento seleccionado
        
        Ser elemento;
        String nombre;
        int indice;
        
        if ( panelSolapas.getSelectedIndex() == 0 ) {
            // Ejemplares
            indice=tablaEjemplar.getSelectedRow();
            nombre=(String)tablaEjemplar.getValueAt(indice,0);
            elemento=listaEjemplar.buscarElemento(nombre);
            
            int cantAnteriorEjemplar, cantNuevaEjemplar, cantAnteriorPersona, cantNuevaPersona;
            cantAnteriorPersona=listaPersona.cantElementos();
            cantAnteriorEjemplar=listaEjemplar.cantElementos();
            GUI_Editar_Ejemplar dialogo=new GUI_Editar_Ejemplar( this, baseDatos, (Ejemplar)elemento );
            cantNuevaPersona=listaPersona.cantElementos();
            cantNuevaEjemplar=listaEjemplar.cantElementos();
            
            if (cantAnteriorPersona != cantNuevaPersona)
                // Puede que se hayan agregado elementos en la búsqueda
                actualizarTablaPersona();
            
            if (cantAnteriorEjemplar != cantNuevaEjemplar || dialogo.seEdito())
                // Puede que se hayan agregado elementos en la búsqueda
                // Puede que se haya agregado un propietario/criador
                actualizarTablaEjemplar();
        } else {
            // Contactos
            indice=tablaPersona.getSelectedRow();
            nombre=(String)tablaPersona.getValueAt(indice,0);
            elemento=listaPersona.buscarElemento(nombre);
            GUI_Editar_Persona dialogo=new GUI_Editar_Persona( this, baseDatos, (Persona)elemento );
            if (dialogo.seEdito())
                actualizarTablaPersona();
        }
    }

    private void eliminar() {
        // Asume que hay un elemento seleccionado
        
        // Se abre un diálogo para confirmar
        final int SI=Constantes.MENSAJE_RESPUESTA_SI;
        int respuesta=Mensaje.mostrarEliminarElemento(this);
        if (respuesta==SI) {
            
            Ser elemento;
            String nombre;
            int indice;
            
            if ( panelSolapas.getSelectedIndex() == 0 ) {
                // Ejemplares
                indice=tablaEjemplar.getSelectedRow();
                nombre=(String)tablaEjemplar.getValueAt(indice,0);
                
                if ( ( (String)tablaEjemplar.getValueAt(indice, 2) ).equals("Macho") )
                    baseDatos.eliminarReferenciaMacho(nombre);
                    else
                        baseDatos.eliminarReferenciaHembra(nombre);
                
                listaEjemplar.eliminarElemento(nombre);
                IO_Fotos.eliminarFotoEjemplar(nombre);
                
                actualizarTablaEjemplar();
            } else {
                // Contactos
                indice=tablaPersona.getSelectedRow();
                nombre=(String)tablaPersona.getValueAt(indice,0);
                baseDatos.eliminarReferenciaContacto(nombre);
                listaPersona.eliminarElemento(nombre);
                IO_Fotos.eliminarFotoContacto(nombre);
                actualizarTablas();
            }
            
            // Aumenta el contador de modificaciones de la base de datos
            baseDatos.aumentarContador();
        }
    }

    private void nuevo() {
        
        if ( panelSolapas.getSelectedIndex() == 0 ) {
            // Ejemplares
            int cantAnteriorPersona, cantNuevaPersona, cantAnteriorEjemplar, cantNuevaEjemplar;
            cantAnteriorPersona=listaPersona.cantElementos();
            cantAnteriorEjemplar=listaEjemplar.cantElementos();
            new GUI_Nuevo_Ejemplar(this, baseDatos);
            cantNuevaPersona=listaPersona.cantElementos();
            cantNuevaEjemplar=listaEjemplar.cantElementos();
            
            if (cantAnteriorPersona!=cantNuevaPersona)
                // Puede que se hayan agregado elementos en la búsqueda
                actualizarTablaPersona();
            
            if (cantAnteriorEjemplar!=cantNuevaEjemplar)
                // Puede que se hayan agregado elementos en la búsqueda
                actualizarTablaEjemplar();
            } else {
                // Contactos
                GUI_Nuevo_Persona dialogo=new GUI_Nuevo_Persona(this, baseDatos);
                if ( dialogo.getElemento() != null )
                    actualizarTablaPersona();
            }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Pedigree Manager - Navegador de Elementos");
        setSize(720,550);
        setMinimumSize( new Dimension(421, 223));
        setLocationRelativeTo(parent);
        parent.dispose();
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
        JPanel panelBotonesVarios=armarPanelBotonesVarios();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelBanner, BorderLayout.NORTH);
            panel.add(panelBotonesVarios, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelBanner() {
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_ELEMENTOS;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(BANNER) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelBotonesVarios() {
        final String ICONO_GUARDAR=Constantes.ARCHIVO_IMAGEN_ICONO_GUARDAR;
        
        JButton botonGuardar=new JButtonChico("Guardar datos ahora", 166);
            botonGuardar.setIcon( new ImageIcon( getClass().getResource(ICONO_GUARDAR) ) );
            botonGuardar.addActionListener( new OyenteGuardar() );
        
        JButton botonMasivo=new JButtonChico("Abrir Agregador masivo",166);
            botonMasivo.addActionListener( new OyenteMasivo() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(botonGuardar);
            panel.add(botonMasivo);
        
        return panel;
    }

    private JPanel armarPanelCentral() {
        JPanel panelBotones=armarPanelBotones();
        JPanel panelTablas=armarPanelTablas();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelBotones, BorderLayout.NORTH);
            panel.add(panelTablas, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelTablas() {
        panelSolapas=armarPanelSolapas();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelSolapas);
        
        return panel;
    }

    private JTabbedPane armarPanelSolapas() {
        final String ICONO_EJEMPLAR=Constantes.ARCHIVO_IMAGEN_ICONO_EJEMPLAR;
        final String ICONO_CONTACTO=Constantes.ARCHIVO_IMAGEN_ICONO_CONTACTO;
        
        JPanel panelTablaEjemplar=armarPanelTablaEjemplar();
        JPanel panelTablaPersona=armarPanelTablaPersona();
        
        JTabbedPane panel=new JTabbedPanePersonalizado();
            panel.addMouseListener(new OyenteSeleccion());
        
        // Establece el ancho inicial de las columnas
        
        // Nombre
        tablaEjemplar.getColumnModel().getColumn(0).setPreferredWidth(190);
        // Nacimiento
        tablaEjemplar.getColumnModel().getColumn(1).setPreferredWidth(100);
        // Sexo
        tablaEjemplar.getColumnModel().getColumn(2).setPreferredWidth(70);
        // Criador
        tablaEjemplar.getColumnModel().getColumn(3).setPreferredWidth(190);
        // Propietario
        tablaEjemplar.getColumnModel().getColumn(4).setPreferredWidth(190);
        
        ImageIcon iconoEjemplar=new ImageIcon( getClass().getResource(ICONO_EJEMPLAR) );
        if ( tablaEjemplar.getRowCount() > 0 )
            panel.addTab("Ejemplares", iconoEjemplar, panelTablaEjemplar);
            else {
                JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
                panel.addTab("Ejemplares", iconoEjemplar, label);
            }
        
        // Establece el ancho inicial de las columnas
        
        // Nombre
        tablaPersona.getColumnModel().getColumn(0).setPreferredWidth(190);
        // Domicilio
        tablaPersona.getColumnModel().getColumn(1).setPreferredWidth(275);
        // Teléfono
        tablaPersona.getColumnModel().getColumn(2).setPreferredWidth(275);
        
        ImageIcon iconoContacto=new ImageIcon( getClass().getResource(ICONO_CONTACTO) );
        if ( tablaPersona.getRowCount() > 0 )
            panel.addTab("Contactos", iconoContacto, panelTablaPersona);
            else {
                JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
                panel.addTab("Contactos",iconoContacto, label);
            }
        
        return panel;
    }

    private JPanel armarPanelTablaEjemplar() {
        // Genera los datos
        
        int cantElementos=listaEjemplar.cantElementos();
        String [ ] [ ] datos=new String [ cantElementos ] [ 5 ];
        
        int i;
        Ejemplar elemento;
        for (i=0; i<cantElementos; i++) {
            elemento=(Ejemplar)listaEjemplar.getElemento( i );
            datos [ i ] [ 0 ] = elemento.getNombre();
            datos [ i ] [ 1 ] = elemento.getFecha().toString();
            if ( elemento.esHembra() )
                datos [ i ] [ 2 ] = "Hembra";
                else
                datos [ i ] [ 2 ] = "Macho";
            try { datos [ i ] [ 3 ] = elemento.getCriador().getNombre(); }
                catch (NullPointerException e) { datos [ i ] [ 3 ] = new String(); }
            try { datos [ i ] [ 4 ] = elemento.getPropietario().getNombre(); }
                catch (NullPointerException e) { datos [ i ] [ 4 ] = new String(); }
        }
        
        // Genera la tabla
        final Color COLOR_SELECCION=Constantes.COLOR_VERDE_OFF;
        tablaEjemplar=new JTablePersonalizado(datos, COLUMNAS_EJEMPLAR);
        tablaEjemplar.addMouseListener( new OyenteTablaEjemplar() );
        tablaEjemplar.setSelectionBackground(COLOR_SELECCION);
        
        JScrollPane scroll=new JScrollPanePersonalizado(tablaEjemplar, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(scroll);
        
        return panel;
    }

    private JPanel armarPanelTablaPersona() {
        // Genera los datos
        
        int cantElementos=listaPersona.cantElementos();
        String [ ] [ ] datos=new String [ cantElementos ] [ 3 ];
        
        int i;
        Persona elemento;
        for (i=0; i<cantElementos; i++) {
            elemento=(Persona)listaPersona.getElemento( i );
            datos [ i ] [ 0 ] = elemento.getNombre();
            datos [ i ] [ 1 ] = elemento.getDomicilio();
            datos [ i ] [ 2 ] = elemento.getTelefono();
        }
        
        // Genera la tabla
        final Color COLOR_SELECCION=Constantes.COLOR_VIOLETA_OFF;
        tablaPersona=new JTablePersonalizado(datos,COLUMNAS_PERSONA);
        tablaPersona.addMouseListener( new OyenteTablaPersona() );
        tablaPersona.setSelectionBackground(COLOR_SELECCION);
        
        JScrollPane scroll=new JScrollPane(tablaPersona, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(scroll);
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        final String ICONO_DETALLES=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_EDITAR=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_ELIMINAR=Constantes.ARCHIVO_IMAGEN_ICONO_ELIMINAR;
        final String ICONO_AGREGAR=Constantes.ARCHIVO_IMAGEN_ICONO_AGREGAR;
        
        botonDetalles=new JButtonChico("Detalles ", 100);
            botonDetalles.setIcon( new ImageIcon( getClass().getResource(ICONO_DETALLES) ) );
            botonDetalles.setHorizontalTextPosition(SwingConstants.LEFT);
            botonDetalles.addActionListener( new OyenteDetalles() );
        botonEditar=new JButtonChico("Editar ", 90);
            botonEditar.setIcon( new ImageIcon( getClass().getResource(ICONO_EDITAR) ) );
            botonEditar.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEditar.addActionListener( new OyenteEditar() );
        botonEliminar=new JButtonChico("Eliminar", 100);
            botonEliminar.setIcon( new ImageIcon( getClass().getResource(ICONO_ELIMINAR) ) );
            botonEliminar.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEliminar.addActionListener( new OyenteEliminar() );
        JButton botonNuevo=new JButtonChico("Nuevo", 90);
            botonNuevo.setIcon( new ImageIcon( getClass().getResource(ICONO_AGREGAR) ) );
            botonNuevo.setHorizontalTextPosition(SwingConstants.LEFT);
            botonNuevo.addActionListener( new OyenteNuevo() );
        
        bloquearBotones();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            panel.add(botonDetalles);
            panel.add(botonEditar);
            panel.add(botonEliminar);
            panel.add(botonNuevo);
        
        return panel;
    }

    private JPanel armarPanelInferior() {
        final String ICONO_PRESSED=Constantes.ARCHIVO_IMAGEN_ICONO_GOBACK_PRESSED;
        final String ICONO_DISABLED=Constantes.ARCHIVO_IMAGEN_ICONO_GOBACK_DISABLED;
        
        JButton boton=new JButtonNormal("Volver al Menú Principal",192);
            boton.setIcon( new ImageIcon( getClass().getResource(ICONO_DISABLED) ) );
            boton.setPressedIcon( new ImageIcon( getClass().getResource(ICONO_PRESSED) ) );
            boton.addActionListener( new OyenteCerrar() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(boton);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void actualizarTablas() {
        final String ICONO_EJEMPLAR=Constantes.ARCHIVO_IMAGEN_ICONO_EJEMPLAR;
        final String ICONO_CONTACTO=Constantes.ARCHIVO_IMAGEN_ICONO_CONTACTO;
        
        int indiceSeleccionado=panelSolapas.getSelectedIndex();
        TableColumnModel modeloColumnasEjemplar=tablaEjemplar.getColumnModel();
        TableColumnModel modeloColumnasPersona=tablaPersona.getColumnModel();
        
        // Elimina las solapas
        panelSolapas.removeTabAt(0);
        panelSolapas.removeTabAt(0);
        
        // Rearma las tablas
        JPanel panelTablaEjemplar=armarPanelTablaEjemplar();
        JPanel panelTablaPersona=armarPanelTablaPersona();
        
        ImageIcon iconoEjemplar=new ImageIcon( getClass().getResource(ICONO_EJEMPLAR) );
        if ( tablaEjemplar.getRowCount() > 0 )
            panelSolapas.addTab("Ejemplares", iconoEjemplar, panelTablaEjemplar);
            else {
                JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
                panelSolapas.addTab("Ejemplares", iconoEjemplar, label);
            }
        
        ImageIcon iconoContacto=new ImageIcon( getClass().getResource(ICONO_CONTACTO) );
        if ( tablaPersona.getRowCount() > 0 )
            panelSolapas.addTab("Contactos", iconoContacto, panelTablaPersona);
            else {
                JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
                panelSolapas.addTab("Contactos", iconoContacto, label);
            }
        
        // Carga el estado anterior de las tablas
        tablaEjemplar.setColumnModel(modeloColumnasEjemplar);
        tablaPersona.setColumnModel(modeloColumnasPersona);
        panelSolapas.setSelectedIndex(indiceSeleccionado);
        
        // Bloquea los botones, ya que la selección se hace nula
        bloquearBotones();
    }

    private void actualizarTablaEjemplar() {
        final String ICONO_EJEMPLAR=Constantes.ARCHIVO_IMAGEN_ICONO_EJEMPLAR;
        
        TableColumnModel modelo=tablaEjemplar.getColumnModel();
        
        // Elimina la solapa Contactos
        panelSolapas.removeTabAt(0);
        
        // Rearma la tabla
        JPanel panelTablaEjemplar=armarPanelTablaEjemplar();
        
        ImageIcon iconoEjemplar=new ImageIcon( getClass().getResource(ICONO_EJEMPLAR) );
        if ( tablaEjemplar.getRowCount() > 0 ) {
            panelSolapas.add(panelTablaEjemplar, 0);
            panelSolapas.setIconAt(0, iconoEjemplar);
        } else {
            JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
            panelSolapas.add(label, 0);
            panelSolapas.setIconAt(0, iconoEjemplar);
        }
        
        panelSolapas.setTitleAt(0, "Ejemplares");
        tablaEjemplar.setColumnModel(modelo);
        panelSolapas.setSelectedIndex(0);
        
        // Bloquea los botones, ya que la selección se hace nula
        bloquearBotones();
    }

    private void actualizarTablaPersona() {
        final String ICONO_CONTACTO=Constantes.ARCHIVO_IMAGEN_ICONO_CONTACTO;
        
        TableColumnModel modelo=tablaPersona.getColumnModel();
        
        // Elimina la solapa Contactos
        panelSolapas.removeTabAt(1);
        
        // Rearma la tabla
        JPanel panelTablaPersona=armarPanelTablaPersona();
        
        ImageIcon iconoContacto=new ImageIcon( getClass().getResource(ICONO_CONTACTO) );
        if ( tablaPersona.getRowCount() > 0 )
            panelSolapas.addTab("Contactos", iconoContacto, panelTablaPersona);
            else {
                JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
                panelSolapas.addTab("Contactos", iconoContacto, label);
            }
        
        tablaPersona.setColumnModel(modelo);
        panelSolapas.setSelectedIndex(1);
        
        // Bloquea los botones, ya que la selección se hace nula
        bloquearBotones();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteGuardar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            guardar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteMasivo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            masivo();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteSeleccion extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            seleccion();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteTablaEjemplar extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            seleccion();
        }
        
        public void mouseClicked(MouseEvent e) {
            if ( e.getClickCount()==2 )
                dobleClickEjemplar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteTablaPersona extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            seleccion();
        }
        
        public void mouseClicked(MouseEvent e) {
            if ( e.getClickCount()==2 )
                dobleClickPersona();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDetalles implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            detalles();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEditar implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            editar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEliminar implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            eliminar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteNuevo implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            nuevo();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}