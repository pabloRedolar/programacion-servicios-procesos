package EJER1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

public class Servidor {
    public static void main(String[] args) {
        Integer port = 65535;
        //Creamos la conexion
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Servidor iniciado en el puerto " + port + " a las " + LocalDateTime.now());
            //Ponemos 10 segundos y si en estos 10 segundos el cliente no envia Info cerramos.
            serverSocket.setSoTimeout(10000);
            while (true){
                //Aceptamos la conexion de cada cliente
                try (Socket socket = serverSocket.accept()){
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    //Leemos y deserializaos el objeto cliente
                    Cliente cliente = (Cliente) ois.readObject();
                    //Creamos el mensaje en reversa
                    String respuesta = new StringBuilder(cliente.getMensaje()).reverse().toString();

                    //Serializamos la respuesta y la enviamos
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(respuesta);

                }catch (SocketTimeoutException ex){
                    System.out.println("Tiempo de espera agotado. Cerrando servidor...");
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
