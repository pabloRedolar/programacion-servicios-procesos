package UPD;

import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) {
        final int PUERTO = 50000; // Puerto en el que escuchará el servidor

        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP esperando conexiones en el puerto " + PUERTO);

            while (true) {
                // 1. Crear un buffer para recibir el mensaje
                byte[] buffer = new byte[1024];
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);

                // 2. Recibir datos del cliente
                socket.receive(paqueteRecibido);
                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println("Cliente dice: " + mensaje);

                // 3. Procesar datos (convertir a número y calcular el doble)
                int numero = Integer.parseInt(mensaje);
                int doble = numero * 2;

                // 4. Preparar la respuesta
                String respuesta = String.valueOf(doble);
                byte[] bufferRespuesta = respuesta.getBytes();

                // 5. Enviar respuesta al cliente
                DatagramPacket paqueteRespuesta = new DatagramPacket(
                        bufferRespuesta,
                        bufferRespuesta.length,
                        paqueteRecibido.getAddress(),
                        paqueteRecibido.getPort()
                );

                socket.send(paqueteRespuesta);
                System.out.println("Enviando doble: " + respuesta);
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
