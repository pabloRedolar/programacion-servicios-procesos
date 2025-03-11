package AREA_UDP;

import java.awt.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.out;

public class Cliente extends Thread implements Serializable {
    private Integer id;
    private Integer ancho;
    private Integer alto;
    private Integer port;
    private String ip;

    public Cliente(Integer id, Integer ancho, Integer alto, Integer port, String ip) {
        this.id = id;
        this.ancho = ancho;
        this.alto = alto;
        this.port = port;
        this.ip = ip;
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

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()){
            InetAddress ipAddress = InetAddress.getByName(ip);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            socket.send(new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,ipAddress,port));
            out.println(this + " ha enviado un rectangulo con una altura de " + this.getAlto() + " cm y un ancho de " + this.getAncho() + "cm");

            byte[] bufferIn = new byte[1024];
            DatagramPacket packectIn = new DatagramPacket(bufferIn,bufferIn.length);
            socket.receive(packectIn);

            ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Integer area = (Integer) ois.readObject();
            out.println(this +  " el area del rectangulo que mandaste son: " + area + " centimetros cuadrados");
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String ip = "127.0.0.1";
        Scanner sc = new Scanner(System.in);
        int ancho = 0, alto = 0;
        do {
            try {
                out.println("Escribe el primer numero entero");
                ancho = sc.nextInt();
                out.println("Escribe el segundo numero entero");
                alto = sc.nextInt();
            }catch (InputMismatchException e){
                out.println("Los numeros deben ser enteros.");
                sc.nextLine();
            }
        }while (ancho <= 0 || alto <=0);

        new Cliente(1,ancho,alto,port,ip).start();
    }
}
