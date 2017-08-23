import java.io.Serializable;

public class Opciones {

// Atributos
    private static final String ARCHIVO=Constantes.ARCHIVO_OPCIONES;
    private static final String DEFAULT_SIGN=Constantes.DEFAULT_SIGN;
    private static final String DEFAULT_CLOSESIGN=Constantes.DEFAULT_CLOSESIGN;
    private static final String AUTOSAVE_SIGN=Constantes.AUTOSAVE_SIGN;
    private static final String AUTOSAVE_CLOSESIGN=Constantes.AUTOSAVE_CLOSESIGN;
    private static final String AUTOSAVE_INTERVAL_SIGN=Constantes.AUTOSAVE_INTERVAL_SIGN;
    private static final String AUTOSAVE_INTERVAL_CLOSESIGN=Constantes.AUTOSAVE_INTERVAL_CLOSESIGN;
    
    private static String baseDefault;
    private static boolean autosaveOnClose;
    private static int autosaveInterval;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public String baseDefault() {
        return baseDefault;
    }

    public void setBaseDefault(String base) {
        baseDefault=base;
    }

    public boolean autosaveOnClose() {
        return autosaveOnClose;
    }

    public void setAutosaveOnClose(boolean b) {
        autosaveOnClose=b;
    }

    public int autosaveInterval() {
        return autosaveInterval;
    }

    public void setAutosaveInterval(int nro) {
        autosaveInterval=nro;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public void guardarOpciones() {
        // Crea/actualiza el archivo de opciones
        
        String texto=
        "--NO EDITAR MANUALMENTE--\n"+
        DEFAULT_SIGN+baseDefault+DEFAULT_CLOSESIGN+"\n"+
        AUTOSAVE_SIGN+autosaveOnClose+AUTOSAVE_CLOSESIGN+"\n"+
        AUTOSAVE_INTERVAL_SIGN+autosaveInterval+AUTOSAVE_INTERVAL_CLOSESIGN;
        
        IO.editarArchivoTexto(ARCHIVO, texto);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static Opciones cargarOpciones() {
        // Carga los valores del archivo de opciones
        
        Opciones opciones;
        
        if ( ! IO.existeArchivo(ARCHIVO) ) {
            opciones=cargarOpcionesDefault();
            opciones.guardarOpciones();
        } else {
            opciones=new Opciones();
            
            String [ ] listaOpciones=obtenerTodasLasOpciones();
            
            opciones.setBaseDefault( listaOpciones [ 0 ] );
            
            final boolean DEFAULT=Constantes.OPCION_AUTOSAVE_ON_CLOSE_DEFAULT;
            if ( listaOpciones [ 1 ].equals(""+( ! DEFAULT) ) )
                opciones.setAutosaveOnClose( ! DEFAULT);
                else
                    opciones.setAutosaveOnClose(DEFAULT);
            
            opciones.setAutosaveInterval( Integer.parseInt( listaOpciones [ 2 ] ) );
        }
        
        return opciones;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private static Opciones cargarOpcionesDefault() {
        // Carga los valores por default
        
        Opciones opciones=new Opciones();
        
        final int AUTOSAVE_INTERVAL_DEFAULT=Constantes.OPCION_AUTOSAVE_INTERVAL_DEFAULT;
        final boolean AUTOSAVE_ON_CLOSE_DEFAULT=Constantes.OPCION_AUTOSAVE_ON_CLOSE_DEFAULT;
        
        opciones.setBaseDefault( new String() );
        opciones.setAutosaveOnClose(AUTOSAVE_ON_CLOSE_DEFAULT);
        opciones.setAutosaveInterval(AUTOSAVE_INTERVAL_DEFAULT);
        
        return opciones;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static String obtenerBaseDefault() {
        // Obtiene el valor del archivo de opciones
        String titulo, texto;
        
        titulo=new String();
        texto=IO.leerArchivoTexto(ARCHIVO);
        
        if (texto != null) {
            
            int indiceInicial, indiceFinal;
            
            indiceInicial=texto.indexOf(DEFAULT_SIGN) + DEFAULT_SIGN.length();
            indiceFinal=texto.indexOf(DEFAULT_CLOSESIGN);
            
            if (indiceFinal > indiceInicial && indiceInicial != -1)
                titulo=texto.substring(indiceInicial, indiceFinal);
        }
        
        return titulo;
    }

    private static String [ ] obtenerTodasLasOpciones() {
        
        final boolean AUTOSAVE_DEFAULT=Constantes.OPCION_AUTOSAVE_ON_CLOSE_DEFAULT;
        final int INTERVALO_DEFAULT=Constantes.OPCION_AUTOSAVE_INTERVAL_DEFAULT;
        
        String opciones [ ];
        String base, autosave, intervalo, texto;
        
        base=new String();
        autosave=""+AUTOSAVE_DEFAULT;
        intervalo=Integer.toString(INTERVALO_DEFAULT);
        
        texto=IO.leerArchivoTexto(ARCHIVO);
        
        if (texto != null) {
            
            int indiceInicial, indiceFinal;
            
            // Base default
            indiceInicial=texto.indexOf(DEFAULT_SIGN) + DEFAULT_SIGN.length();
            indiceFinal=texto.indexOf(DEFAULT_CLOSESIGN);
            
            if (indiceFinal > indiceInicial && indiceInicial != -1)
                base=texto.substring(indiceInicial, indiceFinal).toLowerCase();
            
            // Autosave
            indiceInicial=texto.indexOf(AUTOSAVE_SIGN) + AUTOSAVE_SIGN.length();
            indiceFinal=texto.indexOf(AUTOSAVE_CLOSESIGN);
            
            if (indiceFinal > indiceInicial && indiceInicial != -1)
                autosave=texto.substring(indiceInicial, indiceFinal).toLowerCase();
            
            // Intervalo
            indiceInicial=texto.indexOf(AUTOSAVE_INTERVAL_SIGN) + AUTOSAVE_INTERVAL_SIGN.length();
            indiceFinal=texto.indexOf(AUTOSAVE_INTERVAL_CLOSESIGN);
            
            if (indiceFinal > indiceInicial && indiceInicial != -1) {
                String auxiliar=texto.substring(indiceInicial, indiceFinal);
                if ( Filtro.esEntero(intervalo) )
                    try {
                        Integer.parseInt(auxiliar);
                        intervalo=auxiliar;
                    } catch (NumberFormatException exc) {
                        // El número supera el máximo valor permitido
                }
            }
        }
        
        opciones=new String [ 3 ];
        opciones [ 0 ] = base;
        opciones [ 1 ] = autosave;
        opciones [ 2 ] = intervalo;
        
        return opciones;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}