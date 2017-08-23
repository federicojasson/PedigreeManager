import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Constantes {


// Constantes varias
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static Toolkit TOOLKIT=Toolkit.getDefaultToolkit();
    public static Clipboard PORTAPAPELES=TOOLKIT.getSystemClipboard();
    
    public static final char SEXO_MACHO='M';
    public static final char SEXO_HEMBRA='F';
    
    public static int LISTA_AUMENTO=40;
    
    public static String [ ] TABLA_COLUMNAS_PERSONA= { "Nombre","Domicilio","Teléfono" };
    public static String [ ] TABLA_COLUMNAS_EJEMPLAR= { "Nombre","Fecha(A/M/D)","Sexo","Criador", "Propietario" };
    public static String [ ] TABLA_COLUMNAS_EJEMPLAR_ACORTADA= { "Nombre","Fecha(A/M/D)","Sexo" };
    public static String [ ] TABLA_COLUMNAS_PROGRAMADA= { "Nº", "Padre", "Madre" };
    public static String [ ] TABLA_COLUMNAS_NACIDA= { "Nº", "Padre", "Madre", "Letra", "Nacimiento", "Machos", "Hembras" };
    
    public static String [ ] MESES= { "Se desconoce", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
    public static String [ ] ABECEDARIO() {
        String [ ] lista=new String [ 27 ];
        lista [ 0 ] = " -- ";
        int i;
        for (i=1; i<27; i++)
            lista [ i ] = ""+(char)(i+64);
        
        return lista;
    }
    
    public static int FOTO_REDIMENSIONAR_ANCHO=0;
    public static int FOTO_REDIMENSIONAR_ALTO=1;
    
    public static int BUSQUEDA_CANTIDAD_ELEMENTOS=10;
    
    public static int TIPO_EJEMPLAR_MACHO=0;
    public static int TIPO_EJEMPLAR_HEMBRA=1;
    public static int TIPO_CONTACTO=2;
    
    public static int IMAGEN_DIFERENCIA_ANCHO=16;
    public static int IMAGEN_DIFERENCIA_ALTO=38;
//----------------------------------------------------------------------------------------------------------------------------------------------


// Constantes para archivos
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static String ARCHIVO_PUERTO="puerto.cfg";
    public static String ARCHIVO_OPCIONES="opciones.cfg";
    public static String ARCHIVO_IMAGEN_FONDO_ACERCA_DE="Iconos/Banners/AcercaDe_background.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_PRINCIPAL="Iconos/Banners/Principal.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_DATOS="Iconos/Banners/Datos.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_ELEMENTOS="Iconos/Banners/Elementos.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_CRIAS="Iconos/Banners/Crias.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_OPCIONES="Iconos/Banners/Opciones.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_INFORMACION="Iconos/Banners/Info.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_MASIVO="Iconos/Banners/Masivo.jpg";
    public static String ARCHIVO_IMAGEN_BANNER_BUSCADOR="Iconos/Banners/Buscador.jpg";
    public static String ARCHIVO_IMAGEN_ICONO_ALERTA="Iconos/dialog_alert.png";
    public static String ARCHIVO_IMAGEN_ICONO_ERROR="Iconos/dialog_error.png";
    public static String ARCHIVO_IMAGEN_ICONO_OK="Iconos/dialog_ok.png";
    public static String ARCHIVO_IMAGEN_ICONO_BACKUP="Iconos/dialog_backup.png";
    public static String ARCHIVO_IMAGEN_ICONO_FOLDER="Iconos/folder.png";
    public static String ARCHIVO_IMAGEN_ICONO_GOBACK_PRESSED="Iconos/goback_pressed.png";
    public static String ARCHIVO_IMAGEN_ICONO_GOBACK_DISABLED="Iconos/goback_disabled.png";
    public static String ARCHIVO_IMAGEN_ICONO_DETALLES="Iconos/details.png";
    public static String ARCHIVO_IMAGEN_ICONO_EDITAR="Iconos/edit.png";
    public static String ARCHIVO_IMAGEN_ICONO_ELIMINAR="Iconos/delete.png";
    public static String ARCHIVO_IMAGEN_ICONO_AGREGAR="Iconos/add.png";
    public static String ARCHIVO_IMAGEN_ICONO_EJEMPLAR="Iconos/dog.png";
    public static String ARCHIVO_IMAGEN_ICONO_CONTACTO="Iconos/contact.png";
    public static String ARCHIVO_IMAGEN_ICONO_GUARDAR="Iconos/save.png";
    public static String ARCHIVO_IMAGEN_ICONO_NAVEGACION_ANTERIOR="Iconos/navigation_previous.png";
    public static String ARCHIVO_IMAGEN_ICONO_NAVEGACION_SIGUIENTE="Iconos/navigation_next.png";
    public static String ARCHIVO_IMAGEN_ICONO_INFORMACION="Iconos/information.png";
    public static String ARCHIVO_IMAGEN_ICONO_ERRORES="Iconos/error.png";
    public static String ARCHIVO_IMAGEN_ICONO_ERRORES_FIX="Iconos/error_fix.png";
    public static String ARCHIVO_IMAGEN_ICONO_LOGO="Iconos/logo.png";
    public static String ARCHIVO_IMAGEN_ICONO_HUELLA_PRESSED="Iconos/footprint_pressed.png";
    public static String ARCHIVO_IMAGEN_ICONO_HUELLA_DISABLED="Iconos/footprint_disabled.png";
    
    public static String ARCHIVO_TEXTO_INFORMACION="Recursos/Info.txt";
    public static String ARCHIVO_TEXTO_ERRORES="Recursos/Errores.txt";
    public static String ARCHIVO_TEXTO_CORRECCIONES="Recursos/Correcciones.txt";
    
    public static String DIRECTORIO_DATOS="Datos";
    public static String DIRECTORIO_BACKUPS="Backups";
    public static String DIRECTORIO_FOTOS_EJEMPLARES="Fotos/Perros/Ejemplares";
    public static String DIRECTORIO_FOTOS_CONTACTOS="Fotos/Contactos";
    public static String DIRECTORIO_FOTOS_MINIATURA_EJEMPLARES="Fotos/Thumbnails/Perros/Ejemplares";
    public static String DIRECTORIO_FOTOS_MINIATURA_CONTACTOS="Fotos/Thumbnails/Contactos";
    
    public static String EXTENSION_DATOS="jey";
    public static String [ ] EXTENSIONES_IMAGENES= { "jpg", "jpeg", "gif" };
    public static FileNameExtensionFilter FILTRO_EXTENSIONES_IMAGENES=
        new FileNameExtensionFilter("Imágenes (*.jpg, *jpeg, *gif)" , "jpg" , "jpeg" , "gif");
    
    public static String DEFAULT_SIGN="[default]";
    public static String DEFAULT_CLOSESIGN="[/default]";
    public static String AUTOSAVE_SIGN="[autosave]";
    public static String AUTOSAVE_CLOSESIGN="[/autosave]";
    public static String AUTOSAVE_INTERVAL_SIGN="[autosaveInterval]";
    public static String AUTOSAVE_INTERVAL_CLOSESIGN="[/autosaveInterval]";
    
    public static int NOMBRE_LONGITUD_MAXIMA=50;
//----------------------------------------------------------------------------------------------------------------------------------------------


// Constantes para opciones
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static boolean OPCION_AUTOSAVE_ON_CLOSE_DEFAULT=true;
    public static int OPCION_AUTOSAVE_INTERVAL_DEFAULT=10;
    public static int OPCION_AUTOSAVE_INTERVAL_NONE=0;
//----------------------------------------------------------------------------------------------------------------------------------------------


// Constantes para la interfaz gráfica
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static Font FUENTE_NORMAL=new Font("Dialog", Font.PLAIN, 12);
    public static Font FUENTE_ITALICA=new Font("Dialog", Font.ITALIC, 12);
    public static Font FUENTE_NEGRITA=new Font("Dialog", Font.BOLD, 12);
    public static Font FUENTE_NORMAL_VERDANA=new Font("Verdana", Font.PLAIN, 11);
    public static Font FUENTE_CHICA=new Font("Dialog", Font.BOLD, 10);
    
    public static Color COLOR_BLANCO=new Color(255,255,255);
    public static Color COLOR_NEGRO=new Color(0,0,0);
    public static Color COLOR_GRIS=new Color(128,128,128);
    public static Color COLOR_GRIS_OSCURO=new Color(70,70,70);
    public static Color COLOR_GRIS_OFF=new Color(240,240,240);
    public static Color COLOR_GRIS_DEFAULT=new Color(153,153,153);
    public static Color COLOR_BEIGE=new Color(251,235,210);
    public static Color COLOR_BEIGE_OFF=new Color(255,243,223);
    public static Color COLOR_VIOLETA=new Color(200,209,225);
    public static Color COLOR_VIOLETA_OFF=new Color(227,232,240);
    public static Color COLOR_VERDE=new Color(157,170,149);
    public static Color COLOR_VERDE_TIERRA=new Color(231,231,199);
    public static Color COLOR_VERDE_MARINO=new Color(61,112,114);
    public static Color COLOR_VERDE_OFF=new Color(220,225,217);
    public static Color COLOR_VERDE_CLARO=new Color(228,255,193);
    public static Color COLOR_VERDE_OSCURO=new Color(179,179,0);
    public static Color COLOR_TURQUESA=new Color(176,217,217);
    public static Color COLOR_TURQUESA_OFF=new Color(221,238,238);
    public static Color COLOR_AZUL_APAGADO=new Color(112,146,191);
    public static Color COLOR_AZUL_MARINO=new Color(31,96,60);
    
    public static Border BORDE_NORMAL=BorderFactory.createEmptyBorder(5,5,5,5);
    public static Border BORDE_COSTADOS=BorderFactory.createEmptyBorder(0,40,0,40);
    public static Border BORDE_SUPERIOR=BorderFactory.createEmptyBorder(150,0,0,0);
    public static Border BORDE_DATO=BorderFactory.createLineBorder(COLOR_GRIS_DEFAULT);
    public static Border BORDE_DATO_UP_DOWN=BorderFactory.createMatteBorder(1,0,1,0, COLOR_GRIS_DEFAULT);
    public static Border BORDE_VENTANA=
        BorderFactory.createCompoundBorder( BORDE_DATO , BorderFactory.createEmptyBorder(4,4,0,4) );
    public static Border BORDE_AUXILIAR=
        BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder(4,4,4,10) , BORDE_DATO );
    public static Border BORDE_LISTA=
        BorderFactory.createCompoundBorder( BORDE_DATO_UP_DOWN, BORDE_AUXILIAR );
    public static Border BORDE_DIALOGO=BorderFactory.createEmptyBorder(10,10,5,10);
    public static Border BORDE_SANGRIA=BorderFactory.createEmptyBorder(0,10,0,0);
    public static Border BORDE_SANGRIA_FUERTE=BorderFactory.createEmptyBorder(0,30,0,0);
    public static Border BORDE_FIELD=
        BorderFactory.createCompoundBorder( BORDE_DATO , BorderFactory.createEmptyBorder(2,5,2,5) );
    public static Border BORDE_LADOS_HUNDIDOS=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    public static Border BORDE_HUNDIDO=BorderFactory.createLoweredBevelBorder();
    public static Border BORDE_ELEVADO=BorderFactory.createRaisedBevelBorder();
    public static Border BORDE_RECUADRO=
        BorderFactory.createCompoundBorder( BORDE_ELEVADO , BorderFactory.createEtchedBorder() );
    public static Border BORDE_TITULADO_NORMAL(String title) {
        return BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder(title) , BorderFactory.createEmptyBorder(-5,0,0,0) );
    }
    public static Border BORDE_TITULADO(String title) {
        return BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder(title) , BORDE_NORMAL );
    }
    public static Border BORDE_QUITA_ESPACIADO=BorderFactory.createEmptyBorder(-5,18,-5,0);
    
    public static int DIFERENCIA_BORDE_RECUADRO=10;
    public static int FOTO_MINIATURA_SIZE=144;
    public static int FOTO_MAXIMO_LADO=3000;
    public static int FOTO_MAXIMO_REDIMENSION=500;
    
    public static int SPLIT_PANE_DIFERENCIA=173;
    
    public static int BOTON_CHICO_ANCHO_DEFAULT=80;
    public static int BOTON_CHICO_ALTO_DEFAULT=22;
    public static int BOTON_NORMAL_ANCHO_DEFAULT=96;
    public static int BOTON_NORMAL_ALTO_DEFAULT=24;
    
    public static int FIELD_LONGITUD_DEFAULT=12;
    
    public static int COMBO_ANCHO_DEFAULT=120;
    public static int COMBO_ALTO_DEFAULT=20;
