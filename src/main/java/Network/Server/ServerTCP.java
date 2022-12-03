package Network.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) {
        String host = "192.168.220.192";
        int port = 7777;
        DeliveryServer deliveryServer = new DeliveryServer(host, port);

        deliveryServer.run();
    }
}
