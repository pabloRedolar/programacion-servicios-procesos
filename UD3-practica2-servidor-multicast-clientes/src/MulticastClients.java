import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastClients {

    private static void sendMessageToServer(String clientId, String message, InetAddress group, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();

        // Enviar mensaje
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, port);
        socket.send(packet);  // Enviar mensaje

        // Recibir respuesta del servidor
        byte[] buffer = new byte[256];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);  // Esperar respuesta

        // Mostrar la respuesta
        String response = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Cliente " + clientId + " recibió: " + response);

        socket.close();  // Cerrar socket
    }

    public static void main(String[] args) throws IOException {
        // Dirección del grupo multicast y puerto
        InetAddress group = InetAddress.getByName("230.0.0.0");  // Dirección multicast
        int port = 4446;  // Puerto multicast

        // Crear los mensajes para cada cliente
        String[] messages = {
                "Hola, soy el cliente 1",  // Cliente 1
                "Hola, soy el cliente 2",  // Cliente 2
                "Hola, soy el cliente 3",  // Cliente 3
                "Hola, soy el cliente 4"   // Cliente 4
        };

        // Enviar los mensajes para cada cliente en un hilo separado
        for (int i = 0; i < messages.length; i++) {
            final String clientId = String.valueOf(i + 1);  // Identificar el cliente
            final String message = messages[i];

            // Crear un hilo para cada cliente
            new Thread(() -> {
                try {
                    sendMessageToServer(clientId, message, group, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
