package SMTP;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient.AUTH_METHOD;
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
import java.util.Scanner;

public class ClienteSMTPInteractivo {

    public static void main(String[] args)
            throws NoSuchAlgorithmException, UnrecoverableKeyException,
            KeyStoreException, InvalidKeyException, InvalidKeySpecException {

        // Scanner para leer desde teclado
        Scanner sc = new Scanner(System.in);

        // Solicitar datos por teclado
        System.out.print("Introduce servidor SMTP........: ");
        String server = sc.nextLine().trim();

        System.out.print("Necesita negociación TLS (S/N).: ");
        String tlsOption = sc.nextLine().trim();
        boolean necesitaTLS = tlsOption.equalsIgnoreCase("S");

        System.out.print("Introduce puerto...............: ");
        int puerto = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Introduce usuario..............: ");
        String username = sc.nextLine().trim();

        System.out.print("Introduce password............: ");
        String password = sc.nextLine().trim();

        System.out.print("Introduce correo del remitente.: ");
        String remitente = sc.nextLine().trim();

        System.out.print("Introduce correo del destinatario: ");
        String destinatario = sc.nextLine().trim();

        System.out.print("Introduce asunto...............: ");
        String asunto = sc.nextLine().trim();

        System.out.println("Introduce el mensaje (varias líneas). Finaliza con '*':");
        StringBuilder sbMensaje = new StringBuilder();
        while (true) {
            String linea = sc.nextLine();
            if (linea.equals("*")) {
                break;
            }
            sbMensaje.append(linea).append("\n");
        }
        String mensaje = sbMensaje.toString();

        // Comprobamos si el mensaje está vacío
        if (mensaje.trim().isEmpty()) {
            System.out.println("El mensaje está vacío. No se envía nada.");
            System.exit(1);
        }

        sc.close();

        // Creamos el cliente SMTP
        AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();

        try {
            int respuesta;

            // Configuramos el KeyManager para TLS
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(null, null);
            KeyManager km = kmf.getKeyManagers()[0];

            // Conectamos con el servidor SMTP
            client.connect(server, puerto);
            System.out.println("1 - " + client.getReplyString());

            // Establecemos la clave para la comunicación segura
            client.setKeyManager(km);

            respuesta = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(respuesta)) {
                client.disconnect();
                System.err.println("CONEXIÓN RECHAZADA");
                System.exit(1);
            }

            // Enviamos el comando EHLO
            client.ehlo(server);
            System.out.println("2 - " + client.getReplyString());

            // Si el usuario ha indicado que se necesita TLS, ejecutamos STARTTLS
            if (necesitaTLS) {
                if (client.execTLS()) {
                    System.out.println("3 - " + client.getReplyString());
                } else {
                    System.out.println("Error al ejecutar STARTTLS");
                    client.disconnect();
                    System.exit(1);
                }
            }
            // Independientemente de si hay TLS o no, intentamos autenticarnos
            if (client.auth(AUTH_METHOD.PLAIN, username, password)) {
                System.out.println("4 - " + client.getReplyString());

                // Creamos la cabecera
                SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destinatario, asunto);

                // Fijamos remitente y destinatario
                client.setSender(remitente);
                client.addRecipient(destinatario);
                System.out.println("5 - " + client.getReplyString());

                // Enviamos DATA
                Writer writer = client.sendMessageData();
                if (writer == null) {
                    System.out.println("Fallo al enviar DATA.");
                    System.exit(1);
                }

                writer.write(cabecera.toString());
                writer.write(mensaje);
                writer.close();
                System.out.println("6 - " + client.getReplyString());

                // Completamos el envío
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

        } catch (IOException e) {
            System.err.println("No se puede conectar al servidor");
            e.printStackTrace();
            System.exit(1);
        } finally {
            // Intentamos desconectar
            try {
                if (client.isConnected()) {
                    client.disconnect();
                }
            } catch (IOException f) {
                f.printStackTrace();
            }
        }

        System.out.println("Fin de envío");
        System.exit(0);
    }
}
