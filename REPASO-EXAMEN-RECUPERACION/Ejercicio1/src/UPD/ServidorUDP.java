package UPD;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
        final int PUERTO = 5000;

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP en espera en el puerto " + PUERTO);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);

                // Recibir número del cliente
                socket.receive(paqueteRecibido);
                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                int numero = Integer.parseInt(mensaje);
                System.out.println("Número recibido: " + numero);

                // Calcular el doble
                int doble = numero * 2;
                String respuesta = String.valueOf(doble);
                byte[] bufferRespuesta = respuesta.getBytes();

                // Enviar respuesta al cliente
                DatagramPacket paqueteRespuesta = new DatagramPacket(
                        bufferRespuesta, bufferRespuesta.length,
                        paqueteRecibido.getAddress(), paqueteRecibido.getPort()
                );
                socket.send(paqueteRespuesta);
                System.out.println("Enviado: " + respuesta);
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
