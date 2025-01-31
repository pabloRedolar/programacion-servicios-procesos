package Ejercicio1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Escribe un numero: ");
            int numero = scanner.nextInt();

            String mensaje = Integer.toString(numero);
            byte[] datos = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(datos, datos.length, serverAddress, 12345);
            socket.send(packet);

            byte[] buffer = new byte[1024];
            DatagramPacket paqueteRespuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteRespuesta);

            String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());

            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
