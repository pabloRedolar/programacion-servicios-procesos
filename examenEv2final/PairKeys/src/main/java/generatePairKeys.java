import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class generatePairKeys {
    public static void main(String[] args) {
        try {
            // Generar el par de claves
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(2048, random);
            KeyPair pair = keyGen.generateKeyPair();

            // Guardar la clave privada
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream("Clave.privada"));
            privateKeyOS.writeObject(pair.getPrivate());
            privateKeyOS.close();

            // Guardar la clave pública (opcional, pero útil para verificar)
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                    new FileOutputStream("Clave.publica"));
            publicKeyOS.writeObject(pair.getPublic());
            publicKeyOS.close();

            System.out.println("Las claves se han generado y guardado correctamente");

        } catch (Exception e) {
            System.err.println("Error generando las claves: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
