package TIENDA_UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

import static java.lang.System.out;

public class Tienda {
    public static void main(String[] args) {
        final Semaphore producto = new Semaphore(5);
        final Integer port = 65535;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            out.println("Servidor iniciado en el puerto " + port + " a las " + LocalDateTime.now());
            socket.setSoTimeout(60000);
            while (true) {
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packetIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente cliente = (Cliente) ois.readObject();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);

                    if (producto.tryAcquire()) {
                        out.println(cliente + " pudo comprar un producto. Productos restantes = " + producto.availablePermits());
                        oos.writeObject(true);
                    } else {
                        out.println(cliente + " no pudo comprar porque no quedaban productos.");
                        oos.writeObject(false);
                    }
                    DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, packetIn.getAddress(), packetIn.getPort());
                    socket.send(packectOut);
                } catch (SocketTimeoutException e) {
                    out.println("Tiempo de espera agotado");
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
