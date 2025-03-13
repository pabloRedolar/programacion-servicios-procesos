package EJER2;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cliente implements Serializable{
    private Integer id;
    private String numero;

    public Cliente(Integer id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Cliente() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String host = "127.0.0.1";
        Scanner scanner = new Scanner(System.in);
        String number = "";
        while (true) {
                System.out.println("Escribe un numero o un . para finalizar");
                number = scanner.nextLine();
            // Establecemos la comunicacion con el Server
            try (DatagramSocket socket = new DatagramSocket()) {
                //Obtenemos el host dependiendo de la IP
                InetAddress address = InetAddress.getByName(host);
                Cliente cliente = new Cliente(1, number);
                //Serializamos el objeto cliente y lo enviamos en un DatagramPackect
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(cliente);
                DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, address, port);
                socket.send(packectOut);
                //Comprobamos que lo que escribio el usuario no sea un . si lo es cerramos
                if (number.equals(".")) break;
                System.out.println(cliente + " ha enviado el numero " + cliente.getNumero());

                //Creamos el buffer para la recepcion de el paquete de entrada
                byte[] bufferIn = new byte[1024];
                DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.receive(packectIn);
                //Y deserializamos el objecto que nos llega
                ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Integer numero = (Integer) ois.readObject();
                System.out.println(cliente + " ha recibido el cuadrado del doble de su numero y es: " + numero);
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
