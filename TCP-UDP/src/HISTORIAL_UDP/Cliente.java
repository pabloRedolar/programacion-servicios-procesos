package HISTORIAL_UDP;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class Cliente implements Runnable, Serializable {
    private Integer id;
    private String mensaje;
    private Integer port;
    private String ip;

    public Cliente(Integer id, Integer port, String ip) {
        this.id = id;
        this.port = port;
        this.ip = ip;
    }

    public Integer getId() {
        return id;
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
        try(DatagramSocket sockect = new DatagramSocket()){
            InetAddress ipAddress = InetAddress.getByName(ip);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                out.println("Escribe un mensaje para enviar al servidor (Si escribes historial te devolvemos tus mensajes anteriores)");
                this.setMensaje(scanner.nextLine());

                if (this.getMensaje().equalsIgnoreCase("exit")){
                    out.println("Cerrando cliente...");
                    break;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(this);
                DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, ipAddress, port);
                sockect.send(packectOut);
                out.println(this + " ha enviado el mensaje: " + this.mensaje);

                byte[] bufferIn = new byte[1024];
                DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                sockect.receive(packetIn);
                ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Object obj = ois.readObject();
                if (obj instanceof String) {
                    String respuesta = (String) obj;
                    out.println(this + " ha recibido el mensaje: " + respuesta);
                } else if (obj instanceof List<?>) {
                    List<String> historial = (List<String>) obj;
                    out.println(this + " ha recibido su historial: " + historial);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String ip = "127.0.0.1";
        new Thread(new Cliente(1,port,ip)).start();
    }
}
