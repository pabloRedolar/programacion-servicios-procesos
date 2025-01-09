package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
        int puerto = 12345;
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor UDP esperando");
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);
            String mensaje = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Mensaje recibido: " + mensaje);

            String respuesta = "Hola, cliente!";
            byte[] datosRespuesta = respuesta.getBytes();

            DatagramPacket paqueteRespuesta = new DatagramPacket(datosRespuesta, datosRespuesta.length, packet.getAddress(), packet.getPort());
            socket.send(paqueteRespuesta);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
