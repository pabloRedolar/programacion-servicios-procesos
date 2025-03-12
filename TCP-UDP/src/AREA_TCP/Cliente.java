package AREA_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

public class Cliente implements Serializable {
    //private static final Semaphore semaphore = new Semaphore(1);
    private final Long id;
    private final Integer ancho;
    private final Integer alto;
    private final Integer port;
    private final String ip;

    public Cliente(Long id, Integer ancho, Integer alto, Integer port, String ip) {
        this.id = id;
        this.ancho = ancho;
        this.alto = alto;
        this.port = port;
        this.ip = ip;
    }

    public static void main(String[] args) {
        int port = 65535;
        String ip = "127.0.0.1";
        Scanner sc = new Scanner(System.in);
        int ancho = 0, alto = 0;
        do {
            try {
                out.println("Escribe el primer numero entero: ");
                ancho = sc.nextInt();
                out.println("Escribe el segundo numero entero: ");
                alto = sc.nextInt();
            } catch (InputMismatchException e) {
                out.println("Los numeros deben de ser enteros");
                sc.nextLine();
            }

        } while (ancho <= 0 || alto <= 0);
        sc.close();

        Cliente cliente = new Cliente(1L, ancho, alto, port, ip);

        try (Socket socket = new Socket(ip, port)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(cliente);
            out.println(cliente + " ha enviado al servidor un rectangulo de " + cliente.ancho + " cm de ancho y " + cliente.alto + " cm de alto.");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Integer area = (Integer) ois.readObject();
            out.println(cliente + " el servidor dice que el area de tu rectangulo es " + area + " centimetros cuadrados");

        } catch (SocketException e) {
            out.println(cliente + " no se puede conectar al servidor.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public long getId() {
        return id;
    }

    public Integer getAncho() {
        return ancho;
    }

    public Integer getAlto() {
        return alto;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

}
