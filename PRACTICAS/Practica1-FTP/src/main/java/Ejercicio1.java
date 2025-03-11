import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Ejercicio1 {
    public static void main(String[] args) {
        String server = "ftp.rediris.es";
        int port = 21;
        String user = "anonymous";
        String pass = "anonymous";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            System.out.println("Conectado a " + server);

            listDirectory(ftpClient, "/");

            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void listDirectory(FTPClient ftpClient, String path) throws Exception {
        FTPFile[] files = ftpClient.listFiles(path);
        System.out.println("Contenido de " + path + ":");

        for (FTPFile file : files) {
            System.out.println((file.isDirectory() ? "[DIR] " : "\t[FILE] ") + file.getName());

            if (file.isDirectory()) {
                listDirectory(ftpClient, path + file.getName());
            } else {
                System.out.println(file.getName());
            }
        }
    }
}