package UDP;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    private static final int PORT = 12345;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Dime un numero para hacer una cuenta hacia atras: ");
        int numero = scanner.nextInt();

        try (DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getByName(HOSTNAME);

            byte[] bufferIn = String.valueOf(numero).getBytes();
            DatagramPacket paqueteEnviado = new DatagramPacket(bufferIn, bufferIn.length, address, PORT);
            socket.send(paqueteEnviado);

            while (true) {
                byte[] bufferRespuesta = new byte[1024];
                DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
                socket.receive(paqueteRespuesta);

                String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
                System.out.println(respuesta);

                if (respuesta.equals("0")) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
