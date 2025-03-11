package TIENDA_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

import static java.lang.System.out;

public class Tienda {
    public static void main(String[] args) {
        final Integer port = 65535;
        final String host = "127.0.0.1";
        final Semaphore Iphone16 = new Semaphore(5);

        try (ServerSocket serverSocket = new ServerSocket(port)){
            out.println("Servidor iniciado en el puerto " + port + " a las " + LocalDateTime.now());
            serverSocket.setSoTimeout(60000);
            while (true){
                try (Socket socket = serverSocket.accept()){
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Cliente cliente = (Cliente) ois.readObject();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    if (Iphone16.tryAcquire()){
                        out.println(cliente + " pudo comprar un Iphone 16. Quedan= " + Iphone16.availablePermits());
                        oos.writeObject(true);
                    }else {
                        out.println(cliente + " no pudo comprar porque no quedaban Iphones 16 disponibles");
                        oos.writeObject(false);
                    }

                }catch (SocketTimeoutException e){
                    out.println("Tiempo de espera agotado. Servidor apagado");
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
