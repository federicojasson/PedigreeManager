import java.io.Serializable;

public abstract class Ser implements Serializable {

// Atributos
    private String nombre;
    private String notas;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre=n;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String n) {
        notas=n;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}