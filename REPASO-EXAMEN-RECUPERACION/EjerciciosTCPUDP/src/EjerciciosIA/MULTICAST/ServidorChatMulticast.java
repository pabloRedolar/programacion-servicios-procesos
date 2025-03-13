package EjerciciosIA.MULTICAST;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServidorChatMulticast {
    private static final int PORT = 4446;
    private static final String HOSTNAME = "230.0.0.0";

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress grupo = InetAddress.getByName(HOSTNAME);
            socket.joinGroup(grupo);

            System.out.println("Servidor iniciado...");

            while (true) {
                // Recibir mensaje del grupo multicast
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(packetEntrada);

                // Convertir el mensaje a String
                String mensaje = new String(packetEntrada.getData(), 0, packetEntrada.getLength());

                // Mostrar el mensaje recibido
                System.out.println("Usuario " + packetEntrada.getAddress().getHostAddress() + " dice: " + mensaje);

                // Reenviar el mensaje al grupo multicast (excepto si el remitente es el servidor)
                if (!packetEntrada.getAddress().equals(InetAddress.getLocalHost())) {
                    DatagramPacket packetSalida = new DatagramPacket(
                            packetEntrada.getData(),
                            packetEntrada.getLength(),
                            grupo,
                            PORT
                    );
                    socket.send(packetSalida);
                }

                socket.leaveGroup(grupo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}