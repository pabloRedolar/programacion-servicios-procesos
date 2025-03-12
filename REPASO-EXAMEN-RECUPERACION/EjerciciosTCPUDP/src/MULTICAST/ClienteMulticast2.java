package MULTICAST;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClienteMulticast2 {
    private static final int PORT = 50000;
    private static final String HOSTNAME = "230.0.0.1"; // Dirección multicast


    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)){
            InetAddress grupo = InetAddress.getByName(HOSTNAME);
            socket.joinGroup(grupo);

            while (true) {
                byte[] bufferRespuesta = new byte[1024];
                DatagramPacket paquete = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
                socket.receive(paquete);

                String respuesta = new String(paquete.getData(), 0, paquete.getLength());
                System.out.println(respuesta);

//                if (respuesta.contains("0")) {
//                    break;
//                }
            }

//            socket.leaveGroup(grupo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
