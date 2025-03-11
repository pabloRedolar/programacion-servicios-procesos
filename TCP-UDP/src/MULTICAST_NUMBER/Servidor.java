package MULTICAST_NUMBER;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        String ip = "239.0.0.1";
        try (MulticastSocket socket = new MulticastSocket(port)){
            System.out.println("Servidor iniciado en el puerto " + port);
            InetAddress grupo = InetAddress.getByName(ip);
            socket.joinGroup(grupo);
            socket.setSoTimeout(60000);
            while (true){
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packetIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente cliente = (Cliente) ois.readObject();

                    int numero = cliente.getNumerin() * cliente.getId();
                    
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(numero);
                    DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,packetIn.getAddress(),packetIn.getPort());
                    socket.send(packetOut);
                }catch (SocketTimeoutException ex){
                    System.out.println("Tiempo de espera agotado...");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
