import javax.swing.table.AbstractTableModel;

public class ModeloTabla extends AbstractTableModel {

// Atributos
    private Object [ ] [ ] datos;
    private String [ ] columnas;

// Constructores
    public ModeloTabla (Object [ ] [ ] d, String [ ] c) {
        datos=d;
        columnas=c;
    }

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
   public boolean isCellEditable (int row, int column) {
       return false;
    }

    public String getColumnName (int indice) {
        return columnas [ indice ];
    }

    public Object getValueAt(int fila, int columna) {
        return datos [ fila ] [ columna ];
    }

    public int getColumnCount() {
        return columnas.length;
    }

    public int getRowCount() {
        return datos.length;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}