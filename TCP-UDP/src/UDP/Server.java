package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalDateTime;

public class Server {
    public static void main(String[] args) {
        int port = 10069;

        try (DatagramSocket socket = new DatagramSocket(port)){
            System.out.println("UDP.Server started on port: " + socket.getLocalPort() + " at " + LocalDateTime.now());
            socket.setSoTimeout(10000);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
            DataInputStream dataIn = new DataInputStream(byteArrayInputStream);
            int numero = dataIn.readInt();

            numero*=2;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteArrayOutputStream);
            dataOut.writeInt(numero);

            byte[] bufferOut = byteArrayOutputStream.toByteArray();
            DatagramPacket packetOut = new DatagramPacket(bufferOut,bufferOut.length,packet.getAddress(),packet.getPort());
            socket.send(packetOut);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
