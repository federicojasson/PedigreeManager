import java.io.Serializable;

public class CriaProgramada extends Cria implements Serializable {

// M�todos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public CriaNacida obtenerCriaNacida(char letra, Fecha fecha, int cantM, int cantH) {
        // Devuelve una CriaNacida con los datos de la CriaProgramada
        // y los datos que pasan como par�metro
        
        CriaNacida nuevaCria=new CriaNacida();
        nuevaCria.setPadre(getPadre());
        nuevaCria.setMadre(getMadre());
        nuevaCria.setNotas(getNotas());
        nuevaCria.setLetra(letra);
        nuevaCria.setFecha(fecha);
        nuevaCria.setCantMachos(cantM);
        nuevaCria.setCantHembras(cantH);
        
        return nuevaCria;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}