import java.net.ServerSocket;

public class Main {

// Atributos
    private static final String ARCHIVO_PUERTO=Constantes.ARCHIVO_PUERTO;
    
    private Datos baseDatos;

// Constructores
    public Main() {
        
        baseDatos=new Datos();
        
        String baseDefault=Opciones.obtenerBaseDefault();
        
        if ( ! baseDefault.isEmpty() && Datos.existeBaseDatos(baseDefault) ) {
            // La base de datos existe
            boolean seCargo=baseDatos.cargarDatos(baseDefault);
            
            if (seCargo)
                // No hubo problemas al cargar los datos
                abrirVentanaPrincipal();
                else
                    // Hubo un problema al cargar los datos
                    abrirVentanaInicial();
        } else
            // La base de datos no existe
            abrirVentanaInicial();
    }

// M�todos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String [ ] args) {
        try {
            
            int puerto;
            String puertoTexto;
            
            if ( IO.existeArchivo(ARCHIVO_PUERTO) ) {
                puertoTexto=IO.leerArchivoTexto(ARCHIVO_PUERTO);
                if ( Filtro.esEntero(puertoTexto) )
                    puerto=Integer.parseInt(puertoTexto);
                    else {
                        puerto=2591;
                        establecerNuevoPuerto(2591);
                    }
            } else {
                puerto=2591;
                establecerNuevoPuerto(2591);
            }
            
            new ServerSocket(puerto);
            
            new Main();
            
        } catch (Exception exc) {
            // El puerto ya est� ocupado
            Mensaje.mostrarUnicaInstancia();
            System.exit(0);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private static void establecerNuevoPuerto(int puerto) {
        // Edita el archivo de texto con el nuevo puerto
        IO.editarArchivoTexto(ARCHIVO_PUERTO, Integer.toString(puerto) );
    }

    private void abrirVentanaPrincipal() {
        // Abre la ventana principal del programa, la base de datos est� correctamente inicializada
        new GUI_Principal(baseDatos);
    }

    private void abrirVentanaInicial() {
        // Abre la ventana de selecci�n de la base de datos
        new GUI_Inicial();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}