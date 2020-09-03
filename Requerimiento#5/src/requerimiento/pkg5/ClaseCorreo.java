/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimiento.pkg5;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ClaseCorreo {

    public Properties props = new Properties();
    public static Session session;
    public static String CorreoPrincipal;
    public static String Asunto;
    public static String Mensaje;
    public static String ContraPrincipal;
    public static int contadorbien = 0, contadormal = 0;
    public static JSONArray empList = new JSONArray();

    public void propiedadesCorreo(String direccion) {
        try {
            if (direccion.contains("gmail")) {

                props.setProperty("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "Usuario");
                props.setProperty("mail.smtp.auth", "true");
                session = Session.getDefaultInstance(props);
                session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
                // Preparamos la sesion
            } else {

                props.setProperty("mail.smtp.host", "smtp-MAIL.OUTLOOK.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "Usuario");
                props.setProperty("mail.smtp.auth", "true");
                session = Session.getDefaultInstance(props);
                session.getProperties().put("mail.smtp.ssl.trust", "smtp-MAIL.OUTLOOK.com");
                // Preparamos la sesion
            }

        } catch (Exception e) {
        }

    }

    public void guardarJSON() throws IOException {
        saveJSON();
        try (FileWriter file = new FileWriter("Reporte.json")) {
            file.write(empList.toJSONString());
            file.flush();
            System.out.print(empList);
        }

    }

    public void saveJSON() throws IOException {
        Date objDate = new Date();

        JSONObject emps = new JSONObject();
        emps.put("Fecha: ", objDate.toString());
        emps.put("Correo: ", CorreoPrincipal);
        emps.put("Mensajes Enviados Correctamente: ", contadorbien);
        emps.put("Mensajes Con Errores: ", contadormal);
        // emps.put("Extractor", " ");
        //2-Añadimos el Empleado en un nuevo JSONObject
        JSONObject empObj = new JSONObject();
        empObj.put("Cliente", emps);
        //3-Añadimos a JSONArray
        empList.add(empObj);
    }

    public void mandarCorreoATodos(String InfoCliente) throws MessagingException {

        try {
            String ListaInfo[] = InfoCliente.split("-");
            String Nombre = ListaInfo[1];
            String apellido = ListaInfo[2];
            String correo = ListaInfo[3];
            propiedadesCorreo(CorreoPrincipal);
            System.out.println(Nombre + apellido +"   "+ correo + " esta por Enviar");
            MimeMessage message = new MimeMessage(session);

            message.setFrom(
                    new InternetAddress(CorreoPrincipal));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correo));
            message.setSubject(Asunto);
            message.setText("Hola Señ@r: " + Nombre + "" + apellido + "\n" + Mensaje);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");

            t.connect(CorreoPrincipal, ContraPrincipal);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            contadorbien++;

        } catch (Exception e) {
            System.out.println("Fallo");
            contadormal++;

        }

    }

}
