import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GUI_Crias extends GUI_Frame {

// Atributos
    private static final Color COLOR_FONDO=Constantes.COLOR_VERDE_MARINO;
    private static final String [ ] COLUMNAS_PROGRAMADAS=Constantes.TABLA_COLUMNAS_PROGRAMADA;
    private static final String [ ] COLUMNAS_NACIDAS=Constantes.TABLA_COLUMNAS_NACIDA;
    private static final int CONSTANTE_FRAME=Constantes.SPLIT_PANE_DIFERENCIA;
    
    private GUI_Frame parent;
    private JButton botonDeclarar, botonDetalles1, botonDetalles2, botonEditar1, botonEditar2, botonEliminar1, botonEliminar2, botonNuevo1, botonNuevo2;
    private JCheckBox checkProgramadas, checkNacidas;
    private JPanel panelProgramadas, panelNacidas;
    private JSplitPane splitPane;
    private JTable tablaProgramadas, tablaNacidas;
    private int altura;
    
    private Datos baseDatos;
    private ListaCria listaProgramadas, listaNacidas;

// Constructores
    public GUI_Crias(GUI_Frame p, Datos base) {
        super();
        parent=p;
        baseDatos=base;
        listaProgramadas=baseDatos.getListaCriasProgramadas();
        listaNacidas=baseDatos.getListaCriasNacidas();
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        dispose();
        new GUI_Principal(baseDatos);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void bloquearBotonesProgramadas() {
        botonDeclarar.setEnabled(false);
        botonDetalles1.setEnabled(false);
        botonEditar1.setEnabled(false);
        botonEliminar1.setEnabled(false);   
    }

    private void desbloquearBotonesProgramadas() {
        botonDeclarar.setEnabled(true);
        botonDetalles1.setEnabled(true);
        botonEditar1.setEnabled(true);
        botonEliminar1.setEnabled(true);
    }

    private void bloquearBotonesNacidas() {
        botonDetalles2.setEnabled(false);
        botonEditar2.setEnabled(false);
        botonEliminar2.setEnabled(false);
    }

    private void desbloquearBotonesNacidas() {
        botonDetalles2.setEnabled(true);
        botonEditar2.setEnabled(true);
        botonEliminar2.setEnabled(true);
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
        
        GUI_Agregador_Masivo dialogo=new GUI_Agregador_Masivo(this, baseDatos);
    }

    private void redimensionar() {
        // Actualiza 'altura'
        altura=(int)getSize().getHeight()-CONSTANTE_FRAME;
        splitPane.setDividerLocation(altura/2);
    }

    private void dobleClickProgramadas() {
        int indice=tablaProgramadas.getSelectedRow();
        if (indice != -1) {
            String nroTexto=(String)tablaProgramadas.getValueAt(indice,0);
            int nro=Integer.parseInt(nroTexto)-1;
            Cria cria=baseDatos.getListaCriasProgramadas().getElemento(nro);
            new GUI_Detalles_CriaProgramada(this, baseDatos, (CriaProgramada)cria);
        }
    }

    private void dobleClickNacidas() {
        int indice=tablaNacidas.getSelectedRow();
        if (indice != -1) {
            String nroTexto=(String)tablaNacidas.getValueAt(indice,0);
            int nro=Integer.parseInt(nroTexto)-1;
            Cria cria=baseDatos.getListaCriasNacidas().getElemento(nro);
            new GUI_Detalles_CriaNacida(this, baseDatos, (CriaNacida)cria);
        }
    }

    private void declarar() {
        // Asume que hay un elemento seleccionado
        int indice=tablaProgramadas.getSelectedRow();
        String nroTexto=(String)tablaProgramadas.getValueAt(indice,0);
        int nro=Integer.parseInt(nroTexto)-1;
        GUI_Declarar dialogo=new GUI_Declarar(this, baseDatos, nro);
        if ( dialogo.seDeclaro() ) 
            actualizarTablas();
    }

    private void detallesProgramadas() {
        int indice=tablaProgramadas.getSelectedRow();
        String nroTexto=(String)tablaProgramadas.getValueAt(indice,0);
        int nro=Integer.parseInt(nroTexto)-1;
        Cria cria=baseDatos.getListaCriasProgramadas().getElemento(nro);
        new GUI_Detalles_CriaProgramada(this, baseDatos, (CriaProgramada)cria);
    }

    private void detallesNacidas() {
        int indice=tablaNacidas.getSelectedRow();
        String nroTexto=(String)tablaNacidas.getValueAt(indice,0);
        int nro=Integer.parseInt(nroTexto)-1;
        Cria cria=baseDatos.getListaCriasNacidas().getElemento(nro);
        new GUI_Detalles_CriaNacida(this, baseDatos, (CriaNacida)cria);
    }

    private void editarProgramadas() {
        // Asume que hay un elemento seleccionado
        
        int indice=tablaProgramadas.getSelectedRow();
        String nroTexto=(String)tablaProgramadas.getValueAt(indice,0);
        int nro=Integer.parseInt(nroTexto)-1;
        Cria cria=baseDatos.getListaCriasProgramadas().getElemento(nro);
        GUI_Editar_CriaProgramada dialogo=new GUI_Editar_CriaProgramada(this, baseDatos, (CriaProgramada)cria);
        if ( dialogo.seEdito() )
            actualizarTablaProgramadas();
    }

    private void editarNacidas() {
        // Asume que hay un elemento seleccionado
        
        int indice=tablaNacidas.getSelectedRow();
        String nroTexto=(String)tablaNacidas.getValueAt(indice,0);
        int nro=Integer.parseInt(nroTexto)-1;
        Cria cria=baseDatos.getListaCriasNacidas().getElemento(nro);
        GUI_Editar_CriaNacida dialogo=new GUI_Editar_CriaNacida(this, baseDatos, (CriaNacida)cria);
        if ( dialogo.seEdito() )
            actualizarTablaNacidas();
    }

    private void eliminarProgramadas() {
        // Asume que hay un elemento seleccionado
        
        // Se abre un diálogo para confirmar
        final int SI=Constantes.MENSAJE_RESPUESTA_SI;
        int respuesta=Mensaje.mostrarEliminarElemento(this);
        if (respuesta==SI) {
            
            int indice=tablaProgramadas.getSelectedRow();
            String nroTexto=(String)tablaProgramadas.getValueAt(indice,0);
            int nro=Integer.parseInt(nroTexto)-1;
            
            baseDatos.getListaCriasProgramadas().eliminarElemento(nro);
            
            // Aumenta el contador de modificaciones de la base de datos
            baseDatos.aumentarContador();
            
            actualizarTablaProgramadas();
        }
    }

    private void eliminarNacidas() {
        // Asume que hay un elemento seleccionado
        
        // Se abre un diálogo para confirmar
        final int SI=Constantes.MENSAJE_RESPUESTA_SI;
        int respuesta=Mensaje.mostrarEliminarElemento(this);
        if (respuesta==SI) {
            
            int indice=tablaNacidas.getSelectedRow();
            String nroTexto=(String)tablaNacidas.getValueAt(indice,0);
            int nro=Integer.parseInt(nroTexto)-1;
            
            baseDatos.getListaCriasNacidas().eliminarElemento(nro);
            
            // Aumenta el contador de modificaciones de la base de datos
            baseDatos.aumentarContador();
            
            actualizarTablaNacidas();
        }
    }

    private void nuevoProgramada() {
        GUI_Nuevo_CriaProgramada dialogo=new  GUI_Nuevo_CriaProgramada(this, baseDatos);
        if ( dialogo.seAgrego() )
            actualizarTablaProgramadas();
    }

    private void nuevoNacida() {
        GUI_Nuevo_CriaNacida dialogo=new GUI_Nuevo_CriaNacida(this, baseDatos);
        if ( dialogo.seAgrego() )
            actualizarTablaNacidas();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        addComponentListener( new OyenteRedimensionar() );
        setTitle("Pedigree Manager - Administrador de Crías");
        setSize(720,550);
        setMinimumSize( new Dimension(720,164) );
        altura=(int) getSize().getHeight()-CONSTANTE_FRAME;
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
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_CRIAS;
        
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
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelBotones.add(botonGuardar);
            panelBotones.add(botonMasivo);
        
        OyenteVer oyente=new OyenteVer();
        final Color COLOR_FONDO_BOTON=Constantes.COLOR_GRIS_OFF;
        final Border BORDE_BOTON=Constantes.BORDE_HUNDIDO;
        
        checkProgramadas=new JCheckBoxPersonalizado("Crías programadas");
            checkProgramadas.addActionListener(oyente);
            checkProgramadas.setSelected(true);
        
        checkNacidas=new JCheckBoxPersonalizado("Crías nacidas");
            checkNacidas.addActionListener(oyente);
            checkNacidas.setSelected(true);
        
        JPanel panelCheckProgramadas=new JPanel();
            panelCheckProgramadas.setPreferredSize( new Dimension(145,20) );
            panelCheckProgramadas.setBorder(BORDE_BOTON);
            panelCheckProgramadas.setBackground(COLOR_FONDO_BOTON);
            panelCheckProgramadas.setLayout( new GridLayout() );
            panelCheckProgramadas.add(checkProgramadas);
        
        JPanel panelCheckNacidas=new JPanel();
            panelCheckNacidas.setPreferredSize( new Dimension(112,20) );
            panelCheckNacidas.setBorder(BORDE_BOTON);
            panelCheckNacidas.setBackground(COLOR_FONDO_BOTON);
            panelCheckNacidas.setLayout( new GridLayout() );
            panelCheckNacidas.add(checkNacidas);
        
        JPanel panelChecks=new JPanelTransparente();
            panelChecks.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panelChecks.add(panelCheckProgramadas);
            panelChecks.add(panelCheckNacidas);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2) );
            panel.add(panelBotones);
            panel.add(panelChecks);
        
        return panel;
    }

    private JPanel armarPanelCentral() {
        final Border BORDE=Constantes.BORDE_DATO_UP_DOWN;
        
        panelProgramadas=armarPanelProgramadas();
        panelNacidas=armarPanelNacidas();
        
        splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, panelProgramadas, panelNacidas);
        splitPane.setDividerLocation(altura/2);
        
        JPanel panel=new JPanel();
            panel.setBorder(BORDE);
            panel.setLayout( new BorderLayout() );
            panel.add(splitPane);
        
        return panel;
    }

    private JPanel armarPanelProgramadas() {
        final String ICONO_DETALLES=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_EDITAR=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_ELIMINAR=Constantes.ARCHIVO_IMAGEN_ICONO_ELIMINAR;
        final String ICONO_AGREGAR=Constantes.ARCHIVO_IMAGEN_ICONO_AGREGAR;
        
        JPanel panelTablaProgramadas=armarPanelTablaProgramadas();
        
        botonDeclarar=new JButtonChico("Declarar nacimiento", 150);
            botonDeclarar.addActionListener( new OyenteDeclarar() );
        botonDetalles1=new JButtonChico("Detalles ", 100);
            botonDetalles1.setHorizontalTextPosition(SwingConstants.LEFT);
            botonDetalles1.setIcon( new ImageIcon( getClass().getResource(ICONO_DETALLES) ) );
            botonDetalles1.addActionListener( new OyenteDetallesProgramadas() );
        botonEditar1=new JButtonChico("Editar ", 90);
            botonEditar1.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEditar1.setIcon( new ImageIcon( getClass().getResource(ICONO_EDITAR) ) );
            botonEditar1.addActionListener( new OyenteEditarProgramadas() );
        botonEliminar1=new JButtonChico("Eliminar", 100);
            botonEliminar1.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEliminar1.setIcon( new ImageIcon( getClass().getResource(ICONO_ELIMINAR) ) );
            botonEliminar1.addActionListener( new OyenteEliminarProgramadas() );
        botonNuevo1=new JButtonChico("Nuevo", 90);
            botonNuevo1.setHorizontalTextPosition(SwingConstants.LEFT);
            botonNuevo1.setIcon( new ImageIcon( getClass().getResource(ICONO_AGREGAR) ) );
            botonNuevo1.addActionListener( new OyenteNuevoProgramada() );
        
        bloquearBotonesProgramadas();
        
        JPanel panelSubBotones=new JPanelTransparente();
            panelSubBotones.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panelSubBotones.add(botonDeclarar);
            panelSubBotones.add(botonDetalles1);
            panelSubBotones.add(botonEditar1);
            panelSubBotones.add(botonEliminar1);
            panelSubBotones.add(botonNuevo1);
        
        JLabel labelProgramadas=new JLabelPersonalizado("   Crías programadas");
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new GridLayout() );
            panelLabel.add(labelProgramadas);
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.setLayout( new BorderLayout() );
            panelBotones.add(panelSubBotones, BorderLayout.EAST);
            panelBotones.add(panelLabel, BorderLayout.WEST);
        
         // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelBotones, BorderLayout.NORTH);
        
        // Establece el ancho inicial de las columnas
        
        // Nº
        TableColumn columna=tablaProgramadas.getColumnModel().getColumn(0);
            columna.setPreferredWidth(40);
            columna.setMaxWidth(40);
            columna.setMinWidth(40);
        
        // Padre
        tablaProgramadas.getColumnModel().getColumn(1).setPreferredWidth(350);
        
        // Madre
        tablaProgramadas.getColumnModel().getColumn(2).setPreferredWidth(350);
        
        if (tablaProgramadas.getRowCount() > 0)
            panel.add(panelTablaProgramadas, BorderLayout.CENTER);
            else {
                // Si no hay elementos, muestra una etiqueta en lugar de la tabla
                JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
                panel.add(label, BorderLayout.CENTER);
            }
        
        return panel;
    }

    private JPanel armarPanelTablaProgramadas() {
        // Genera los datos
        
        int cantElementos=listaProgramadas.cantElementos();
        String [ ] [ ] datos=new String [ cantElementos ] [ 3 ];
        
        int i;
        CriaProgramada cria;
        String ceros;
        for (i=0; i<cantElementos; i++) {
            cria=(CriaProgramada)listaProgramadas.getElemento( i );
            
            ceros=Filtro.devolverCeros(cantElementos, i+1);
            datos [ i ] [ 0 ] = ceros+(i+1);
            try { datos [ i ] [ 1 ] = cria.getPadre().getNombre(); }
                catch (NullPointerException exc) { datos [ i ] [ 1 ] = new String(); }
            try { datos [ i ] [ 2 ] = cria.getMadre().getNombre(); }
                catch (NullPointerException exc) { datos [ i ] [ 2 ] = new String(); }
        }
        
        // Genera la tabla
        final Color COLOR_FONDO=Constantes.COLOR_VERDE_CLARO;
        tablaProgramadas=new JTablePersonalizado(datos, COLUMNAS_PROGRAMADAS);
        tablaProgramadas.setSelectionBackground(COLOR_FONDO);
        tablaProgramadas.addMouseListener( new OyenteTablaProgramadas() );
        
        JScrollPane scroll=new JScrollPanePersonalizado(tablaProgramadas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(scroll);
        
        return panel;
    }

    private JPanel armarPanelNacidas() {
        final String ICONO_DETALLES=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_EDITAR=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_ELIMINAR=Constantes.ARCHIVO_IMAGEN_ICONO_ELIMINAR;
        final String ICONO_AGREGAR=Constantes.ARCHIVO_IMAGEN_ICONO_AGREGAR;
        
        JPanel panelTablaNacidas=armarPanelTablaNacidas();
        
        botonDetalles2=new JButtonChico("Detalles ", 100);
            botonDetalles2.setHorizontalTextPosition(SwingConstants.LEFT);
            botonDetalles2.setIcon( new ImageIcon( getClass().getResource(ICONO_DETALLES) ) );
            botonDetalles2.addActionListener( new OyenteDetallesNacidas() );
        botonEditar2=new JButtonChico("Editar ", 90);
            botonEditar2.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEditar2.setIcon( new ImageIcon( getClass().getResource(ICONO_EDITAR) ) );
            botonEditar2.addActionListener( new OyenteEditarNacidas() );
        botonEliminar2=new JButtonChico("Eliminar", 100);
            botonEliminar2.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEliminar2.setIcon( new ImageIcon( getClass().getResource(ICONO_ELIMINAR) ) );
            botonEliminar2.addActionListener( new OyenteEliminarNacidas() );
        botonNuevo2=new JButtonChico("Nuevo", 90);
            botonNuevo2.setHorizontalTextPosition(SwingConstants.LEFT);
            botonNuevo2.setIcon( new ImageIcon( getClass().getResource(ICONO_AGREGAR) ) );
            botonNuevo2.addActionListener( new OyenteNuevoNacida() );
        
        bloquearBotonesNacidas();
        
        JPanel panelSubBotones=new JPanelTransparente();
            panelSubBotones.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panelSubBotones.add(botonDetalles2);
            panelSubBotones.add(botonEditar2);
            panelSubBotones.add(botonEliminar2);
            panelSubBotones.add(botonNuevo2);
        
        JLabel labelNacidas=new JLabelPersonalizado("   Crías nacidas");
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new GridLayout() );
            panelLabel.add(labelNacidas);
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.setLayout( new BorderLayout() );
            panelBotones.add(panelSubBotones, BorderLayout.EAST);
            panelBotones.add(panelLabel, BorderLayout.WEST);
        
        // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelBotones, BorderLayout.NORTH);
        
        // Establece el ancho inicial de las columnas
        
        // Nº
        TableColumn columna=tablaNacidas.getColumnModel().getColumn(0);
            columna.setPreferredWidth(40);
            columna.setMaxWidth(40);
            columna.setMinWidth(40);
        
        // Padre
        tablaNacidas.getColumnModel().getColumn(1).setPreferredWidth(175);
        
        // Madre
        tablaNacidas.getColumnModel().getColumn(2).setPreferredWidth(175);
        
        // Letra
        tablaNacidas.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        // Nacimiento
        tablaNacidas.getColumnModel().getColumn(4).setPreferredWidth(90);
        
        // Machos
        tablaNacidas.getColumnModel().getColumn(5).setPreferredWidth(90);
        
        // Hembras
        tablaNacidas.getColumnModel().getColumn(6).setPreferredWidth(90);
        
        if (tablaNacidas.getRowCount() > 0)
            panel.add(panelTablaNacidas, BorderLayout.CENTER);
            else {
                // Si no hay elementos, muestra una etiqueta en lugar de la tabla
                JLabel label=new JLabel("No hay elementos para mostrar", JLabel.CENTER);
                panel.add(label, BorderLayout.CENTER);
            }
        
        return panel;
    }

    private JPanel armarPanelTablaNacidas() {
        // Genera los datos
        
        int cantElementos=listaNacidas.cantElementos();
        String [ ] [ ] datos=new String [ cantElementos ] [ 7 ];
        
        int i;
        CriaNacida cria;
        String ceros;
        for (i=0; i<cantElementos; i++) {
            cria=(CriaNacida) listaNacidas.getElemento(i);
            ceros=Filtro.devolverCeros(cantElementos, i+1);
            datos [ i ] [ 0 ] = ceros+(i+1);
            try { datos [ i ] [ 1 ] = cria.getPadre().getNombre(); }
                catch (NullPointerException exc) { datos [ i ] [ 1 ] = new String(); }
            try { datos [ i ] [ 2 ] = cria.getMadre().getNombre(); }
                catch (NullPointerException exc) { datos [ i ] [ 2 ] = new String(); }
            char letra=cria.getLetra();
            if ( letra != '\0' )
                datos [ i ] [ 3 ] = ""+letra;
                else
                // '\0' es el caracter "nulo"
                datos [ i ] [ 3 ] = new String();
            datos [ i ] [ 4 ] = cria.getFecha().toString();
            int cant;
            cant=cria.getCantMachos();
            if ( cant < 0 )
                datos [ i ] [ 5 ] = new String();
                else
                datos [ i ] [ 5 ] = Integer.toString(cant);
            cant=cria.getCantHembras();
            if ( cant < 0 )
                datos [ i ] [ 6 ] = new String();
                else
                datos [ i ] [ 6 ] = Integer.toString(cant);
        }
        
        // Genera la tabla
        final Color COLOR_FONDO=Constantes.COLOR_TURQUESA_OFF;
        tablaNacidas=new JTablePersonalizado(datos, COLUMNAS_NACIDAS);
        tablaNacidas.setSelectionBackground(COLOR_FONDO);
        tablaNacidas.addMouseListener( new OyenteTablaNacidas() );
        
        JScrollPane scroll=new JScrollPanePersonalizado(tablaNacidas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(scroll);
        
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
        TableColumnModel modeloProgramadas=tablaProgramadas.getColumnModel();
        TableColumnModel modeloNacidas=tablaNacidas.getColumnModel();
        
        // Elimina las tablas
        panelProgramadas.remove(1);
        panelNacidas.remove(1);
        
        // Rearma las tablas
        JPanel panelTablaProgramadas=armarPanelTablaProgramadas();
        JPanel panelTablaNacidas=armarPanelTablaNacidas();
        
        if ( tablaProgramadas.getRowCount() > 0 )
            panelProgramadas.add(panelTablaProgramadas, BorderLayout.CENTER);
            else
            panelProgramadas.add(new JLabel("No hay elementos para mostrar", JLabel.CENTER), BorderLayout.CENTER);
        
        if ( tablaNacidas.getRowCount() > 0 )
            panelNacidas.add(panelTablaNacidas, BorderLayout.CENTER);
            else
            panelNacidas.add(new JLabel("No hay elementos para mostrar", JLabel.CENTER), BorderLayout.CENTER);
        
        tablaProgramadas.setColumnModel(modeloProgramadas);
        tablaNacidas.setColumnModel(modeloNacidas);
        
        // Bloquea los botones, ya que las selecciones se hacen nulas
        bloquearBotonesProgramadas();
        bloquearBotonesNacidas();
        
        // Actualiza el panel contenedor
        panelProgramadas.validate();
        panelNacidas.validate();
    }

    private void actualizarTablaProgramadas() {
        
        TableColumnModel modelo=tablaProgramadas.getColumnModel();
        
        // Elimina la tabla
        panelProgramadas.remove(1);
        
        // Rearma la tabla
        JPanel panelTablaProgramadas=armarPanelTablaProgramadas();
        
        if ( tablaProgramadas.getRowCount() > 0 )
            panelProgramadas.add(panelTablaProgramadas, BorderLayout.CENTER);
            else
            panelProgramadas.add(new JLabel("No hay elementos para mostrar", JLabel.CENTER), BorderLayout.CENTER);
        
        tablaProgramadas.setColumnModel(modelo);
        
        // Bloquea los botones, ya que la selección se hace nula
        bloquearBotonesProgramadas();
        
        // Actualiza el panel contenedor
        panelProgramadas.validate();
    }

    private void actualizarTablaNacidas() {
        
        TableColumnModel modelo=tablaNacidas.getColumnModel();
        
        // Elimina la tabla
        panelNacidas.remove(1);
        
        // Rearma la tabla
        JPanel panelTablaNacidas=armarPanelTablaNacidas();
        
        if ( tablaNacidas.getRowCount() > 0 )
            panelNacidas.add(panelTablaNacidas, BorderLayout.CENTER);
            else
            panelNacidas.add(new JLabel("No hay elementos para mostrar", JLabel.CENTER), BorderLayout.CENTER);
        
        tablaNacidas.setColumnModel(modelo);
        
        // Bloquea los botones, ya que la selección se hace nula
        bloquearBotonesNacidas();
        
        // Actualiza el panel contenedor
        panelNacidas.validate();
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
    private class OyenteTablaProgramadas extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            desbloquearBotonesProgramadas();
        }
        
        public void mouseClicked(MouseEvent e) {
            if ( e.getClickCount()==2 )
                dobleClickProgramadas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteTablaNacidas extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            desbloquearBotonesNacidas();
        }
        
        public void mouseClicked(MouseEvent e) {
            if ( e.getClickCount()==2 )
                dobleClickNacidas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteRedimensionar extends ComponentAdapter implements ComponentListener {
        public void componentResized(ComponentEvent evento) {
            redimensionar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDetallesProgramadas implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            detallesProgramadas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDetallesNacidas implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            detallesNacidas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEditarProgramadas implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            editarProgramadas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEditarNacidas implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            editarNacidas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEliminarProgramadas implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            eliminarProgramadas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEliminarNacidas implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            eliminarNacidas();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteNuevoProgramada implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            nuevoProgramada();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteNuevoNacida implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            nuevoNacida();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDeclarar implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            declarar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteVer implements ActionListener {
    
    // Atributos
        boolean acomodar;
    
    // Constructores
        public OyenteVer() {
            acomodar=false;
        }
    
        public void actionPerformed(ActionEvent evento) {
            
            if ( ! checkProgramadas.isSelected() && ! checkNacidas.isSelected() ) {
                
                // Ambos están destildados
                splitPane.setVisible(false);
                acomodar=true;
                
            } else {
                
                splitPane.setVisible(true);
                
                if ( checkProgramadas.isSelected() )
                    // Ver Crías Programadas está tildado
                    panelProgramadas.setVisible(true);
                    else
                    panelProgramadas.setVisible(false);
                
                if ( checkNacidas.isSelected() )
                    // Ver Crías Nacidas está tildado
                    panelNacidas.setVisible(true);
                    else
                    panelNacidas.setVisible(false);
                
                if ( acomodar && checkProgramadas.isSelected() && checkNacidas.isSelected() ) {
                    splitPane.setDividerLocation(altura/2);
                    acomodar=false;
                } else
                    splitPane.setDividerLocation ( splitPane.getLastDividerLocation() );
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}