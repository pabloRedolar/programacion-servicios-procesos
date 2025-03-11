package MULTICAST;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente implements Serializable{
    private Integer id;
    private String mensaje;

    public Cliente(Integer id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    public Cliente() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {
        Integer port = 65535;
        Scanner sc = new Scanner(System.in);
        String mensaje = "";
        for (int i = 0; i < 4; i++) {
            do {
                System.out.println("\nEscribe un mensaje:");
                mensaje = sc.nextLine().trim();
                if (mensaje.isEmpty()) {
                    System.out.println("El mensaje no puede estar vacio");
                }
            } while (mensaje.isEmpty());
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress address = InetAddress.getByName("239.0.0.1");
                Cliente cliente = new Cliente(i+1, mensaje);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(cliente);
                DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, address, port);
                socket.send(packectOut);
                System.out.println(cliente + " ha enviado " + cliente.getMensaje());


                byte[] bufferIn = new byte[1024];
                DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.receive(packectIn);
                ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                String respuesta = (String) ois.readObject();
                System.out.println(cliente + " ha recibido " + respuesta);

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
