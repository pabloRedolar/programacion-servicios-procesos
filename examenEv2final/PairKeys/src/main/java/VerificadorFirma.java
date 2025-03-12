import java.security.*;
import java.io.*;

public class VerificadorFirma {
    public static void main(String[] args) {
        try {
            // Nombre de los archivos
            String inputFile = "FICHERO.DAT";
            String publicKeyFile = "Clave.publica";
            String signatureFile = "FICHERO.FIRMA";

            // Leer la clave pública
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(publicKeyFile));
            PublicKey publicKey = (PublicKey) ois.readObject();
            ois.close();

            // Leer la firma
            FileInputStream signatureIn = new FileInputStream(signatureFile);
            byte[] signatureBytes = signatureIn.readAllBytes();
            signatureIn.close();

            // Inicializar el verificador
            Signature verificador = Signature.getInstance("SHA256withDSA");
            verificador.initVerify(publicKey);

            // Leer el archivo original
            FileInputStream dataIn = new FileInputStream(inputFile);
            BufferedInputStream bufin = new BufferedInputStream(dataIn);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) >= 0) {
                verificador.update(buffer, 0, len);
            }
            bufin.close();

            // Verificar la firma
            boolean verificada = verificador.verify(signatureBytes);

            if (verificada) {
                System.out.println("LA FIRMA ES VÁLIDA");
            } else {
                System.out.println("LA FIRMA NO ES VÁLIDA");
            }

        } catch (Exception e) {
            System.err.println("Error durante la verificación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}