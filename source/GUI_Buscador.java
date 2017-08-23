import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.TableColumnModel;

public class GUI_Buscador extends GUI_Frame {

// Atributos
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE_OFF;
    private static final String [ ] COLUMNAS_EJEMPLARES=Constantes.TABLA_COLUMNAS_EJEMPLAR_ACORTADA;
    private static final String [ ] COLUMNAS_CONTACTOS=Constantes.TABLA_COLUMNAS_PERSONA;
    
    private GUI_Frame parent;
    
    private JButton botonBuscar;
    private JTabbedPane panelSolapas;
    private JRadioButton botonNormal, botonHijosDe, botonEjemplaresDe;
    private OyenteSeleccionarBusqueda oyenteBusqueda;
    
    private JButton botonInformacion;
    private JCheckBox checkEjemplares, checkContactos;
    private JComboBox boxMes;
    private JRadioButton botonInclusivo, botonExclusivo, botonTodos, botonMachos, botonHembras;
    private JTextField fieldNombre, fieldNotas, fieldDia, fieldAnio, fieldRegistro, fieldTatuaje, fieldDomicilio, fieldTelefono;
    private OyenteChecks oyenteChecks;
    
    private JButton botonEjemplar, botonDescartarEjemplar, botonContacto, botonDescartarContacto;
    private JTextField fieldEjemplar, fieldContacto;
    
    private JButton botonDetallesEjemplares, botonEditarEjemplares, botonDetallesContactos, botonEditarContactos;
    private JLabel labelEjemplares, labelContactos;
    private JPanel panelEjemplares, panelContactos;
    private JTable tablaEjemplares, tablaContactos;
    private JSplitPane splitPane;
    
