import java.io.Serializable;

public abstract class Cria implements Serializable {

// Atributos
    private Perro padre;
    private Perro madre;
    private String notas;

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
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

    public String getNotas() {
        return notas;
    }

    public void setNotas(String n) {
        notas=n;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    
}