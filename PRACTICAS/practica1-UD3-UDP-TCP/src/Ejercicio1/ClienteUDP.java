package Ejercicio1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 666;
        byte[] buffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Introduce un número: ");
            String mensaje = scanner.nextLine();

            byte[] datosEnviar = mensaje.getBytes();
            InetAddress direccionServidor = InetAddress.getByName(SERVIDOR);
            DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length, direccionServidor, PUERTO);
            socket.send(paqueteEnviar);

            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteRecibido);
            String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());

            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
