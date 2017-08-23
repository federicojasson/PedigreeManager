import java.io.Serializable;

public class ListaSer implements Serializable {

// Atributos
    private int cantElementos;
    private Ser [ ] elementos;

// Constructores
    public ListaSer() {
        final int LISTA_AUMENTO=Constantes.LISTA_AUMENTO;
        elementos=new Ser [ LISTA_AUMENTO ];
        cantElementos=0;
    }

// M�todos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public int cantElementos() {
        return cantElementos;
    }

    public Ser getElemento(int indice) {
        return elementos [ indice ];
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    protected void agregarElementoLinealmente(Ser elemento) {
        // Agrega un elemento en la �ltima posici�n
        // ATENCI�N: el m�todo no asegura que la lista permanezca ordenada
        
        // Verifica
        if ( ! hayEspacio() )
            aumentarEspacio();
        
        // Agrega el elemento
        elementos [ cantElementos ] = elemento;
        
        cantElementos++;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean agregarElemento(Ser elemento) {
        // Devuelve verdadero �nicamente si el elemento se agreg� correctamente
        
        // Verifica que haya espacio
        if ( ! hayEspacio() )
            aumentarEspacio();
        
        // Agrega el elemento
        boolean seAgrego=agregarElementoBinario(elemento);
        return seAgrego;
    }

    public void eliminarElemento(Ser elemento) {
        // Elimina, si existe, el Ser que pasa como par�metro
        eliminarElemento( elemento.getNombre() );
    }

    public void eliminarElemento(String nombre) {
        // Elimina, si existe, el elemento que tiene como nombre el String que pasa como par�metro
        int indice=buscarElementoBinario(nombre);
        if (indice != -1) {
            // Si existe, lo elimina
            arrastrarElementos(indice);
            cantElementos--;
        }
    }

    public Ser buscarElemento(String nombre) {
        // Devuelve el elemento que tiene como nombre el String que pasa como par�metro
        // Si no se encuentra, devuelve null
        Ser elemento;
        int indice=buscarElementoBinario(nombre);
        if (indice==-1)
            elemento=null;
            else
                elemento=elementos [ indice ];
        
        return elemento;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private boolean agregarElementoBinario(Ser elemento) {
        // Agrega un elemento, buscando su posici�n con una b�squeda binaria
        // Devuelve verdadero �nicamente si el elemento se agreg� correctamente
        // Asume que hay espacio para agregar el elemento
        boolean seAgrego;
        int indice=buscarPosicionBinario( elemento.getNombre() );
        if (indice==-1)
            // El elemento ya existe
            seAgrego=false;
            else {
                seAgrego=true;
                correrElementos(indice);
                elementos [ indice ] = elemento;
                cantElementos++;
            }
        
        return seAgrego;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private int buscarPosicionBinario(String nombre) {
        // Devuelve la posici�n que corresponder�a al elemento que se quiere agregar
        // Si el elemento ya est� agregado, devuelve -1
        int indice;
        if (cantElementos==0)
            indice=0;
            else
                indice=buscarPosicionRecursion(nombre, 0, cantElementos-1);
        
        return indice;
    }

    private int buscarPosicionRecursion(String nombre, int indiceInicial, int indiceFinal) {
        // Realiza una b�squeda de la posici�n que corresponde al elemento cuyo nombre
        // pasa como par�metro. Si el elemento ya existe, devuelve -1
        // Asume que hay al menos un elemento
        int indice;
        
        nombre=nombre.toUpperCase();
        
        String nombreComparar;
        
        if (indiceInicial==indiceFinal) {
            // Hay s�lo un elemento
            nombreComparar = elementos [ indiceInicial ].getNombre().toUpperCase();
            if ( nombreComparar.equals(nombre) )
                // El elemento ya existe
                indice=-1;
                else
                    if ( nombreComparar.compareTo(nombre) > 0 )
                        indice=indiceInicial;
                        else
                            indice=indiceInicial+1;
        } else {
            // Hay m�s de un elemento
            int indiceMedio=(indiceInicial+indiceFinal) / 2;
            nombreComparar = elementos [ indiceMedio ].getNombre().toUpperCase();
            if ( nombreComparar.equals(nombre) )
                // El elemento ya existe
                indice=-1;
                else
                    if ( nombreComparar.compareTo(nombre) > 0 )
                        indice=buscarPosicionRecursion(nombre, indiceInicial, indiceMedio);
                        else
                            indice=buscarPosicionRecursion(nombre, indiceMedio+1, indiceFinal);
        }
        
        return indice;
    }

    private int buscarElementoBinario(String nombre) {
        // Devuelve el �ndice correspondiente al elemento que tiene como nombre el String que pasa como
        // par�metro. Devuelve -1 si no lo encuentra
        int indice;
        if (cantElementos==0)
            // Si no hay elementos, no hace falta hacer una b�squeda
            indice=-1;
            else
                indice=buscarElementoRecursion(nombre, 0, cantElementos-1);
        
        return indice;
    }

    private int buscarElementoRecursion(String nombre, int indiceInicial, int indiceFinal) {
        // Realiza una b�squeda del elemento que tiene como nombre el String que pasa como par�metro.
        // Si no se encuentra, devuelve -1
        // Asume que hay al menos un elemento
        int indice;
        
        nombre=nombre.toUpperCase();
        
        if (indiceInicial==indiceFinal)
            // Hay s�lo un elemento
            if ( elementos [ indiceInicial ].getNombre().toUpperCase().equals(nombre) )
                indice=indiceInicial;
                else
                    // Si hay un s�lo elemento y no es el buscado, no hay elementos con el nombre buscado
                    indice=-1;
            else {
                int indiceMedio=(indiceInicial+indiceFinal) / 2;
                String nombreComparar = elementos [ indiceMedio ].getNombre().toUpperCase();
                if ( nombreComparar.equals(nombre) )
                    indice=indiceMedio;
                    else
                        if ( nombreComparar.compareTo(nombre) > 0 )
                            indice=buscarElementoRecursion(nombre, indiceInicial, indiceMedio-1);
                            else
                                indice=buscarElementoRecursion(nombre, indiceMedio+1, indiceFinal);
            }
        
        return indice;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private boolean hayEspacio() {
        // Retorna verdadero si hay el arreglo no est� totalmente lleno
        return cantElementos<elementos.length;
    }

    private void aumentarEspacio() {
        // Genera un nuevo arreglo con m�s espacio y copia los datos al mismo
        final int LISTA_AUMENTO=Constantes.LISTA_AUMENTO;
        Ser [ ] nuevoArreglo=new Ser [ elementos.length + LISTA_AUMENTO ];
        int i;
        for(i=0; i<cantElementos; i++)
            nuevoArreglo [ i ] = elementos [ i ];
        
        // Se asigna el nuevo arreglo
        elementos=nuevoArreglo;
    }

    private void correrElementos(int indice) {
        // Realiza un corrimiento para liberar la posici�n correspondiente al �ndice que pasa como par�metro
        // Asume que hay espacio para realizar la operaci�n
        int i;
        for (i=cantElementos-1; i>=indice; i--)
            elementos [ i + 1 ] = elementos [ i ];
        
        // Anula el espacio correspondiente al �ndice
        elementos [ indice ] = null;
    }

    private void arrastrarElementos(int indice) {
        // Realiza un corrimiento para llenar una posici�n vac�a
        // Asume que cantElementos  corresponde al primer �ndice posterior al �ltimo elemento
        int i;
        for (i=indice+1; i<cantElementos; i++)
            elementos [ i - 1 ] = elementos [ i ];
        
        elementos [ cantElementos-1 ] = null;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}