import java.io.Serializable;

public class ListaEjemplar extends ListaPerro implements Serializable {

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
/**    public ListaEjemplar buscarPorRegistro(String registro) {
        int cantElementos=cantElementos();
        
        // Genera una lista auxiliar con la misma capacidad
        ListaEjemplar listaDevolver=new ListaEjemplar(cantElementos);
        
        // Como la lista está ordenada, se agregan linealmente
        // a medida que se encuentran correspondencias
        int i;
        Ejemplar elemento;
        String registroComparar;
        for (i=0; i<cantElementos; i++) {
            elemento=(Ejemplar)getElemento(i);
            registroComparar=elemento.getRegistro();
            if ( registroComparar.contains(registro) )
                // Contiene parte o todo el registro que pasa como parámetro
                listaDevolver.setElemento(elemento);
        }
        
        return listaDevolver;
    }

    public ListaEjemplar buscarPorTatuaje(String tatuaje) {
        int cantElementos=cantElementos();
        
        // Genera una lista auxiliar con la misma capacidad
        ListaEjemplar listaDevolver=new ListaEjemplar(cantElementos);
        
        // Como la lista está ordenada, se agregan linealmente
        // a medida que se encuentran correspondencias
        int i;
        Ejemplar elemento;
        String tatuajeComparar;
        for (i=0; i<cantElementos; i++) {
            elemento=(Ejemplar)getElemento(i);
            tatuajeComparar=elemento.getTatuaje();
            if ( tatuajeComparar.contains(tatuaje) )
                // Contiene parte o todo el registro que pasa como parámetro
                listaDevolver.setElemento(elemento);
        }
        
        return listaDevolver;
    }*/
//----------------------------------------------------------------------------------------------------------------------------------------------

}