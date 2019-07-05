import java.io.*;
import java.net.Socket;

public class FakeClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", ConnectionDataBaseDetail.PORT);
            SocketDetail socketDetail = new SocketDetail(socket);
            ReaderWriter readerWriter = new ReaderWriter(socketDetail);
            String s = "login a b";
            Message message = new Message(s);
            readerWriter.getWriter().write(message);
            readerWriter.getReader().run();
            while (true){}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
