import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer {

    public static void main(String[] args) throws IOException {
        InetAddress group = InetAddress.getByName("230.0.0.0");  // Dirección multicast
        int port = 4446;  // Puerto multicast

        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);  // Unirse al grupo multicast

        byte[] buffer = new byte[256];  // Buffer para los mensajes recibidos

        System.out.println("Servidor multicast iniciado...");

        while (true) {
            // Recibir mensajes de los clientes
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);  // Esperar y recibir un paquete

            // Extraer el mensaje recibido
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Mensaje recibido: " + message);

            // Determinar qué cliente envió el mensaje basado en el contenido
            String response = "";
            if (message.contains("cliente 1")) {
                response = message.toUpperCase();  // Convertir a mayúsculas
            } else if (message.contains("cliente 2")) {
                response = message.toLowerCase();  // Convertir a minúsculas
            } else if (message.contains("cliente 3")) {
                response = new StringBuilder(message).reverse().toString();  // Invertir el mensaje
            } else if (message.contains("cliente 4")) {
                response = message;  // Devolver el mensaje intacto
            } else {
                response = "Cliente desconocido";  // Si no se reconoce el mensaje
            }

            // Enviar la respuesta al cliente
            DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), response.length(),
                    packet.getAddress(), packet.getPort());
            socket.send(responsePacket);  // Enviar la respuesta

            System.out.println("Respuesta enviada: " + response);
        }
    }
}
