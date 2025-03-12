import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClienteMulticast {
    private static final String MULTICAST_ADDRESS = "224.0.0.1"; // Dirección de grupo multicast
    private static final int PUERTO = 5000; // Puerto de comunicación

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress grupo = InetAddress.getByName(MULTICAST_ADDRESS);

            // 1. Pedir ID del cliente
            int clienteID;
            do {
                System.out.print("Introduce tu número de cliente (1-4): ");
                clienteID = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
            } while (clienteID < 1 || clienteID > 4);

            // 2. Pedir el mensaje
            System.out.print("Introduce un mensaje: ");
            String mensaje = scanner.nextLine();

            // 3. Preparar mensaje con el ID
            String mensajeFinal = clienteID + mensaje;
            byte[] bufferEnvio = mensajeFinal.getBytes();

            // 4. Enviar mensaje al servidor
            DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, grupo, PUERTO);
            socket.send(paqueteEnvio);
            System.out.println("Mensaje enviado al servidor.");

            // 5. Recibir la respuesta del servidor
            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
            socket.receive(paqueteRespuesta);

            // 6. Mostrar la respuesta recibida
            String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
            System.out.println("Servidor responde: " + respuesta);

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
