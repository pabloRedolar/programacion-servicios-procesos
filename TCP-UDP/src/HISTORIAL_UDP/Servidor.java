package HISTORIAL_UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.out;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        List<String> mensjesCliente = new ArrayList<>();
        try (DatagramSocket socket = new DatagramSocket(port)){
            out.println("Servidor iniciado en el puerto " + port + " a las " + LocalDateTime.now());
            socket.setSoTimeout(60000);
            while (true){
                try {
                    byte[] bufferIn = new byte[1024];
                    DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                    socket.receive(packectIn);
                    ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Cliente cliente = (Cliente) ois.readObject();
                    Object respuesta = processClient(cliente, mensjesCliente);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(respuesta);
                    DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,packectIn.getAddress(),packectIn.getPort());
                    socket.send(packectOut);

                    Optional.of(cliente.getMensaje()).filter(x -> !x.equalsIgnoreCase("historial"))
                            .ifPresent(mensjesCliente::add);
                }catch (SocketTimeoutException ex){
                    out.println("Tiempo de espera agotado. Cerrando servidor...");
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }

    private static Object processClient(Cliente cliente, List<String> mensjesCliente) {
        int id = cliente.getId();
        String mensaje = cliente.getMensaje();

        return !mensaje.equalsIgnoreCase("historial") ? mensaje : mensjesCliente.isEmpty()
                ? new String("No podemos devolver el historial porque esta vacio") : mensjesCliente;
    }
}
