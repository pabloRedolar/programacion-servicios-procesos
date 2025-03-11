package Ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
        final int PUERTO = 666;
        byte[] buffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP escuchando en el puerto: " + PUERTO);

            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);

                String mensaje = new String(paquete.getData(), 0, paquete.getLength());
                int numero = Integer.parseInt(mensaje.trim());
                int doble = numero * 2;

                String respuesta = String.valueOf(doble);
                byte[] datosDeRespuesta = respuesta.getBytes();

                DatagramPacket paqueteDeRespuesta = new DatagramPacket(
                        datosDeRespuesta,
                        datosDeRespuesta.length,
                        paquete.getAddress(),
                        paquete.getPort()
                );

                socket.send(paqueteDeRespuesta);
                System.out.println("Procesado número: " + numero + ", enviado: " + doble);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
