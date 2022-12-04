package Network.User;

import java.io.*;
import java.net.*;

public class UserMain {
    public static void main(String args[]) {
        Socket cliSocket = null;
        String host = "127.0.0.1";

        try {
                cliSocket = new Socket(host, 7777);
                UserAPP app = new UserAPP(cliSocket);
                app.run();
        }
        catch (UnknownHostException e) {
            System.err.println("Network.Server not found");
        }
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                cliSocket.close();
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
