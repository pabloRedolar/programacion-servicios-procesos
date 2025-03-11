package FTP;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.net.SocketException;

public class ListFiles {
    public static void main(String[] args) {
        String server = "ftp.rediris.es";
        int port = 21;
        String user = "anonymous";
        String pass = "anonymous";

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server,port);
            ftpClient.login(user,pass);
            ftpClient.enterLocalPassiveMode();
            printFiles("/", ftpClient);

            ftpClient.logout();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void printFiles(String path, FTPClient ftpClient) throws IOException {
        FTPFile[] files = ftpClient.listFiles(path);
        System.out.println("Directorio: " + path);
        for (FTPFile file : files){
            if (file.isDirectory()){
                System.out.println("[DIR] " + file.getName());
                printFiles(path + file.getName() , ftpClient);
            }else {
                System.out.println("\t [FILE] " + file.getName());
            }
        }
    }
}
