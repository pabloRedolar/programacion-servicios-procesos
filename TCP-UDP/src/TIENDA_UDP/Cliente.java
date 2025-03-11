package TIENDA_UDP;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

import static java.lang.System.exit;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

public class Cliente implements Runnable, Serializable {
    private static final Semaphore semaphore = new Semaphore(1);
    private final Integer id;
    private final Integer port;
    private final String ip;
    private Integer intentos;

    public Cliente(Integer id, Integer intentos, Integer port, String ip) {
        this.id = id;
        this.intentos = intentos;
        this.port = port;
        this.ip = ip;
    }

    public static void main(String[] args) {
        Integer port = 65535;
        String ip = "127.0.0.1";
        Integer intentos = 10;
        Integer numeroDeClientes = 5;

        for (int i = 0; i < numeroDeClientes; i++) {
            new Thread(new Cliente(i + 1, intentos, port, ip)).start();
        }
    }

    @Override
    public String toString() {
        return "Cliente " + "id=" + id;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            while (this.intentos != 0) {
                if (Math.random() <= 0.25) {
                    try (DatagramSocket socket = new DatagramSocket()) {
                        InetAddress address = InetAddress.getByName(ip);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(this);
                        DatagramPacket packetOut = new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, address, port);
                        socket.send(packetOut);

                        byte[] bufferIn = new byte[1024];
                        DatagramPacket packetIn = new DatagramPacket(bufferIn, bufferIn.length);
                        socket.receive(packetIn);
                        ByteArrayInputStream bais = new ByteArrayInputStream(packetIn.getData());
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        Boolean respuesta = (Boolean) ois.readObject();
                        if (respuesta) {
                            out.println(this + " ha conseguido entrar a la tienda y ha podido comprar");
                            break;
                        } else {
                            out.println(this + " ha conseguido entrar a la tienda pero no ha podido comprar porque no habia productos disponibles");
                        }

                    } catch (SocketException e) {
                        out.println("Error de conexion con la Tienda");
                        exit(0);
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    out.println(this + " no tuvo suerte. Intentos restantes: " + this.intentos);
                }
                this.intentos--;
                sleep(1500);
            }
            if (this.intentos == 0) {
                out.println(this + " se ha quedado sin intentos");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }
}
