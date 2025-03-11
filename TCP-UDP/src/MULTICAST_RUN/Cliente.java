package MULTICAST_RUN;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static java.lang.System.out;

public class Cliente implements Runnable, Serializable {
    private final Integer id;
    private final String message;
    private Integer port;
    private String ip;

    public Cliente(Integer id, String message, Integer port, String ip) {
        this.id = id;
        this.message = message;
        this.port = port;
        this.ip = ip;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            byte[] bufferOut = baos.toByteArray();
            InetAddress grupo = InetAddress.getByName(ip);
            DatagramPacket dataOut = new DatagramPacket(bufferOut, bufferOut.length, grupo, port);
            socket.send(dataOut);
            out.println("El cliente con id: " + this.id + " ha enviado el mensaje: " + this.message);

            byte[] bufferIn = new byte[1024];
            DatagramPacket dataIn = new DatagramPacket(bufferIn, bufferIn.length);
            socket.receive(dataIn);
            String respuesta = new String(dataIn.getData(), dataIn.getOffset(), dataIn.getLength());
            out.println("El cliente con id: " + this.id + " ha recibido el mensaje: " + respuesta);

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        final String IP = "239.255.255.255";
        final Integer PORT = 65535;

        for (int i = 0; i < 4; i++) {
            new Thread(new Cliente(i + 1, "HoLa", PORT, IP)).start();
        }

    }
}
