package Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private static final String URL = "localhost";
    private static final int PORT = 5000;

    private static Socket socket;

    public static void startClient() {
        try {
            socket = new Socket(URL, PORT);

            ClientListener clientListener = new ClientListener(socket);
            new Thread(clientListener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message) {
        try {
            OutputStream output = socket.getOutputStream();
            output.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startClient();
    }
}
