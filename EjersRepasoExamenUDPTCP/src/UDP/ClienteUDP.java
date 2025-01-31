package UDP;

import java.io.IOException;
import java.net.*;

public class ClienteUDP {
    public static void main(String[] args) {
        String serverIP = "127.0.0.1";
        int serverPort = 12345;
        String message = "Hola, servidor!";

        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] data = message.getBytes();
            InetAddress serverAddress = InetAddress.getByName(serverIP);

            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
            socket.send(packet);

            byte[] buffer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(respuesta);
            System.out.println("Respuesta del server: " + new String(respuesta.getData(), 0, respuesta.getLength()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
