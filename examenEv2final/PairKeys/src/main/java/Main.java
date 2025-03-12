import java.io.*;
import java.security.*;


public class Main {
    public static void main(String[] args) {
        String ficheroLeer = "FICHERO.DAT";
        String privateKeyFile = "Clave.privada";
        String signatureFile = "FICHERO.FIRMA";

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(privateKeyFile));
             BufferedInputStream bisfis = new BufferedInputStream(new FileInputStream(ficheroLeer));
             FileOutputStream signature = new FileOutputStream(signatureFile)){

            PrivateKey privateKey = (PrivateKey) ois.readObject();
            Signature dsa = Signature.getInstance("SHA256withDSA");
            dsa.initSign(privateKey);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = bisfis.read(buffer)) >=0){
                dsa.update(buffer, 0, len);
            }
            byte[] realSig = dsa.sign();
            signature.write(realSig);

            System.out.println("La firma se ha generado y guardado en " + signatureFile);

        } catch (IOException | SignatureException | NoSuchAlgorithmException | InvalidKeyException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
