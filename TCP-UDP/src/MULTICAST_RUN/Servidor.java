package MULTICAST_RUN;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

import static java.lang.System.out;

public class Servidor {
    public static void main(String[] args) {
        final String IP = "239.255.255.255";
        final Integer PORT = 65535;
        final Integer TIME_OUT = 60000;

        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress group = InetAddress.getByName(IP);
            socket.joinGroup(group);
            socket.setSoTimeout(TIME_OUT);
            out.println("Server initialize in port: " + PORT + " at: " + LocalDateTime.now());
            while (true) {
                byte[] bufferIn = new byte[1024];
                DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                try {
                    socket.receive(packetIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente client = (Cliente) ois.readObject();

                    String respuesta = processMessage(client);

                    DatagramPacket packetOut = new DatagramPacket(respuesta.getBytes(), respuesta.getBytes().length, packetIn.getAddress(), packetIn.getPort());
                    socket.send(packetOut);

                } catch (SocketTimeoutException e) {
                    out.println("Server timed out.");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String processMessage(Cliente client) {
        Integer id = client.getId();
        String mensaje = client.getMessage();

        switch (id) {
            case 1 -> {
                return mensaje.toUpperCase();
            }
            case 2 -> {
                return mensaje.toLowerCase();
            }
            case 3 -> {
                return new StringBuilder(mensaje).reverse().toString();
            }
            case 4 -> {
                return mensaje;
            }
            default -> {
                return "Id de cliente no reconocido: " + id;
            }
        }
    }
}
