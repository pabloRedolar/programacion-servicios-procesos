package MULTICAST_NUMBER;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cliente implements Serializable {
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
        return "Cliente " + "ip=" + id;
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String host = "localhost";
        Scanner scanner = new Scanner(System.in);
        Integer numero = 0;
        for (int i = 0; i < 5; i++) {
            while (true) {
                try {
                    System.out.println("Ingrese el numero del cliente: ");
                    numero = scanner.nextInt();

                    if (numero > 0) {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Debes introducir un numero entero mayor a 0");
                    scanner.nextLine();
                }
            }
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress address = InetAddress.getByName(host);
                Cliente cliente = new Cliente(i+1, numero);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(cliente);
                DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, address, port);
                socket.send(packetOut);
                System.out.println(cliente + " ha enviado el numero " + numero);

                byte[] bufferIn = new byte[1024];
                DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.receive(packetIn);
                ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Integer respuesta = (Integer) ois.readObject();
                System.out.println(cliente + " recibe el numero " + respuesta);

            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
