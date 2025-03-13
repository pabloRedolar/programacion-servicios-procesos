package EJER2;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalDateTime;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        String host = "127.0.0.1";

        //Abrimos la conexion
        try (DatagramSocket socket = new DatagramSocket(port)){
            System.out.println("Servidor iniciado en el puerto " + port + " a las " + LocalDateTime.now());
            while (true){
                //Creamos el buffer de entrada por el cual nos llegara un cliente
                byte[] bufferIn = new byte[1024];
                //Recibimos el paquete
                DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.receive(packetIn);
                //Deserializamos el paquete para poder obtener el cliente
                ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Cliente cliente = (Cliente) ois.readObject();
                //Comprobamos que lo que envia el cliente no sea un .
                if (".".equals(cliente.getNumero()))break;
                Integer numeroCliente = Integer.parseInt(cliente.getNumero());
                //Creamos la respuesta que vamos a enviar al cliente
                Integer respuesta = (numeroCliente*2)*numeroCliente;

                //Lo convertimos en objecto para finalizar enviandolo al Cliente en este caso solo mandamos el Integer como Object
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(respuesta);
                DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,packetIn.getAddress(), packetIn.getPort());
                socket.send(packetOut);
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