    private Datos baseDatos;
    private Ejemplar ejemplar;
    private Persona contacto;
    private ListaEjemplar listaEjemplares;
    private ListaPersona listaContactos;

// Constructores
    public GUI_Buscador(GUI_Frame p, Datos base) {
        parent=p;
        baseDatos=base;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        dispose();
        new GUI_Principal(baseDatos);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void buscar() {
        if ( botonNormal.isSelected() )
            buscarNormal();
            else
                if ( botonHijosDe.isSelected() )
                    buscarHijosDe();
                    else
                        buscarPerrosDe();
        
        panelSolapas.setSelectedIndex(1);
    }

    private void buscarNormal() {
        // Asume que hay algún check seleccionado
        
        // Reúne la información y crea el objeto de búsqueda
        Busqueda busqueda=new Busqueda(baseDatos);
        
        // Datos generales
        busqueda.setNombre( fieldNombre.getText() );
        busqueda.setNotas( fieldNotas.getText() );
        
        if ( checkEjemplares.isSelected() ) {
            
            // Datos ejemplares
            if ( botonTodos.isSelected() ) {
                busqueda.setBuscarMachos(true);
                busqueda.setBuscarHembras(true);
            } else
                if ( botonMachos.isSelected() ) {
                    busqueda.setBuscarMachos(true);
                    busqueda.setBuscarHembras(false);
                } else {
                    busqueda.setBuscarMachos(false);
                    busqueda.setBuscarHembras(true);
                }
            
            String texto;
            int dia, mes, anio;
            
            texto=fieldDia.getText();
            if ( Filtro.esEntero(texto) )
                dia=Integer.parseInt(texto);
                else
                    dia=-1;
            
            mes=boxMes.getSelectedIndex();
            if (mes==0)
                mes=-1;
            
            texto=fieldAnio.getText();
            if ( Filtro.esEntero(texto) )
                anio=Integer.parseInt(texto);
                else
                    anio=-1;
            
            busqueda.setDia(dia);
            busqueda.setMes(mes);
            busqueda.setAnio(anio);
            busqueda.setRegistro( fieldRegistro.getText() );
            busqueda.setTatuaje( fieldTatuaje.getText() );
            
            if ( botonInclusivo.isSelected() )
                listaEjemplares=busqueda.buscarEjemplaresInclusivo();
                else
                    listaEjemplares=busqueda.buscarEjemplaresExclusivo();
            
            actualizarTablaEjemplares();
        }
        
        if ( checkContactos.isSelected() ) {
            
            // Datos contactos
            busqueda.setDomicilio( fieldDomicilio.getText() );
            busqueda.setTelefono( fieldTelefono.getText() );
            
            if ( botonInclusivo.isSelected() )
                listaContactos=busqueda.buscarContactosInclusivo();
                else
                    listaContactos=busqueda.buscarContactosExclusivo();
            
            actualizarTablaContactos();
        }
    }

    private void buscarHijosDe() {
        // Asume que ejemplar no es nulo
        listaEjemplares=(ListaEjemplar) Busqueda.buscarEjemplaresHijosDe( baseDatos.getListaEjemplares(), ejemplar );
        actualizarTablaEjemplares();
    }

    private void buscarPerrosDe() {
        // Asume que contacto no es nulo
        listaEjemplares=(ListaEjemplar) Busqueda.buscarEjemplaresDe( baseDatos.getListaEjemplares(), contacto );
        actualizarTablaEjemplares();
    }

    private void informacion() {
        final String ARCHIVO=Constantes.ARCHIVO_TEXTO_INFORMACION;
        if ( IO.existeArchivo(ARCHIVO) )
            try {
                Runtime.getRuntime().exec("notepad "+ARCHIVO);
            } catch(Exception exc) {
                // Hubo un problema
                // Puede deberse a la plataforma de ejecución
            }
            else
                // El archivo no existe, se muestra un mensaje
                Mensaje.mostrarNoSeEncuentraArchivo(this, ARCHIVO);
    }

    private void ejemplar() {
        GUI_Busqueda_Ejemplar_Existente dialogo=new GUI_Busqueda_Ejemplar_Existente(this, baseDatos);
        ejemplar=dialogo.obtenerElementoSeleccionado();
        if (ejemplar != null) {
            fieldEjemplar.setText( ejemplar.getNombre() );
            botonEjemplar.setEnabled(false);
            botonDescartarEjemplar.setEnabled(true);
            
            botonBuscar.setEnabled(true);
        }
    }

    private void descartarEjemplar() {
        ejemplar=null;
        botonEjemplar.setEnabled(true);
        botonDescartarEjemplar.setEnabled(false);
        fieldEjemplar.setText("Sin datos");
        
        botonBuscar.setEnabled(false);
    }

    private void contacto() {
        GUI_Busqueda_Persona_Existente dialogo=new GUI_Busqueda_Persona_Existente(this, baseDatos);
        contacto=dialogo.obtenerElementoSeleccionado();
        if (contacto != null) {
            fieldContacto.setText( contacto.getNombre() );
            botonContacto.setEnabled(false);
            botonDescartarContacto.setEnabled(true);
            
            botonBuscar.setEnabled(true);
        }
    }

    private void descartarContacto() {
        contacto=null;
        botonContacto.setEnabled(true);
        botonDescartarContacto.setEnabled(false);
        fieldContacto.setText("Sin datos");
        
        botonBuscar.setEnabled(false);
    }

    private void detallesEjemplares() {
        int indice=tablaEjemplares.getSelectedRow();
        String nombre=(String)tablaEjemplares.getValueAt(indice,0);
        Ser elemento=listaEjemplares.buscarElemento(nombre);
        new GUI_Detalles_Ejemplar(this, baseDatos, (Ejemplar)elemento );
    }

    private void editarEjemplares() {
        int indice=tablaEjemplares.getSelectedRow();
        String nombre=(String)tablaEjemplares.getValueAt(indice,0);
        Ejemplar elemento=(Ejemplar)listaEjemplares.buscarElemento(nombre);
        
        String fechaAnterior=elemento.getFecha().toString();
        
        GUI_Editar_Ejemplar dialogo=new GUI_Editar_Ejemplar( this, baseDatos, elemento );
        if ( dialogo.seEdito() )
            actualizarTablaEjemplares();
    }

    private void detallesContactos() {
        int indice=tablaContactos.getSelectedRow();
        String nombre=(String)tablaContactos.getValueAt(indice,0);
        Ser elemento=listaContactos.buscarElemento(nombre);
        new GUI_Detalles_Persona(this, (Persona)elemento );
    }

    private void editarContactos() {
        int indice=tablaContactos.getSelectedRow();
        String nombre=(String)tablaContactos.getValueAt(indice,0);
        Persona elemento=(Persona)listaContactos.buscarElemento(nombre);
        
        String domicilioAnterior=elemento.getDomicilio();
        String telefonoAnterior=elemento.getTelefono();
        
        GUI_Editar_Persona dialogo=new GUI_Editar_Persona( this, baseDatos, elemento );
        if ( dialogo.seEdito() )
            actualizarTablaContactos();
    }

    private void dobleClickContactos() {
        int seleccion=tablaContactos.getSelectedRow();
        if ( seleccion != -1 ) {
            String nombre=(String)tablaContactos.getValueAt(seleccion, 0);
            Ser elemento=listaContactos.buscarElemento(nombre);
            new GUI_Detalles_Persona(this, (Persona)elemento);
        }
    }

    private void dobleClickEjemplares() {
        int seleccion=tablaEjemplares.getSelectedRow();
        if ( seleccion != -1 ) {
            String nombre=(String)tablaEjemplares.getValueAt(seleccion, 0);
            Ser elemento=listaEjemplares.buscarElemento(nombre);
            new GUI_Detalles_Ejemplar(this, baseDatos, (Ejemplar)elemento);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void restaurar() {
        botonNormal.setSelected(true);
        
        checkEjemplares.setEnabled(true);
        checkEjemplares.setSelected(true);
        checkContactos.setEnabled(true);
        checkContactos.setSelected(true);
        
        botonInclusivo.setEnabled(true);
        botonInclusivo.setSelected(true);
        botonExclusivo.setEnabled(true);
        botonInformacion.setEnabled(true);
        
        fieldNombre.setEnabled(true);
        fieldNombre.setText( new String() );
        fieldNotas.setEnabled(true);
        fieldNotas.setText( new String() );
        
        fieldDomicilio.setEnabled(true);
        fieldDomicilio.setText( new String() );
        fieldTelefono.setEnabled(true);
        fieldTelefono.setText( new String() );
        
        fieldDia.setEnabled(true);
        fieldDia.setText( new String() );
        boxMes.setEnabled(true);
        boxMes.setSelectedIndex(0);
        fieldAnio.setEnabled(true);
        fieldAnio.setText( new String() );
        
        botonTodos.setEnabled(true);
        botonTodos.setSelected(true);
        botonMachos.setEnabled(true);
        botonHembras.setEnabled(true);
        
        fieldRegistro.setEnabled(true);
        fieldRegistro.setText( new String() );
        fieldTatuaje.setEnabled(true);
        fieldTatuaje.setText( new String() );
        
        ejemplar=null;
        botonEjemplar.setEnabled(false);
        botonDescartarEjemplar.setEnabled(false);
        fieldEjemplar.setEnabled(false);
        fieldEjemplar.setText("Sin datos");
        
        contacto=null;
        botonContacto.setEnabled(false);
        botonDescartarContacto.setEnabled(false);
        fieldContacto.setEnabled(false);
        fieldContacto.setText("Sin datos");
        
        panelSolapas.setSelectedIndex(0);
        
        botonBuscar.setEnabled(true);
    }

    private void seleccionarBusqueda() {
        if ( botonNormal.isSelected() )
            seleccionarBusquedaNormal();
            else
                if ( botonHijosDe.isSelected() )
                    seleccionarBusquedaHijosDe();
                    else
                        seleccionarBusquedaEjemplaresDe();
    }

    private void seleccionarBusquedaNormal() {
        desbloquearBusquedaNormal();
        bloquearBusquedaHijosDe();
        bloquearBusquedaEjemplaresDe();
        
        if ( ! fieldNombre.isEnabled() )
            // Si fieldNombre no está activado, los checks están desactivados
            botonBuscar.setEnabled(false);
            else
                botonBuscar.setEnabled(true);
    }

    private void seleccionarBusquedaHijosDe() {
        desbloquearBusquedaHijosDe();
        bloquearBusquedaNormal();
        bloquearBusquedaEjemplaresDe();
        
        if (ejemplar == null)
            botonBuscar.setEnabled(false);
            else
                botonBuscar.setEnabled(true);
    }

    private void seleccionarBusquedaEjemplaresDe() {
        desbloquearBusquedaEjemplaresDe();
        bloquearBusquedaNormal();
        bloquearBusquedaHijosDe();
        
        if (contacto == null)
            botonBuscar.setEnabled(false);
            else
                botonBuscar.setEnabled(true);
    }

    private void analizarChecks() {
        boolean ejemplaresSeleccionado=checkEjemplares.isSelected();
        boolean contactosSeleccionado=checkContactos.isSelected();
        
        if ( ! ejemplaresSeleccionado && ! contactosSeleccionado ) {
            // Ambos deseleccionados
            bloquearElementosGenerales();
            bloquearElementosEjemplares();
            bloquearElementosContactos();
        } else {
            desbloquearElementosGenerales();
            
            if (ejemplaresSeleccionado)
                desbloquearElementosEjemplares();
                else
                    bloquearElementosEjemplares();
            
            if (contactosSeleccionado)
                desbloquearElementosContactos();
                else
                    bloquearElementosContactos();
        }
    }

    private void bloquearElementosEjemplares() {
        fieldDia.setEnabled(false);
        boxMes.setEnabled(false);
        fieldAnio.setEnabled(false);
        botonTodos.setEnabled(false);
        botonMachos.setEnabled(false);
        botonHembras.setEnabled(false);
        fieldRegistro.setEnabled(false);
        fieldTatuaje.setEnabled(false);
    }

    private void desbloquearElementosEjemplares() {
        fieldDia.setEnabled(true);
        boxMes.setEnabled(true);
        fieldAnio.setEnabled(true);
        botonTodos.setEnabled(true);
        botonMachos.setEnabled(true);
        botonHembras.setEnabled(true);
        fieldRegistro.setEnabled(true);
        fieldTatuaje.setEnabled(true);
    }

    private void bloquearElementosContactos() {
        fieldDomicilio.setEnabled(false);
        fieldTelefono.setEnabled(false);
    }

    private void desbloquearElementosContactos() {
        fieldDomicilio.setEnabled(true);
        fieldTelefono.setEnabled(true);
    }

    private void bloquearElementosGenerales() {
        fieldNombre.setEnabled(false);
        fieldNotas.setEnabled(false);
        
        botonBuscar.setEnabled(false);
    }

    private void desbloquearElementosGenerales() {
        fieldNombre.setEnabled(true);
        fieldNotas.setEnabled(true);
        
        botonBuscar.setEnabled(true);
    }

    private void bloquearElementosRestantes() {
        botonInclusivo.setEnabled(false);
        botonExclusivo.setEnabled(false);
        botonInformacion.setEnabled(false);
        checkEjemplares.setEnabled(false);
        checkContactos.setEnabled(false);
    }

    private void desbloquearElementosRestantes() {
        botonInclusivo.setEnabled(true);
        botonExclusivo.setEnabled(true);
        botonInformacion.setEnabled(true);
        checkEjemplares.setEnabled(true);
        checkContactos.setEnabled(true);
    }

    private void bloquearBusquedaNormal() {
        bloquearElementosEjemplares();
        bloquearElementosContactos();
        bloquearElementosGenerales();
        bloquearElementosRestantes();
    }

    private void desbloquearBusquedaNormal() {
        desbloquearElementosRestantes();
        analizarChecks();
    }

    private void bloquearBusquedaHijosDe() {
        fieldEjemplar.setEnabled(false);
        botonEjemplar.setEnabled(false);
        botonDescartarEjemplar.setEnabled(false);
    }

    private void desbloquearBusquedaHijosDe() {
        fieldEjemplar.setEnabled(true);
        if (ejemplar == null)
            botonEjemplar.setEnabled(true);
            else
                botonDescartarEjemplar.setEnabled(true);
    }

    private void bloquearBusquedaEjemplaresDe() {
        fieldContacto.setEnabled(false);
        botonContacto.setEnabled(false);
        botonDescartarContacto.setEnabled(false);
    }

    private void desbloquearBusquedaEjemplaresDe() {
        fieldContacto.setEnabled(true);
        if (contacto == null)
            botonContacto.setEnabled(true);
            else
                botonDescartarContacto.setEnabled(true);        
    }

    private void bloquearBotonesEjemplares() {
        botonDetallesEjemplares.setEnabled(false);
        botonEditarEjemplares.setEnabled(false);
    }

    private void desbloquearBotonesEjemplares() {
        botonDetallesEjemplares.setEnabled(true);
        botonEditarEjemplares.setEnabled(true);
    }

    private void bloquearBotonesContactos() {
        botonDetallesContactos.setEnabled(false);
        botonEditarContactos.setEnabled(false);
    }

    private void desbloquearBotonesContactos() {
        botonDetallesContactos.setEnabled(true);
        botonEditarContactos.setEnabled(true);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setTitle("Pedigree Manager - Buscador");
        setSize(499,640);
        setResizable(false);
        setLocationRelativeTo(parent);
        parent.dispose();
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelBanner=armarPanelBanner();
        JPanel panelSolapas=armarPanelSolapas();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setLayout( new BorderLayout() );
            panelPrincipal.add(panelBanner, BorderLayout.NORTH);
            panelPrincipal.add(panelSolapas, BorderLayout.CENTER);
            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelBanner() {
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_BUSCADOR;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(BANNER) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelSolapas() {
        panelSolapas=armarSolapas();
        
        JPanel panel=new JPanelTransparente();
            panel.add(panelSolapas);
        
        return panel;
    }

    private JTabbedPane armarSolapas() {
        JPanel panelParametros=armarPanelParametros();
        JPanel panelResultados=armarPanelResultados();
        
        JTabbedPane panel=new JTabbedPanePersonalizado();
            panel.addTab("Parámetros", panelParametros);
            panel.addTab("Resultados", panelResultados);
        
        return panel;
    }

    private JPanel armarPanelParametros() {
        oyenteBusqueda=new OyenteSeleccionarBusqueda();
        
        JPanel panelNormal=armarPanelNormal();
        JPanel panelHijosDe=armarPanelHijosDe();
        JPanel panelEjemplaresDe=armarPanelEjemplaresDe();
        
        ButtonGroup grupo=new ButtonGroup();
            grupo.add(botonNormal);
            grupo.add(botonHijosDe);
            grupo.add(botonEjemplaresDe);
        
        final Border BORDE_NORMAL=Constantes.BORDE_NORMAL;
        final Border BORDE_DATO=Constantes.BORDE_DATO_UP_DOWN;
        JPanel panel=new JPanel();
            panel.setBorder( BorderFactory.createCompoundBorder( BORDE_DATO,BORDE_NORMAL) );
            panel.setLayout( new BorderLayout(0,10) );
            panel.add(panelNormal, BorderLayout.NORTH);
            panel.add(panelHijosDe, BorderLayout.CENTER);
            panel.add(panelEjemplaresDe, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelNormal() {
        botonNormal=new JRadioButtonPersonalizado("Búsqueda normal");
            botonNormal.addActionListener(oyenteBusqueda);
            botonNormal.setSelected(true);
        
        JPanel panelIzquierda=armarPanelIzquierda();
        JPanel panelDerecha=armarPanelDerecha();
        
        JPanel panelFinal=new JPanelTransparente();
            panelFinal.setLayout( new GridLayout(1,2,5,0) );
            panelFinal.add(panelIzquierda);
            panelFinal.add(panelDerecha);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(botonNormal, BorderLayout.NORTH);
            panel.add(panelFinal, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelIzquierda() {
        JPanel panelQueBuscar=armarPanelQueBuscar();
        JPanel panelOpcionesGenerales=armarPanelOpcionesGenerales();
        JPanel panelOpcionesContactos=armarPanelOpcionesContactos();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelQueBuscar, BorderLayout.NORTH);
            panel.add(panelOpcionesGenerales, BorderLayout.CENTER);
            panel.add(panelOpcionesContactos, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelQueBuscar() {
        oyenteChecks=new OyenteChecks();
        
        checkEjemplares=new JCheckBoxPersonalizado("Ejemplares    ");
            checkEjemplares.setSelected(true);
            checkEjemplares.addActionListener(oyenteChecks);
        checkContactos=new JCheckBoxPersonalizado("Contactos");
            checkContactos.setSelected(true);
            checkContactos.addActionListener(oyenteChecks);
        
        final Border BORDE=Constantes.BORDE_TITULADO_NORMAL("Buscar");
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.add(checkEjemplares);
            panel.add(checkContactos);
        
        return panel;
    }

    private JPanel armarPanelOpcionesGenerales() {
        JPanel panelNombre=armarPanelNombre();
        JPanel panelNotas=armarPanelNotas();
        
        JPanel panelFinal=new JPanelTransparente();
            panelFinal.setLayout( new GridLayout(2,1,0,5) );
            panelFinal.add(panelNombre);
            panelFinal.add(panelNotas);
        
        final Border BORDE=Constantes.BORDE_TITULADO_NORMAL("General");
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.add(panelFinal);
        
        return panel;
    }

    private JPanel armarPanelNombre() {
        JLabel label=new JLabelPersonalizado("Nombre");
        fieldNombre=new JTextFieldPersonalizado(18);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldNombre, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelNotas() {
        JLabel label=new JLabelPersonalizado("Notas (fragmento)");
        fieldNotas=new JTextFieldPersonalizado(18);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldNotas, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelOpcionesContactos() {
        JPanel panelDomicilio=armarPanelDomicilio();
        JPanel panelTelefono=armarPanelTelefono();
        
        JPanel panelFinal=new JPanelTransparente();
            panelFinal.setLayout( new GridLayout(2,1,0,5) );
            panelFinal.add(panelDomicilio);
            panelFinal.add(panelTelefono);
        
        final Border BORDE=Constantes.BORDE_TITULADO_NORMAL("Contactos");
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.add(panelFinal);
        
        return panel;
    }

    private JPanel armarPanelDomicilio() {
        JLabel label=new JLabelPersonalizado("Domicilio");
        fieldDomicilio=new JTextFieldPersonalizado(18);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldDomicilio, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelTelefono() {
        JLabel label=new JLabelPersonalizado("Teléfono");
        fieldTelefono=new JTextFieldPersonalizado(18);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(label, BorderLayout.NORTH);
            panel.add(fieldTelefono, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelDerecha() {
        JPanel panelMetodo=armarPanelMetodo();
        JPanel panelOpcionesEjemplares=armarPanelOpcionesEjemplares();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelMetodo, BorderLayout.NORTH);
            panel.add(panelOpcionesEjemplares, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelMetodo() {
        botonInclusivo=new JRadioButtonPersonalizado("Inclusivo");
        botonExclusivo=new JRadioButtonPersonalizado("Exclusivo");
        
        ButtonGroup grupo=new ButtonGroup();
            grupo.add(botonInclusivo);
            grupo.add(botonExclusivo);
        
        botonInclusivo.setSelected(true);
        
        JPanel panelFinal=new JPanelTransparente();
            panelFinal.setLayout( new GridLayout(2,1) );
            panelFinal.add(botonInclusivo);
            panelFinal.add(botonExclusivo);
        
        final String ICONO_INFO=Constantes.ARCHIVO_IMAGEN_ICONO_INFORMACION;
        botonInformacion=new JButtonChico("¿Qué es esto?", 130);
            botonInformacion.setIcon( new ImageIcon( getClass().getResource(ICONO_INFO) ) );
            botonInformacion.addActionListener( new OyenteInformacion() );
        
        final Border BORDE=Constantes.BORDE_TITULADO_NORMAL("Método");
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.add(panelFinal);
            panel.add(botonInformacion);
        
        return panel;
    }

    private JPanel armarPanelOpcionesEjemplares() {
        JPanel panelFecha=armarPanelFecha();
        JPanel panelSexo=armarPanelSexo();
        JPanel panelRegistroTatuaje=armarPanelRegistroTatuaje();
        
        JPanel panelFinal=new JPanelTransparente();
            panelFinal.setLayout( new BorderLayout() );
            panelFinal.add(panelFecha, BorderLayout.NORTH);
            panelFinal.add(panelSexo, BorderLayout.CENTER);
            panelFinal.add(panelRegistroTatuaje, BorderLayout.SOUTH);
        
        final Border BORDE=Constantes.BORDE_TITULADO_NORMAL("Ejemplares");
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.add(panelFinal);
        
        return panel;
    }

    private JPanel armarPanelFecha() {
        JLabel label=new JLabelPersonalizado("Fecha de nacimiento");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelLabel.add(label);
        
        fieldDia=new JTextFieldPersonalizado(2);
            fieldDia.setHorizontalAlignment(JTextField.CENTER);
        
        final String [ ] MESES=Constantes.MESES;
        boxMes=new JComboBoxPersonalizado(MESES);
        
        fieldAnio=new JTextFieldPersonalizado(3);
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

    private JPanel armarPanelSexo() {
        JLabel label=new JLabelPersonalizado("Sexo: ");
        
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.add(label);
        
        botonTodos=new JRadioButtonPersonalizado("Todos");
        botonMachos=new JRadioButtonPersonalizado("Machos");
        botonHembras=new JRadioButtonPersonalizado("Hembras");
        
        ButtonGroup grupo=new ButtonGroup();
            grupo.add(botonTodos);
            grupo.add(botonMachos);
            grupo.add(botonHembras);
        
        botonTodos.setSelected(true);
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.setLayout( new GridLayout(3,1) );
            panelBotones.add(botonTodos);
            panelBotones.add(botonMachos);
            panelBotones.add(botonHembras);
        
        JPanel panelFinal=new JPanelTransparente();
            panelFinal.setLayout( new BorderLayout() );
            panelFinal.add(panelLabel, BorderLayout.CENTER);
            panelFinal.add(panelBotones, BorderLayout.EAST);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(panelFinal);
        
        return panel;
    }

    private JPanel armarPanelRegistroTatuaje() {
        JLabel labelRegistro=new JLabelPersonalizado("Nº de registro");
        fieldRegistro=new JTextFieldPersonalizado(0);
        
        JPanel panelRegistro=new JPanelTransparente();
            panelRegistro.setLayout( new BorderLayout() );
            panelRegistro.add(labelRegistro, BorderLayout.NORTH);
            panelRegistro.add(fieldRegistro, BorderLayout.CENTER);
        
        JLabel labelTatuaje=new JLabelPersonalizado("Tatuaje");
        fieldTatuaje=new JTextFieldPersonalizado(0);
        
        JPanel panelTatuaje=new JPanelTransparente();
            panelTatuaje.setLayout( new BorderLayout() );
            panelTatuaje.add(labelTatuaje, BorderLayout.NORTH);
            panelTatuaje.add(fieldTatuaje, BorderLayout.CENTER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(1,2,6,0) );
            panel.add(panelRegistro);
            panel.add(panelTatuaje);
        
        return panel;
    }

    private JPanel armarPanelHijosDe() {
        botonHijosDe=new JRadioButtonPersonalizado("Buscar hijos de...");
            botonHijosDe.addActionListener(oyenteBusqueda);
        
        JPanel panelBuscarEjemplar=armarPanelBuscarEjemplar();
        
        bloquearBusquedaHijosDe();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(botonHijosDe, BorderLayout.NORTH);
            panel.add(panelBuscarEjemplar, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel armarPanelBuscarEjemplar() {
        JPanel panelLabel=armarPanelLabelEjemplar();
        JPanel panelField=armarPanelFieldEjemplar();
        
        final Border BORDE=Constantes.BORDE_SANGRIA_FUERTE;
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.setLayout( new BorderLayout() );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelField, BorderLayout.CENTER);
        
        return panel;
    }

   private JPanel armarPanelLabelEjemplar() {
        JLabel label=new JLabelPersonalizado("Busque los hijos de un ejemplar de la base de datos");
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(label);
        
        return panel;
    }

   private JPanel armarPanelFieldEjemplar() {
        JLabel label=new JLabelPersonalizado("Ejemplar:");
        
        fieldEjemplar=new JTextFieldPersonalizado("Sin datos");
            fieldEjemplar.setEditable(false);
        
        botonEjemplar=new JButtonChico("Agregar", 96);
            botonEjemplar.addActionListener( new OyenteEjemplar() );
        
        botonDescartarEjemplar=new JButtonChico("Descartar", 96);
            botonDescartarEjemplar.addActionListener( new OyenteDescartarEjemplar() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(label);
            panel.add(fieldEjemplar);
            panel.add(botonEjemplar);
            panel.add(botonDescartarEjemplar);
        
        return panel;
    }

    private JPanel armarPanelEjemplaresDe() {
        botonEjemplaresDe=new JRadioButtonPersonalizado("Buscar ejemplares de...");
            botonEjemplaresDe.addActionListener(oyenteBusqueda);
        
        JPanel panelBuscarContacto=armarPanelBuscarContacto();
        
        bloquearBusquedaEjemplaresDe();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(botonEjemplaresDe, BorderLayout.NORTH);
            panel.add(panelBuscarContacto, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelBuscarContacto() {
        JPanel panelLabel=armarPanelLabelContacto();
        JPanel panelField=armarPanelFieldContacto();
        
        final Border BORDE=Constantes.BORDE_SANGRIA_FUERTE;
        JPanel panel=new JPanelTransparente();
            panel.setBorder(BORDE);
            panel.setLayout( new BorderLayout() );
            panel.add(panelLabel, BorderLayout.NORTH);
            panel.add(panelField, BorderLayout.CENTER);
        
        return panel;
    }

   private JPanel armarPanelLabelContacto() {
        JLabel label=new JLabelPersonalizado("Busque los perros de un contacto de la base de datos");
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(label);
        
        return panel;
    }

   private JPanel armarPanelFieldContacto() {
        JLabel label=new JLabelPersonalizado("Contacto:");
        
        fieldContacto=new JTextFieldPersonalizado("Sin datos");
            fieldContacto.setEditable(false);
        
        botonContacto=new JButtonChico("Agregar", 96);
            botonContacto.addActionListener( new OyenteContacto() );
        
        botonDescartarContacto=new JButtonChico("Descartar", 96);
            botonDescartarContacto.addActionListener( new OyenteDescartarContacto() );
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panel.add(label);
            panel.add(fieldContacto);
            panel.add(botonContacto);
            panel.add(botonDescartarContacto);
        
        return panel;
    }

    private JPanel armarPanelResultados() {
        panelEjemplares=armarPanelEjemplares();
        panelContactos=armarPanelContactos();
        
        splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, panelEjemplares, panelContactos);
        splitPane.setDividerLocation(239);
        
        final Border BORDE=Constantes.BORDE_DATO_UP_DOWN;
        JPanel panel=new JPanel();
            panel.setBorder(BORDE);
            panel.setPreferredSize( new Dimension(483,483) );
            panel.setLayout( new GridLayout() );
            panel.add(splitPane);
        
        return panel;
    }

    private JPanel armarPanelEjemplares() {
        final String ICONO_DETALLES=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_EDITAR=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_ELIMINAR=Constantes.ARCHIVO_IMAGEN_ICONO_ELIMINAR;
        
        botonDetallesEjemplares=new JButtonChico("Detalles ", 100);
            botonDetallesEjemplares.setIcon( new ImageIcon( getClass().getResource(ICONO_DETALLES) ) );
            botonDetallesEjemplares.setHorizontalTextPosition(SwingConstants.LEFT);
            botonDetallesEjemplares.addActionListener( new OyenteDetallesEjemplares() );
        botonEditarEjemplares=new JButtonChico("Editar ", 90);
            botonEditarEjemplares.setIcon( new ImageIcon( getClass().getResource(ICONO_EDITAR) ) );
            botonEditarEjemplares.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEditarEjemplares.addActionListener( new OyenteEditarEjemplares() );
        
        bloquearBotonesEjemplares();
        
        JPanel panelSubBotones=new JPanelTransparente();
            panelSubBotones.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panelSubBotones.add(botonDetallesEjemplares);
            panelSubBotones.add(botonEditarEjemplares);
        
        labelEjemplares=new JLabelPersonalizado("   Ejemplares encontrados");
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new GridLayout() );
            panelLabel.add(labelEjemplares);
        
        JLabel labelNoHayResultados=new JLabel("No se buscaron ejemplares", JLabel.CENTER);
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.setLayout( new BorderLayout() );
            panelBotones.add(panelSubBotones, BorderLayout.EAST);
            panelBotones.add(panelLabel, BorderLayout.WEST);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelBotones, BorderLayout.NORTH);
            panel.add(labelNoHayResultados, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelContactos() {
        final String ICONO_DETALLES=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_EDITAR=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        final String ICONO_ELIMINAR=Constantes.ARCHIVO_IMAGEN_ICONO_ELIMINAR;
        
        botonDetallesContactos=new JButtonChico("Detalles ", 100);
            botonDetallesContactos.setIcon( new ImageIcon( getClass().getResource(ICONO_DETALLES) ) );
            botonDetallesContactos.setHorizontalTextPosition(SwingConstants.LEFT);
            botonDetallesContactos.addActionListener( new OyenteDetallesContactos() );
        botonEditarContactos=new JButtonChico("Editar ", 90);
            botonEditarContactos.setIcon( new ImageIcon( getClass().getResource(ICONO_EDITAR) ) );
            botonEditarContactos.setHorizontalTextPosition(SwingConstants.LEFT);
            botonEditarContactos.addActionListener( new OyenteEditarContactos() );
        
        bloquearBotonesContactos();
        
        JPanel panelSubBotones=new JPanelTransparente();
            panelSubBotones.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panelSubBotones.add(botonDetallesContactos);
            panelSubBotones.add(botonEditarContactos);
        
        labelContactos=new JLabelPersonalizado("   Contactos encontrados");
        JPanel panelLabel=new JPanelTransparente();
            panelLabel.setLayout( new GridLayout() );
            panelLabel.add(labelContactos);
        
        JLabel labelNoHayResultados=new JLabel("No se buscaron contactos", JLabel.CENTER);
        
        JPanel panelBotones=new JPanelTransparente();
            panelBotones.setLayout( new BorderLayout() );
            panelBotones.add(panelSubBotones, BorderLayout.EAST);
            panelBotones.add(panelLabel, BorderLayout.WEST);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelBotones, BorderLayout.NORTH);
            panel.add(labelNoHayResultados, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        final String ICONO=Constantes.ARCHIVO_IMAGEN_ICONO_DETALLES;
        
        JButton botonCerrar=new JButtonChico("Cerrar");
            botonCerrar.addActionListener( new OyenteCerrar() );
        
        JPanel panelIzquierda=new JPanelTransparente();
            panelIzquierda.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelIzquierda.add(botonCerrar);
        
        JButton botonRestaurar=new JButtonChico("Restaurar valores iniciales", 180);
            botonRestaurar.addActionListener( new OyenteRestaurar() );
        botonBuscar=new JButtonChico("Buscar", 90);
            botonBuscar.setIcon( new ImageIcon( getClass().getResource(ICONO) ) );
            botonBuscar.setHorizontalTextPosition(SwingConstants.LEFT);
            botonBuscar.addActionListener( new OyenteBuscar() );
        
        JPanel panelDerecha=new JPanelTransparente();
            panelDerecha.setLayout( new FlowLayout(FlowLayout.RIGHT) );
            panelDerecha.add(botonRestaurar);
            panelDerecha.add(botonBuscar);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(panelIzquierda, BorderLayout.WEST);
            panel.add(panelDerecha, BorderLayout.EAST);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void actualizarTablaEjemplares() {
        if (tablaEjemplares == null) {
            
            // Elimina el label
            panelEjemplares.remove(1);
            
            // Rearma la tabla
            JPanel panelTablaEjemplares=armarPanelTablaEjemplares();
            
            int count=tablaEjemplares.getRowCount();
            if (count > 0)
                panelEjemplares.add(panelTablaEjemplares, BorderLayout.CENTER);
                else
                    panelEjemplares.add(new JLabel("No se encontraron ejemplares", JLabel.CENTER), BorderLayout.CENTER);
            
            labelEjemplares.setText("   Ejemplares encontrados ("+count+")");
            
            // Establece el ancho inicial de las columnas
            
            // Nombre
            tablaEjemplares.getColumnModel().getColumn(0).setPreferredWidth(324);
            // Domicilio
            tablaEjemplares.getColumnModel().getColumn(1).setPreferredWidth(175);
            // Teléfono
            tablaEjemplares.getColumnModel().getColumn(2).setPreferredWidth(175);
            
            // Bloquea los botones, ya que la selección se hace nula
            bloquearBotonesEjemplares();
            
            // Actualiza el panel contenedor
            panelEjemplares.validate();
        } else {
            
            // La tabla ya ha sido creada
            TableColumnModel modelo=tablaEjemplares.getColumnModel();
            
            // Elimina la tabla
            panelEjemplares.remove(1);
            
            // Rearma la tabla
            JPanel panelTablaEjemplares=armarPanelTablaEjemplares();
            
            int count=tablaEjemplares.getRowCount();
            if (count > 0)
                panelEjemplares.add(panelTablaEjemplares, BorderLayout.CENTER);
                else
                    panelEjemplares.add(new JLabel("No se encontraron ejemplares", JLabel.CENTER), BorderLayout.CENTER);
            
            labelEjemplares.setText("   Ejemplares encontrados ("+count+")");
            
            tablaEjemplares.setColumnModel(modelo);
            
            // Bloquea los botones, ya que la selección se hace nula
            bloquearBotonesEjemplares();
            
            // Actualiza el panel contenedor
            panelEjemplares.validate();
        }
    }

    private JPanel armarPanelTablaEjemplares() {
        // Genera los datos
        
        int cantElementos=listaEjemplares.cantElementos();
        String [ ] [ ] datos=new String [ cantElementos ] [ 3 ];
        
        int i;
        Ejemplar elemento;
        for (i=0; i<cantElementos; i++) {
            elemento=(Ejemplar)listaEjemplares.getElemento( i );
            datos [ i ] [ 0 ] = elemento.getNombre();
            datos [ i ] [ 1 ] = elemento.getFecha().toString();
            if ( elemento.esHembra() )
                datos [ i ] [ 2 ] = "Hembra";
                else
                datos [ i ] [ 2 ] = "Macho";
        }
        
        // Genera la tabla
        final Color COLOR_SELECCION=Constantes.COLOR_VERDE_OFF;
        tablaEjemplares=new JTablePersonalizado(datos,COLUMNAS_EJEMPLARES);
        tablaEjemplares.setSelectionBackground(COLOR_SELECCION);
        tablaEjemplares.addMouseListener( new OyenteTablaEjemplares() );
        
        JScrollPane scroll=new JScrollPane(tablaEjemplares, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(scroll);
        
        return panel;
    }

    private void actualizarTablaContactos() {
        if (tablaContactos == null) {
            
            // Elimina el label
            panelContactos.remove(1);
            
            // Rearma la tabla
            JPanel panelTablaContactos=armarPanelTablaContactos();
            
            int count=tablaContactos.getRowCount();
            if (count > 0)
                panelContactos.add(panelTablaContactos, BorderLayout.CENTER);
                else
                    panelContactos.add(new JLabel("No se encontraron contactos", JLabel.CENTER), BorderLayout.CENTER);
            
            labelContactos.setText("   Contactos encontrados ("+count+")");
            
            // Establece el ancho inicial de las columnas
            
            // Nombre
            tablaContactos.getColumnModel().getColumn(0).setPreferredWidth(324);
            // Domicilio
            tablaContactos.getColumnModel().getColumn(1).setPreferredWidth(175);
            // Teléfono
            tablaContactos.getColumnModel().getColumn(2).setPreferredWidth(175);
            
            // Bloquea los botones, ya que la selección se hace nula
            bloquearBotonesContactos();
            
            // Actualiza el panel contenedor
            panelContactos.validate();
        } else {
            
            // La tabla ya ha sido creada
            TableColumnModel modelo=tablaContactos.getColumnModel();
            
            // Elimina la tabla
            panelContactos.remove(1);
            
            // Rearma la tabla
            JPanel panelTablaContactos=armarPanelTablaContactos();
            
            int count=tablaContactos.getRowCount();
            if (count > 0)
                panelContactos.add(panelTablaContactos, BorderLayout.CENTER);
                else
                    panelContactos.add(new JLabel("No se encontraron contactos", JLabel.CENTER), BorderLayout.CENTER);
            
            labelContactos.setText("   Contactos encontrados ("+count+")");
            
            tablaContactos.setColumnModel(modelo);
            
            // Bloquea los botones, ya que la selección se hace nula
            bloquearBotonesContactos();
            
            // Actualiza el panel contenedor
            panelContactos.validate();
        }
    }

    private JPanel armarPanelTablaContactos() {
        // Genera los datos
        
        int cantElementos=listaContactos.cantElementos();
        String [ ] [ ] datos=new String [ cantElementos ] [ 3 ];
        
        int i;
        Persona elemento;
        for (i=0; i<cantElementos; i++) {
            elemento=(Persona)listaContactos.getElemento( i );
            datos [ i ] [ 0 ] = elemento.getNombre();
            datos [ i ] [ 1 ] = elemento.getDomicilio();
            datos [ i ] [ 2 ] = elemento.getTelefono();
        }
        
        // Genera la tabla
        final Color COLOR_SELECCION=Constantes.COLOR_VIOLETA_OFF;
        tablaContactos=new JTablePersonalizado(datos,COLUMNAS_CONTACTOS);
        tablaContactos.setSelectionBackground(COLOR_SELECCION);
        tablaContactos.addMouseListener( new OyenteTablaContactos() );
        
        JScrollPane scroll=new JScrollPane(tablaContactos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Forma el panel
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout() );
            panel.add(scroll);
        
        return panel;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteChecks implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            analizarChecks();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteSeleccionarBusqueda implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            seleccionarBusqueda();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteRestaurar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            restaurar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteBuscar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            buscar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteInformacion implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            informacion();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEjemplar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            ejemplar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDescartarEjemplar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            descartarEjemplar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteContacto implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            contacto();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDescartarContacto implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            descartarContacto();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDetallesEjemplares implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            detallesEjemplares();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEditarEjemplares implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            editarEjemplares();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteDetallesContactos implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            detallesContactos();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteEditarContactos implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            editarContactos();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteTablaEjemplares extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            desbloquearBotonesEjemplares();
        }
        
        public void mouseClicked(MouseEvent e) {
            if ( e.getClickCount()==2 )
                dobleClickEjemplares();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteTablaContactos extends MouseAdapter implements MouseListener {
        public void mousePressed(MouseEvent e) {
            desbloquearBotonesContactos();
        }
        
        public void mouseClicked(MouseEvent e) {
            if ( e.getClickCount()==2 )
                dobleClickContactos();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}