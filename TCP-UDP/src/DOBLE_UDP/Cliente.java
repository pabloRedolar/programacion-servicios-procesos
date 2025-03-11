package DOBLE_UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer port = 65535;
        String host = "127.0.0.1";
        Integer numero = 0;
        while (true) {
            do {
                try {
                    out.println("Introduce un numero entero mayor a 0 y el servidor te devolvera el doble: (con 999 cerramos porgrama)");
                    numero = sc.nextInt();

                } catch (InputMismatchException ex) {
                    out.println("Debes de introducir un numero: ");
                    sc.nextLine();
                }

            } while (numero <= 0);
            if (numero == 999) break;
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress addres = InetAddress.getByName(host);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(numero);
                DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, addres, port);
                socket.send(packetOut);
                out.println("Cliente envia el numero " + numero);

                byte[] bufferIn = new byte[1024];
                DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.receive(packetIn);
                ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Integer doble = (Integer) ois.readObject();
                out.println("Cliente recibe el doble de su numero " + doble);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
