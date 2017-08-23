public class Dialogo {

// Métodos

//----------------------------------------------------------------------------------------------------------------------------------------------
    public static void mostrarMensajeAviso(GUI_Frame parent, String texto) {
        // Muestra un mensaje informativo
        GUI_Mensaje_Aviso dialogo=new GUI_Mensaje_Aviso(parent, texto);
    }

    public static void mostrarMensajeAviso(GUI_Dialog parent, String texto) {
        // Muestra un mensaje informativo
        GUI_Mensaje_Aviso dialogo=new GUI_Mensaje_Aviso(parent, texto);
    }

    public static int mostrarMensajeConfirmacion(GUI_Frame parent, String texto) {
        // Muestra un mensaje Si-No para confirmar una acción
        GUI_Mensaje_Confirmacion dialogo=new GUI_Mensaje_Confirmacion(parent, texto);
        return dialogo.getRespuesta();
    }

    public static int mostrarMensajeConfirmacion(GUI_Dialog parent, String texto) {
        // Muestra un mensaje Si-No para confirmar una acción
        GUI_Mensaje_Confirmacion dialogo=new GUI_Mensaje_Confirmacion(parent, texto);
        return dialogo.getRespuesta();
    }

    public static void mostrarMensajeError(GUI_Frame parent, String texto) {
        // Muestra un mensaje de error
        GUI_Mensaje_Error dialogo=new GUI_Mensaje_Error(parent, texto);
    }

    public static void mostrarMensajeError(GUI_Dialog parent, String texto) {
        // Muestra un mensaje de error
        GUI_Mensaje_Error dialogo=new GUI_Mensaje_Error(parent, texto);
    }

    public static void mostrarMensajeError(GUI_Frame parent, String texto, String title) {
        // Muestra un mensaje de error
        GUI_Mensaje_Error dialogo=new GUI_Mensaje_Error(parent, texto, title);
    }

    public static void mostrarMensajeError(GUI_Dialog parent, String texto, String title) {
        // Muestra un mensaje de error
        GUI_Mensaje_Error dialogo=new GUI_Mensaje_Error(parent, texto, title);
    }

    public static void mostrarMensajeError(String texto) {
        // Muestra un mensaje de error
        GUI_Mensaje_Error dialogo=new GUI_Mensaje_Error(texto);
    }
//----------------------------------------------------------------------------------------------------------------------------------------------

}