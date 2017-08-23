import java.io.Serializable;

public class Datos implements Serializable {

// ATENCIÓN:
// Cualquier modificación de las clases que se serializan provocará que las bases de datos 
// ya creadas en otra versión de Pedigree Manager no puedan ser cargadas.

// Atributos
    private static final long serialVersionUID=9L;
    
    private static final int OPCION_AUTOSAVE_INTERVAL_NONE=Constantes.OPCION_AUTOSAVE_INTERVAL_NONE;
    private static final String DIRECTORIO_DATOS=Constantes.DIRECTORIO_DATOS;
    private static final String DIRECTORIO_BACKUPS=Constantes.DIRECTORIO_BACKUPS;
    private static final String DIRECTORIO_FOTOS_EJEMPLARES=Constantes.DIRECTORIO_FOTOS_EJEMPLARES;
    private static final String DIRECTORIO_FOTOS_CONTACTOS=Constantes.DIRECTORIO_FOTOS_CONTACTOS;
    private static final String EXTENSION_DATOS=Constantes.EXTENSION_DATOS;
    
    private int contador;
    private ListaEjemplar listaEjemplares;
    private ListaPersona listaContactos;
    private ListaCria listaCriasProgramadas;
    private ListaCria listaCriasNacidas;
    private String titulo;
    private transient Opciones opciones;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String nombre) {
        titulo=nombre;
    }

    public Opciones getOpciones() {
        return opciones;
    }

    public ListaEjemplar getListaEjemplares() {
        return listaEjemplares;
    }

    public ListaPersona getListaContactos() {
        return listaContactos;
    }

    public ListaCria getListaCriasProgramadas() {
        return listaCriasProgramadas;
    }

    public ListaCria getListaCriasNacidas() {
        return listaCriasNacidas;
    }

    public void aumentarContador() {
        int autosaveInterval=opciones.autosaveInterval();
        if (autosaveInterval != OPCION_AUTOSAVE_INTERVAL_NONE) {
            contador++;
            if (contador >= autosaveInterval)
                guardarDatos();
        } else
            // Se reinicia el contador, si el autosave está desactivado
            contador=0;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public void establecerComoDefault() {
        opciones.setBaseDefault(titulo);
    }

    public boolean esBaseDefault() {
        String baseDefault=opciones.baseDefault();
        
        return baseDefault.equals(titulo);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static String [ ] obtenerBasesDisponibles() {
        // Devuelve un arreglo con los nombres de las bases disponibles
        // Si no las hay, devuelve null
        
        String [ ] basesDisponibles=null;
        
        if ( ! IO.existeArchivo(DIRECTORIO_DATOS) ) {
            // Si el directorio no existe, no hay bases disponibles
            // Se crea el directorio
            IO.crearDirectorios(DIRECTORIO_DATOS);
        } else {
            // Genera un arreglo de los archivos del directorio de datos cuya extensión sea la buscada
            String [ ] archivos=IO.obtenerArchivos(DIRECTORIO_DATOS, EXTENSION_DATOS);
            
            if (archivos != null) {
                // Si hay algún archivo, verifica que tengan la extensión correspondiente y almacena sus nombres
                int cantArchivos=archivos.length;
                basesDisponibles=new String [ cantArchivos ];
                
                String nombre;
                int i;
                for (i=0; i<cantArchivos; i++) {
                    nombre=Filtro.sinExtension(archivos [ i ]);
                    basesDisponibles [ i ] = nombre;
                }
            }
        }
        
        return basesDisponibles;
    }

    public static boolean existeBaseDatos(String nombre) {
        // Devuelve verdadero si la base de datos representada por el nombre ya existe
        String ruta=DIRECTORIO_DATOS+"/"+nombre+"."+EXTENSION_DATOS;
        
        return IO.existeArchivo(ruta);
    }

    public static boolean eliminarBaseDatos(String nombre) {
        // Devuelve verdadero si y sólo si la base se elimina correctamente
        String ruta=DIRECTORIO_DATOS+"/"+nombre+"."+EXTENSION_DATOS;
        
        return IO.eliminarArchivo(ruta);
    }

    public static void crearDirectoriosNecesarios() {
        // Crea, si no existen, los directorios necesarios para el funcionamiento
        IO.crearDirectorios(DIRECTORIO_DATOS);
        IO.crearDirectorios(DIRECTORIO_BACKUPS);
        IO.crearDirectorios(DIRECTORIO_FOTOS_EJEMPLARES);
        IO.crearDirectorios(DIRECTORIO_FOTOS_CONTACTOS);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean cargarDatos(String nombre) {
        // Devuelve verdadero si la base se carga correctamente
        // Carga los datos de una base de datos existente
        boolean seCargo=true;
        
        String ruta=DIRECTORIO_DATOS+"/"+nombre+"."+EXTENSION_DATOS;
        Datos baseAuxiliar=(Datos)IO.obtenerObjetoSerializado(ruta);
        
        if (baseAuxiliar == null)
            seCargo=false;
            else {
                titulo=nombre;
                
                // Inicializa los atributos
                opciones=Opciones.cargarOpciones();
                listaEjemplares=baseAuxiliar.getListaEjemplares();
                listaContactos=baseAuxiliar.getListaContactos();
                listaCriasProgramadas=baseAuxiliar.getListaCriasProgramadas();
                listaCriasNacidas=baseAuxiliar.getListaCriasNacidas();
                contador=0;
            }
        
        return seCargo;
    }

    public boolean cargarNuevosDatos(String nombre) {
        // Devuelve verdadero si la base se carga correctamente
        // Inicializa una nueva base de datos
        
        titulo=nombre;
        
        // Inicializa los atributos
        opciones=Opciones.cargarOpciones();
        listaEjemplares=new ListaEjemplar();
        listaContactos=new ListaPersona();
        listaCriasProgramadas=new ListaCria();
        listaCriasNacidas=new ListaCria();
        contador=0;
        
        // Genera la base de datos
        boolean seCargo=guardarDatos();
        
        return seCargo;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean guardarDatos() {
        // Devuelve verdadero si la base de datos se guarda correctamente
        boolean seGuardo=true;
        
        try {
            
            // Si no existe, genera el directorio de datos
            IO.crearDirectorios(DIRECTORIO_DATOS);
            
            // Genera el archivo serializado
            IO.serializarObjeto(this, DIRECTORIO_DATOS+"/"+titulo+"."+EXTENSION_DATOS);
            
        } catch(Exception exc) {
            // Hubo un problema y no se guardó la base de datos
            seGuardo=false;
        }
        
        // Reinicia el contador
        contador=0;
        
        return seGuardo;        
    }

    public boolean crearBackup(String nombreCopia, boolean guardar) {
        // Crea un backup de la base de datos actual
        // Antes de crear la copia de seguridad, guarda los datos
        if (guardar)
            guardarDatos();
        return crearBackup(titulo, nombreCopia);
    }

    public static boolean crearBackup(String nombreBase, String nombreCopia) {
        // Devuelve verdadero si la copia se crea bien
        // No verifica si existe un nombre igual en el directorio
        boolean seCreo=true;
        
        String rutaOrigen=DIRECTORIO_DATOS+"/"+nombreBase+"."+EXTENSION_DATOS;
        String rutaDestino=DIRECTORIO_BACKUPS+"/BACKUP."+nombreCopia+"."+EXTENSION_DATOS;
        
        try {
            IO.copiarArchivo(rutaOrigen, rutaDestino);
        } catch (Exception exc) {
            // Hubo un problema
            seCreo=false;
        }
        
        return seCreo;
    }

    public static boolean existeBackup(String nombre) {
        // Devuelve verdadero si la copia de seguridad representada por el nombre ya existe
        String ruta=DIRECTORIO_BACKUPS+"/BACKUP."+nombre+"."+EXTENSION_DATOS;
        
        return IO.existeArchivo(ruta);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public void eliminarReferenciaContacto(String nombre) {
        // Elimina, si existen, referencias del contacto cuyo nombre pasa como parámetro
        // Busca en los casos de Propietario y Criador
        int cantElementos=listaEjemplares.cantElementos();
        int i;
        Ejemplar elemento;
        Persona contacto;
        for (i=0; i<cantElementos; i++) {
            // Búsqueda lineal
            elemento=(Ejemplar)listaEjemplares.getElemento( i );
            
            // Propietario
            contacto=elemento.getPropietario();
            if (contacto != null)
                if ( contacto.getNombre().equals(nombre) )
                    elemento.setPropietario(null);
            
            // Criador
            contacto=elemento.getCriador();
            if (contacto != null)
                if ( contacto.getNombre().equals(nombre) )
                    elemento.setCriador(null);
        }
    }

    public void eliminarReferenciaMacho(String nombre) {
        // Elimina, si existen, referencias del ejemplar macho cuyo nombre pasa como parámetro
        // Busca en el caso de Padre
        int cantElementos, i;
        Perro padre;
        
        // Padre
        cantElementos=listaEjemplares.cantElementos();
        Perro elemento;
        for (i=0; i<cantElementos; i++) {
            // Búsqueda lineal
            elemento=(Perro)listaEjemplares.getElemento( i );
            
            padre=elemento.getPadre();
            if (padre != null)
                if ( padre.getNombre().equals(nombre) )
                    elemento.setPadre(null);
        }
        
        Cria cria;
        
        // Crías programadas
        cantElementos=listaCriasProgramadas.cantElementos();
        for (i=0; i<cantElementos; i++) {
            // Búsqueda lineal
            cria=listaCriasProgramadas.getElemento( i );
            
            padre=cria.getPadre();
            if (padre != null)
                if ( padre.getNombre().equals(nombre) )
                    cria.setPadre(null);
        }
        
        // Crias nacidas
        cantElementos=listaCriasNacidas.cantElementos();
        for (i=0; i<cantElementos; i++) {
            // Búsqueda lineal
            cria=listaCriasNacidas.getElemento( i );
            
            padre=cria.getPadre();
            if (padre != null)
                if ( padre.getNombre().equals(nombre) )
                    cria.setPadre(null);
        }
    }

    public void eliminarReferenciaHembra(String nombre) {
        // Elimina, si existen, referencias del ejemplar macho cuyo nombre pasa como parámetro
        // Busca en el caso de Madre
        int cantElementos, i;
        Perro madre;
        
        // Madre
        cantElementos=listaEjemplares.cantElementos();
        Perro elemento;
        for (i=0; i<cantElementos; i++) {
            // Búsqueda lineal
            elemento=(Perro)listaEjemplares.getElemento( i );
            
            madre=elemento.getMadre();
            if (madre != null)
                if ( madre.getNombre().equals(nombre) )
                    elemento.setMadre(null);
        }
        
        Cria cria;
        
        // Crías programadas
        cantElementos=listaCriasProgramadas.cantElementos();
        for (i=0; i<cantElementos; i++) {
            // Búsqueda lineal
            cria=listaCriasProgramadas.getElemento( i );
            
            madre=cria.getMadre();
            if (madre != null)
                if ( madre.getNombre().equals(nombre) )
                    cria.setMadre(null);
        }
        
        // Crias nacidas
        cantElementos=listaCriasNacidas.cantElementos();
        for (i=0; i<cantElementos; i++) {
            // Búsqueda lineal
            cria=listaCriasNacidas.getElemento( i );
            
            madre=cria.getMadre();
            if (madre != null)
                if ( madre.getNombre().equals(nombre) )
                    cria.setMadre(null);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public void declararNacimiento(int indiceProgramada, char letra, Fecha fecha, int cantM, int cantH) {
        // Convierte una CriaProgramda en CriaNacida, eliminándola de la lista original y agregándola en la otra
        CriaProgramada cria=(CriaProgramada) listaCriasProgramadas.getElemento(indiceProgramada);
        CriaNacida nuevaCria=cria.obtenerCriaNacida(letra, fecha, cantM, cantH);
        
        listaCriasNacidas.agregarElemento(nuevaCria);
        listaCriasProgramadas.eliminarElemento(indiceProgramada);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}