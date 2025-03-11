package DOBLE_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

import static java.lang.System.out;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(65535)){
            out.println("Servidor iniciado en el puerto " + serverSocket.getLocalPort() + " a las " + LocalDateTime.now());
            serverSocket.setSoTimeout(30000);

            try (Socket socket = serverSocket.accept()) {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                int numero = (int) ois.readObject();

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(numero*2);

            }catch (SocketTimeoutException ex){
                out.println("Tiempo de espera del servidor agotado...");
                System.exit(0);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
