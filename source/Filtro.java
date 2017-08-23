public class Filtro {


// Métodos varios
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static boolean esEntero(String cadena) {
        boolean esValido=true;
        int i=0;
        char caracter;
        if ( cadena.isEmpty() )
            esValido=false;
            else {
                int cantElementos=cadena.length();
                while ( esValido && i<cantElementos ) {
                    caracter=cadena.charAt( i );
                    esValido=caracter>='0' && caracter<='9';
                    i++;
                }
            }
        
        return esValido;
    }

    public static String [ ] arregloLleno(String [ ] arregloOrigen, int capacidad) {
        // Devuelve un arreglo con la capacidad indicada, llenándolo con los datos del arreglo
        // que pasa como parámetro
        // Asume que arregloOrigen tiene la cantidad suficiente de elementos y que no hay espacios
        // intermedios vacíos
        int i;
        String [ ] nuevoArreglo=new String [ capacidad ];
        for (i=0; i<capacidad; i++)
            nuevoArreglo [ i ] = arregloOrigen [ i ];
        
        return nuevoArreglo;
    }

    public static String devolverCeros(int numeroMax, int numero) {
        String cadenaCeros;
        int i, cantCeros, cantDigitosNumero;
        
        cantCeros=contarDigitos(numeroMax);
        cantDigitosNumero=contarDigitos(numero);
        
        cantCeros=cantCeros - cantDigitosNumero;
        
        cadenaCeros=new String();
        for (i=0; i<cantCeros; i++)
            cadenaCeros=cadenaCeros+"0";
        
        return cadenaCeros;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private static int contarDigitos(int numero) {
        int digitos=1;
        
        while (numero >= 10) {
            numero=numero / 10;
            digitos++;
        }
        
        return digitos;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------


// Métodos para archivos
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static boolean nombreValido(String nombre) {
        // Devuelve verdadero si el nombre es válido para un archivo.
        // Debe tener entre ( 0, LONGITUD_MAXIMA ] caracteres
        // y no contener los siguientes: | \ / : * ? " < >
        boolean esValida=true;
        
        if ( ! longAceptable(nombre) )
            esValida=false;
            else {
                int i=0;
                char caracter;
                while ( esValida && i<nombre.length() ) {
                    caracter=nombre.charAt( i );
                    esValida=caracterValido(caracter);
                    i++;
                }
            }
        
        return esValida;
    }

    private static boolean longAceptable(String cadena) {
        // Devuelve verdadero si la cadena tiene al menos un caracter y no más de LONGITUD_MAXIMA
        final int LONGITUD_MAXIMA=Constantes.NOMBRE_LONGITUD_MAXIMA;
        int longitud=cadena.length();
        return longitud > 0 && longitud <= LONGITUD_MAXIMA;
    }

    private static boolean caracterValido(char caracter) {
        // Devuelve verdadero si el caracter no es uno de los siguientes: | \ / : * ? " < >
        boolean esValido=true;
        
        switch (caracter) {
            case '|' : ;
            case '\\' : ;
            case '/' : ;
            case ':' : ;
            case '*' : ;
            case '?' : ;
            case '"' : ;
            case '<' : ;
            case '>' : esValido=false;
        }
        
        return esValido;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static String extension(String archivo) {
        // Devuelve la extensión, en minúsculas, del archivo
        // Devuelve un String vacío si el archivo no tiene extensión
        String extension=new String();
        int indice=archivo.lastIndexOf('.');
        int longitud=archivo.length();
        if ( indice != -1 && indice < longitud-1 )
            extension=archivo.substring(indice+1, longitud);
        
        return extension.toLowerCase();
    }

    public static String sinExtension(String archivo) {
        // Devuelve el nombre del archivo sin la extensión
        String nombre;
        int indice;
        
        indice=archivo.lastIndexOf('.');
        if ( indice != -1 )
            // Tiene extensión
            nombre=archivo.substring(0,indice);
            else
                // No tiene extensión
                nombre=archivo;
        
        return nombre;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}