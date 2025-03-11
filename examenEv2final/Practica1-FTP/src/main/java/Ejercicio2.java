import org.apache.commons.net.ftp.*;
import java.io.*;

public class Ejercicio2 {
    public static void main(String[] args) {
        String server = "127.0.0.1";
        int port = 21;
        String user = "smr2";
        String pass = "jhonvonneumann1945";
        String localFilePath = "src/archivoAsubir.txt";
        String remoteFilePath = "/archivoAsubir.txt";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(localFilePath);
            InputStream inputStream = new FileInputStream(localFile);

            System.out.println("Subiendo " + localFilePath + " al servidor FTP...");
            boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();

            if (done) {
                System.out.println("Archivo subido con éxito.");
            } else {
                System.out.println("Error al subir el archivo.");
            }

            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}