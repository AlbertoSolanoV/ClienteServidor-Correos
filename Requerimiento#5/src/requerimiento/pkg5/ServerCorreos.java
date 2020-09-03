package requerimiento.pkg5;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ServerCorreos {

    public static int cont = 0;
    public static String mensaje;
    public static JSONArray empList = new JSONArray();
    public static DataInputStream in;
    public static DataOutputStream out;
    public static List listaContactos = new ArrayList();
    public static ClaseCorreo correo = new ClaseCorreo();

    public static void RecibirMensajes(Socket sc) throws IOException, MessagingException {

        while (true) {

            in = new DataInputStream(sc.getInputStream());//Leer Recibir Mensajes del Cliente
            mensaje = in.readUTF();//Quedarse a la espera del Cliente
            System.out.println("Mensaje de cliente: " + mensaje);//Mostramos Mensaje
            //Correo Empresarial
            if (mensaje.contains("CorreoPrincipal")) {

                String Correo[] = mensaje.split("-");
                correo.CorreoPrincipal = Correo[1];

            }
            //Contraseña Empresarial
            if (mensaje.contains("ContraseñaPrincipal")) {

                String Correo[] = mensaje.split("-");
                correo.ContraPrincipal = Correo[1];

            }

            if (mensaje.contains("AsuntoPrincipal")) {

                String Correo[] = mensaje.split("-");
                correo.Asunto = Correo[1];

            }

            if (mensaje.contains("MensajePrincipal")) {

                String Correo[] = mensaje.split("-");
                correo.Mensaje = Correo[1];

            }

            if (mensaje.contains("CorreoADar")) {
                listaContactos.add(mensaje);

            }
            if (mensaje.equalsIgnoreCase("salir")) {
                int cantidad = listaContactos.size();

                int cantidadentredos = cantidad / 3;

                int sumadedos = (cantidadentredos + cantidadentredos);

                Hilo1 h1 = new Hilo1(0, cantidadentredos);

                h1.start();

                Hilo2 h2 = new Hilo2((cantidadentredos + 1), sumadedos);

                h2.start();

                Hilo3 h3 = new Hilo3((sumadedos+1), cantidad);

                h3.start();

                break;

            }
            mensaje = " ";

        }
        
        
    }

    public static void main(String args[]) throws IOException, MessagingException {

        ServerSocket servidor = null;
        Socket sc = null;

        final int Puerto = 8888;

        servidor = new ServerSocket(Puerto);
        int cont = 0;
        System.out.println("SERVER CORREOS INICIADO...");
        System.out.println("----------------------->\n");
        try {
            while (true) {
                sc = servidor.accept();
                cont++;
                System.out.println("Se cominica con: " + sc);

                RecibirMensajes(sc);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
