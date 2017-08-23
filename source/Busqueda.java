public class Busqueda {

// Atributos
    private String refNombre;
    private String refNotas;
    
    private String refDomicilio;
    private String refTelefono;
    
    private boolean buscarMachos;
    private boolean buscarHembras;
    private int refDia;
    private int refMes;
    private int refAnio;
    private String refRegistro;
    private String refTatuaje;
    
    private Datos baseDatos;

// Constructores
    public Busqueda(Datos base) {
        baseDatos=base;
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public void setNombre(String nombre) {
        refNombre=nombre;
    }

    public void setNotas(String notas) {
        refNotas=notas;
    }

    public void setDomicilio(String domicilio) {
        refDomicilio=domicilio;
    }

    public void setTelefono(String telefono) {
        refTelefono=telefono;
    }

    public void setBuscarMachos(boolean machos) {
        buscarMachos=machos;
    }

    public void setBuscarHembras(boolean hembras) {
        buscarHembras=hembras;
    }

    public void setDia(int dia) {
        refDia=dia;
    }

    public void setMes(int mes) {
        refMes=mes;
    }

    public void setAnio(int anio) {
        refAnio=anio;
    }

    public void setRegistro(String registro) {
        refRegistro=registro;
    }

    public void setTatuaje(String tatuaje) {
        refTatuaje=tatuaje;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static ListaPerro buscarEjemplaresMachos(ListaPerro listaOrigen) {
        // Devuelve una lista de ejemplares machos
        ListaPerro lista=new ListaEjemplar();
        
        // Comienza la búsqueda
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Perro elemento;
        
        for(i=0; i<cantElementos; i++) {
            elemento=(Perro)listaOrigen.getElemento(i);
            
            if ( elemento.esMacho() )
                lista.agregarElementoLinealmente(elemento);
        }
        
        return lista;
    }

    public static ListaPerro buscarEjemplaresHembras(ListaPerro listaOrigen) {
        // Devuelve una lista de ejemplares hembras
        ListaPerro lista=new ListaEjemplar();
        
        // Comienza la búsqueda
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Perro elemento;
        
        for(i=0; i<cantElementos; i++) {
            elemento=(Perro)listaOrigen.getElemento(i);
            
            if ( elemento.esHembra() )
                lista.agregarElementoLinealmente(elemento);
        }
        
        return lista;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static ListaPerro buscarEjemplaresHijosDe(ListaPerro listaOrigen, Ejemplar ejemplar) {
        // Devuelve una lista de ejemplares encontrados
        ListaPerro lista=new ListaEjemplar();
        
        // Comienza la búsqueda
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Perro elemento;
        
        if ( ejemplar.esMacho() )
            // Es macho, busca en padres
            for (i=0; i<cantElementos; i++) {
                elemento=(Perro)listaOrigen.getElemento( i );
                
                if ( ejemplar == elemento.getPadre() )
                    lista.agregarElementoLinealmente(elemento);
            }
            else
                // Es hembra, busca en madres
                for (i=0; i<cantElementos; i++) {
                    elemento=(Perro)listaOrigen.getElemento( i );
                    
                    if ( ejemplar == elemento.getMadre() )
                        lista.agregarElementoLinealmente(elemento);
                }
        
        return lista;
    }

    public static ListaPerro buscarEjemplaresDe(ListaPerro listaOrigen, Persona contacto) {
        // Devuelve una lista de ejemplares encontrados
        ListaPerro lista=new ListaEjemplar();
        
        // Comienza la búsqueda
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Perro elemento;
        
        for (i=0; i<cantElementos; i++) {
            elemento=(Perro)listaOrigen.getElemento( i );
            
            if ( contacto == elemento.getPropietario() || contacto == elemento.getCriador() )
                lista.agregarElementoLinealmente(elemento);
        }
        
        return lista;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public ListaEjemplar buscarEjemplaresInclusivo() {
        // Tiene que coincidir algún campo
        // Devuelve una lista de ejemplares encontrados
        ListaEjemplar lista=new ListaEjemplar();
        
        // Filtra los atributos que se necesitan
        filtrarAtributosGenerales();
        filtrarAtributosEjemplares();
        
        // Comienza la búsqueda
        ListaEjemplar listaOrigen=baseDatos.getListaEjemplares();
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Ejemplar elemento;
        String texto;
        Fecha fecha;
        int nro;
        for (i=0; i<cantElementos; i++) {
            
            elemento=(Ejemplar)listaOrigen.getElemento( i );
            
            // Comienza el filtrado
            
            if ( buscarMachos && elemento.esMacho() || buscarHembras && elemento.esHembra() ) {
                
                texto=filtrarCaracteres( elemento.getNombre() );
                if ( ! refNombre.isEmpty() && ! texto.isEmpty() ) {
                    // Ninguno es vacío
                    if ( seContienen(refNombre,texto) )
                        // Hay coincidencia: nombre
                        lista.agregarElementoLinealmente(elemento);
                } else {
                    texto=filtrarCaracteres( elemento.getNotas() ).replace("\n", " ");
                    if ( ! refNotas.isEmpty() && ! texto.isEmpty() ) {
                        // Ninguno es vacío
                        if ( seContienen(refNotas,texto) )
                            // Hay coincidencia: notas
                            lista.agregarElementoLinealmente(elemento);
                    } else {
                        fecha=elemento.getFecha();
                        nro=fecha.getDia();
                        if ( refDia != -1 && nro != -1 ) {
                            if ( refDia == nro )
                                // Hay coincidencia: día
                                lista.agregarElementoLinealmente(elemento);
                        } else {
                            nro=fecha.getMes();
                            if ( refMes != -1 && nro != -1 ) {
                                if ( refMes == nro )
                                    // Hay coincidencia: mes
                                    lista.agregarElementoLinealmente(elemento);
                            } else {
                                nro=fecha.getAnio();
                                if ( refAnio != -1 && nro != -1 ) {
                                    if ( refAnio == nro )
                                        // Hay coincidencia: año
                                        lista.agregarElementoLinealmente(elemento);
                                } else {
                                    texto=filtrarCaracteres( elemento.getRegistro() );
                                    if ( ! refRegistro.isEmpty() && ! texto.isEmpty() ) {
                                        // Ninguno es vacío
                                        if ( seContienen(refRegistro,texto) )
                                            // Hay coincidencia: registro
                                            lista.agregarElementoLinealmente(elemento);
                                    } else {
                                        texto=filtrarCaracteres( elemento.getTatuaje() );
                                        if ( ! refTatuaje.isEmpty() && ! texto.isEmpty() ) {
                                            // Ninguno es vacío
                                            if ( seContienen(refTatuaje,texto) )
                                                // Hay coincidencia: tatuaje
                                                lista.agregarElementoLinealmente(elemento);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
            }
        }
        
        return lista;
    }

    public ListaEjemplar buscarEjemplaresExclusivo() {
        // Tiene que coincidir algún campo
        // Devuelve una lista de ejemplares encontrados
        ListaEjemplar lista=new ListaEjemplar();
        
        // Filtra los atributos que se necesitan
        filtrarAtributosGenerales();
        filtrarAtributosEjemplares();
        
        // Comienza la búsqueda
        ListaEjemplar listaOrigen=baseDatos.getListaEjemplares();
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Ejemplar elemento;
        String texto;
        Fecha fecha;
        int nro;
        for (i=0; i<cantElementos; i++) {
            
            elemento=(Ejemplar)listaOrigen.getElemento( i );
            
            // Comienza el filtrado
            
            if ( buscarMachos && elemento.esMacho() || buscarHembras && elemento.esHembra() ) {
                texto=filtrarCaracteres( elemento.getNombre() );
                if ( ! esExcluyente(refNombre,texto) ) {
                    texto=filtrarCaracteres( elemento.getNotas() ).replace("\n", " ");;
                    if ( ! esExcluyente(refNotas,texto) ) {
                        fecha=elemento.getFecha();
                        nro=fecha.getDia();
                        if ( ! esExcluyente(refDia,nro) ) {
                            nro=fecha.getMes();
                            if ( ! esExcluyente(refMes,nro) ) {
                                nro=fecha.getAnio();
                                if ( ! esExcluyente(refAnio,nro) ) {
                                    texto=elemento.getRegistro();
                                    if ( ! esExcluyente(refRegistro,texto) ) {
                                        texto=elemento.getTatuaje();
                                        if ( ! esExcluyente(refTatuaje,texto) )
                                            lista.agregarElementoLinealmente(elemento);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        }
        
        return lista;
    }

    public ListaPersona buscarContactosInclusivo() {
        // Tiene que coincidir algún campo
        // Devuelve una lista de contactos encontrados
        ListaPersona lista=new ListaPersona();
        
        // Filtra los atributos que se necesitan
        filtrarAtributosGenerales();
        filtrarAtributosContactos();
        
        // Comienza la búsqueda
        ListaPersona listaOrigen=baseDatos.getListaContactos();
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Persona elemento;
        String texto;
        for (i=0; i<cantElementos; i++) {
            
            elemento=(Persona)listaOrigen.getElemento( i );
            
            // Comienza el filtrado
            
            texto=filtrarCaracteres( elemento.getNombre() );
            if ( ! refNombre.isEmpty() && ! texto.isEmpty() ) {
                // Ninguno es vacío
                if ( seContienen(refNombre,texto) )
                    // Hay coincidencia: nombre
                    lista.agregarElementoLinealmente(elemento);
            } else {
                texto=filtrarCaracteres( elemento.getNotas() ).replace("\n", " ");;
                if ( ! refNotas.isEmpty() && ! texto.isEmpty() ) {
                    // Ninguno es vacío
                    if ( seContienen(refNotas,texto) )
                        // Hay coincidencia: notas
                        lista.agregarElementoLinealmente(elemento);
                } else {
                    texto=filtrarCaracteres( elemento.getDomicilio() );
                    if ( ! refDomicilio.isEmpty() && ! texto.isEmpty() ) {
                        // Ninguno es vacío
                        if ( seContienen(refDomicilio,texto) )
                            // Hay coincidencia: domicilio
                            lista.agregarElementoLinealmente(elemento);
                    } else {
                        texto=filtrarCaracteres( elemento.getTelefono() );
                        if ( ! refTelefono.isEmpty() && ! texto.isEmpty() ) {
                            // Ninguno es vacío
                            if ( seContienen(refTelefono,texto) )
                                // Hay coincidencia: teléfono
                                lista.agregarElementoLinealmente(elemento);
                        }
                    }
                }
            }
        }
        
        return lista;
    }

    public ListaPersona buscarContactosExclusivo() {
        // Tienen que coincidir todos los campos no vacíos
        // Devuelve una lista de contactos encontrados
        ListaPersona lista=new ListaPersona();
        
        // Filtra los atributos que se necesitan
        filtrarAtributosGenerales();
        filtrarAtributosContactos();
        
        // Comienza la búsqueda
        ListaPersona listaOrigen=baseDatos.getListaContactos();
        int cantElementos=listaOrigen.cantElementos();
        
        int i;
        Persona elemento;
        String texto;
        for (i=0; i<cantElementos; i++) {
            
            elemento=(Persona)listaOrigen.getElemento( i );
            
            // Comienza el filtrado
            
            texto=filtrarCaracteres( elemento.getNombre() );
            if ( ! esExcluyente(refNombre,texto) ) {
                texto=filtrarCaracteres( elemento.getNotas() ).replace("\n", " ");;
                if ( ! esExcluyente(refNotas,texto) ) {
                    texto=filtrarCaracteres( elemento.getDomicilio() );
                    if ( ! esExcluyente(refDomicilio,texto) ) {
                        texto=filtrarCaracteres( elemento.getTelefono() );
                        if ( ! esExcluyente(refTelefono,texto) )
                            // No hay datos excluyentes
                            lista.agregarElementoLinealmente(elemento);
                    }
                }
            }
        }
        
        return lista;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private boolean seContienen(String texto1, String texto2) {
        return texto1.contains(texto2) || texto2.contains(texto1);
    }

    private boolean esExcluyente(String textoRef, String texto) {
        boolean esExcluyente;
        
        if ( textoRef.isEmpty() )
            esExcluyente=false;
            else
                if ( texto.isEmpty() )
                    esExcluyente=true;
                    else
                        esExcluyente = ! seContienen(textoRef,texto);
        
        return esExcluyente;
    }

    private boolean esExcluyente(int nroRef, int nro) {
        boolean esExcluyente;
        
        if ( nroRef == -1 )
            esExcluyente=false;
            else
                if ( nro == -1 )
                    esExcluyente=true;
                    else
                        esExcluyente = nroRef != nro;
        
        return esExcluyente;
    }

    private void filtrarAtributosGenerales() {
        // Transforma los String en mayúsculas y reemplaza ciertos caracteres
        refNombre=filtrarCaracteres(refNombre);
        refNotas=filtrarCaracteres(refNotas);
    }

    private void filtrarAtributosEjemplares() {
        // Transforma los String en mayúsculas y reemplaza ciertos caracteres
        refRegistro=filtrarCaracteres(refRegistro);
        refTatuaje=filtrarCaracteres(refTatuaje);
    }

    private void filtrarAtributosContactos() {
        // Transforma los String en mayúsculas y reemplaza ciertos caracteres
        refDomicilio=filtrarCaracteres(refDomicilio);
        refTelefono=filtrarCaracteres(refTelefono);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private String filtrarCaracteres(String cadena) {
        String devuelveCadena=cadena.toUpperCase();
        
        char [ ] arregloCadena=devuelveCadena.toCharArray();
        
        int i, longitud;
        char caracter;
        
        longitud=arregloCadena.length;
        for (i=0; i<longitud; i++) {
            caracter = arregloCadena [ i ];
            
            switch (caracter) {
                case 'À' :
                case 'Á' : arregloCadena [ i ] = 'A'; break;
                case 'È' :
                case 'É' : arregloCadena [ i ] = 'E'; break;
                case 'Ì' :
                case 'Í' : arregloCadena [ i ] = 'I'; break;
                case 'Ò' :
                case 'Ó' : arregloCadena [ i ] = 'O'; break;
                case 'Ù' :
                case 'Ú' :
                case 'Ü' : arregloCadena [ i ] = 'U';
            }
        }
        
        devuelveCadena=new String(arregloCadena);
        return devuelveCadena;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}