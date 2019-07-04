package duelyst.Server;

public class ReaderWriter {
    private Reader reader;
    private Writer writer;
    private SocketDetail socketDetail;

    public ReaderWriter(SocketDetail socketDetail) {
        this.reader = new Reader(socketDetail.socket);
        this.writer = new Writer(socketDetail.socket);
        this.socketDetail = socketDetail;
    }

    public Reader getReader() {
        return reader;
    }

    public SocketDetail getSocketDetail() {
        return socketDetail;
    }

    public Writer getWriter() {
        return writer;
    }
}
