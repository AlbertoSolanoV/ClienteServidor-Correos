package requerimiento.pkg5;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.*;

public class CrearArchivo {

    public String Apellido;
    public String Nombre;
    public String email;
    public String Telefono;
    public String Codigo;
    public String Info = "";
    String Host = "localhost";
    int Puerto = 8888;
    public static Socket socketCliente;
    private OutputStream outputStream;
    private DataOutputStream SalidaDatos;

    public void readActivosXml(String Direccion) throws ParserConfigurationException, IOException {

        File fXmlFile = new File(Direccion);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("personne");;

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;

                Nombre = eElement.getElementsByTagName("firstname").item(0).getTextContent();
                Apellido = eElement.getElementsByTagName("lastname").item(0).getTextContent();
                Telefono = eElement.getElementsByTagName("phone").item(0).getTextContent();
                email = eElement.getElementsByTagName("email").item(0).getTextContent();
                Info = "CorreoADar" + "-" + Nombre + "-" + Apellido + "-" + email+"-"+Telefono;
                EnviarAServerXML(Info);
                Info = "";
            }
        }

    }

    public void conexion() {
        try {

            socketCliente = new Socket(Host, Puerto);

        } catch (Exception ex) {
            System.out.println("ERROR" + ex.getMessage());
        }
    }

    public void cerrarTodo() {
        try {
            outputStream = socketCliente.getOutputStream();
            SalidaDatos = new DataOutputStream(outputStream);
            SalidaDatos.writeUTF("salir");
            SalidaDatos.close();
            socketCliente.close();
        } catch (IOException ex) {

        }
    }

    public void EnviarAduntoMensaje(String Asunto, String Mensaje) {
        try {
            outputStream = socketCliente.getOutputStream();
            SalidaDatos = new DataOutputStream(outputStream);
            SalidaDatos.writeUTF("AsuntoPrincipal" + "-" + Asunto);
            SalidaDatos.flush();
            SalidaDatos.writeUTF("MensajePrincipal" + "-" + Mensaje);
            SalidaDatos.flush();
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void EnviarNombrePrincipal(String correo, String Contraseña) {
        try {
            outputStream = socketCliente.getOutputStream();
            SalidaDatos = new DataOutputStream(outputStream);
            SalidaDatos.writeUTF("CorreoPrincipal" + "-" + correo);
            SalidaDatos.flush();
            SalidaDatos.writeUTF("ContraseñaPrincipal" + "-" + Contraseña);
            SalidaDatos.flush();
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void EnviarAServerXML(String datos) {
        try {
            outputStream = socketCliente.getOutputStream();
            SalidaDatos = new DataOutputStream(outputStream);
            SalidaDatos.writeUTF(datos);
            SalidaDatos.flush();
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
