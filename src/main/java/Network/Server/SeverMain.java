package Network.Server;

public class SeverMain {
    public static void main(String[] args) {
        String host = "192.168.0.26";
        int port = 7777;

        DeliveryServer deliveryServer = new DeliveryServer(host, port);

    }
}
