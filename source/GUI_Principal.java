import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI_Principal extends GUI_Frame {

// Atributos
    private static final Border BORDE=Constantes.BORDE_COSTADOS;
    private static final Color COLOR_FONDO=Constantes.COLOR_BEIGE;
    private static final Font FUENTE_TITULO=new Font("Vani", Font.PLAIN, 18);
    private static final Font FUENTE_EXPLICACION=new Font("Verdana", Font.PLAIN, 14);
    
    private Datos baseDatos;

// Constructores
    public GUI_Principal(Datos base) {
        baseDatos=base;
        crearGUI();
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void cerrar() {
        Opciones opciones=baseDatos.getOpciones();
        
        if ( opciones.autosaveOnClose() ) {
            boolean seGuardo=baseDatos.guardarDatos();
            if ( ! seGuardo )
                Mensaje.mostrarError101(this);
        }
        
        opciones.guardarOpciones();
        System.exit(0);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void guardar() {
        boolean seGuardo=baseDatos.guardarDatos();
        if ( seGuardo )
            Mensaje.mostrarGuardarCorrectamente(this);
            else
                Mensaje.mostrarError101(this);
    }

    private void backup() {
        new GUI_Backup(this, baseDatos);
    }

    private void infoBaseDatos() {
        new GUI_Informacion(this, baseDatos);
    }

    private void volverMenuInicial() {
        Opciones opciones=baseDatos.getOpciones();
        
        if ( opciones.autosaveOnClose() ) {
            boolean seGuardo=baseDatos.guardarDatos();
            if ( ! seGuardo )
                Mensaje.mostrarError101(this);
        }
        
        opciones.guardarOpciones();
        dispose();
        new GUI_Inicial();
    }

    private void masivo() {
        new GUI_Agregador_Masivo(this, baseDatos);
    }

    private void abrirNuevoPersona() {
        new GUI_Nuevo_Persona(this, baseDatos);
    }

    private void abrirNuevoEjemplar() {
        new GUI_Nuevo_Ejemplar(this, baseDatos);
    }

    private void abrirNavegador() {
        new GUI_Navegador(this, baseDatos);
    }

    private void abrirCrias() {
        new GUI_Crias(this, baseDatos);
    }

    private void abrirBuscador() {
        new GUI_Buscador(this, baseDatos);
    }

    private void abrirOpciones() {
        new GUI_Opciones(this, baseDatos);
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

    private void errores() {
        final String ARCHIVO=Constantes.ARCHIVO_TEXTO_ERRORES;
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

    private void solucionar() {
        final String ARCHIVO=Constantes.ARCHIVO_TEXTO_CORRECCIONES;
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

    private void acercaDe() {
        new GUI_AcercaDe(this);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private void crearGUI() {
        armarPaneles();
        setJMenuBar( armarMenuBar() );
        setTitle("Pedigree Manager");
        setSize(655,700);
        setMinimumSize( new Dimension(655,620) );
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void armarPaneles() {
        JPanel panelBanner=armarPanelBanner();
        JPanel panelBotones=armarPanelBotones();
        
        JPanel panelPrincipal=new JPanel();
            panelPrincipal.setBorder(BORDE);
            panelPrincipal.setBackground(COLOR_FONDO);
            panelPrincipal.setLayout( new BorderLayout() );
            panelPrincipal.add(panelBanner, BorderLayout.NORTH);
            panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        
        getContentPane().add(panelPrincipal);
    }

    private JPanel armarPanelBanner() {
        final String BANNER=Constantes.ARCHIVO_IMAGEN_BANNER_PRINCIPAL;
        
        JLabel labelIcono=new JLabel( new ImageIcon( getClass().getResource(BANNER) ) );
        
        JPanel panel=new JPanelTransparente();
            panel.add(labelIcono);
        
        return panel;
    }

    private JPanel armarPanelBotones() {
        JPanel panelBuscar=armarPanelBuscar();
        JPanel panelNavegador=armarPanelNavegador();
        JPanel panelCrias=armarPanelCrias();
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new GridLayout(3,1) );
            panel.add(panelBuscar);
            panel.add(panelNavegador);
            panel.add(panelCrias);
        
        return panel;
    }

    private JPanel armarPanelBuscar() {
        JButton boton=new JButtonHuella();
            boton.addActionListener( new OyenteBuscador() );
        
        JPanel panelBoton=new JPanelTransparente();
            panelBoton.add(boton);
        
        JLabel labelTitulo=new JLabel("   Abrir Buscador");
            labelTitulo.setFont(FUENTE_TITULO);
        
        JPanel panelTitulo=new JPanelTransparente();
            panelTitulo.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelTitulo.add(labelTitulo);
        
        JLabel label1=new JLabel("Encuentre ejemplares y contactos de su base de datos mediante");
            label1.setFont(FUENTE_EXPLICACION);
        JLabel label2=new JLabel("un sistema muy completo. El Buscador le permite, además,");
            label2.setFont(FUENTE_EXPLICACION);
        JLabel label3=new JLabel("obtener una lista de los hijos de un determinado ejemplar o los");
            label3.setFont(FUENTE_EXPLICACION);
        JLabel label4=new JLabel("perros de un contacto en particular.");
            label4.setFont(FUENTE_EXPLICACION);
        
        JPanel panelExplicacion=new JPanelTransparente();
            panelExplicacion.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelExplicacion.add(label1);
            panelExplicacion.add(label2);
            panelExplicacion.add(label3);
            panelExplicacion.add(label4);
        
        JPanel panelLabels=new JPanelTransparente();
            panelLabels.setLayout( new BorderLayout() );
            panelLabels.add(panelTitulo , BorderLayout.NORTH);
            panelLabels.add(panelExplicacion , BorderLayout.CENTER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(20,0) );
            panel.add(panelBoton , BorderLayout.WEST);
            panel.add(panelLabels , BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelNavegador() {
        JButton boton=new JButtonHuella();
            boton.addActionListener( new OyenteNavegador() );
        
        JPanel panelBoton=new JPanelTransparente();
            panelBoton.add(boton);
        
        JLabel labelTitulo=new JLabel("   Abrir Navegador de Elementos");
            labelTitulo.setFont(FUENTE_TITULO);
        
        JPanel panelTitulo=new JPanelTransparente();
            panelTitulo.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelTitulo.add(labelTitulo);
        
        JLabel label1=new JLabel("El Navegador de Elementos le permite agregar, eliminar, editar");
            label1.setFont(FUENTE_EXPLICACION);
        JLabel label2=new JLabel("y visualizar los ejemplares y contactos de su base de datos.");
            label2.setFont(FUENTE_EXPLICACION);
        
        JPanel panelExplicacion=new JPanelTransparente();
            panelExplicacion.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelExplicacion.add(label1);
            panelExplicacion.add(label2);
        
        JPanel panelLabels=new JPanelTransparente();
            panelLabels.setLayout( new BorderLayout() );
            panelLabels.add(panelTitulo , BorderLayout.NORTH);
            panelLabels.add(panelExplicacion , BorderLayout.CENTER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(20,0) );
            panel.add(panelBoton , BorderLayout.WEST);
            panel.add(panelLabels , BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel armarPanelCrias() {
        JButton boton=new JButtonHuella();
            boton.addActionListener( new OyenteCrias() );
        
        JPanel panelBoton=new JPanelTransparente();
            panelBoton.add(boton);
        
        JLabel labelTitulo=new JLabel("   Abrir Administrador de Crías");
            labelTitulo.setFont(FUENTE_TITULO);
        
        JPanel panelTitulo=new JPanelTransparente();
            panelTitulo.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelTitulo.add(labelTitulo);
        
        JLabel label1=new JLabel("El Administrador de Crías le permite programar crías");
            label1.setFont(FUENTE_EXPLICACION);
        JLabel label2=new JLabel("futuras y llevar un registro de las ya nacidas.");
            label2.setFont(FUENTE_EXPLICACION);
        
        JPanel panelExplicacion=new JPanelTransparente();
            panelExplicacion.setLayout( new FlowLayout(FlowLayout.LEFT) );
            panelExplicacion.add(label1);
            panelExplicacion.add(label2);
        
        JPanel panelLabels=new JPanelTransparente();
            panelLabels.setLayout( new BorderLayout() );
            panelLabels.add(panelTitulo , BorderLayout.NORTH);
            panelLabels.add(panelExplicacion , BorderLayout.CENTER);
        
        JPanel panel=new JPanelTransparente();
            panel.setLayout( new BorderLayout(20,0) );
            panel.add(panelBoton , BorderLayout.WEST);
            panel.add(panelLabels , BorderLayout.CENTER);
        
        return panel;
    }

    private JMenuBar armarMenuBar() {
        JMenu menuDatos=armarMenuDatos();
        JMenu menuHerramientas=armarMenuHerramientas();
        JMenu menuAyuda=armarMenuAyuda();
        
        JMenuBar menu=new JMenuBar();
            menu.add(menuDatos);
            menu.add(menuHerramientas);
            menu.add(menuAyuda);
        
        return menu;
    }

    private JMenu armarMenuDatos() {
        final String ICONO_GUARDAR=Constantes.ARCHIVO_IMAGEN_ICONO_GUARDAR;
        
        JMenuItem menuGuardar=new JMenuItemPersonalizado("Guardar datos ahora");
            menuGuardar.setIcon( new ImageIcon( getClass().getResource(ICONO_GUARDAR) ) );
            menuGuardar.addActionListener( new OyenteGuardar() );
        JMenuItem menuBackup=new JMenuItemPersonalizado("Crear copia de seguridad");
            menuBackup.addActionListener( new OyenteBackup() );
        JMenuItem menuInfo=new JMenuItemPersonalizado("Información de la base de datos");
            menuInfo.addActionListener( new OyenteInfoBaseDatos() );
        JMenuItem menuInicial=new JMenuItemPersonalizado("Abrir ventana inicial");
            menuInicial.addActionListener( new OyenteInicial() );
        JMenuItem menuSalir=new JMenuItem("Salir");
            menuSalir.addActionListener( new OyenteCerrar() );
        
        JMenu menu=new JMenuPersonalizado("Datos");
            menu.add(menuGuardar);
            menu.add(menuBackup);
            menu.add(menuInfo);
            menu.addSeparator();
            menu.add(menuInicial);
            menu.addSeparator();
            menu.add(menuSalir);
        
        return menu;
    }

    private JMenu armarMenuHerramientas() {
        final String ICONO_OPCIONES=Constantes.ARCHIVO_IMAGEN_ICONO_EDITAR;
        
        JMenu menuAgregar=armarMenuAgregar();
        JMenuItem menuBuscador=new JMenuItemPersonalizado("Abrir Buscador");
            menuBuscador.addActionListener( new OyenteBuscador() );
        JMenuItem menuElementos=new JMenuItemPersonalizado("Abrir Navegador de Elementos");
            menuElementos.addActionListener( new OyenteNavegador() );
        JMenuItem menuCrias=new JMenuItemPersonalizado("Abrir Administrador de Crías");
            menuCrias.addActionListener( new OyenteCrias() );
        JMenuItem menuOpciones=new JMenuItemPersonalizado("Opciones");
            menuOpciones.setIcon( new ImageIcon( getClass().getResource(ICONO_OPCIONES) ) );
            menuOpciones.addActionListener( new OyenteOpciones() );
        
        JMenu menu=new JMenuPersonalizado("Herramientas");
            menu.add(menuAgregar);
            menu.addSeparator();
            menu.add(menuBuscador);
            menu.add(menuElementos);
            menu.add(menuCrias);
            menu.addSeparator();
            menu.add(menuOpciones);
        
        return menu;
    }

    private JMenu armarMenuAgregar() {
        final String ICONO_EJEMPLAR=Constantes.ARCHIVO_IMAGEN_ICONO_EJEMPLAR;
        final String ICONO_CONTACTO=Constantes.ARCHIVO_IMAGEN_ICONO_CONTACTO;
        final String ICONO_AGREGAR=Constantes.ARCHIVO_IMAGEN_ICONO_AGREGAR;
        
        JMenuItem menuMasivo=new JMenuItemPersonalizado("Masivamente");
            menuMasivo.addActionListener( new OyenteMasivo() );
        JMenuItem menuEjemplar=new JMenuItemPersonalizado("Ejemplar");
            menuEjemplar.setIcon( new ImageIcon( getClass().getResource(ICONO_EJEMPLAR) ) );
            menuEjemplar.addActionListener( new OyenteNuevoEjemplar() );
        JMenuItem menuContacto=new JMenuItemPersonalizado("Contacto");
            menuContacto.setIcon( new ImageIcon( getClass().getResource(ICONO_CONTACTO) ) );
            menuContacto.addActionListener( new OyenteNuevoPersona() );
        
        JMenu menu=new JMenuPersonalizado("Agregar");
            menu.setIcon( new ImageIcon( getClass().getResource(ICONO_AGREGAR) ) );
            menu.add(menuMasivo);
            menu.addSeparator();
            menu.add(menuEjemplar);
            menu.add(menuContacto);
        
        return menu;
    }

    private JMenu armarMenuAyuda() {
        final String ICONO_INFO=Constantes.ARCHIVO_IMAGEN_ICONO_INFORMACION;
        final String ICONO_ERROR=Constantes.ARCHIVO_IMAGEN_ICONO_ERRORES;
        final String ICONO_ERROR_FIX=Constantes.ARCHIVO_IMAGEN_ICONO_ERRORES_FIX;
        final String ICONO_LOGO=Constantes.ARCHIVO_IMAGEN_ICONO_LOGO;
        
        JMenuItem menuInformacion=new JMenuItemPersonalizado("Información");
            menuInformacion.setIcon( new ImageIcon( getClass().getResource(ICONO_INFO) ) );
            menuInformacion.addActionListener( new OyenteInformacion() );
        JMenuItem menuErrores=new JMenuItemPersonalizado("Errores");
            menuErrores.setIcon( new ImageIcon( getClass().getResource(ICONO_ERROR) ) );
            menuErrores.addActionListener( new OyenteErrores() );
        JMenuItem menuSolucionar=new JMenuItemPersonalizado("Solucionar problemas");
            menuSolucionar.setIcon( new ImageIcon( getClass().getResource(ICONO_ERROR_FIX) ) );
            menuSolucionar.addActionListener( new OyenteSolucionar() );
        JMenuItem menuAcercaDe=new JMenuItemPersonalizado("Acerca de");
            menuAcercaDe.setPreferredSize( new Dimension(212,24) );
            menuAcercaDe.setHorizontalTextPosition(SwingConstants.LEFT);
            menuAcercaDe.setIcon( new ImageIcon( getClass().getResource(ICONO_LOGO) ) );
            menuAcercaDe.addActionListener( new OyenteAcercaDe() );
        
        JMenu menu=new JMenuPersonalizado("Ayuda");
            menu.add(menuInformacion);
            menu.add(menuErrores);
            menu.add(menuSolucionar);
            menu.addSeparator();
            menu.add(menuAcercaDe);
        
        return menu;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Clases embebidas

//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteGuardar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            guardar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteBackup implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            backup();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteInfoBaseDatos implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            infoBaseDatos();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteInicial implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            volverMenuInicial();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteMasivo implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            masivo();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteNuevoPersona implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            abrirNuevoPersona();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteNuevoEjemplar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            abrirNuevoEjemplar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteNavegador implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            abrirNavegador();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteCrias implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            abrirCrias();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteBuscador implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            abrirBuscador();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteOpciones implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            abrirOpciones();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteInformacion implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            informacion();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteErrores implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            errores();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteSolucionar implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            solucionar();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private class OyenteAcercaDe implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            acercaDe();
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}