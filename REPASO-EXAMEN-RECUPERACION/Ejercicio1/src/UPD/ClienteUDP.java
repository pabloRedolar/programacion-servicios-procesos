package UPD;

import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 50000; // Puerto del servidor

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Introduce un número: ");
            String mensaje = scanner.nextLine();

            // 1. Convertir el mensaje en bytes
            byte[] bufferEnvio = mensaje.getBytes();

            // 2. Crear el paquete UDP para enviar al servidor
            InetAddress direccionServidor = InetAddress.getByName(SERVIDOR);
            DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, direccionServidor, PUERTO);

            // 3. Enviar el paquete
            socket.send(paqueteEnvio);
            System.out.println("Número enviado al servidor...");

            // 4. Preparar un buffer para recibir la respuesta
            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);

            // 5. Recibir respuesta del servidor
            socket.receive(paqueteRespuesta);
            String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());

            // 6. Mostrar el resultado en consola
            System.out.println("Respuesta del servidor (doble del número): " + respuesta);

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
