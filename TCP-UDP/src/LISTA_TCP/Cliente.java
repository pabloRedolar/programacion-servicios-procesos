package LISTA_TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Cliente implements Serializable {
    private Integer id;
    private List<Integer> numerines;

    public Cliente() {
    }

    public Cliente(Integer id, List<Integer> numerines) {
        this.id = id;
        this.numerines = numerines;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getNumerines() {
        return numerines;
    }

    public void setNumerines(List<Integer> numerines) {
        this.numerines = numerines;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String host = "localhost";
        Scanner sc = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<Integer>();
        Integer number = 0;
        for (int i = 0; i < 5; i++) {
            while (true){
                try{
                    System.out.println("Escribe el numero " + (i+1) + ": ");
                    number = sc.nextInt();
                    numbers.add(number);
                    if (number>0){
                        break;
                    }
                }catch (InputMismatchException ex){
                    System.out.println("Introduce un numero");
                    sc.nextLine();
                }
            }
        }
        try (Socket socket = new Socket(host,port)){
            Cliente cliente = new Cliente(1,numbers);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(cliente);
            System.out.println(cliente + " ha enviado " + cliente.getNumerines());

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            List<Integer> respuesta = (List<Integer>) ois.readObject();
            System.out.println(cliente + " ha recibido " + respuesta);


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
