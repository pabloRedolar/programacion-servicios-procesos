package LISTA_UDP;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cliente implements Serializable{
    private Integer id;
    private List<Integer> numeros;

    public Cliente() {
    }

    public Cliente(List<Integer> numeros, Integer id) {
        this.numeros = numeros;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<Integer> numeros) {
        this.numeros = numeros;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer port = 65535;
        String host = "127.0.0.1";
        List<Integer> numeros = new ArrayList<>();
        Integer numero = 0;
        for (int c = 0; c < 5; c++) {
            for (int i = 0; i < 5; i++) {
                while (true) {
                    try {
                        System.out.println("Escribe el número " + (i + 1) + ":");
                        numero = sc.nextInt();

                        if (numero > 0) {
                            numeros.add(numero);
                            break;
                        } else {
                            System.out.println("El número debe ser positivo.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Debes ingresar un número entero válido.");
                        sc.nextLine();
                    }
                }
            }
            try (DatagramSocket socket = new DatagramSocket()) {
                Cliente cliente = new Cliente(numeros,c+1);
                InetAddress address = InetAddress.getByName(host);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(cliente);
                DatagramPacket packectOut = new DatagramPacket(baos.toByteArray(),baos.toByteArray().length,address,port);
                socket.send(packectOut);
                System.out.println(cliente + " ha enviado " + cliente.getNumeros());

                byte[] bufferIn = new byte[1024];
                DatagramPacket packectIn = new DatagramPacket(bufferIn, bufferIn.length);
                socket.setSoTimeout(5000);
                socket.receive(packectIn);
                ByteArrayInputStream bais = new ByteArrayInputStream(packectIn.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                List<Integer> respuesta = (List<Integer>) ois.readObject();
                System.out.println(cliente + " ha recibido " + respuesta);
                numeros.clear();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        sc.close();
    }
}
