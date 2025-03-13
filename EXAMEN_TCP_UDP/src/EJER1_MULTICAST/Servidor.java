package EJER1_MULTICAST;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        String ip = "239.0.0.1";

        //Creamos el multicastSocket por el que permitiremos que se conecten multiples clientes
        try (MulticastSocket socket = new MulticastSocket(port)){
            System.out.println("Servidor iniciado en el puerto " + port + " a las " + LocalDateTime.now());
            //Obtenemos el host y hacemos el joinGroup
            InetAddress address = InetAddress.getByName(ip);
            socket.joinGroup(address);
            //Establecemos el tiempo de espera en 10 segundos
            socket.setSoTimeout(10000);
            while (true){
                try {
                    //Creamos el buffer de entrada
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packetIn = new DatagramPacket(bufferIn,bufferIn.length);
                    //Recibimos el datagrama
                    socket.receive(packetIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    //Deserializamos el cliente
                    Cliente cliente = (Cliente) ois.readObject();

                    //Tratamos el mensaje del cliente y le damos la vuelta al string
                    String respuesta = new StringBuilder(cliente.getMensaje()).reverse().toString();

                    //Ahora vamos a proceder a transformar el String en un objeto para enviarselo al cliente
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    //Escribimos el objeto y lo enviamos en un datagramPackect
                    oos.writeObject(respuesta);
                    DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,packetIn.getAddress(), packetIn.getPort());
                    socket.send(packetOut);


                }catch (SocketTimeoutException ex){
                    // Aqui estamos esperando a que el setSoTimeOut lanze su excepcion al pasar 10 segundos sin recibir una peticion
                    System.out.println("Tiempo de espera agotado. Cerrando servidor...");
                    //Cerrramos el servidor
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
