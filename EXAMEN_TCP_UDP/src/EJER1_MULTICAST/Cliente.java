package EJER1_MULTICAST;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente implements Runnable, Serializable {
    private Integer id;
    private String mensaje;
    private String ip;
    private Integer port;

    public Cliente(Integer id, String mensaje, String ip, Integer port) {
        this.id = id;
        this.mensaje = mensaje;
        this.ip = ip;
        this.port = port;
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

    @Override
    public void run() {
        // Creamos el sockect por el cual le enviaremos informacion al servidor
        try (DatagramSocket socket = new DatagramSocket()){
            //Obtenemos la addres
            InetAddress address = InetAddress.getByName(ip);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            //Serializamos el objeto cliente y seguidamente lo enviamos en su correspondiente datagramPackect
            oos.writeObject(this);
            DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,address,port);
            socket.send(packetOut);
            System.out.println(this + " ha enviado el mensaje: " + this.getMensaje());

            //Creamos el buffer de entrada por el que recibiremos el cuadrado del doble
            byte[] bufferIn = new byte[1024];
            DatagramPacket packetIn = new DatagramPacket(bufferIn,bufferIn.length);
            socket.receive(packetIn);
            ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            // Aqui lo que nos llega del servidor lo transformamos en String y lo mostramos al usuario
            String respuesta = (String) ois.readObject();
            System.out.println(this + " ha recibido "  + respuesta);

        } catch (SocketException e) {
            System.exit(0);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String ip = "239.0.0.1";
        Integer port = 65535;
        Scanner scanner = new Scanner(System.in);
        String mensaje = "";

        int i = 1;
        do {
            mensaje = scanner.next();
            new Thread(new Cliente(i,mensaje,ip,port)).start();
            scanner.nextLine();
            i++;
        }while (i<4);
    }
}

