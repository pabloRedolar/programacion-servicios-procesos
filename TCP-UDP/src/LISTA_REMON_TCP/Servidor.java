package LISTA_REMON_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        List<Integer> numeros = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Servidor conectado en el puerto " + port + " a las " + LocalDateTime.now());
            serverSocket.setSoTimeout(60000);
            while (true){
                try (Socket socket = serverSocket.accept()){
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Cliente cliente = (Cliente) ois.readObject();

                    for (int i = cliente.getNumero(); i >= 0; i--) {
                        numeros.add(i);
                    }
                    System.out.println(numeros);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(numeros);
                    numeros.clear();
                }catch (SocketTimeoutException ex){
                    System.out.println("Tiempo de espera agotado...");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
