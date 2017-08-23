import java.io.Serializable;

public class Persona extends Ser implements Serializable {

// Atributos
    private String domicilio;
    private String telefono;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String d) {
        domicilio=d;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String t) {
        telefono=t;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}