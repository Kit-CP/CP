package Network.Server;

public class SeverMain {
    public static void main(String[] args) {
        String host = "192.168.0.34";
        int port = 7777;
        new DeliveryServer(host, port);
    }
}
