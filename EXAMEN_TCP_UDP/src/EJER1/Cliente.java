package EJER1;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente implements Serializable {
    private Integer id;
    private String mensaje;

    public Cliente(Integer id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    public Cliente() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String host = "127.0.0.1";
        String mensaje = "Hola, buenas tardes";

        //Hacemos el for para imitar a 5 clientes
        for (int i = 0; i < 5; i++) {
            //Creamos la conexion con el servidor
            try (Socket socket = new Socket(host, port)) {
                Cliente cliente = new Cliente(i+1,mensaje);
                //Creamos el output para que los clientes puedan enviar informacion
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //Escribimos el objeto cliente
                oos.writeObject(cliente);
                System.out.println(cliente + " ha enviado el mensaje: " + cliente.getMensaje());

                //Creamos el input para que el servidor nos pueda enviar informacion
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String respuesta = (String) ois.readObject();
                System.out.println(cliente + " ha recibido: " + respuesta);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}