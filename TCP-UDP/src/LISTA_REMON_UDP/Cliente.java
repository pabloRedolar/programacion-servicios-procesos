package LISTA_REMON_UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cliente implements Serializable{
    private Integer id;
    private Integer numerin;

    public Cliente() {
    }

    public Cliente(Integer id, Integer numerin) {
        this.id = id;
        this.numerin = numerin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumerin() {
        return numerin;
    }

    public void setNumerin(Integer numerin) {
        this.numerin = numerin;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String host = "localhost";
        Scanner scanner = new Scanner(System.in);
        Integer numero = 0;
        for (int i = 0; i < 5; i++) {

            while (true) {
                try {
                    System.out.println("\nEscribe un numero entero: ");
                    numero = scanner.nextInt();
                    if (numero > 0) {
                        break;
                    } else System.out.println("El numero debe ser positivo y mayor que 0");
                } catch (InputMismatchException e) {
                    System.out.println("Debes introducir un numero");
                    scanner.nextLine();
                }
            }

            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress address = InetAddress.getByName(host);
                Cliente cliente = new Cliente(i+1, numero);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(cliente);
                DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, address, port);
                socket.send(packectOut);
                System.out.println(cliente + " ha enviado el numero " + numero);

                byte[] bufferIn = new byte[1024];
                DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.receive(packectIn);
                ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                List<Integer> respuesta = (List<Integer>) ois.readObject();
                System.out.println(cliente + " ha recibido " + respuesta);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
