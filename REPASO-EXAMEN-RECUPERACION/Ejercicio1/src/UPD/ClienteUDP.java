package UPD;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 5000;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Introduce un número: ");
            String mensaje = scanner.nextLine();
            byte[] bufferEnvio = mensaje.getBytes();

            // Crear paquete y enviarlo
            InetAddress direccionServidor = InetAddress.getByName(SERVIDOR);
            DatagramPacket paqueteEnvio = new DatagramPacket(bufferEnvio, bufferEnvio.length, direccionServidor, PUERTO);
            socket.send(paqueteEnvio);
            System.out.println("Número enviado.");

            // Recibir respuesta
            byte[] bufferRespuesta = new byte[1024];
            DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
            socket.receive(paqueteRespuesta);
            String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());

            System.out.println("Doble recibido del servidor: " + respuesta);
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
