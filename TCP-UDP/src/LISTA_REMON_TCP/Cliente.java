package LISTA_REMON_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cliente implements Serializable {
    private Integer id;
    private Integer numero;

    public Cliente() {
    }

    public Cliente(Integer id, Integer numero) {
        this.id = id;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int port = 65535;
        String ip = "127.0.0.1";
        int numero = 0;
        for (int i = 0; i < 5; i++) {
            do {
                try {
                    System.out.println("Escribe un numero y te devolvere una lista desde tu numero al 0: ");
                    numero = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Escribe un numero entero y mayor a 0");
                    sc.nextLine();
                }

            } while (numero <= 0);
            try (Socket socket = new Socket(ip, port)) {
                Cliente cliente = new Cliente(i+1, numero);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(cliente);
                System.out.println(cliente + " ha enviado el numero " + cliente.getNumero());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                List<Integer> respuesta = (List<Integer>) ois.readObject();
                System.out.println(cliente + " ha recibido " + respuesta);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        sc.close();
    }

}
