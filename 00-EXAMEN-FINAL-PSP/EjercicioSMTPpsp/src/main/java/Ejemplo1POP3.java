import java.io.IOException;

import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;

public class Ejemplo1POP3 {
    public static void main(String[] args) {
        String server = "localhost";
        String username = "usu1";
        String password = "usu1";
        int puerto = 110;

        POP3SClient pop3 = new POP3SClient();
        try {
            //nos conectamos al servidor
            pop3.connect(server, puerto);
            System.out.println("Conexión realizada al servidor POP3 " + server);
            if (!pop3.login(username, password)){
                System.err.println("Error al logear");
            } else {
                POP3MessageInfo[] men = pop3.listMessages();
                if(men == null) {
                    System.out.println("Imposible recuperar los mensajes");
                } else {
                    System.out.println("Nº de mensajes: " + men.length);
                    RecuperarMensajes(men, pop3);
                }

                pop3.logout();
            }

            pop3.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }

    private static void RecuperarMensajes (POP3MessageInfo[] men,  POP3SClient pop3) throws IOException {
        for (int i = 0; i < men.length; i++) {
            System.out.println("Mensaje " + (i+1));
            POP3MessageInfo msginfo = men[i];
            System.out.println("Identificador: " + msginfo.identifier + (", Numero: " + msginfo.number + (", Tamaño: " + msginfo.size)));

            System.out.println("Prueba de listUniqueIdentifier: ");
            POP3MessageInfo pmi = pop3.listUniqueIdentifier(i+1);
            System.out.println("\tIdentificador: " + pmi.identifier + ", Numero: " + pmi.number + ", Tamaño: " + pmi.size);
        }
    }
}
