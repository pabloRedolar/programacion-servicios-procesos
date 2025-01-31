package Ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
        int port = 12345;

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Servidor esperando...");
            while (true) {
                byte[] recibidos = new byte[1024];
                DatagramPacket packetRecibido = new DatagramPacket(recibidos, recibidos.length);
                socket.receive(packetRecibido);

                String mensajeRecibido = new String(packetRecibido.getData(), 0, packetRecibido.getLength());
                int numero = Integer.parseInt(mensajeRecibido);
                int resultado = numero * 2;

                String mensajeRespuesta = "El doble es: " + resultado;
                byte[] datosRespuesta = mensajeRespuesta.getBytes();
                DatagramPacket paqueteRespuesta = new DatagramPacket(datosRespuesta, datosRespuesta.length, packetRecibido.getAddress(), packetRecibido.getPort());
                socket.send(paqueteRespuesta);

                System.out.println("Respuesta enviada: " + mensajeRespuesta);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
