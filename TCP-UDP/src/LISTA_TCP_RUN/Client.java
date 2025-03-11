package LISTA_TCP_RUN;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static java.lang.System.out;

public class Client implements Runnable, Serializable {
    private Integer id;
    private List<Integer> numbers;
    private Integer port;
    private String ipAddress;
    private static Semaphore semaphore = new Semaphore(1);

    public Client() {
    }

    public Client(Integer id, List<Integer> numbers, Integer port, String ipAddress) {
        this.id = id;
        this.numbers = numbers;
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public Integer getId() {
        return id;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return "Client id=" + id;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();

            try (Socket socket = new Socket(ipAddress, port)) {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(this);
                out.println(this + " ha enviado:  " + this.getNumbers());

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                List<Integer> respuesta = (List<Integer>) ois.readObject();
                out.println(this + " ha recibido " + respuesta + "\n");

            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        final Integer port = 65535;
        final String ipAddress = "127.0.0.1";

        for (int i = 0; i < 5; i++) {
            out.println("Escribe un numero: ");
            int numero = new Scanner(System.in).nextInt();
            numbers.add(numero);
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Client(i+1,numbers, port, ipAddress)).start();
        }
    }
}
