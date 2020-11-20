package questionseven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static ServerSocket serverSocket = null;
    private static Socket socket = null;
    private static ObjectInputStream inputStream = null;
    private static Payload payload;
    private static File payloadFile = null;
    private static FileOutputStream fileOutputStream = null;

    private static void Connect() throws IOException {
        serverSocket = new ServerSocket(4445);
        socket = serverSocket.accept();
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    private static void Download() throws IOException, ClassNotFoundException {
        System.out.println("Downloading file...");
        payload = (Payload) inputStream.readObject();
        System.out.println("Downloaded file!");

        System.out.println("Where would you like to save the file? (insert file path)");
        String destination = getUserInput();

        byte[] payloadData = payload.getData();

        System.out.println("Writing file to " + destination);
        Files.write(Paths.get(destination), payloadData);
        System.out.println("Successfully wrote file");

    }

    public static void main(String[] args) {
        try {
            Connect();
        } catch (Exception e) {
            System.out.println("Failed to connect, closing server");
            return;
        }

        try {
            Download();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getUserInput() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }
}
