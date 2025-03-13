package EjerciciosIA.MULTICAST;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ClienteMulticastChat2 {
    private static final String HOSTNAME = "230.0.0.0";
    private static final int PORT = 4446;

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress grupo = InetAddress.getByName(HOSTNAME);
            socket.joinGroup(grupo);

            System.out.println("Cliente conectado al grupo multicast. Escribe mensajes:");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Verificar si hay mensajes del grupo multicast
                if (socket.getSoTimeout() == 0) {
                    socket.setSoTimeout(100); // Establecer un timeout para recibir mensajes
                }

                try {
                    byte[] bufferEntrada = new byte[1024];
                    DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                    socket.receive(packetEntrada);

                    // Mostrar el mensaje recibido
                    String mensajeRecibido = new String(packetEntrada.getData(), 0, packetEntrada.getLength());
                    System.out.println("Mensaje recibido: " + mensajeRecibido);
                } catch (java.net.SocketTimeoutException e) {
                    // No se recibió ningún mensaje en el tiempo especificado
                }

                // Verificar si el usuario ha escrito un mensaje
                if (System.in.available() > 0) {
                    String mensaje = scanner.nextLine();
                    if (!mensaje.isEmpty()) {
                        // Enviar mensaje al grupo multicast
                        byte[] bufferSalida = mensaje.getBytes();
                        DatagramPacket packetSalida = new DatagramPacket(bufferSalida, bufferSalida.length, grupo, PORT);
                        socket.send(packetSalida);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}