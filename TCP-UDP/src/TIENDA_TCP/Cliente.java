package TIENDA_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;
import static java.lang.System.out;

public class Cliente extends Thread implements Serializable {
    private static final Semaphore semaphore = new Semaphore(1);
    private final Integer id;
    private Integer intentos;
    private final String ip;
    private final Integer puerto;

    public Cliente(Integer id, Integer intentos, String ip, Integer puerto) {
        this.id = id;
        this.intentos = intentos;
        this.ip = ip;
        this.puerto = puerto;
    }

    public static void main(String[] args) {
        final Integer port = 65535;
        final String ip = "127.0.0.1";

        for (int i = 0; i < 10; i++) {
            new Cliente(i + 1, 10, ip, port).start();
        }
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            while (this.intentos != 0) {
                out.println(this + " intenta comprar. Intentos restantes: " + this.intentos);
                if (Math.random() <= 0.25) {
                    try (Socket socket = new Socket(ip, puerto)) {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(this);
                        //out.println(this + "ha conseguido entrar a la tienda");
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        Boolean compra = (Boolean) ois.readObject();
                        if (compra) {
                            out.println(this + " ha conseguido comprar\n");
                            break;
                        } else {
                            out.println(this + " consiguio entrar pero no quedaban Iphones\n");
                        }
                    } catch (SocketException e){
                        out.println("Error de conexion con la tienda");
                        System.exit(0);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    out.println(this + " no tuvo suerte\n");
                }
                this.intentos--;
                sleep(1500);

            }
            if (this.intentos == 0) {
                out.println(this + " ya no tiene mas intentos\n");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}
