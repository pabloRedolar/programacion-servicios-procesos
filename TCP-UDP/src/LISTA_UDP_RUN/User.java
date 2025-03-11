package LISTA_UDP_RUN;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static java.lang.System.out;

public class User implements Runnable, Serializable {
    private final static Semaphore semaphore = new Semaphore(1);
    private int id;
    private List<Integer> numeros;
    private String ip;
    private int port;

    public User(int id, List<Integer> numeros, String ip, int port) {
        this.id = id;
        this.numeros = numeros;
        this.ip = ip;
        this.port = port;
    }

    public User() {
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            out.println("Escribe un número: ");
            int numero = new Scanner(System.in).nextInt();
            list.add(numero);
        }

        for (int i = 0; i < 4; i++) {
            new Thread(new User(i + 1, list, "127.0.0.1", 65535)).start();
        }
    }

    public int getId() {
        return id;
    }

    public List<Integer> getNumeros() {
        return numeros;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();

            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress address = InetAddress.getByName(ip);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(this);
                byte[] dataOut = baos.toByteArray();
                DatagramPacket packetOut = new DatagramPacket(dataOut, dataOut.length, address, port);
                socket.send(packetOut);
                out.println("El cliente con ID: " + this.id + " ha enviado: " + this.numeros);

                byte[] bufferIn = new byte[1024];
                DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.receive(packetIn);

                ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                List<Integer> respuesta = (List<Integer>) ois.readObject();

                out.println("EL cliente con ID: " + this.id + " ha recibido: " + respuesta + "\n");

            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }

    }
}
