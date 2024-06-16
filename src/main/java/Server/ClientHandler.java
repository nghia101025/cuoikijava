package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable {

    private Socket mySocket;
    private Server server;
    private String id;
    private InputStream input;
    private OutputStream output;

    public ClientHandler(Socket mySocket,String id, Server server) {
        this.mySocket = mySocket;
        this.server = server;
        this.id = id;
        try {
            this.input = mySocket.getInputStream();
            this.output = mySocket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            output.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = input.read(buffer)) != -1) {
                String message = new String(buffer, 0, byteRead);
                
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                server.broadcastMessage("Thông báo từ quản trị viên", message,localTime.format(formatter).toString());
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
