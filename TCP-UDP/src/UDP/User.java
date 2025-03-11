package UDP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class User {
    public static void main(String[] args) {
        InetAddress inetAddress;
        int port = 10069;
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce un numero: ");
        int numero = scan.nextInt();
        try {
             inetAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        try(DatagramSocket socket = new DatagramSocket()){

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteArrayOutputStream);
            dataOut.writeInt(numero);

            byte[] bufferOut = byteArrayOutputStream.toByteArray();
            DatagramPacket packetOut = new DatagramPacket(bufferOut, bufferOut.length, inetAddress, port);
            socket.send(packetOut);


            byte[] bufferIn = new byte[1024];
            DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
            socket.receive(packetIn);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packetIn.getData());
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            int response = dataInputStream.readInt();

            System.out.println("La respuesta del servidor es: " + response);

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
