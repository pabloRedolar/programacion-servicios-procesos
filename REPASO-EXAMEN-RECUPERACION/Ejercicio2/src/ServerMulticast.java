import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

public class ServerMulticast {
    final static int PORT = 5000;
    final static String MULTICAST_ADDRESS = "224.0.0.1";

    public static void main(String[] args) {

        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress grupo = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(grupo);  // Unirse al grupo multicast
            System.out.println("Server esperando mensajes...");

            while (true) {
                MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();

                DatagramPacket paqueteRecibido = new DatagramPacket(mensajeRespuesta);
                socket.receive(paqueteRecibido);

                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                InetAddress direccionCliente = paqueteRecibido.getAddress();
                int puertoCliente = paqueteRecibido.getPort();

                // Validar que el mensaje tiene el formato esperado (ID + mensaje)
                if (mensaje.length() < 2) {
                    System.out.println("Mensaje mal formado");
                    continue;
                }

                int clienteID;
                try {
                    clienteID = Character.getNumericValue(mensaje.charAt(0));
                } catch (Exception e) {
                    System.out.println("ID de cliente no válido");
                    continue;
                }

                String texto = mensaje.substring(1);

                // Verificar que el clienteID esté entre 1 y 4
                if (clienteID < 1 || clienteID > 4) {
                    System.out.println("ID de cliente fuera de rango");
                    continue;
                }

                // Responder según el clienteID
                String respuesta;
                switch (clienteID) {
                    case 1:
                        respuesta = texto.toUpperCase();
                        break;
                    case 2:
                        respuesta = texto.toLowerCase();
                        break;
                    case 3:
                        respuesta = new StringBuilder(texto).reverse().toString();
                        break;
                    case 4:
                        respuesta = texto;
                        break;
                    default:
                        System.out.println("Cliente desconocido");
                        continue;
                }

                System.out.println("Cliente " + clienteID + " envío: " + texto + ", respuesta: " + respuesta);

                // Enviar la respuesta al cliente (uso de DatagramSocket en lugar de MulticastSocket)
                try (DatagramSocket unicastSocket = new DatagramSocket()) {
                    byte[] bufferRespuesta = respuesta.getBytes();
                    DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, direccionCliente, puertoCliente);
                    unicastSocket.send(paqueteRespuesta);
                }
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
