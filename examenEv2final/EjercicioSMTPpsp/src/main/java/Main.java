import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Introduce servidor SMTP: ");
            String smtpServidor = reader.readLine();

            System.out.print("Necesita negociación TLS (S, N)?: ");
            String tls = reader.readLine().toUpperCase();

            System.out.print("Introduce usuario: ");
            String usuario = reader.readLine();

            System.out.print("Introduce contrasenna: ");
            String contrasenna = reader.readLine();

            System.out.print("Introduce puerto: ");
            int puerto = Integer.parseInt(reader.readLine());

            System.out.print("Introduce correo del remitente: ");
            String remitente = reader.readLine();

            System.out.print("Introduce correo destinatario: ");
            String destinatario = reader.readLine();

            System.out.print("Introduce asunto: ");
            String asunto = reader.readLine();

            System.out.println("Introduce el mensaje, finalizará cuando se introduzca un punto (.) en una línea:");
            StringBuilder cuerpo = new StringBuilder();
            String linea;
            while (!(linea = reader.readLine()).equals(".")) {
                cuerpo.append(linea).append("\n");
            }

            if (cuerpo.isEmpty()) {
                System.out.println("El mensaje no puede estar vacío.");
                return;
            }

            AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();
            if (tls.equals("S")) {
                client = new AuthenticatingSMTPClient("TLS", true);
            }

            System.out.println("Conectando al servidor SMTP...");
            client.connect(smtpServidor, puerto);
            System.out.println("Respuesta del servidor: " + client.getReplyString());

            if (tls.equals("S")) {
                System.out.println("Iniciando negociación TLS...");
                if (client.execTLS()) {
                    System.out.println("Negociación TLS establecida.");
                } else {
                    System.out.println("No se pudo establecer la negociación TLS.");
                    return;
                }
            }

            System.out.println("Autenticando...");
            if (client.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, usuario, contrasenna)) {
                System.out.println("Autenticación exitosa.");
            } else {
                System.out.println("Error en la autenticación.");
                return;
            }

            System.out.println("Enviando correo...");
            if (client.setSender(remitente)) {
                System.out.println("Remitente establecido: " + remitente);
            } else {
                System.out.println("Error al establecer el remitente.");
                return;
            }

            if (client.addRecipient(destinatario)) {
                System.out.println("Destinatario añadido: " + destinatario);
            } else {
                System.out.println("Error al añadir el destinatario.");
                return;
            }

            Writer writer = client.sendMessageData();
            if (writer != null) {
                SimpleSMTPHeader header = new SimpleSMTPHeader(remitente, destinatario, asunto);
                writer.write(header.toString());
                writer.write(cuerpo.toString());
                writer.close();

                if (client.completePendingCommand()) {
                    System.out.println("Correo enviado correctamente.");
                } else {
                    System.out.println("Error al enviar el correo.");
                }
            } else {
                System.out.println("Error al enviar el mensaje.");
            }

            client.logout();
            client.disconnect();
            System.out.println("Conexión cerrada.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}