package LISTA_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Servidor conectado en el puerto " + port);
            serverSocket.setSoTimeout(60000);
            while (true){
                try (Socket socket = serverSocket.accept()){
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Cliente cliente = (Cliente) ois.readObject();

                    List<Integer> respuesta = proccesClient(cliente);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(respuesta);
                }catch (SocketTimeoutException ex){
                    System.out.println("Tiempo de espera del servidor agotado...");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Integer> proccesClient(Cliente cliente) {
        List<Integer> lista = cliente.getNumerines();
        return (cliente.getId()%2) == 0 ? lista.stream().map(x -> x*2).toList()
                : lista.stream().map(x-> x*x*x).toList();
    }
}
