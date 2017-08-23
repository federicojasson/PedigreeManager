public class Mensaje {


// Mensajes de aviso
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static void mostrarGuardarCorrectamente(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_GUARDAR;
        Dialogo.mostrarMensajeAviso(parent, MENSAJE);
    }

    public static void mostrarGuardarCorrectamente(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_GUARDAR;
        Dialogo.mostrarMensajeAviso(parent, MENSAJE);
    }

    public static void mostrarAgregarMasivoCorrectamente(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_AGREGAR_MASIVO;
        Dialogo.mostrarMensajeAviso(parent, MENSAJE);
    }

    public static void mostrarBackup(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_BACKUP;
        Dialogo.mostrarMensajeAviso(parent, MENSAJE);
    }

    public static void mostrarRenombrarCorrectamente(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_RENOMBRAR;;
        Dialogo.mostrarMensajeAviso(parent, MENSAJE);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

// Mensajes de error
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static void mostrarUnicaInstancia() {
        final String MENSAJE=Constantes.MENSAJE_UNICA_INSTANCIA;
        Dialogo.mostrarMensajeError(MENSAJE);
    }

    public static void mostrarError101(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_ERROR_101;
        Dialogo.mostrarMensajeError(parent, MENSAJE, "Error 101");
    }

    public static void mostrarError101(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_ERROR_101;
        Dialogo.mostrarMensajeError(parent, MENSAJE, "Error 101");
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static void mostrarNombreInvalido(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_NOMBRE_INVALIDO;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarNombreInvalido(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_NOMBRE_INVALIDO;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarTituloInvalido(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_TITULO_INVALIDO;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarErrorRenombrar(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_ERROR_RENOMBRAR;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarNombreEnUso(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_NOMBRE_EN_USO;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarErrorFoto(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_FOTO_ERROR;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarErrorNombreIgual(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_NOMBRE_IGUAL;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarNoSeEncuentraArchivo(GUI_Frame parent, String archivo) {
        final String MENSAJE=Constantes.MENSAJE_NO_SE_ENCUENTRA(archivo);
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarElementoEliminado(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_ELEMENTO_ELIMINADO;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }

    public static void mostrarFotoEliminada(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_FOTO_ELIMINADA;
        Dialogo.mostrarMensajeError(parent, MENSAJE);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------


// Mensajes input
//----------------------------------------------------------------------------------------------------------------------------------------------
    public static int mostrarSobreescribirBaseDatos(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_SOBREESCRIBIR_BASE_DATOS;
        return Dialogo.mostrarMensajeConfirmacion(parent, MENSAJE);
    }

    public static int mostrarSobreescribirBaseDatos(GUI_Dialog parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_SOBREESCRIBIR_BASE_DATOS;
        return Dialogo.mostrarMensajeConfirmacion(parent, MENSAJE);
    }

    public static int mostrarEliminarBaseDatos(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_ELIMINAR_BASE_DATOS;
        return Dialogo.mostrarMensajeConfirmacion(parent, MENSAJE);
    }

    public static int mostrarEliminarElemento(GUI_Frame parent) {
        final String MENSAJE=Constantes.MENSAJE_TEXTO_ELIMINAR_ELEMENTO;
        return Dialogo.mostrarMensajeConfirmacion(parent, MENSAJE);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}