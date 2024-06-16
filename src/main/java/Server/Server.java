package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import user.Login;

public class Server {

    private static final int PORT = 5000;
    private List<ClientHandler> clients = new ArrayList<>();

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on PORT: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from IP: " + clientSocket.getInetAddress().getHostAddress());
                String id = Login.jTextField1.getText().toString();

                ClientHandler clientHandler = new ClientHandler(clientSocket,id, this);
                clients.add(clientHandler);
                
                

                new Thread(clientHandler).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessageHistory() {

    }

    public void broadcastMessage(String id, String message, String time) {
        for (ClientHandler client : clients) {
            client.sendMessage("Thông báo từ quản trị viên : " + message + "\n" + time);
        }
    }

    public static void main(String[] args) {
        new Server().startServer();
    }
}
