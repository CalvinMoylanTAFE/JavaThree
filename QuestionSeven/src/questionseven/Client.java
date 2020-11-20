package questionseven;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {

    private static Socket socket = null;
    private static ObjectOutputStream outputStream = null;
    private static boolean connected = false;

    public static void Connect() {
        while (!connected) {
            try {
                socket = new Socket("localHost", 4445);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                connected = true;
                System.out.println("Connected to server");
            } catch (Exception e) {
                System.out.println("Could not connect...");
            }
        }
    }

    public static void Send(Payload payload) throws IOException, InterruptedException {
        System.out.println("Sending file...");
        outputStream.writeObject(payload);
        System.out.println("Sent file!");
        TimeUnit.SECONDS.sleep(5);
    }

    public static void main(String[] args) {
        System.out.println("Input file path for the file you want to send:");
        String filePath = getUserInput();

        Payload payload = new Payload(filePath);

        Connect();

        try {
            Send(payload);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static String getUserInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
