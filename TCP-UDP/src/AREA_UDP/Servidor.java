package AREA_UDP;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

import static java.lang.System.out;

public class Servidor extends Socket {
    public static void main(String[] args) {
        final Integer port = 65535;
        try (DatagramSocket socket = new DatagramSocket(port)){
            out.println("Servidor iniciado en el puerto " + port + " a las " + LocalDateTime.now());
            socket.setSoTimeout(60000);
            while (true){
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packetIn = new DatagramPacket(bufferIn,bufferIn.length);
                    socket.receive(packetIn);

                    ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente cliente = (Cliente) ois.readObject();

                    Integer respuesta = cliente.getAlto()*cliente.getAncho();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);

                    oos.writeObject(respuesta);
                    DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,packetIn.getAddress(), packetIn.getPort());
                    socket.send(packectOut);

                }catch (SocketTimeoutException ex){
                    out.println("Tiempo de espera agotado...");
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
