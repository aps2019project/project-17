package duelyst.Server;

import java.io.*;
import java.net.Socket;

public class FakeClient {
    public static class Message implements Serializable {
        Object object;

        public Message(Object object) {
            this.object = object;
        }

        @Override
        public boolean equals(Object obj) {
            return obj.toString().equals(this.object.toString());
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", ConnectionDataBaseDetail.PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            ObjectOutputStream dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
            String s = "create account a b\n";
            Message message = new Message(s);
            dataOutputStream.writeObject(message);
            dataOutputStream.flush();
            while (true) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
