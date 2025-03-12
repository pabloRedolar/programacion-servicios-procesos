package MULTICAST;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServidorMulticast {
    private static final int PORT = 50000;
    private static final String HOSTNAME = "230.0.0.1";

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress grupo = InetAddress.getByName(HOSTNAME);

            socket.joinGroup(grupo);

            System.out.println("Servidor multicast iniciado...");

            while (true) {
                for (int i = 10; i >= 0; i--) {
                    String mensaje = "Cuenta atrás: " + i;

                    byte[] buffer = mensaje.getBytes();

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, grupo, PORT);
                    socket.send(packet);
                    System.out.println("Mensaje enviado: " + mensaje);

                    Thread.sleep(1000);
                }

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
