package Server;

import java.io.*;
import java.net.Socket;

public class SocketDetail {
    public Socket socket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;

    public SocketDetail(Socket socket) {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
