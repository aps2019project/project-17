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
            SocketDetail socketDetail = new SocketDetail(socket);
            ReaderWriter readerWriter = new ReaderWriter(socketDetail);
            String s = "create account a b\n";
            Message message = new Message(s);
            readerWriter.getWriter().write(message);
            readerWriter.getReader().run();
            while (true){}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
