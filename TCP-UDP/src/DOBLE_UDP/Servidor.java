package DOBLE_UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        try (DatagramSocket socket = new DatagramSocket(port)){
            socket.setSoTimeout(50000);
            while(true) {
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packectIn);

                    ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Integer numero = (Integer) ois.readObject();

                    numero *= 2;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(numero);

                    DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, packectIn.getAddress(), packectIn.getPort());
                    socket.send(packetOut);

                }catch (SocketTimeoutException ex){
                    System.out.println("Cerrando servidor");
                    System.exit(0);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