//----------------------------------------------------------------------------------------------------------------------------------------------


// Constantes para mensajes
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static String MENSAJE_UNICA_INSTANCIA=
    "Pedigree Manager ya se está\nejecutando. Sólo se puede abrir\nuna única instancia del programa.";
    
    public static String MENSAJE_TEXTO_GUARDAR=
    "Los datos se guardaron correctamente. Se recomienda realizar copias de\nseguridad frecuentemente para evitar la pérdida accidental de la base de\ndatos. Para más información, utilice el menú Ayuda de la ventana principal.";
    
    public static String MENSAJE_TEXTO_BACKUP=
    "La base de datos ha sido copiada en el directorio\n\""+DIRECTORIO_BACKUPS+"\" de la carpeta del programa. Recuerde\nrealizar una copia de seguridad frecuentemente.";
    
    public static String MENSAJE_TEXTO_AGREGAR_MASIVO=
    "Los elementos han sido agregados. La base de datos\nNO ha sido guardada con estas modificaciones.\nPuede seguir agregando elementos en la planilla.";
    
    public static String MENSAJE_TEXTO_ERROR_101=
    "Hubo un problema con un archivo o directorio requerido: no existe,\nno es válido o es inaccesible. El programa seguirá ejecutándose.\nPuede ver la Ayuda para solucionar este problema.";
    
    public static String MENSAJE_TEXTO_NOMBRE_INVALIDO=
    "El nombre elegido no es válido. Recuerde que no es\nposible utilizar los siguientes caracteres: | \\ / : * ? \" < >\ny que debe tener una longitud de entre 1 y "+NOMBRE_LONGITUD_MAXIMA+" caracteres.";
    
    public static String MENSAJE_TEXTO_TITULO_INVALIDO=
    "El título elegido no es válido. Recuerde que no es\nposible utilizar los siguientes caracteres: | \\ / : * ? \" < >\ny que debe tener una longitud de entre 1 y "+NOMBRE_LONGITUD_MAXIMA+" caracteres.";
    
    public static String MENSAJE_TEXTO_RENOMBRAR=
    "La base de datos ha sido renombrada\nexitosamente. Recuerde realizar una\ncopia de seguridad frecuentemente.";
    
    public static String MENSAJE_TEXTO_ERROR_RENOMBRAR=
    "No fue posible renombrar la base de datos.\nPara más información sobre este error\nlea el archivo de Ayuda.";
    
    public static String MENSAJE_TEXTO_NOMBRE_EN_USO=
    "El nombre elegido ya está en uso. Para continuar\ndebe seleccionar un nombre distinto o cancelar\nla operación.";
    
    public static String MENSAJE_TEXTO_NOMBRE_IGUAL=
    "El elemento seleccionado es el mismo que el editado.\nUn ejemplar no puede ser padre/madre de sí mismo.\nPara continuar, seleccione otro elemento.";
    
    public static String MENSAJE_TEXTO_FOTO_ERROR=
    "La ruta de la foto seleccionada no es válida o no es posible copiar el archivo.\nVerifique que la foto existe y que tiene un formato soportado. La operación\nse realizará de todas formas. Puede seleccionar una nueva foto editando el elemento.";
    
    public static String MENSAJE_TEXTO_SOBREESCRIBIR_BASE_DATOS=
    "El título elegido ya está en uso por otra base de datos.\n¿Desea sobreescribir el archivo?\nSe perderán todos los datos de la misma.";
    
    public static String MENSAJE_TEXTO_ELIMINAR_BASE_DATOS=
    "Está a punto de eliminar una base de datos.\nLa información se perderá de forma irreversible.\n¿Está seguro que desea proceder?";
    
    public static String MENSAJE_TEXTO_ELIMINAR_ELEMENTO=
    "Está a punto de eliminar un elemento.\nEsta acción no puede deshacerse.\n¿Está seguro que desea proceder?";
    
    public static String MENSAJE_TEXTO_ELEMENTO_ELIMINADO=
    "El elemento buscado no ha podido\nser encontrado. El mismo ha sido\neliminado de la base de datos.";
    
    public static String MENSAJE_TEXTO_FOTO_ELIMINADA=
    "La foto buscada no ha podido ser encontrada.\nLa misma ha sido eliminada o renombrada\nmanualmente.";
    
    public static String MENSAJE_NO_SE_ENCUENTRA(String archivo) {
        return "No fue posible cargar \""+archivo+"\".\nEl archivo no ha sido encontrado.\nEl programa seguirá ejecutándose.";
    }
    
    public static int MENSAJE_RESPUESTA_NO=0;
    public static int MENSAJE_RESPUESTA_SI=1;
//----------------------------------------------------------------------------------------------------------------------------------------------

}