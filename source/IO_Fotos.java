import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class IO_Fotos {

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public static boolean guardarFotoEjemplar(String nombre, String rutaFoto) {
        // Devuelve verdadero si la foto se guarda correctamente
        
        boolean seGuardo=false;
        
        String extension=Filtro.extension(rutaFoto);
        
        if ( formatoValido(extension) ) {
            
            File archivoImagen=new File(rutaFoto);
            BufferedImage imagen=null;
            
            try {
                imagen=ImageIO.read(archivoImagen);
            } catch (Exception exc) {
                // Hubo un problema al cargar la foto
            }
            
            if (imagen != null) {
                // La foto existe y se leyó correctamente
                
                String rutaDestino;
                File archivoOut;
                
                final String DIRECTORIO_FOTO=Constantes.DIRECTORIO_FOTOS_EJEMPLARES;
                
                // Si el directorio no existe, lo crea
                IO.crearDirectorios(DIRECTORIO_FOTO);
                
                rutaDestino=DIRECTORIO_FOTO+"/"+nombre+"."+extension;
                archivoOut=new File(rutaDestino);
                
                try {
                    seGuardo=ImageIO.write(imagen, extension, archivoOut);
                } catch(Exception exc) {
                    // Hubo un problema al guardar la foto
                }
                
                if (seGuardo) {
                    // Si la foto se guardó, crea la foto miniatura
                    final String DIRECTORIO_MINIATURA=Constantes.DIRECTORIO_FOTOS_MINIATURA_EJEMPLARES;
                    final int FOTO_SIZE=Constantes.FOTO_MINIATURA_SIZE;
                    
                    imagen=redimensionar(imagen, FOTO_SIZE);
                    
                    // Si el directorio no existe, lo crea
                    IO.crearDirectorios(DIRECTORIO_MINIATURA);
                    
                    rutaDestino=DIRECTORIO_MINIATURA+"/"+nombre+"."+extension;
                    archivoOut=new File(rutaDestino);
                    
                    try {
                        ImageIO.write(imagen, extension, archivoOut);
                    } catch(Exception exc) {
                        // Hubo un problema al guardar la foto miniatura
                    }
                }
            }
        }
        
        return seGuardo;
    }

    public static boolean guardarFotoContacto(String nombre, String rutaFoto) {
        // Devuelve verdadero si la foto se guarda correctamente
        
        boolean seGuardo=false;
        
        String extension=Filtro.extension(rutaFoto);
        
        if ( formatoValido(extension) ) {
            
            File archivoImagen=new File(rutaFoto);
            BufferedImage imagen=null;
            
            try {
                imagen=ImageIO.read(archivoImagen);
            } catch (Exception exc) {
                // Hubo un problema al cargar la foto
            }
            
            if (imagen != null) {
                // La foto existe y se leyó correctamente
                
                String rutaDestino;
                File archivoOut;
                
                final String DIRECTORIO_FOTO=Constantes.DIRECTORIO_FOTOS_CONTACTOS;
                
                // Si el directorio no existe, lo crea
                IO.crearDirectorios(DIRECTORIO_FOTO);
                
                rutaDestino=DIRECTORIO_FOTO+"/"+nombre+"."+extension;
                archivoOut=new File(rutaDestino);
                
                try {
                    seGuardo=ImageIO.write(imagen, extension, archivoOut);
                } catch(Exception exc) {
                    // Hubo un problema al guardar la foto
                }
                
                if (seGuardo) {
                    // Si la foto se guardó, crea la foto miniatura
                    final String DIRECTORIO_MINIATURA=Constantes.DIRECTORIO_FOTOS_MINIATURA_CONTACTOS;
                    final int FOTO_SIZE=Constantes.FOTO_MINIATURA_SIZE;
                    
                    imagen=redimensionar(imagen, FOTO_SIZE);
                    
                    // Si el directorio no existe, lo crea
                    IO.crearDirectorios(DIRECTORIO_MINIATURA);
                    
                    rutaDestino=DIRECTORIO_MINIATURA+"/"+nombre+"."+extension;
                    archivoOut=new File(rutaDestino);
                    
                    try {
                        ImageIO.write(imagen, extension, archivoOut);
                    } catch(Exception exc) {
                        // Hubo un problema al guardar la foto miniatura
                    }
                }
            }
        }
        
        return seGuardo;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static void eliminarFotoEjemplar(String nombre) {
        // Elimina la foto normal y la miniatura
        final String [ ] EXTENSIONES=Constantes.EXTENSIONES_IMAGENES;
        String ruta, extension;
        int i;
        
        int cantExtensiones=EXTENSIONES.length;
        
        final String DIRECTORIO_FOTO=Constantes.DIRECTORIO_FOTOS_EJEMPLARES;
        
        if ( IO.existeArchivo(DIRECTORIO_FOTO) )
            for (i=0; i<cantExtensiones; i++) {
                extension=EXTENSIONES [ i ];
                ruta=DIRECTORIO_FOTO+"/"+nombre+"."+extension;
                IO.eliminarArchivo(ruta);
            }
        
        final String DIRECTORIO_MINIATURA=Constantes.DIRECTORIO_FOTOS_MINIATURA_EJEMPLARES;
        
        if ( IO.existeArchivo(DIRECTORIO_MINIATURA) )
            for (i=0; i<cantExtensiones; i++) {
                extension=EXTENSIONES [ i ];
                ruta=DIRECTORIO_MINIATURA+"/"+nombre+"."+extension;
                IO.eliminarArchivo(ruta);
            }
    }

    public static void eliminarFotoContacto(String nombre) {
        // Elimina la foto normal y la miniatura
        final String [ ] EXTENSIONES=Constantes.EXTENSIONES_IMAGENES;
        String ruta, extension;
        int i;
        
        int cantExtensiones=EXTENSIONES.length;
        
        final String DIRECTORIO_FOTO=Constantes.DIRECTORIO_FOTOS_CONTACTOS;
        
        if ( IO.existeArchivo(DIRECTORIO_FOTO) )
            for (i=0; i<cantExtensiones; i++) {
                extension=EXTENSIONES [ i ];
                ruta=DIRECTORIO_FOTO+"/"+nombre+"."+extension;
                IO.eliminarArchivo(ruta);
            }
        
        final String DIRECTORIO_MINIATURA=Constantes.DIRECTORIO_FOTOS_MINIATURA_CONTACTOS;
        
        if ( IO.existeArchivo(DIRECTORIO_MINIATURA) )
            for (i=0; i<cantExtensiones; i++) {
                extension=EXTENSIONES [ i ];
                ruta=DIRECTORIO_MINIATURA+"/"+nombre+"."+extension;
                IO.eliminarArchivo(ruta);
            }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static BufferedImage cargarFotoEjemplar(String nombre) {
        // Devuelve null si no se encuentra la foto
        
        final String DIRECTORIO=Constantes.DIRECTORIO_FOTOS_EJEMPLARES;
        String extension=buscarExtension(DIRECTORIO, nombre);
        
        BufferedImage imagen=null;
        
        if (extension != null) {
            String ruta=DIRECTORIO+"/"+nombre+"."+extension;
            imagen=cargarFoto(ruta);
        }
        
        return imagen;
    }

    public static BufferedImage cargarFotoMiniaturaEjemplar(String nombre) {
        // Devuelve null si no se encuentra la foto
        
        final String DIRECTORIO=Constantes.DIRECTORIO_FOTOS_MINIATURA_EJEMPLARES;
        
        String ruta, extension;
        
        extension=buscarExtension(DIRECTORIO, nombre);
        BufferedImage imagen=null;
        
        if (extension != null) {
            ruta=DIRECTORIO+"/"+nombre+"."+extension;
            imagen=cargarFoto(ruta);
        } else {
            // Si la foto miniatura no existe, la crea
            imagen=cargarFotoEjemplar(nombre);
            if (imagen != null) {
                
                final String DIRECTORIO_FOTO=Constantes.DIRECTORIO_FOTOS_EJEMPLARES;
                final int FOTO_SIZE=Constantes.FOTO_MINIATURA_SIZE;
                
                imagen=redimensionar(imagen, FOTO_SIZE);
                
                // Si el directorio no existe, lo crea
                IO.crearDirectorios(DIRECTORIO);
                
                extension=buscarExtension(DIRECTORIO_FOTO, nombre);
                ruta=DIRECTORIO+"/"+nombre+"."+extension;
                
                File archivoOut=new File(ruta);
                
                try {
                    ImageIO.write(imagen, extension, archivoOut);
                } catch(Exception exc) {
                    // Hubo un problema al guardar la foto miniatura
                }
                
            }
        }
        
        return imagen;
    }

    public static BufferedImage cargarFotoContacto(String nombre) {
        // Devuelve null si no se encuentra la foto
        
        final String DIRECTORIO=Constantes.DIRECTORIO_FOTOS_CONTACTOS;
        String extension=buscarExtension(DIRECTORIO, nombre);
        
        BufferedImage imagen=null;
        
        if (extension != null) {
            String ruta=DIRECTORIO+"/"+nombre+"."+extension;
            imagen=cargarFoto(ruta);
        }
        
        return imagen;
    }

    public static BufferedImage cargarFotoMiniaturaContacto(String nombre) {
        // Devuelve null si no se encuentra la foto
        
        final String DIRECTORIO=Constantes.DIRECTORIO_FOTOS_MINIATURA_CONTACTOS;
        
        String ruta, extension;
        
        extension=buscarExtension(DIRECTORIO, nombre);
        
        BufferedImage imagen=null;
        
        if (extension != null) {
            ruta=DIRECTORIO+"/"+nombre+"."+extension;
            imagen=cargarFoto(ruta);
        } else {
            // Si la foto miniatura no existe, la crea
            imagen=cargarFotoContacto(nombre);
            if (imagen != null) {
                
                final String DIRECTORIO_FOTO=Constantes.DIRECTORIO_FOTOS_CONTACTOS;
                final int FOTO_SIZE=Constantes.FOTO_MINIATURA_SIZE;
                
                imagen=redimensionar(imagen, FOTO_SIZE);
                
                // Si el directorio no existe, lo crea
                IO.crearDirectorios(DIRECTORIO);
                
                extension=buscarExtension(DIRECTORIO_FOTO, nombre);
                ruta=DIRECTORIO+"/"+nombre+"."+extension;
                
                File archivoOut=new File(ruta);
                
                try {
                    ImageIO.write(imagen, extension, archivoOut);
                } catch(Exception exc) {
                    // Hubo un problema al guardar la foto miniatura
                }
                
            }
        }
        
        return imagen;
    }

    public static BufferedImage cargarFoto(String ruta) {
        // Devuelve null si no se encuentra la foto
        
        BufferedImage imagen=null;
        
        try {
            File archivoImagen=new File(ruta);
            imagen=ImageIO.read(archivoImagen);
        } catch(Exception exc) {
            // Hubo un problema al cargar la foto
        }
        
        return imagen;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static BufferedImage redimensionar(BufferedImage imagen, int ladoMaximo) {
    
        final int LADO_INICIAL=Constantes.FOTO_MAXIMO_REDIMENSION;
        
        BufferedImage nuevaImagen;
        
        if (ladoMaximo >= LADO_INICIAL)
            nuevaImagen=redimensionarRapido(imagen, ladoMaximo);
            else {
                nuevaImagen=redimensionarRapido(imagen, LADO_INICIAL);
                nuevaImagen=redimensionarCalidad(nuevaImagen, ladoMaximo);
            }
        
        return nuevaImagen;
    }

    public static BufferedImage redimensionar(BufferedImage imagen, int ladoMaximo, int lado) {
        
        final int LADO_INICIAL=Constantes.FOTO_MAXIMO_REDIMENSION;
        
        BufferedImage nuevaImagen;
        
        if (ladoMaximo >= LADO_INICIAL)
            nuevaImagen=redimensionarRapido(imagen, ladoMaximo, lado);
            else {
                nuevaImagen=redimensionarRapido(imagen, LADO_INICIAL, lado);
                nuevaImagen=redimensionarCalidad(nuevaImagen, ladoMaximo, lado);
            }
        
        return nuevaImagen;
    }

    private static BufferedImage redimensionarRapido(BufferedImage imagen, int ladoMaximo) {
        
        int ancho, alto;
        
        ancho=imagen.getWidth();
        alto=imagen.getHeight();
        
        if (ancho > alto) {
            alto=alto * ladoMaximo / ancho;
            ancho=ladoMaximo;
        } else {
            ancho=ancho * ladoMaximo / alto;
            alto=ladoMaximo;
        }
        
        BufferedImage nuevaImagen=new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D grafico=nuevaImagen.createGraphics();
            grafico.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            grafico.drawImage(imagen, 0, 0, ancho, alto, null);
            grafico.dispose();
        
        return nuevaImagen;
    }

    private static BufferedImage redimensionarRapido(BufferedImage imagen, int ladoMaximo, int lado) {
        
        final int LADO_ANCHO=Constantes.FOTO_REDIMENSIONAR_ANCHO;
        
        int ancho, alto;
        
        ancho=imagen.getWidth();
        alto=imagen.getHeight();
        
        if (lado == LADO_ANCHO) {
            if (ancho > ladoMaximo) {
                alto=alto * ladoMaximo / ancho;
                ancho=ladoMaximo;
            }
        } else {
            if (alto > ladoMaximo) {
                ancho=ancho * ladoMaximo / alto;
                alto=ladoMaximo;
            }
        }
        
        BufferedImage nuevaImagen=new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D grafico=nuevaImagen.createGraphics();
            grafico.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            grafico.drawImage(imagen, 0, 0, ancho, alto, null);
            grafico.dispose();
        
        return nuevaImagen;
    }

    private static BufferedImage redimensionarCalidad(BufferedImage imagen, int ladoMaximo) {
        
        int anchoActual, anchoBuscado, altoActual, altoBuscado;
        
        anchoActual=imagen.getWidth();
        altoActual=imagen.getHeight();
        
        if (anchoActual > altoActual) {
            altoBuscado=altoActual * ladoMaximo / anchoActual;
            anchoBuscado=ladoMaximo;
        } else {
            anchoBuscado=anchoActual * ladoMaximo / altoActual;
            altoBuscado=ladoMaximo;
        }
        
        BufferedImage nuevaImagen, temp;
        
        nuevaImagen=imagen;
        
        Graphics2D grafico;
        
        do {
            
            if (anchoActual > anchoBuscado) {
                anchoActual=anchoActual / 2;
                if (anchoActual < anchoBuscado) {
                    anchoActual=anchoBuscado;
                }
            }
            
            if (altoActual > altoBuscado) {
                altoActual = altoActual / 2;
                if (altoActual < altoBuscado) {
                    altoActual = altoBuscado;
                }
            }
            
            temp=new BufferedImage(anchoActual, altoActual, BufferedImage.TYPE_INT_RGB);
            grafico=temp.createGraphics();
                grafico.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                grafico.drawImage(nuevaImagen, 0, 0, anchoActual, altoActual, null);
                grafico.dispose();
            
            nuevaImagen=temp;
            
        } while (anchoActual != anchoBuscado || altoActual != altoBuscado);
        
        return nuevaImagen;
    }

    private static BufferedImage redimensionarCalidad(BufferedImage imagen, int ladoMaximo, int lado) {
        
        final int LADO_ANCHO=Constantes.FOTO_REDIMENSIONAR_ANCHO;
        
        int anchoActual, anchoBuscado, altoActual, altoBuscado;
        
        anchoActual=anchoBuscado=imagen.getWidth();
        altoActual=altoBuscado=imagen.getHeight();
        
        if (lado == LADO_ANCHO) {
            if (anchoActual > ladoMaximo) {
                altoBuscado=altoActual * ladoMaximo / anchoActual;
                anchoBuscado=ladoMaximo;
            }
        } else {
            if (altoActual > ladoMaximo) {
                anchoBuscado=anchoActual * ladoMaximo / altoActual;
                altoBuscado=ladoMaximo;
            }
        }
        
        BufferedImage nuevaImagen, temp;
        
        nuevaImagen=imagen;
        
        Graphics2D grafico;
        
        do {
            
            if (anchoActual > anchoBuscado) {
                anchoActual=anchoActual / 2;
                if (anchoActual < anchoBuscado) {
                    anchoActual=anchoBuscado;
                }
            }
            
            if (altoActual > altoBuscado) {
                altoActual = altoActual / 2;
                if (altoActual < altoBuscado) {
                    altoActual = altoBuscado;
                }
            }
            
            temp=new BufferedImage(anchoActual, altoActual, BufferedImage.TYPE_INT_RGB);
            grafico=temp.createGraphics();
                grafico.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                grafico.drawImage(nuevaImagen, 0, 0, anchoActual, altoActual, null);
                grafico.dispose();
            
            nuevaImagen=temp;
            
        } while (anchoActual != anchoBuscado || altoActual != altoBuscado);
        
        return nuevaImagen;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    private static String buscarExtension(String directorio, String nombre) {
        // Busca, si existe, la extensión de la foto del directorio que pasa como parámetro
        // Orden de prioridad: jpg, jpeg, gif
        // Si no existe, devuelve null
        final String [ ] EXTENSIONES=Constantes.EXTENSIONES_IMAGENES;
        
        String extension=null;
        String ruta;
        
        int length=EXTENSIONES.length;
        int i=0;
        boolean seEncontro=false;
        while ( ! seEncontro && i<length ) {
            ruta=directorio+"/"+nombre+"."+EXTENSIONES [ i ];
            seEncontro=IO.existeArchivo(ruta);
            i++;
        }
        
        if (seEncontro)
            extension=EXTENSIONES [ i - 1 ];
        
        return extension;
    }

    private static boolean formatoValido(String extension) {
        // Devuelve verdadero si la extensión es válida
        // La extensión es válida si está en EXTENSIONES
        final String [ ] EXTENSIONES=Constantes.EXTENSIONES_IMAGENES;
        
        boolean esValido=false;
        int i=0;
        while ( ! esValido && i<EXTENSIONES.length )
            esValido=EXTENSIONES [ i ] . equals(extension);
        
        return esValido;
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}