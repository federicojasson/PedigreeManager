import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class IO {

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public static Object obtenerObjetoSerializado(String ruta) {
        // Retorna null si hay problemas
        
        FileInputStream inStream;
        ObjectInputStream inFile=null;
        
        Object objeto=null;
        
        try {
            // Se intenta cargar el objeto
            
            inStream=new FileInputStream(ruta);
            inFile=new ObjectInputStream(inStream);
            
            // Genera el objeto
            objeto=inFile.readObject();
            
        } catch (Exception exc) {
            // Hubo un problema al cargar el objeto
        }
        
        try {
            // Si el stream permaneció abierto, se intenta cerrar
            
            if (inFile != null)
                inFile.close();
            
        } catch (Exception exc) {
            // Hubo un problema al cerrar el stream
        }
        
        return objeto;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static void serializarObjeto(Object archivo, String ruta) throws Exception {
        FileOutputStream outStream=new FileOutputStream(ruta);
        ObjectOutputStream outFile=new ObjectOutputStream(outStream);
        
        // Crea el archivo
        outFile.writeObject(archivo);
        
        // Finaliza
        outFile.close();
    }

    public static void copiarArchivo(String rutaOrigen, String rutaDestino) throws Exception {
        FileInputStream inStream=new FileInputStream(rutaOrigen);
        FileOutputStream outStream=new FileOutputStream(rutaDestino);
        
        // Copia la información
        int byteInfo=inStream.read();
        
        while (byteInfo != -1) {
            outStream.write(byteInfo);
            byteInfo=inStream.read();
        }
        
        // Finaliza
        inStream.close();
        outStream.close();
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static String leerArchivoTexto(String ruta) {
        // Devuelve un String con los contenidos del archivo de texto
        // Si no es posible leer el texto, devuelve null
        
        FileReader inStream;
        BufferedReader inFile=null;
        
        String texto=null;
        
        try {
            inStream=new FileReader(ruta);
            inFile=new BufferedReader(inStream);
            
            texto=inFile.readLine();
            if (texto != null) {
                String linea=inFile.readLine();
                while (linea != null) {
                    texto=texto+"\n"+linea;
                    linea=inFile.readLine();
                }
            }
            
        } catch(Exception exc) {
            // Hubo un problema
        }
        
        try {
            // Si el stream permaneció abierto, se intenta cerrar
            
            if (inFile != null)
                inFile.close();
            
        } catch (Exception exc) {
            // Hubo un problema al cerrar el stream
        }
        
        return texto;
    }

    public static void editarArchivoTexto(String ruta, String texto) {
        // Edita o crea un archivo de texto
        
        FileOutputStream outStream;
        PrintWriter outFile=null;
        
        try {
            outStream=new FileOutputStream (ruta);
            outFile=new PrintWriter(outStream);
            
            // Escribe el texto
            outFile.print(texto);
            
        } catch (Exception exc) {
            // Hubo un problema
        }
        
        try {
            // Si el stream permaneció abierto, se intenta cerrar
            
            if (outFile != null)
                outFile.close();
            
        } catch (Exception exc) {
            // Hubo un problema al cerrar el stream
        }
        
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static String [ ] obtenerArchivos (String directorioOrigen, String extensionFiltro) {
        // Devuelve un arreglo conteniendo los nombres de los archivos del directorio de origen
        // cuya extensión coincida con el filtro
        // Devuelve null si no hay archivos que cumplan con lo anterior
        
        String [ ] archivosValidos=null;
        String [ ] archivos=null;
        
        try {
            File directorio=new File(directorioOrigen);
            archivos=directorio.list();
        } catch (Exception exc) {
            // Hubo un problema
        }
        
        int cantArchivos=archivos.length;
        if (archivos != null && cantArchivos > 0) {
            // Si se encontraron archivos verifica su extensión
            int contador=0;
            
            archivosValidos=new String [ cantArchivos ];
            
            int i;
            String archivo, extension;
            for (i=0; i<cantArchivos; i++) {
                archivo=archivos [ i ];
                extension=Filtro.extension(archivo);
                if (extension.equals(extensionFiltro)) {
                    archivosValidos [ contador ] = archivo;
                    contador++;
                }
            }
            
            if (contador < cantArchivos)
                if (contador == 0)
                    // Si no se agregaron datos, el arreglo se hace nulo
                    archivosValidos=null;
                    else
                        // Para que el arreglo quede sin espacios vacíos, se lo vuelve a crear con la capacidad justa
                        archivosValidos=Filtro.arregloLleno(archivosValidos, contador);
        }
        
        return archivosValidos;
    }

    public static boolean renombrarArchivo(String rutaOrigen, String rutaDestino) {
        boolean seRenombro=false;
        
        try {
            // Si el archivo existe, lo elimina
            eliminarArchivo(rutaDestino);
            seRenombro=renombrar(rutaOrigen, rutaDestino);
        } catch (Exception exc) {
            // Hubo un problema
            seRenombro=false;
        }
        
        return seRenombro;
    }

    public static boolean renombrar(String origen, String destino) {
        File archivoOrigen=new File(origen);
        File archivoDestino=new File(destino);
        return archivoOrigen.renameTo(archivoDestino);
    }

    public static boolean eliminarArchivo(String ruta) {
        // Devuelve verdadero si el archivo se elimina correctamente
        boolean seElimino=true;
        
        try {
            File archivo=new File(ruta);
            seElimino=archivo.delete();
        } catch (Exception exc) {
            // Hubo un problema
            seElimino=false;
        }
        
        return seElimino;
    }

    public static void crearDirectorios(String ruta) {
        try {
            File archivo=new File(ruta);
            archivo.mkdirs();
        } catch (Exception exc) {
            // Hubo un problema
        }
    }

    public static boolean existeArchivo(String ruta) {
        // Devuelve verdadero si el archivo existe
        boolean existe=true;
        
        try {
            File archivo=new File(ruta);
            existe=archivo.exists();
        } catch (Exception exc) {
            // Hubo un problema
            existe=false;
        }
        
        return existe;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}