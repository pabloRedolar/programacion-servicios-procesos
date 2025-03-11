package DOBLE_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.out;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        out.println("Introduce un numero entero y te devolvere su doble: ");
        int numero = sc.nextInt();
        try (Socket socket = new Socket("localhost",65535)){
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(numero);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            int doble = (Integer) ois.readObject();
            out.println("El numero recibido por el servidor es " + doble);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
