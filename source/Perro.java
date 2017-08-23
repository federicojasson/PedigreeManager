import java.io.Serializable;

public abstract class Perro extends Ser implements Serializable {

// Atributos
    private Fecha fechaNac;
    private char sexo;
    private Perro padre;
    private Perro madre;
    private Persona propietario;
    private Persona criador;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public Fecha getFecha() {
        return fechaNac;
    }

    public void setFecha ( Fecha f ) {
        fechaNac=f;
    }

    public void setSexo(char c) {
        sexo=c;
    }

    public Perro getPadre() {
        return padre;
    }

    public void setPadre(Perro p) {
        padre=p;
    }

    public Perro getMadre() {
        return madre;
    }

    public void setMadre(Perro m) {
        madre=m;
    }

    public Persona getPropietario() {
        return propietario;
    }

    public void setPropietario(Persona p) {
        propietario=p;
    }

    public Persona getCriador() {
        return criador;
    }

    public void setCriador (Persona c) {
        criador=c;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public boolean esMacho() {
        final int MACHO=Constantes.SEXO_MACHO;
        return sexo==MACHO;
    }

    public boolean esHembra() {
        final int HEMBRA=Constantes.SEXO_HEMBRA;
        return sexo==HEMBRA;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}