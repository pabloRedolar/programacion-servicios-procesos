package AREA_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

import static java.lang.System.*;

public class Servidor {
    public static void main(String[] args) {
        final int PORT = 65535;
        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            out.println("Servidor conectado en el puerto " + PORT + " a las " + LocalDateTime.now());
            serverSocket.setSoTimeout(60000);
            while (true){
                try (Socket socket = serverSocket.accept()){
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Cliente cliente = (Cliente) ois.readObject();
                    out.println(cliente + " ha enviado el ancho y el alto de un rectangulo Alto=" + cliente.getAlto() + " Ancho=" + cliente.getAncho());
                    Integer area = cliente.getAlto()*cliente.getAncho();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(area);
                }catch (SocketTimeoutException ex){
                    out.println("Tiempo de espera del servidor agotado");
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
