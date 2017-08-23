import java.io.Serializable;

public class Ejemplar extends Perro implements Serializable {

// Atributos
    private String nroRegistro;
    private String nroTatuaje;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public String getRegistro() {
        return nroRegistro;
    }

    public void setRegistro ( String r ) {
        nroRegistro=r;
    }

    public String getTatuaje() {
        return nroTatuaje;
    }

    public void setTatuaje ( String t ) {
        nroTatuaje=t;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}