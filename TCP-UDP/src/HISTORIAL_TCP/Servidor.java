package HISTORIAL_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.exit;
import static java.lang.System.out;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        List<String> stringsCliente = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(port)){
            out.println("Servidor iniciado en el puerto " + port);
            serverSocket.setSoTimeout(50000);
            while (true) {
                try (Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());) {

                    Cliente cliente = (Cliente) ois.readObject();
                    out.println(cliente + " esta siendo tratado");
                    Object respuesta = proccesarCliente(cliente, stringsCliente);
                    out.println(respuesta);

                    oos.writeObject(respuesta);

                    if (!cliente.getMensaje().equalsIgnoreCase("historial")){
                        stringsCliente.add(cliente.getMensaje());
                    }

                } catch (SocketTimeoutException ex) {
                    out.println("Tiempo de espera agotado");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object proccesarCliente(Cliente cliente, List<String> stringsCliente) {
        int idCliente = cliente.getId();
        String mensaje = cliente.getMensaje();

        return !mensaje.equalsIgnoreCase("historial") ? mensaje : stringsCliente.isEmpty()
                ? new String("No hay historial disponible") : stringsCliente;
    }
}
