package Network.Server;

public class SeverMain {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 7777;
        new DeliveryServer(host, port);
    }
}
