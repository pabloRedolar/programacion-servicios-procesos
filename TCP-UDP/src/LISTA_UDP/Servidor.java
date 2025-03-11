package LISTA_UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        try (DatagramSocket socket = new DatagramSocket(port)){
            System.out.println("Servidor conectado en el puerto " + port);
            socket.setSoTimeout(60000);
            while (true){
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packectIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente cliente = (Cliente) ois.readObject();
                    List<Integer> respuesta = proccesClient(cliente);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(respuesta);
                    DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,packectIn.getAddress(), packectIn.getPort());
                    socket.send(packectOut);
                }catch (SocketTimeoutException ex){
                    System.out.println("Servidor timed out");
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> proccesClient(Cliente cliente) {
        return (cliente.getId()%2) == 0 ? cliente.getNumeros().stream()
                .map(x -> x*2).toList() : cliente.getNumeros().stream()
                .map(x-> x*x*x).toList();
    }
}
