package LISTA_TCP_RUN;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;

public class Server {
    public static void main(String[] args) {
        final Integer port = 65535;
        final String ipAddress = "127.0.0.1";

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            out.println("Server started on port " + port + " at " + LocalDateTime.now());
            serverSocket.setSoTimeout(25000);

            while (true) {
                try (Socket socket = serverSocket.accept()){
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Client client = (Client) ois.readObject();

                    List<Integer> respuesta = proccesClient(client);

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(respuesta);

                }catch (SocketTimeoutException ex){
                    out.println("Server timed out");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> proccesClient(Client client) {
        int id = client.getId();
        List<Integer> numbers = client.getNumbers();
        return (id%2) == 0 ? numbers.stream().map(x -> x*2).toList() : numbers.stream().map(x -> x*x*x).toList();
    }
}
