package LISTA_UDP_RUN;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;


public class Server {
    public static void main(String[] args) {
        final Integer port = 65535;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP.Server started on port: " + port + " at " + LocalDateTime.now());
            socket.setSoTimeout(25000);
            while (true) {

                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packetIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    User user = (User) ois.readObject();

                    List<Integer> lista = processUser(user);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(lista);
                    DatagramPacket pakectOut = new DatagramPacket(baos.toByteArray(), baos.size(), packetIn.getAddress(), packetIn.getPort());
                    socket.send(pakectOut);

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SocketTimeoutException e) {
                    out.println("Server time out");
                    break;
                }

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> processUser(User user) {
        int userId = user.getId();
        List<Integer> list = user.getNumeros();

        //Si es par devolvemos el doble si no el cubo
        return (userId % 2) == 0 ? list.stream().map(x -> x * 2).toList() : list.stream().map(x -> x * x * x).toList();
    }
}
