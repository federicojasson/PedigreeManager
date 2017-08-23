import java.io.Serializable;

public class ListaCria implements Serializable {

// Atributos
    private int cantElementos;
    private Cria [ ] elementos;

// Constructores
    public ListaCria() {
        final int LISTA_AUMENTO=Constantes.LISTA_AUMENTO;
        elementos=new Cria [ LISTA_AUMENTO ];
        cantElementos=0;
    }

// M�todos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public int cantElementos() {
        return cantElementos;
    }

    public Cria getElemento(int indice) {
        return elementos [ indice ];
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public void agregarElemento(Cria elemento) {
        
        // Verifica que haya espacio        
        if ( ! hayEspacio() )
            aumentarEspacio();
        
        // Agrega el elemento linealmente, no hay un orden establecido
        elementos [ cantElementos ] = elemento;
        cantElementos++;
    }

    public void eliminarElemento (int indice) {
        elementos [ indice ] = null;
        arrastrarElementos(indice);
        cantElementos--;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private boolean hayEspacio() {
        // Retorna verdadero si hay el arreglo no est� totalmente lleno
        return cantElementos<elementos.length;
    }

    private void aumentarEspacio() {
        // Genera un nuevo arreglo con m�s espacio y copia los datos al mismo
        final int LISTA_AUMENTO=Constantes.LISTA_AUMENTO;
        Cria [ ] nuevoArreglo=new Cria [ elementos.length + LISTA_AUMENTO ];
        int i;
        for (i=0; i<cantElementos; i++)
            nuevoArreglo [ i ] = elementos [ i ];
        
        // Se asigna el nuevo arreglo
        elementos=nuevoArreglo;
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