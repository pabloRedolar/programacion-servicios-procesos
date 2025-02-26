package FTP;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SubirFicheroFTP {

    public static void main(String[] args) {
        String server = "ftp.rediris.es";
        int port = 21;
        String user = "anonymous";
        String pass = "anonymous";

        String localFilePath = "src\\hola.txt";
        String remoteFilePath = "archivo_remoto.txt";

        FTPClient ftpClient = new FTPClient();
        try (InputStream inputStream = new FileInputStream(new File(localFilePath))) {

            ftpClient.connect(server, port);

            if (!ftpClient.login(user, pass)) {
                System.err.println("Error en autenticación.");
                return;
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("Subiendo archivo...");

            boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);
            if (uploaded) {
                System.out.println("¡Archivo subido exitosamente!");
            } else {
                System.err.println("Error al subir el archivo.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}