package HISTORIAL_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static java.lang.System.out;

public class Cliente implements Runnable, Serializable {
//    private static Semaphore semaphore = new Semaphore(1);
    private Integer id;
    private String mensaje;
    private String ip;
    private Integer port;

    public Cliente(Integer id, String ip, Integer port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public Cliente() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {

        final Integer port = 65535;
        final String host = "127.0.0.1";
        for (int i = 0; i <3 ; i++) {
            new Thread(new Cliente(i+1,host,port)).start();
        }


    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
            while (true) {
                try (Socket socket = new Socket(ip, port);
                     ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                    String entrada;

                    do {
                        out.println("Escribe un mensaje y te lo devolveremos (Si escribes historial te devolvemos el historial)");
                        entrada = scanner.nextLine().trim();
                        if (entrada.isEmpty()) {
                            out.println("No se admiten mensajes vacios");
                        }
                    } while (entrada.isEmpty());


                    this.setMensaje(entrada);

                    if (this.getMensaje().equalsIgnoreCase("exit")) {
                        out.println("Cerrando cliente...");
                        break;
                    }

                    oos.writeObject(this);
                    oos.flush();
                    out.println(this + " ha enviado el mensaje: " + this.getMensaje());

                    Object obj = ois.readObject();

                    if (obj instanceof String) {
                        String respuesta = (String) obj;
                        out.println(this + " recibe del servidor: " + respuesta);
                    } else if (obj instanceof List<?>) {
                        List<?> respuesta = (List<?>) obj;
                        out.println(this + " recibe del servidor: " + respuesta);
                    }
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
    }
}
