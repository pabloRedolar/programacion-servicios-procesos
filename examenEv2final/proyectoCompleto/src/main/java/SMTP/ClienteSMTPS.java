package SMTP;

import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SMTPSClient;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import java.io.IOException;
import java.io.Writer;
import java.util.Base64;

public class ClienteSMTPS {
    public static void main(String[] args) {
        SMTPSClient client = new SMTPSClient(); // Modo explícito

        String server = "smtp.gmail.com";
        String username = "tu_correo@gmail.com"; // Reemplazar con tu correo
        String password = "tu_contraseña";       // Reemplazar con tu contraseña
        int puerto = 587;
        String remitente = "tu_correo@gmail.com"; // Debe coincidir con username

        try {
            client.connect(server, puerto);
            System.out.println("1 - " + client.getReplyString());

            // Verificar conexión
            if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                client.disconnect();
                System.err.println("Conexión rechazada");
                System.exit(1);
            }

            // Enviar EHLO y negociar TLS
            client.helo(server);
            System.out.println("2 - " + client.getReplyString());

            if (client.execTLS()) { // Negociación TLS explícita
                System.out.println("3 - " + client.getReplyString());

                // Autenticación AUTH PLAIN (codificación Base64)
                String credenciales = Base64.getEncoder().encodeToString(
                        ("\000" + username + "\000" + password).getBytes()
                );
                int respuesta = client.sendCommand("AUTH PLAIN", credenciales);
                System.out.println("4 - " + client.getReplyString());

                if (!SMTPReply.isPositiveCompletion(respuesta)) {
                    System.err.println("Autenticación fallida");
                    System.exit(1);
                }

                // Configurar remitente y destinatario
                String destino = "destino@example.com";
                String asunto = "Prueba SMTP con SMTPSClient";
                String mensaje = "Hola,\n\nEste es un correo de prueba.\nSaludos.";

                SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destino, asunto);
                client.setSender(remitente);
                client.addRecipient(destino);
                System.out.println("5 - " + client.getReplyString());

                // Enviar cuerpo del mensaje
                Writer writer = client.sendMessageData();
                if (writer == null) {
                    System.err.println("Error al enviar DATA");
                    System.exit(1);
                }

                writer.write(cabecera.toString());
                writer.write(mensaje);
                writer.close();
                System.out.println("6 - " + client.getReplyString());

                // Finalizar transacción
                boolean exito = client.completePendingCommand();
                System.out.println("7 - " + client.getReplyString());
                if (exito) {
                    System.out.println("Mensaje enviado correctamente");
                } else {
                    System.err.println("Error al finalizar la transacción");
                }
            } else {
                System.err.println("Error en STARTTLS");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fin del envío");
    }
}