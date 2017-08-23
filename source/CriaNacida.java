import java.io.Serializable;

public class CriaNacida extends Cria implements Serializable {

// Atributos
    private Fecha fechaNac;
    private char letra;
    private int cantMachos;
    private int cantHembras;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public Fecha getFecha() {
        return fechaNac;
    }

    public void setFecha(Fecha f) {
        fechaNac=f;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char l) {
        letra=l;
    }

    public int getCantMachos() {
        return cantMachos;
    }

    public void setCantMachos(int cant) {
        cantMachos=cant;
    }

    public int getCantHembras() {
        return cantHembras;
    }

    public void setCantHembras(int cant) {
        cantHembras=cant;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}