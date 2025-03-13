package SMTP;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;

public class ClienteSMTP3 {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, InvalidKeyException, InvalidKeySpecException {
        AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();

        String server = "smtp.gmail.com";
        String username = "";
        String password = "";
        int puerto = 587;
        String remitente = "atorresd@iesch.org";

        try{
            int respuesta;
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(null, null);
            KeyManager km = kmf.getKeyManagers()[0];

            //conectamos con el servidor SMTP
            client.connect(server, puerto);
            System.out.println("1 - " + client.getReplyString());
            //se establece la clave para la comunicación segura
            client.setKeyManager(km);

            respuesta = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(respuesta)) {
                client.disconnect();
                System.err.println("CONEXIÓN RECHAZADA");
                System.exit(1);
            }

            //se envía el comando EHLO
            client.ehlo(server);
            System.out.println("2 - " + client.getReplyString());

            //NECESITA NEGOCIACIÓN TLS - MODO NO IMPLICITO
            if (client.execTLS()) {
                System.out.println("3 - " + client.getReplyString());

                if (client.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, username, password)) {
                    System.out.println("4 - " + client.getReplyString());
                    String destino1 = "atorresd@iesch.org";
                    String asunto = "Prueba de SMTP con Gmail";
                    String mensaje = "Hola. \nEnviando saludos.\nUsando Gmail\nChao.";

                    //creamos cabecera
                    SimpleSMTPHeader cabecera = new SimpleSMTPHeader(username, destino1, asunto);
                    //el nombre de usuario y el email de origen coinciden
                    client.setSender(remitente);
                    client.addRecipient(destino1);
                    System.out.println("5 - " + client.getReplyString());

                    //se envia DATA
                    Writer writer = client.sendMessageData();
                    if (writer == null) {
                        System.out.println("Fallo al enviar data.");
                        System.exit(1);
                    }

                    writer.write(cabecera.toString());
                    writer.write(mensaje);
                    writer.close();
                    System.out.println("6 - " + client.getReplyString());

                    boolean exito = client.completePendingCommand();
                    System.out.println("7 - " + client.getReplyString());
                    if (!exito) {
                        System.out.println("Fallo al finalizar transacción");
                        System.exit(1);
                    } else {
                        System.out.println("Mensaje enviado con éxito");
                    }
                } else {
                    System.out.println("Usuario no autenticado");
                }
            } else {
                System.out.println("Error al ejecutar STARTTLS");
            }


        } catch (IOException e) {
            System.err.println("No se puede conectar al servidor");
            e.printStackTrace();
            System.exit(1);

        }

        try {
            client.disconnect();
        } catch (IOException f) {f.printStackTrace();}

        System.out.println("Fin de envío");
        System.exit(0);
    }
}
