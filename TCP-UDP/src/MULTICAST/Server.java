package MULTICAST;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class Server {
    public static void main(String[] args) {
        Integer port = 65535;
        String host = "239.0.0.1";
        try (MulticastSocket socket = new MulticastSocket(port)){
            InetAddress group = InetAddress.getByName(host);
            socket.joinGroup(group);
            System.out.println("Server listening on port " + port + " at " + LocalDateTime.now());
            socket.setSoTimeout(60000);
            while(true){
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packet);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente cliente = (Cliente) ois.readObject();

                    String respuesta = processClient(cliente);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(respuesta);
                    DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length, packet.getAddress(), packet.getPort());
                    socket.send(packetOut);

                }catch (SocketTimeoutException ex){
                    System.out.println("Socket timed out");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String processClient(Cliente cliente) {
        int id = cliente.getId();
        String mensaje = cliente.getMensaje();
        switch (id){
            case 1 ->{
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
                return "No se reconoce tu id " + id;
            }
        }
    }
}
