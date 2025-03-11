package LISTA_REMON_UDP;



import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        List<Integer> numeros = new ArrayList<Integer>();
        try (DatagramSocket socket = new DatagramSocket(port)){
            System.out.println("Servidor conectado en ell puerto " + port);
            socket.setSoTimeout(60000);
            while (true){
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packectIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente cliente = (Cliente) ois.readObject();

                    for (int i = cliente.getNumerin(); i >= 0 ; i--) {
                        numeros.add(i);
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(numeros);
                    DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length, packectIn.getAddress(), packectIn.getPort());
                    socket.send(packectOut);
                    numeros.clear();

                }catch (SocketTimeoutException e){
                    System.out.println("Tiempo de espera agotado");
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
}
