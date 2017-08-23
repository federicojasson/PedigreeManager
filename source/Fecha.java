import java.io.Serializable;

public class Fecha implements Serializable {

// Atributos
    private int dia;
    private int mes;
    private int anio;

// Constructores
    public Fecha(int d, int m, int a) {
        dia=d;
        mes=m;
        anio=a;
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public String toString() {
        String cadena=new String();
        
        if ( anio!=-1 || mes!=-1 || dia!=-1 ) {
            
            String diaCadena, mesCadena, anioCadena;
            
            if (dia==-1)
                diaCadena=" -- ";
                else
                    if ( dia>=0 && dia<=9 )
                        diaCadena="0"+dia;
                        else
                        diaCadena=Integer.toString(dia);
            
            if (mes==-1)
                mesCadena=" -- ";
                else
                    if ( mes>=0 && mes<=9 )
                        mesCadena="0"+mes;
                        else
                        mesCadena=Integer.toString(mes);
            
            if (anio==-1)
                anioCadena=" -- ";
                else
                anioCadena=Integer.toString(anio);
            
            cadena=anioCadena+" / "+mesCadena+" / "+diaCadena;
        }
        
        return cadena;
    }

    public String toStringPalabras() {
        String cadena;
        
        if ( anio!=-1 || mes!=-1 || dia!=-1 ) {
            
            String diaCadena, mesCadena, anioCadena;
            
            if (dia != -1)
                if (dia >=0 && dia <= 9)
                    diaCadena="0"+dia;
                    else
                        diaCadena=Integer.toString(dia);
                else
                    diaCadena=new String();
            
            if (mes != -1)
                mesCadena=Constantes.MESES [ mes ];
                else
                    mesCadena=new String();
            
            if (anio != -1)
                anioCadena=Integer.toString(anio);
                else
                    anioCadena=new String();
            
            if ( ! diaCadena.isEmpty() && mesCadena.isEmpty() && anioCadena.isEmpty() )
                // Datos: día
                cadena="Día: "+diaCadena;
                else
                    if ( diaCadena.isEmpty() && ! mesCadena.isEmpty() && anioCadena.isEmpty() )
                        // Datos: mes
                        cadena="Mes: "+mesCadena;
                        else
                            if ( diaCadena.isEmpty() && mesCadena.isEmpty() && ! anioCadena.isEmpty() )
                                // Datos: año
                                cadena="Año: "+anioCadena;
                                else {
                                    // Hay al menos dos datos
                                    
                                    String separador=" de ";
                                    
                                    if ( ! diaCadena.isEmpty() && ! mesCadena.isEmpty() && ! anioCadena.isEmpty() )
                                        // Datos: día, mes, año
                                        cadena=diaCadena+separador+mesCadena+separador+anioCadena;
                                        else
                                            if ( diaCadena.isEmpty() )
                                                // Datos: mes, año
                                                cadena=mesCadena+separador+anioCadena;
                                                else
                                                    if ( mesCadena.isEmpty() )
                                                        // Datos: día, año
                                                        cadena="Algún "+diaCadena+separador+anioCadena;
                                                        else
                                                            // Datos: día, mes
                                                            cadena=diaCadena+separador+mesCadena;
		   }
            
        } else
            cadena="No determinada";
        
        return cadena;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}