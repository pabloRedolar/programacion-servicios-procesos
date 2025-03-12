package UDP;

import java.io.IOException;
import java.net.*;

// El usuario tiene que mandar un numero y el servidor devuelve una cuenta atras desde ese num hasta 0 (UDP)
public class ServidorUPD {
    private static final int PORT = 12345;

    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Servidor iniciado...");

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecibido);

                String mensajeCliente = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println(mensajeCliente);

                int numeroFinal = Integer.parseInt(mensajeCliente);

                for (int i = numeroFinal-1; i >= 0; i--) {
                    String respuesta = String.valueOf(i);
                    System.out.println(respuesta);
                    byte[] bufferRespuesta = respuesta.getBytes();

                    InetAddress clienteAddress = paqueteRecibido.getAddress();
                    int puertoCliente = paqueteRecibido.getPort();

                    DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, clienteAddress, puertoCliente);
                    socket.send(paqueteRespuesta);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
